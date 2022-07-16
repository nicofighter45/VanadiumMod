package fr.vana_mod.nicofighter45.machine.purificator;

import fr.vana_mod.nicofighter45.machine.basic.AbstractMachineScreenHandler;
import fr.vana_mod.nicofighter45.machine.basic.ArrayMachinePropertyDelegate;
import fr.vana_mod.nicofighter45.machine.basic.MachinePropertyDelegate;
import fr.vana_mod.nicofighter45.machine.basic.slot.ItemOutputSlot;
import fr.vana_mod.nicofighter45.machine.basic.MachineInventory;
import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.machine.basic.slot.WaterInputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;

public class PurificatorScreenHandler extends AbstractMachineScreenHandler {

    public PurificatorScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY, MachineInventory.ofSize(3), new ArrayMachinePropertyDelegate(3));
    }

    public PurificatorScreenHandler(int syncId, @NotNull PlayerInventory playerInventory, ScreenHandlerContext context,
                                    @NotNull MachineInventory inventory, @NotNull MachinePropertyDelegate propertyDelegate) {
        super(ModMachines.PURIFICATOR_SCREEN_HANDLER, syncId, playerInventory, context, inventory, propertyDelegate);

        this.addSlot(new WaterInputSlot(inventory, 0, 16, 12));
        this.addSlot(new Slot(inventory, 1, 44, 39));
        this.addSlot(new ItemOutputSlot(inventory, 2, 116,39));
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

    public int getWater(){
        return this.propertyDelegate.get(0);
    }

    public int getFillingTime(){
        return this.propertyDelegate.get(1);
    }

    public int getCraftingTime(){
        return this.propertyDelegate.get(2);
    }

}