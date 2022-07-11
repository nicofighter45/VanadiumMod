package fr.vana_mod.nicofighter45.machine.enchanter;

import fr.vana_mod.nicofighter45.machine.ModMachines;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class EnchanterBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {
    private final BlockPos pos;

    public EnchanterBlockEntity(BlockPos pos, BlockState state) {
        super(ModMachines.ENCHANTER_BLOCK_ENTITY_TYPE, pos, state);
        this.pos = pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new EnchanterScreenHandler(syncId, inv, player, ScreenHandlerContext.create(player.getEntityWorld(), pos));
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
    }
}
