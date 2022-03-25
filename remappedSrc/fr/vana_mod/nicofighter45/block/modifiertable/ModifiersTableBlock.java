package fr.vana_mod.nicofighter45.block.modifiertable;

import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import fr.vana_mod.nicofighter45.main.VanadiumMod;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ModifiersTableBlock extends Block implements BlockEntityProvider{
    public static final Identifier ID = new Identifier(VanadiumMod.MODID, "modifierstable");

    public ModifiersTableBlock(){
        super(FabricBlockSettings.of(Material.METAL).breakByHand(false).sounds(BlockSoundGroup.METAL)
                .strength(5, 0.5f).breakByTool(FabricToolTags.PICKAXES, 3).requiresTool());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return ModifiersTableBlockEntity::tick;
    }

    @Nullable
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof NamedScreenHandlerFactory ? (NamedScreenHandlerFactory)blockEntity : null;
    }

    @Override
    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        // You need a Block.createScreenHandlerFactory implementation that delegates to the block entity,
        // such as the one from BlockWithEntity
        player.openHandledScreen(blockState.createScreenHandlerFactory(world, blockPos));
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ModifiersTableBlockEntity(pos, state);
    }
}