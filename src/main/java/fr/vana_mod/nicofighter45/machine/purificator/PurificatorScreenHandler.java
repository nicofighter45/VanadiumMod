package fr.vana_mod.nicofighter45.machine.purificator;

import fr.vana_mod.nicofighter45.machine.ModMachines;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;

public class PurificatorScreenHandler extends ScreenHandler {

    private final ScreenHandlerContext context;
    private final PurificatorBlockEntity blockEntity;

    public PurificatorScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY, null);
    }

    public PurificatorScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, PurificatorBlockEntity blockEntity) {
        super(ModMachines.PURIFICATOR_SCREEN_HANDLER, syncId);
        this.context = context;
        this.blockEntity = blockEntity;

        this.addSlot(new WaterInputSlot(blockEntity.propertyDelegate, blockEntity.inventory, 0, 16, 12));
        this.addSlot(new Slot(blockEntity.inventory, 1, 44, 39));
        this.addSlot(new Slot(blockEntity.inventory, 2, 116,39));

        int m;
        int l;
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }

        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    @Override
    public void close(PlayerEntity player) {
        this.blockEntity.handler = null;
        super.close(player);
    }

    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ModMachines.PURIFICATOR_BLOCK);
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index >= 0 && index < 3) {
                if (!this.insertItem(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (index >= 3 && index < 39) {
                if (!this.insertItem(itemStack2, 0, 3, false)) {
                    if (index < 30) {
                        if (!this.insertItem(itemStack2, 30, 39, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.insertItem(itemStack2, 3, 30, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.insertItem(itemStack2, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, itemStack2);
            if (index >= 0 && index < 3) {
                player.dropItem(itemStack2, false);
            }
        }

        return itemStack;
    }

    public PropertyDelegate getProperty(){
        return this.blockEntity.propertyDelegate;
    }

}