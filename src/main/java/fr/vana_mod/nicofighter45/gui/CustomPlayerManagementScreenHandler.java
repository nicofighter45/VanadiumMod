package fr.vana_mod.nicofighter45.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CustomPlayerManagementScreenHandler extends ScreenHandler {

    private final PlayerInventory playerInventory, playerInventoryToManage;

    public CustomPlayerManagementScreenHandler(ScreenHandlerType<?> type, int syncId, @NotNull PlayerInventory playerInventory, PlayerInventory playerInventoryToManage) {
        super(type, syncId);
        this.playerInventory = playerInventory;
        this.playerInventoryToManage = playerInventoryToManage;
        int j;
        int k;
        for(k = 0; k < 5; k ++){
            this.addSlot(new Slot(this.playerInventoryToManage, 36 + k, 8 + k * 18, 1));
        }
        for(j = 0; j < 4; j++){
            for(k = 0; k < 9; k ++){
                this.addSlot(new Slot(this.playerInventoryToManage, j + k * 9 + 5, 8 + k * 18, 19 + j * 18));
            }
        }
        for(k = 0; k < 5; k ++){
            this.addSlot(new Slot(this.playerInventory, 36 + k, 8 + k * 18, 85));
        }
        for(j = 0; j < 4; j++){
            for(k = 0; k < 9; k ++){
                this.addSlot(new Slot(this.playerInventory, j + k * 9 + 5, 8 + k * 18, 103 + j * 18));
            }
        }

    }

    @Override
    public boolean canUse(@NotNull PlayerEntity player) {
        return Objects.requireNonNull(player.getServer()).getPlayerManager().isOperator(player.getGameProfile());
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index < 41) {
                if (!this.insertItem(itemStack2, 41, 62, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, 41, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return itemStack;
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.playerInventory.onClose(player);
    }

}