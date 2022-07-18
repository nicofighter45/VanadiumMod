package fr.vana_mod.nicofighter45.machine.pipe;

import fr.vana_mod.nicofighter45.machine.basic.block.AbstractMachineBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PipeBlock extends BlockWithEntity {

    public static final IntProperty configuration = IntProperty.of("configuration", 0, 15);
    public PipeBlockEntity blockEntity;

    public PipeBlock() {
        super(Settings.copy(Blocks.GLASS));
        setDefaultState(getStateManager().getDefaultState().with(configuration, 0));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        builder.add(configuration);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        blockEntity = new PipeBlockEntity(pos, state);
        return blockEntity;
    }

    @Override
    public VoxelShape getOutlineShape(@NotNull BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int config = state.get(configuration);
        return switch (config) {
            case 0 -> VoxelShapes.cuboid(0.375, 0.375, 0.375, 0.625, 0.625, 0.625);
            case 1 -> VoxelShapes.cuboid(0.375, 0f, 0.375, 0.625, 1, 0.625);
            case 2 -> VoxelShapes.cuboid(0.375, 0.375, 0.0f, 0.625, 0.625, 1.0f);
            case 3 -> VoxelShapes.cuboid(0f, 0.375, 0.375, 1, 0.625, 0.625);
            default -> VoxelShapes.fullCube();//todo add other facings
        };
    }

    public BlockState getStateForNeighborUpdate(@NotNull BlockState state, @NotNull Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if(blockEntity != null){
            BlockEntity neighborEntity = world.getBlockEntity(neighborPos);
            if(neighborEntity instanceof AbstractMachineBlockEntity || neighborEntity instanceof PipeBlockEntity){
                if(blockEntity.inputBlock == null){
                    blockEntity.inputBlock = neighborPos;
                }else if(blockEntity.outputBlock == null){
                    blockEntity.outputBlock = neighborPos;
                }
            }else{
                if (blockEntity.inputBlock == neighborPos){
                    if(blockEntity.outputBlock != null){
                        blockEntity.inputBlock = blockEntity.outputBlock;
                        blockEntity.outputBlock = null;
                    }else{
                        blockEntity.inputBlock = null;
                    }
                }else if (blockEntity.outputBlock == neighborPos){
                    blockEntity.outputBlock = null;
                }
            }
            if(blockEntity.inputBlock == null){
                System.out.println("State 0");
                return state.with(configuration, 0);
            }else if(blockEntity.outputBlock == null){
                return switch(getFacing(pos, blockEntity.inputBlock)){
                    case UP, DOWN -> state.with(configuration, 1);
                    case NORTH, SOUTH -> state.with(configuration, 2);
                    case EAST, WEST -> state.with(configuration, 3);
                };
            }
        }
        return state.with(configuration, 0);
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

    @Contract(pure = true)
    private Direction getOppositeFacing(@NotNull Direction direction){
        switch (direction){
            case DOWN -> {
                return Direction.UP;
            }
            case UP -> {
                return Direction.DOWN;
            }
            case NORTH -> {
                return Direction.SOUTH;
            }
            case SOUTH -> {
                return Direction.NORTH;
            }
            case WEST -> {
                return Direction.EAST;
            }
            case EAST -> {
                return Direction.WEST;
            }
        }
        return direction;
    }

    @Override
    public void onBlockAdded(@NotNull BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!state.isOf(state.getBlock())) {
            world.updateNeighbor(state, pos, Blocks.AIR, pos, false);
        }
    }

    //todo implement rotate and mirror method

}