package fr.vana_mod.nicofighter45.machine.purificator;

import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.machine.basic.block.AbstractMachineBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class PurificatorBlock extends AbstractMachineBlock {

    public final static int waterLevelToTransform = 100;
    public final static int waterLevelTotal = 4000;

    public static final IntProperty configuration = IntProperty.of("configuration", 0, 4);
    private PurificatorBlockEntity blockEntity;

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        setDefaultState(getStateManager().getDefaultState().with(configuration, 0));
        blockEntity = new PurificatorBlockEntity(pos, state, configuration);
        return blockEntity;
    }

    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        builder.add(configuration);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModMachines.PURIFICATOR_BLOCK_ENTITY_TYPE, PurificatorBlockEntity::tick);
    }

    @Override
    public ActionResult onUse(BlockState state, @NotNull World world, BlockPos pos, @NotNull PlayerEntity player, Hand hand, BlockHitResult hit) {
        player.sendMessage(Text.of("BOU :" + player.getMainHandStack().getItem().toString()));
        if (player.getMainHandStack().getItem() == Items.WATER_BUCKET) {
            player.getInventory().setStack(player.getInventory().selectedSlot, new ItemStack(Items.BUCKET));
            player.getWorld().playSoundFromEntity(null, player, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.PLAYERS, 2, 0);
            PurificatorBlockEntity.updateWater(world, pos, blockEntity, state);
        } else if (player.getMainHandStack().getItem() == Items.POTION) {
            player.sendMessage(Text.of(player.getMainHandStack().getOrCreateNbt().get("Potion").asString() + "  " + Registries.POTION.getId(Potions.WATER)));
            /// PotionUtil.setPotion(player.getMainHandStack(), Potions.EMPTY);
            //            player.getWorld().playSoundFromEntity(null, player, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.PLAYERS, 2, 0);
            //            PurificatorBlockEntity.updateWater(world, pos, blockEntity, state);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

}