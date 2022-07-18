package fr.vana_mod.nicofighter45.machine.pipe;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
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

    @Override
    public void onBlockAdded(@NotNull BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!state.isOf(state.getBlock())) {
            new PipeNetwork(this.blockEntity);
        }
    }

    //todo implement rotate and mirror method

}