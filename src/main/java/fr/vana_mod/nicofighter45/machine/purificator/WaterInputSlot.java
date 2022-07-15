package fr.vana_mod.nicofighter45.machine.purificator;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;

public class WaterInputSlot extends Slot {

    private final PurificatorScreenHandler handler;

    public WaterInputSlot(PurificatorScreenHandler handler, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.handler = handler;
    }

    @Override
    public boolean canInsert(@NotNull ItemStack stack) {
        return stack.getItem().equals(Items.WATER_BUCKET) || stack.getItem().equals(Items.BUCKET);
    }

    @Override
    public ItemStack insertStack(@NotNull ItemStack stack, int count) {
        if(!stack.isEmpty() && this.canInsert(stack)) {
            ItemStack itemStack = this.getStack();
            int i = Math.min(Math.min(count, stack.getCount()), this.getMaxItemCount(stack) - itemStack.getCount());
            if(stack.getItem() == Items.WATER_BUCKET && handler.getFillingTime() + handler.getWater() < 301){
                this.setStack(new ItemStack(Items.BUCKET));
                handler.fill();
                return ItemStack.EMPTY;
            }
            if (itemStack.isEmpty()) {
                this.setStack(stack.split(i));
            } else if (ItemStack.canCombine(itemStack, stack)) {
                stack.decrement(i);
                itemStack.increment(i);
                this.setStack(itemStack);
            }
        }
        return stack;
    }
}