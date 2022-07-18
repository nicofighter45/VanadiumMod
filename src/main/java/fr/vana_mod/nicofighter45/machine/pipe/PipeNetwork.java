package fr.vana_mod.nicofighter45.machine.pipe;

import fr.vana_mod.nicofighter45.machine.basic.block.AbstractMachineBlockEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PipeNetwork {

    private List<BlockPos> pipes = new ArrayList<>();
    private List<BlockPos> inputs = new ArrayList<>();
    private List<BlockPos> outputs = new ArrayList<>();
    private int fluidAmount;
    private Fluid fluid;

    public PipeNetwork(@NotNull PipeBlockEntity blockEntity){
        checkBlock(blockEntity, 1, 0, 0);
        checkBlock(blockEntity, -1, 0, 0);
        checkBlock(blockEntity, 0, 1, 0);
        checkBlock(blockEntity, 0, -1, 0);
        checkBlock(blockEntity, 0, 0, 1);
        checkBlock(blockEntity, 0, 0, -1);
        if(pipes.size() < 3){
            ((PipeBlockEntity) Objects.requireNonNull(Objects.requireNonNull(blockEntity.getWorld())
                    .getBlockEntity(pipes.get(0)))).network.addPipe(blockEntity);
            return;
        }
        this.fluidAmount = 0;
        this.fluid = Fluids.WATER;
    }

    private void checkBlock(@NotNull PipeBlockEntity blockEntity, int x, int y, int z){
        BlockPos pos = new BlockPos(blockEntity.getPos().getX() + x, blockEntity.getPos().getX() + y,
                blockEntity.getPos().getZ() + z);
        World world = blockEntity.getWorld();
        assert world != null;
        if(world.getBlockEntity(pos) instanceof PipeBlockEntity networkBlockEntity){
            pipes.add(networkBlockEntity.getPos());
        }else if(world.getBlockEntity(pos) instanceof AbstractMachineBlockEntity machineBlockEntity){
            this.outputs.add(machineBlockEntity.getPos());
        }
    }

    private void actualizeTexture(@NotNull PipeBlockEntity blockEntity){

    }

    private void addPipe(@NotNull PipeBlockEntity blockEntity) {
        blockEntity.network = this;
        pipes.add(blockEntity.getPos());
        BlockPos newPipe = blockEntity.getPos();
        List<BlockPos> clothPipes = getClothPipes(newPipe);
        if(clothPipes.size() == 1){
            setSimpleTextureConfiguration(blockEntity, clothPipes.get(0));
            clothPipes = getClothPipes(clothPipes.get(0));
            if(clothPipes.size() == 1){
                setSimpleTextureConfiguration((PipeBlockEntity) Objects.requireNonNull(Objects.requireNonNull(blockEntity
                                .getWorld()).getBlockEntity(clothPipes.get(0))), clothPipes.get(0));
            }else{

            }
        }else{

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
        for(BlockPos pipe : this.pipes){
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