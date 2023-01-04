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

    public static final IntProperty configuration = IntProperty.of("configuration", 0, 63);
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
        return new PipeBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(@NotNull BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int config = state.get(configuration);
        return switch (config) {
            case 0 -> shape(4, 4, 4, 12, 12, 12);

            case 1 -> shape(4, 0, 4, 12, 16, 12);
            case 2 -> shape(4, 4, 0, 12, 12, 16);
            case 3 -> shape(0, 4, 4, 16, 12, 12);

            case 4 -> shape(4, 4, 0, 12, 12, 12);
            case 5 -> shape(4, 4, 4, 12, 12, 16);

            case 6 -> shape(4, 4, 4, 16, 12, 12);
            case 7 -> shape(0, 4, 4, 12, 12, 12);

            case 8 -> shape(4, 4, 4, 12, 16, 12);
            case 9 -> shape(4, 0, 4, 12, 12, 12);

            default -> shape(2, 2, 2, 14, 14, 14);
        };
    }

    private VoxelShape shape(int minX, int minY, int minZ, int maxX, int maxY, int maxZ){
        return VoxelShapes.cuboid(((float) minX)/16, ((float) minY)/16f, ((float) minZ)/16f,
                ((float) maxX)/16f, ((float) maxY)/16f, ((float) maxZ)/16f);
    }

    @Override
    public void onPlaced(@NotNull World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        new PipeNetwork((PipeBlockEntity) Objects.requireNonNull(world.getBlockEntity(pos))); //todo add pipe
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        if (this.blockEntity.network != null){
            this.blockEntity.network.removePipe(this.blockEntity); //todo remove pipe
        }
    }

    @Override
    public BlockState rotate(@NotNull BlockState state, @NotNull BlockRotation rotation) { // todo need to work for all possibilities
        int config = state.get(configuration);
        if(config == 0 || config == 1 || config == 4 || config == 5){
            return state.with(configuration, 0);
        }
        int value = 0;
        switch (rotation) {
            case NONE:
                break;
            case CLOCKWISE_90:
                if (config == 2) {
                    value = 3;
                } else if (config == 3) {
                    value = 2;
                } else if (config > 3) {
                    if (config == 7) {
                        value = 4;
                    } else if (config == 11) {
                        value = 8;
                    } else if (config == 12) {
                        value = 14;
                    } else if (config == 14) {
                        value = 15;
                    } else if (config == 15) {
                        value = 13;
                    } else if (config == 13) {
                        value = 12;
                    } else {
                        value = config + 1;
                    }
                }
                break;
            case CLOCKWISE_180:
                if (config > 3) {
                    if (config < 6 || (config > 7 && config < 10)) {
                        value = config + 2;
                    } else if (config < 12) {
                        value = config - 2;
                    } else if (config == 12) {
                        value = 15;
                    } else if (config == 14) {
                        value = 13;
                    } else if (config == 15) {
                        value = 12;
                    } else if (config == 13) {
                        value = 14;
                    }
                }
                break;
            case COUNTERCLOCKWISE_90:
                if (config == 2) {
                    value = 3;
                } else if (config == 3) {
                    value = 2;
                } else if (config > 3) {
                    if (config == 4) {
                        value = 7;
                    } else if (config == 8) {
                        value = 11;
                    } else if (config == 12) {
                        value = 13;
                    } else if (config == 13) {
                        value = 15;
                    } else if (config == 15) {
                        value = 14;
                    } else if (config == 14) {
                        value = 12;
                    } else {
                        value = config - 1;
                    }
                }
                break;
        }
        return state.with(configuration, value);
    }

}