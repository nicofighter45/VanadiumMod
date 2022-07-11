package fr.vana_mod.nicofighter45.machine.purificator;

import fr.vana_mod.nicofighter45.machine.ModMachines;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;

public class PurificatorScreenHandler extends ScreenHandler {

    private final Inventory input, output;
    private final ScreenHandlerContext context;
    private final PropertyDelegate propertyDelegate;

    public PurificatorScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new ArrayPropertyDelegate(2));
    }

    public PurificatorScreenHandler(int syncId, PlayerInventory playerInventory, PropertyDelegate propertyDelegate) {
        this(syncId, playerInventory, propertyDelegate, playerInventory.player);
    }

    public PurificatorScreenHandler(int syncId, PlayerInventory inv, PropertyDelegate propertyDelegate, PlayerEntity player){
        this(syncId, inv, propertyDelegate, player, ScreenHandlerContext.EMPTY);
    }

    public PurificatorScreenHandler(int syncId, @NotNull PlayerInventory playerInventory, PropertyDelegate propertyDelegate,
                                    PlayerEntity player, ScreenHandlerContext context) {
        super(ModMachines.HIGH_FURNACE_SCREEN_HANDLER, syncId);
        this.context = context;
        this.propertyDelegate = propertyDelegate;
        this.input = new SimpleInventory(2);
        this.output = new SimpleInventory(1);


        this.addSlot(new Slot(input, 0, 44, 120));
        this.addSlot(new WaterInputSlot(this, input, 1, 70, 102));

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

    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, pos) -> this.dropInventory(player, this.input));
    }

    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ModMachines.HIGH_FURNACE_BLOCK);
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index >= 3 && index < 10) {
                if (!this.insertItem(itemStack2, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (index >= 10 && index < 46) {
                if (!this.insertItem(itemStack2, 0, 3, false)) {
                    if (!this.insertItem(itemStack2, 3, 10, false)) {
                        if (index < 37) {
                            if (!this.insertItem(itemStack2, 37, 46, false)) {
                                return ItemStack.EMPTY;
                            }
                        } else if (!this.insertItem(itemStack2, 10, 37, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            } else if (!this.insertItem(itemStack2, 10, 46, false)) {
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
            if (index >= 3 && index < 10) {
                player.dropItem(itemStack2, false);
            }
        }

        return itemStack;
    }

    public PropertyDelegate getPropertyDelegate() {
        return propertyDelegate;
    }
}