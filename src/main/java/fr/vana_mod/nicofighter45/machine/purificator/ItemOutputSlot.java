package fr.vana_mod.nicofighter45.machine.purificator;

import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.machine.purificator.craft.PurificatorRecipe;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ItemOutputSlot extends Slot {

    private final PurificatorScreenHandler handler;

    public ItemOutputSlot(PurificatorScreenHandler handler, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.handler = handler;
    }

    @Override
    public ItemStack insertStack(@NotNull ItemStack stack, int count) {
        if(!stack.isEmpty() && this.canInsert(stack)) {
            ItemStack itemStack = this.getStack();
            int i = Math.min(Math.min(count, stack.getCount()), this.getMaxItemCount(stack) - itemStack.getCount());

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