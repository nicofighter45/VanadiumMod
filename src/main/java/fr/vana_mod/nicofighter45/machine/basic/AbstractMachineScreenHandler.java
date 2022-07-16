package fr.vana_mod.nicofighter45.machine.basic;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractMachineScreenHandler extends ScreenHandler {

    private static final int BASE_WEIGHT = 128, BASE_HEIGHT = 128;

    protected final ScreenHandlerContext context;
    protected final MachinePropertyDelegate propertyDelegate;
    protected final MachineInventory inventory;

    protected AbstractMachineScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId,
                                           @NotNull PlayerInventory playerInventory, ScreenHandlerContext context,
                                           @NotNull MachineInventory inventory, @NotNull MachinePropertyDelegate propertyDelegate) {
        this(type, syncId, playerInventory, context, inventory, propertyDelegate, BASE_WEIGHT, BASE_HEIGHT);
    }

    protected AbstractMachineScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId,
                                           @NotNull PlayerInventory playerInventory, ScreenHandlerContext context,
                                           @NotNull MachineInventory inventory, @NotNull MachinePropertyDelegate propertyDelegate,
                                           int weight, int height) {
        super(type, syncId);

        this.context = context;
        this.propertyDelegate = propertyDelegate;
        this.addProperties(propertyDelegate);
        this.inventory = inventory;

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

    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        int size = inventory.size();
        if (slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index >= 0 && index < size) {
                if (!this.insertItem(itemStack2, size, 36 + size, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (index >= size && index < 36 + size) {
                if (!this.insertItem(itemStack2, 0, size, false)) {
                    if (index < 27 + size) {
                        if (!this.insertItem(itemStack2, 27 + size, 36 + size, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.insertItem(itemStack2, size, 27 + size, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.insertItem(itemStack2, size, 36 + size, false)) {
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
            if (index >= 0 && index < size) {
                player.dropItem(itemStack2, false);
            }
        }
        return itemStack;
    }

}