package fr.vana_mod.nicofighter45.machine.pipe;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PipeNetwork {

    private final World world;
    public List<BlockPos> pipes = new ArrayList<>();

    public List<BlockPos> inputs = new ArrayList<>(); //todo check for network input and output isn't create
    public List<BlockPos> outputs = new ArrayList<>();
    public int fluidAmount;
    public Fluid fluid; //todo only compatible with water for now
    public boolean isActive = true;

    private PipeNetwork(@NotNull World world) {
        this.world = world;
        this.fluidAmount = 0;
        this.fluid = Fluids.WATER;
    }

    public PipeNetwork(World world, List<BlockPos> pipes) {
        this(world);
        this.inputs = pipes;
    }

    public PipeNetwork(@NotNull PipeBlockEntity blockEntity, @NotNull World world) {
        this.world = world;
        List<BlockPos> clothPipes = getNearByPipes(blockEntity.getPos());
        if (!clothPipes.isEmpty()) {
            PipeNetwork newNetwork = ((PipeBlockEntity) Objects.requireNonNull(this.world
                    .getBlockEntity(clothPipes.get(0)))).network;
            blockEntity.network = newNetwork;
            newNetwork.addPipe(blockEntity, clothPipes);
            this.deactivate();
            return;
        }
        blockEntity.network = this;
        this.pipes.add(blockEntity.getPos());
        this.fluidAmount = 0;
        this.fluid = Fluids.WATER;
        this.inputs.clear();
    }

    private void deactivate(){
        this.pipes.clear();
        this.inputs.clear();
        this.outputs.clear();
        this.fluidAmount = 0;
        this.isActive = false;
    }

    private @NotNull List<BlockPos> getNearByPipes(@NotNull BlockPos centerBlockPosition){
        Vec3i[] vectors = {
                new Vec3i(-1, 0, 0), new Vec3i(1, 0, 0),
                new Vec3i(0, -1, 0), new Vec3i(0, 1, 0),
                new Vec3i(0, 0, -1), new Vec3i(0, 0, 1)
        };
        List<BlockPos> pipes = new ArrayList<>();
        for (Vec3i vector : vectors){
            BlockPos position = centerBlockPosition.add(vector);
            if(world.getBlockEntity(position) instanceof PipeBlockEntity){
                pipes.add(position);
            }
        }
        return pipes;
    }

    private void addPipe(@NotNull PipeBlockEntity blockEntity, @NotNull List<BlockPos> pipes) {
        this.pipes.add(blockEntity.getPos());
        for(BlockPos pipe : pipes){
            if (!this.pipes.contains(pipe)){
                this.pipes.add(blockEntity.getPos()); // todo delete this line and fusion network
                System.out.println("Network must be fusion");
                return;
            }
        }
        actualizeTexture(blockEntity, pipes);
        for(BlockPos pipe : pipes){
            actualizeTexture(((PipeBlockEntity) Objects.requireNonNull(world.getBlockEntity(pipe))));
        }
    }

    public void removePipe(@NotNull BlockPos centerBlock) {
        List<BlockPos> clothPipes = getNearByPipes(centerBlock);
        if (clothPipes.size() > 1) {
            checkForNetworkSeparation(centerBlock, clothPipes);
        } else if (clothPipes.size() == 1) {
            if (world.getBlockEntity(clothPipes.get(0)) instanceof PipeBlockEntity pipeBlockEntity){
                PipeNetwork network = pipeBlockEntity.network;
                network.pipes.remove(pipeBlockEntity.getPos());
                pipeBlockEntity.setTextureConfiguration(0);
            }else{
                System.out.println("Error detected in removePipe");
            }
        }else{
            deactivate();
        }
    }

    private void checkForNetworkSeparation(@NotNull BlockPos centerBlock, @NotNull List<BlockPos> clothPipes) { // todo not working
        System.out.println("checkForNetworkSeparation :)");
        for(BlockPos firstBlockOfNetwork : clothPipes){
            if(world.getBlockEntity(firstBlockOfNetwork) instanceof PipeBlockEntity mainPipeBlockEntity){
                if(mainPipeBlockEntity.network != this){
                    continue;
                }
                PipeNetwork newNetwork = new PipeNetwork(this.world);
                newNetwork.fluidAmount = this.fluidAmount/clothPipes.size();
                newNetwork.pipes = getNewPipesBlock(firstBlockOfNetwork, clothPipes, centerBlock);
                for(BlockPos position : newNetwork.pipes){
                    if(world.getBlockEntity(position) instanceof PipeBlockEntity pipeBlockEntity){
                        if(pipeBlockEntity.getPos() == centerBlock){
                            continue;
                        }
                        pipeBlockEntity.network = newNetwork;
                        newNetwork.actualizeTexture(pipeBlockEntity);
                    }else{
                        System.out.println("Error in checkForNetworkSeparation (2)");
                    }
                }
            }else{
                System.out.println("Error in checkForNetworkSeparation (1)");
            }
        }
        deactivate();
    }

    private @NotNull List<BlockPos> getNewPipesBlock(BlockPos pos, @NotNull List<BlockPos> foundPos, BlockPos centerBlock) {
        foundPos.add(centerBlock);
        return getNewPipesBlock(pos, foundPos);
    }
    private @NotNull List<BlockPos> getNewPipesBlock(BlockPos pos, List<BlockPos> foundPos) {
        for (BlockPos newPos : getNearByPipes(pos)) {
            if (!foundPos.contains(newPos)) {
                foundPos.add(newPos);
                foundPos.addAll(getNewPipesBlock(newPos, foundPos));
            }
        }
        return foundPos;
    }

    private void actualizeTexture(@NotNull PipeBlockEntity blockEntity){
        actualizeTexture(blockEntity, getNearByPipes(blockEntity.getPos()));
    }

    private void actualizeTexture(@NotNull PipeBlockEntity blockEntity, @NotNull List<BlockPos> clothPipes){
        int i = 0;
        for(BlockPos pos : clothPipes){
            System.out.println("actualizeTexture position : " + i + "::" + pos + "::" + blockEntity.getPos());
            i++;
        }
        blockEntity.setTextureConfiguration(
                switch(clothPipes.size()){
                    case 0 -> 0;
                    case 1 -> getSimpleTextureConfiguration(getFacing(blockEntity.getPos(), clothPipes.get(0)));
                    case 2 -> getDoubleTextureConfiguration(getFacing(blockEntity.getPos(), clothPipes.get(0)),
                                                            getFacing(blockEntity.getPos(), clothPipes.get(1)));
                    default -> {
                        if (clothPipes.size() <= 6){
                            System.out.println("Setting more than 2 cloth pipes textures"); //todo add 3 to 6 face texture config
                        }else{
                            System.out.println("Error inside clothPipes detection : more than 6 detected");
                        }
                        yield 0;
                    }
                }
        );
    }

    private Direction getFacing(@NotNull BlockPos firstBlock, @NotNull BlockPos secondBlock) { // todo face not working
        if (firstBlock.getY() == secondBlock.getY() - 1) {
            return Direction.UP;
        } else if (firstBlock.getY() == secondBlock.getY() + 1) {
            return Direction.DOWN;
        } else if (firstBlock.getZ() == secondBlock.getX() - 1) {
            return Direction.SOUTH;
        } else if (firstBlock.getZ() == secondBlock.getX() + 1) {
            return Direction.NORTH;
        } else if (firstBlock.getX() == secondBlock.getX() - 1) {
            return Direction.EAST;
        } else if (firstBlock.getX() == secondBlock.getX() + 1) {
            return Direction.WEST;
        }
        return Direction.NORTH;
    }

    private int getSimpleTextureConfiguration(@NotNull Direction face){
        return switch (face){
            case NORTH -> 4;
            case SOUTH -> 5;
            case EAST -> 6;
            case WEST -> 7;
            case UP -> 8;
            case DOWN -> 9;
        };
    }

    private int getDoubleTextureConfiguration(@NotNull Direction face1, @NotNull Direction face2){
        return switch (face1) {
            case NORTH -> switch(face2) {
                case NORTH -> -1;
                case SOUTH -> 2;
                case EAST -> 18;
                case WEST -> 19;
                case UP -> 14;
                case DOWN -> 10;
            };
            case SOUTH -> switch(face2) {
                case NORTH -> 2;
                case SOUTH -> -1;
                case EAST -> 21;
                case WEST -> 20;
                case UP -> 16;
                case DOWN -> 12;
            };
            case EAST -> switch(face2) {
                case NORTH -> 18;
                case SOUTH -> 21;
                case EAST -> -1;
                case WEST -> 3;
                case UP -> 15;
                case DOWN -> 11;
            };
            case WEST -> switch(face2) {
                case NORTH -> 19;
                case SOUTH -> 20;
                case EAST -> 3;
                case WEST -> -1;
                case UP -> 17;
                case DOWN -> 13;
            };
            case UP -> switch(face2) {
                case NORTH -> 14;
                case SOUTH -> 16;
                case EAST -> 15;
                case WEST -> 17;
                case UP -> -1;
                case DOWN -> 1;
            };
            case DOWN -> switch(face2) {
                case NORTH -> 10;
                case SOUTH -> 12;
                case EAST -> 11;
                case WEST -> 13;
                case UP -> 1;
                case DOWN -> -1;
            };
        };
    }

}