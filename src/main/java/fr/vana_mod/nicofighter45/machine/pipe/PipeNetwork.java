package fr.vana_mod.nicofighter45.machine.pipe;

import fr.vana_mod.nicofighter45.machine.basic.block.AbstractMachineBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PipeNetwork {

    public Map<Integer, BlockPos> pipes = new HashMap<>();
    //todo check for network input and output isn't create
    public List<BlockPos> inputs = new ArrayList<>();
    public List<BlockPos> outputs = new ArrayList<>();
    public int fluidAmount;
    //todo only compatible with water for now
    public Fluid fluid;
    private final World world;

    private PipeNetwork(@NotNull World world){
        this.world = world;
        this.fluidAmount = 0;
        this.fluid = Fluids.WATER;
    }

    public PipeNetwork(World world, List<BlockPos> pipes){
        this(world);
        this.inputs = pipes;
    }

    public PipeNetwork(@NotNull PipeBlockEntity blockEntity){
        this.world = blockEntity.getWorld();
        assert this.world != null;
        checkBlock(blockEntity, 1, 0, 0);
        checkBlock(blockEntity, -1, 0, 0);
        checkBlock(blockEntity, 0, 1, 0);
        checkBlock(blockEntity, 0, -1, 0);
        checkBlock(blockEntity, 0, 0, 1);
        checkBlock(blockEntity, 0, 0, -1);
        if(pipes.size() == 1) {
            PipeNetwork newNetwork = ((PipeBlockEntity) Objects.requireNonNull(this.world
                    .getBlockEntity(pipes.get(0)))).network;
            newNetwork.outputs.addAll(this.outputs);
            newNetwork.addPipe(blockEntity);
            return;
        }
        blockEntity.network = this;
        this.pipes.put(0, blockEntity.getPos());
        this.fluidAmount = 0;
        this.fluid = Fluids.WATER;
        this.inputs.clear();
        this.inputs.add(0, blockEntity.getPos());
    }

    public void addLastPipe(BlockPos pipe){
        this.pipes.put(this.pipes.size(), pipe);
    }

    public void addFirstPipe(BlockPos pipe) {
        Map<Integer, BlockPos> pipes = new HashMap<>();
        pipes.put(0, pipe);
        int i = 0;
        for(BlockPos pip : this.pipes.values()){
            pipes.put(i + 1, pip);
            i++;
        }
        this.pipes = pipes;
    }

    public void removePipe(BlockPos pos) {
        boolean replace = false;
        for(int i : this.pipes.keySet()){
            if(pipes.get(i) == pos){
                pipes.remove(i, pos);
                replace = true;
            }else if(replace){
                pipes.put(i - 1, pipes.get(i));
            }
        }
    }

    private void checkBlock(@NotNull PipeBlockEntity blockEntity, int x, int y, int z){
        BlockPos pos = blockEntity.getPos().add(x, y, z);
        BlockEntity bl = world.getBlockEntity(pos);
        if(bl == null){
            return;
        }
        if(world.getBlockEntity(pos) instanceof PipeBlockEntity){
            addLastPipe(pos);
        }else if(world.getBlockEntity(pos) instanceof AbstractMachineBlockEntity){
            this.outputs.add(pos);
        }
    }
    public void removePipe(@NotNull PipeBlockEntity blockEntity){
        List<BlockPos> clothPipes = getClothPipes(blockEntity.getPos());
        if(clothPipes.size() > 1){
            checkForNetworkSeparation(clothPipes);
        }else if(clothPipes.size() == 1){
            ((PipeBlockEntity) Objects.requireNonNull(world.getBlockEntity(clothPipes.get(0)))).network.removePipe(blockEntity.getPos());
        }
    }

    private void checkForNetworkSeparation(@NotNull List<BlockPos> clothPipes){
        PipeNetwork newNetwork = new PipeNetwork(this.world);
        newNetwork.inputs.addAll(getNewPipesBlock(clothPipes.get(0)));
        if(newNetwork.inputs.contains(clothPipes.get(1))){
            newNetwork.fluidAmount = fluidAmount;
        }else{
            PipeNetwork newNetwork2 = new PipeNetwork(this.world);
            newNetwork2.inputs.addAll(getNewPipesBlock(clothPipes.get(1)));
            newNetwork.fluidAmount = fluidAmount/2;
            newNetwork2.fluidAmount = fluidAmount - newNetwork.fluidAmount;
            for(BlockPos pipes : newNetwork2.pipes.values()){
                ((PipeBlockEntity) Objects.requireNonNull(world.getBlockEntity(pipes))).network = newNetwork2;
            }
        }
        for(BlockPos pipes : newNetwork.pipes.values()){
            ((PipeBlockEntity) Objects.requireNonNull(world.getBlockEntity(pipes))).network = newNetwork;
        }
    }

    private @NotNull List<BlockPos> getNewPipesBlock(BlockPos pos){
        List<BlockPos> pipes = new ArrayList<>();
        for(BlockPos newPos : getClothPipes(pos)){
            if(!pipes.contains(newPos)){
                pipes.add(newPos);
                for(BlockPos newNewPos : getNewPipesBlock(newPos)){
                    if(!pipes.contains(newPos)){
                        pipes.add(newNewPos);
                    }
                }
            }
        }
        return pipes;
    }

    private void addPipe(@NotNull PipeBlockEntity blockEntity) {
        blockEntity.network = this;
        BlockPos newPipe = blockEntity.getPos();
        List<BlockPos> clothPipes = getClothPipes(newPipe);
        if(this.pipes.get(0).equals(clothPipes.get(0))){
            Map<Integer, BlockPos> newPipes = new HashMap<>();
            newPipes.put(0, newPipe);
            for(int i : this.pipes.keySet()){
                newPipes.put(i + 1, this.pipes.get(i));
            }
            this.pipes = newPipes;
        }else{
            addLastPipe(newPipe);
        }
        if(clothPipes.size() == 1){
            setSimpleTextureConfiguration(blockEntity, clothPipes.get(0));
            setNextPipesTextures(blockEntity, getClothPipes(clothPipes.get(0)));
        }else{
            System.out.println("More than 1 pipe detected on blockPos : " + blockEntity.getPos());
        }
    }

    private void setNextPipesTextures(@NotNull PipeBlockEntity blockEntity, @NotNull List<BlockPos> clothPipes){
        if(clothPipes.size() == 1){
            setSimpleTextureConfiguration((PipeBlockEntity) Objects.requireNonNull(world
                    .getBlockEntity(clothPipes.get(0))), clothPipes.get(0));
        }else{
            setComplexTextureConfiguration((PipeBlockEntity) Objects.requireNonNull(world
                    .getBlockEntity(clothPipes.get(0))), clothPipes.get(0), clothPipes.get(1));
        }
    }

    private void setSimpleTextureConfiguration(@NotNull PipeBlockEntity blockEntity, BlockPos blockPos) {
        Direction face = getFacing(blockEntity.getPos(), blockPos);
        switch (face) {
            case UP, DOWN -> blockEntity.setTextureConfiguration(1);
            case NORTH, SOUTH -> blockEntity.setTextureConfiguration(2);
            case EAST, WEST -> blockEntity.setTextureConfiguration(3);
        }
    }

    private void setComplexTextureConfiguration(@NotNull PipeBlockEntity blockEntity, BlockPos blockPos1, BlockPos blockPos2) {
        Direction face1 = getFacing(blockEntity.getPos(), blockPos1);
        Direction face2 = getFacing(blockEntity.getPos(), blockPos2);
        int config = 0;
        if(face1 == Direction.UP || face2 == Direction.UP){
            config = 4;
        }else if(face1 == Direction.DOWN || face2 == Direction.DOWN){
            config = 8;
        }
        if(config != 0){
            if(face1 == Direction.NORTH || face2 == Direction.NORTH){
                blockEntity.setTextureConfiguration(config);
            }else if(face1 == Direction.EAST || face2 == Direction.EAST){
                blockEntity.setTextureConfiguration(config + 1);
            }else if(face1 == Direction.SOUTH || face2 == Direction.SOUTH){
                blockEntity.setTextureConfiguration(config + 2);
            }else if(face1 == Direction.WEST || face2 == Direction.WEST){
                blockEntity.setTextureConfiguration(config + 3);
            }
        }else if(face1 == Direction.NORTH || face2 == Direction.NORTH){
            if(face1 == Direction.EAST || face2 == Direction.EAST){
                blockEntity.setTextureConfiguration(12);
            }else if(face1 == Direction.WEST || face2 == Direction.WEST){
                blockEntity.setTextureConfiguration(13);
            }
        }else if(face1 == Direction.SOUTH || face2 == Direction.SOUTH){
            if(face1 == Direction.EAST || face2 == Direction.EAST){
                blockEntity.setTextureConfiguration(14);
            }else if(face1 == Direction.WEST || face2 == Direction.WEST){
                blockEntity.setTextureConfiguration(15);
            }
        }else{
            System.out.println("Issue with configuration finding for complex patern here is facing and blockPos\n" + face1 + face2 + blockEntity.getPos());
        }
    }

    private Direction getFacing(@NotNull BlockPos firstBlock, @NotNull BlockPos secondBlock){
        if(firstBlock.getY() == secondBlock.getY() - 1){
            return Direction.UP;
        }else if(firstBlock.getY() == secondBlock.getY() + 1){
            return Direction.DOWN;
        }else if(firstBlock.getZ() == secondBlock.getX() - 1){
            return Direction.SOUTH;
        }else if(firstBlock.getZ() == secondBlock.getX() + 1){
            return Direction.NORTH;
        }else if(firstBlock.getX() == secondBlock.getX() - 1){
            return Direction.EAST;
        }else if(firstBlock.getX() == secondBlock.getX() + 1){
            return Direction.WEST;
        }
        return Direction.NORTH;
    }

    private @NotNull List<BlockPos> getClothPipes(BlockPos newPipe){
        List<BlockPos> pipes = new ArrayList<>();
        for(BlockPos pipe : this.pipes.values()){
            if(pipe == newPipe){
                continue;
            }
            int x = newPipe.getX() - pipe.getX();
            int y = newPipe.getY() - pipe.getY();
            int z = newPipe.getZ() - pipe.getZ();
            if(x <= 1 && x >= -1 && y <= 1 && y >= -1 && z <= 1 && z >= -1){
                pipes.add(pipe);
            }
        }
        return pipes;
    }

}