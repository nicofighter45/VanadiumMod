package fr.vana_mod.nicofighter45.block.enchanter_table;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EnchantingTableBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnchanterTableBlock extends BlockWithEntity {

    public EnchanterTableBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EnchantingTableBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        //With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, @NotNull World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
            if(screenHandlerFactory!=null){
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }


    //This method will drop all items onto the ground when the block is broken
    @Override
    public void onStateReplaced(@NotNull BlockState state, World world, BlockPos pos, @NotNull BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof EnchanterTableBlockEntity) {
                ItemScatterer.spawn(world, pos, (EnchanterTableBlockEntity) blockEntity);
                // update comparators
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, @NotNull World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }
}
