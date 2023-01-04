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

    public PipeNetwork(@NotNull PipeBlockEntity blockEntity) {
        this.world = blockEntity.getWorld();
        assert this.world != null;
        checkBlock(blockEntity, 1, 0, 0);
        checkBlock(blockEntity, -1, 0, 0);
        checkBlock(blockEntity, 0, 1, 0);
        checkBlock(blockEntity, 0, -1, 0);
        checkBlock(blockEntity, 0, 0, 1);
        checkBlock(blockEntity, 0, 0, -1);
        if (!pipes.isEmpty()) {
            PipeNetwork newNetwork = ((PipeBlockEntity) Objects.requireNonNull(this.world
                    .getBlockEntity(pipes.get(0)))).network;
            newNetwork.outputs.addAll(this.outputs);
            newNetwork.addPipe(blockEntity, pipes);
            return;
        }
        blockEntity.network = this;
        this.pipes.add(blockEntity.getPos());
        this.fluidAmount = 0;
        this.fluid = Fluids.WATER;
        this.inputs.clear();
        this.inputs.add(blockEntity.getPos());
    }

    private void deactivate(){
        this.pipes.clear();
        this.inputs.clear();
        this.outputs.clear();
        this.fluidAmount = 0;
        this.isActive = false;
    }

    private void checkBlock(@NotNull PipeBlockEntity blockEntity, int x, int y, int z) {
        BlockPos pos = blockEntity.getPos().add(x, y, z);
        BlockEntity bl = world.getBlockEntity(pos);
        if (bl == null) {
            return;
        }
        if (world.getBlockEntity(pos) instanceof PipeBlockEntity) {
            this.pipes.add(pos);
        } else if (world.getBlockEntity(pos) instanceof AbstractMachineBlockEntity) {
            this.outputs.add(pos);
        }
    }

    public void removePipe(@NotNull PipeBlockEntity pipeBlockEntity) {
        List<BlockPos> clothPipes = getClothPipes(pipeBlockEntity.getPos());
        if (clothPipes.size() > 1) {
            checkForNetworkSeparation(clothPipes);
        } else if (clothPipes.size() == 1) {
            BlockEntity blockEntity =  world.getBlockEntity(clothPipes.get(0));
            if (blockEntity instanceof PipeBlockEntity){
                PipeNetwork network = ((PipeBlockEntity) blockEntity).network;
                network.pipes.remove(blockEntity.getPos());
                network.actualizeTexture((PipeBlockEntity) blockEntity);
            }else{
                System.out.println("Error detected in removePipe");
            }
        }else{
            deactivate();
        }
    }

    private void checkForNetworkSeparation(@NotNull List<BlockPos> clothPipes) { // todo not working
        PipeNetwork newNetwork = new PipeNetwork(this.world);
        newNetwork.inputs.addAll(getNewPipesBlock(clothPipes.get(0)));
        if (newNetwork.inputs.contains(clothPipes.get(1))) {
            newNetwork.fluidAmount = fluidAmount;
        } else {
            PipeNetwork newNetwork2 = new PipeNetwork(this.world);
            newNetwork2.inputs.addAll(getNewPipesBlock(clothPipes.get(1)));
            newNetwork.fluidAmount = fluidAmount / 2;
            newNetwork2.fluidAmount = fluidAmount - newNetwork.fluidAmount;
            for (BlockPos pipes : newNetwork2.pipes) {
                ((PipeBlockEntity) Objects.requireNonNull(world.getBlockEntity(pipes))).network = newNetwork2;
            }
        }
        for (BlockPos pipes : newNetwork.pipes) {
            ((PipeBlockEntity) Objects.requireNonNull(world.getBlockEntity(pipes))).network = newNetwork;
        }
    }

    private @NotNull List<BlockPos> getNewPipesBlock(BlockPos pos) {
        List<BlockPos> pipes = new ArrayList<>();
        for (BlockPos newPos : getClothPipes(pos)) {
            if (!pipes.contains(newPos)) {
                pipes.add(newPos);
                for (BlockPos newNewPos : getNewPipesBlock(newPos)) {
                    if (!pipes.contains(newPos)) {
                        pipes.add(newNewPos);
                    }
                }
            }
        }
        return pipes;
    }

    private void addPipe(@NotNull PipeBlockEntity blockEntity, @NotNull List<BlockPos> pipes) {
        blockEntity.network = this;
        for(BlockPos pipe : pipes){
            if (!this.pipes.contains(pipe)){
                this.pipes.add(blockEntity.getPos()); // todo delete this line and fusion network
                System.out.println("Network must be fusion");
                return;
            }
        }
        actualizeTexture(blockEntity);
        this.pipes.add(blockEntity.getPos());
        for(BlockPos pipe : pipes){
            actualizeTexture(((PipeBlockEntity) Objects.requireNonNull(world.getBlockEntity(pipe))));
        }
    }

    private void actualizeTexture(@NotNull PipeBlockEntity blockEntity){
        BlockPos pos = blockEntity.getPos();
        List<BlockPos> clothPipes = getClothPipes(pos);

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

    private Direction getFacing(@NotNull BlockPos firstBlock, @NotNull BlockPos secondBlock) {
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

    private @NotNull List<BlockPos> getClothPipes(BlockPos newPipe) {
        List<BlockPos> pipes = new ArrayList<>();
        for (BlockPos pipe : this.pipes) {
            if (pipe == newPipe) {
                continue;
            }
            int x = newPipe.getX() - pipe.getX();
            int y = newPipe.getY() - pipe.getY();
            int z = newPipe.getZ() - pipe.getZ();
            if (x <= 1 && x >= -1 && y <= 1 && y >= -1 && z <= 1 && z >= -1) {
                pipes.add(pipe);
            }
        }
        return pipes;
    }

}