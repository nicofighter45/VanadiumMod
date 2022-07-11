package fr.vana_mod.nicofighter45.machine.modifiertable;

import fr.vana_mod.nicofighter45.machine.ModMachines;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class ModifiersTableBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {

    private final BlockPos pos;
    public ModifiersTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModMachines.MODIFIERS_TABLE_BLOCK_ENTITY_TYPE, pos, state);
        this.pos = pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ModifiersTableScreenHandler(syncId, inv, player, ScreenHandlerContext.create(player.getEntityWorld(), pos));
    }

}