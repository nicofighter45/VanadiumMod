package fr.vana_mod.nicofighter45.main.gui;

import fr.vana_mod.nicofighter45.machine.ModMachines;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CustomPlayerManagementScreenHandler extends ScreenHandler {

    private final PlayerInventory playerInventory;

    public CustomPlayerManagementScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, playerInventory);
    }

    public CustomPlayerManagementScreenHandler(int syncId, @NotNull PlayerInventory playerInventory, PlayerInventory playerInventoryToManage) {
        super(ModMachines.CUSTOM_PLAYER_MANAGER_SCREEN_HANDLER, syncId);
        this.playerInventory = playerInventory;
        int k, j;
        for(k = 0; k < 4; k ++){
            this.addSlot(new Slot(playerInventoryToManage, 39 - k, 8 + k * 18, 8));
        }
        this.addSlot(new Slot(playerInventoryToManage, 40, 80, 8));
        for(j = 1; j < 4; j++){
            for(k = 0; k < 9; k ++){
                this.addSlot(new Slot(playerInventoryToManage, j * 9 + k, 8 + k * 18, 8 + j * 18));
            }
        }
        for(k = 0; k < 9; k ++){
            this.addSlot(new Slot(playerInventoryToManage, k, 8 + k * 18, 80));
        }

        for(k = 0; k < 4; k ++){
            this.addSlot(new Slot(playerInventoryToManage, 39 - k, 8 + k * 18, 102));
        }
        this.addSlot(new Slot(playerInventoryToManage, 40, 80, 102));
        for(j = 1; j < 4; j++){
            for(k = 0; k < 9; k ++){
                this.addSlot(new Slot(this.playerInventory, j * 9 + k, 8 + k * 18, 102 + j * 18));
            }
        }
        for(k = 0; k < 9; k ++){
            this.addSlot(new Slot(this.playerInventory, k, 8 + k * 18, 178));
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