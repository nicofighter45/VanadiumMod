package fvm.nicofighter45.fr.block.modifiertable;

import fvm.nicofighter45.fr.FVM;
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
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ModifiersTableBlock extends Block implements BlockEntityProvider{
    public static final Identifier ID = new Identifier(FVM.MODID, "modifierstable");

    public ModifiersTableBlock(){
        super(FabricBlockSettings.of(Material.METAL).breakByHand(false).sounds(BlockSoundGroup.METAL)
                .strength(5, 0.5f).breakByTool(FabricToolTags.PICKAXES, 3).requiresTool());
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockView world) {
        return new ModifiersTableBlockEntity();
    }

    @Override
    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        // You need a Block.createScreenHandlerFactory implementation that delegates to the block entity,
        // such as the one from BlockWithEntity
        player.openHandledScreen(blockState.createScreenHandlerFactory(world, blockPos));
        return ActionResult.SUCCESS;
    }
}