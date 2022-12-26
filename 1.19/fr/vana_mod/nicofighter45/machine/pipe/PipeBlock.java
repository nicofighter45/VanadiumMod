package fr.vana_mod.nicofighter45.machine.pipe;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

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
            case 1 -> VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 1, 0.625);
            case 2 -> VoxelShapes.cuboid(0.375, 0.375, 0, 0.625, 0.625, 1);
            case 3 -> VoxelShapes.cuboid(0, 0.375, 0.375, 1, 0.625, 0.625);
            case 4 -> VoxelShapes.combineAndSimplify(
                    VoxelShapes.cuboid(0.375, 0.375, 0.375, 0.625, 1, 0.625),
                    VoxelShapes.cuboid(0, 0.375, 0.375, 0.375, 0.625,0.625),
                    (a, b) -> a);
            default -> VoxelShapes.combineAndSimplify(
                    VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 0.625, 0.625),
                    VoxelShapes.cuboid(0.375, 0, 0, 0.625, 0.625,0.625),
                    (a, b) -> a);
        };
    }

    @Override
    public void onPlaced(@NotNull World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        new PipeNetwork((PipeBlockEntity) Objects.requireNonNull(world.getBlockEntity(pos)));
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        this.blockEntity.network.removePipe(this.blockEntity);
    }

    @Override
    public BlockState rotate(@NotNull BlockState state, @NotNull BlockRotation rotation) {
        int config = state.get(configuration);
        int value = 0;
        switch (rotation){
            case NONE:
                break;
            case CLOCKWISE_90:
                if(config == 2){
                    value = 3;
                }else if(config == 3){
                    value = 2;
                }else if(config > 3){
                    if(config == 7){
                        value = 4;
                    }else if(config == 11){
                        value = 8;
                    }else if(config == 12){
                        value = 14;
                    }else if(config == 14){
                        value = 15;
                    }else if(config == 15){
                        value = 13;
                    }else if(config == 13){
                        value = 12;
                    }else{
                        value = config + 1;
                    }
                }
                break;
            case CLOCKWISE_180:
                if(config > 3){
                    if(config < 6 || (config > 7 && config < 10)){
                        value = config + 2;
                    }else if(config < 12){
                        value = config - 2;
                    }else if(config == 12){
                        value = 15;
                    }else if(config == 14){
                        value = 13;
                    }else if(config == 15){
                        value = 12;
                    }else if(config == 13){
                        value = 14;
                    }
                }
                break;
            case COUNTERCLOCKWISE_90:
                if(config == 2){
                    value = 3;
                }else if(config == 3){
                    value = 2;
                }else if(config > 3){
                    if(config == 4){
                        value = 7;
                    }else if(config == 8){
                        value = 11;
                    }else if(config == 12){
                        value = 13;
                    }else if(config == 13){
                        value = 15;
                    }else if(config == 15){
                        value = 14;
                    }else if(config == 14){
                        value = 12;
                    }else{
                        value = config - 1;
                    }
                }
                break;
        }
        return state.with(configuration, value);
    }

}