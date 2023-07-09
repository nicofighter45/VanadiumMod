package fr.vana_mod.nicofighter45.machine.basic.screen;

import fr.vana_mod.nicofighter45.machine.basic.properties.MachineInventory;
import fr.vana_mod.nicofighter45.machine.basic.properties.MachinePropertyDelegate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;

public abstract class AbstractMachineScreenHandler extends ScreenHandler {

    private static final int BASE_WIDTH = 176, BASE_HEIGHT = 166;

    protected final ScreenHandlerContext context;
    protected final MachinePropertyDelegate propertyDelegate;
    protected final MachineInventory inventory;
    protected final PlayerInventory playerInventory;
    private final int width, height;

    protected AbstractMachineScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId,
                                           @NotNull PlayerInventory playerInventory, ScreenHandlerContext context,
                                           @NotNull MachineInventory inventory, @NotNull MachinePropertyDelegate propertyDelegate) {
        this(type, syncId, playerInventory, context, inventory, propertyDelegate, BASE_WIDTH, BASE_HEIGHT);
    }

    protected AbstractMachineScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId,
                                           @NotNull PlayerInventory playerInventory, ScreenHandlerContext context,
                                           @NotNull MachineInventory inventory, @NotNull MachinePropertyDelegate propertyDelegate,
                                           int width, int height) {
        super(type, syncId);

        this.context = context;
        this.propertyDelegate = propertyDelegate;
        this.addProperties(propertyDelegate);
        this.inventory = inventory;
        this.width = width;
        this.height = height;
        this.playerInventory = playerInventory;
    }

    protected void registerPlayerInventory() {
        int m;
        int l;
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(this.playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }

        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(this.playerInventory, m, 8 + m * 18, 142));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        int size = inventory.size();
        if (slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index >= 0 && index < size) {
                if (!this.insertItem(itemStack2, 27 + size, 36 + size, true)) {
                    if (!this.insertItem(itemStack2, size, 27 + size, true)) {
                        return ItemStack.EMPTY;
                    }
                }

                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (index >= size && index < 36 + size) {
                if (!this.insertItem(itemStack2, 0, size, false)) {
                    if (index >= 27 + size) {
                        if (!this.insertItem(itemStack2, size, 27 + size, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.insertItem(itemStack2, 27 + size, 36 + size, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (itemStack2.isEmpty()) {
                slot.setStackNoCallbacks(ItemStack.EMPTY);
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}