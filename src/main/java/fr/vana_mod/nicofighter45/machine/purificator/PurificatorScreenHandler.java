package fr.vana_mod.nicofighter45.machine.purificator;

import fr.vana_mod.nicofighter45.machine.basic.MachineInventory;
import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.machine.purificator.recipe.PurificatorRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PurificatorScreenHandler extends ScreenHandler {

    private final ScreenHandlerContext context;
    private final MachineInventory inventory;
    private final PropertyDelegate propertyDelegate;

    public PurificatorScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY, MachineInventory.ofClientSize(3), new ArrayPropertyDelegate(3));
    }

    public PurificatorScreenHandler(int syncId, @NotNull PlayerInventory playerInventory, ScreenHandlerContext context, @NotNull MachineInventory inventory, @NotNull PropertyDelegate propertyDelegate) {
        super(ModMachines.PURIFICATOR_SCREEN_HANDLER, syncId);
        this.context = context;
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.addProperties(propertyDelegate);

        this.addSlot(new WaterInputSlot(this, inventory, 0, 16, 12));
        this.addSlot(new Slot(inventory, 1, 44, 39));
        this.addSlot(new Slot(inventory, 2, 116,39));

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

    protected void updateResult(@NotNull World world, MachineInventory machineInventory, ItemStack itemStack) {
        System.out.println("updateResult");
        if (!world.isClient) {
            System.out.println("Updating result " + itemStack.getItem().toString() + " " + machineInventory.getStack(1).getItem().toString());
            Optional<PurificatorRecipe> optional = world.getRecipeManager().getFirstMatch(ModMachines.PURIFICATOR_RECIPE_TYPE, new SimpleInventory(inventory.getStack(1)), world);
            if (optional.isPresent()) {
                if(machineInventory.getStack(1).getItem() != itemStack.getItem()){
                    propertyDelegate.set(2, 100);
                }
            }else if(propertyDelegate.get(2) > 0){
                propertyDelegate.set(2, 0);
            }
        }
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

    public void fill(){
        this.propertyDelegate.set(1, this.propertyDelegate.get(1) + 100);
    }

}