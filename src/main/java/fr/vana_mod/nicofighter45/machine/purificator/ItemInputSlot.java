package fr.vana_mod.nicofighter45.machine.purificator;

import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.machine.purificator.craft.PurificatorRecipe;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.Optional;

public class ItemInputSlot extends Slot {

    private final PurificatorScreenHandler handler;

    public ItemInputSlot(PurificatorScreenHandler handler, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.handler = handler;
    }

    @Override
    public ItemStack insertStack(ItemStack stack, int count) {
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

            Optional<PurificatorRecipe> optional = handler.getWorld().getRecipeManager().getFirstMatch(ModMachines.PURIFICATOR_RECIPE_TYPE, inventory, handler.getWorld());
            if(handler.getProperty().get(PurificatorBlockEntity.Properties.CRAFTING.value) > 0 &&
                    handler.getRecipe() != null &&
                    !handler.getRecipe().getInput().getMatchingStacks()[0].equals(getStack())){
                handler.getProperty().set(PurificatorBlockEntity.Properties.CRAFTING.value, 0);
            }
            if (optional.isPresent()) {
                if(!inventory.getStack(2).getItem().equals(optional.get().getOutput().getItem())){
                    handler.getProperty().set(PurificatorBlockEntity.Properties.CRAFTING.value, 0);
                }else{
                    handler.setRecipe(optional.get());
                    if(handler.getProperty().get(PurificatorBlockEntity.Properties.CRAFTING.value) == 0){
                        handler.getProperty().set(PurificatorBlockEntity.Properties.CRAFTING.value, 100);
                    }
                }

            }
        }
        return stack;
    }
}