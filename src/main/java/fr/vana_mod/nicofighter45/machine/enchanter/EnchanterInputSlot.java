package fr.vana_mod.nicofighter45.machine.enchanter;

import fr.vana_mod.nicofighter45.items.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class EnchanterInputSlot extends Slot {

    private final EnchanterScreenHandler handler;

    public EnchanterInputSlot(@NotNull EnchanterScreenHandler handler, int index, int x, int y) {
        super(handler.getInput(), index, x, y);
        this.handler = handler;
    }

    private boolean areInputsFull() {
        for (int i = 0; i < this.handler.getInput().size(); i++) {
            if (this.handler.getInput().getStack(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return (getIndex() == 0 && (stack.isEnchantable() || (stack.hasEnchantments() && EnchantmentHelper.get(stack).size() < 8)) ||
                (getIndex() == 1 && stack.getItem() == ModItems.VANADIUM_NUGGET) ||
                (getIndex() == 2 && stack.getItem() == Items.LAPIS_LAZULI) ||
                (stack.getItem() instanceof EnchantedBookItem && EnchantmentHelper.get(stack).size() > 1));
    }

    @Override
    public ItemStack insertStack(@NotNull ItemStack stack, int count) {
        if (!stack.isEmpty() && this.canInsert(stack)) {
            ItemStack itemStack = this.getStack();
            int i = Math.min(Math.min(count, stack.getCount()), this.getMaxItemCount(stack) - itemStack.getCount());
            if (itemStack.isEmpty()) {
                this.setStack(stack.split(i));
            } else if (ItemStack.canCombine(itemStack, stack)) {
                stack.decrement(i);
                itemStack.increment(i);
                this.setStack(itemStack);
            }
            checkingWithInsertion();
        }
        return stack;
    }

    public void checkingWithInsertion() {
        if (this.getIndex() == 0) {
            this.handler.setFirstItem();
        }
        if (this.areInputsFull()) {
            ItemStack itemStack = handler.getInput().getStack(0);
            if (!itemStack.isEmpty() && handler.getInput().getStack(1).getCount() != 0 && handler.getInput().getStack(2).getCount() != 0) {
                Map<Enchantment, Integer> enchant = EnchantmentHelper.get(itemStack);
                int slot = 0;
                for (Enchantment enchantment : enchant.keySet()) {
                    if (slot == 7) {
                        break;
                    }
                    ItemStack enchanted_book = new ItemStack(Items.ENCHANTED_BOOK);
                    EnchantedBookItem.addEnchantment(enchanted_book, new EnchantmentLevelEntry(enchantment, enchant.get(enchantment)));
                    this.handler.getOutput().setStack(slot, enchanted_book.copy());
                    slot++;
                }
            }
        }
    }

    @Override
    public void onTakeItem(@NotNull PlayerEntity player, @NotNull ItemStack stack) {
        if (this.getStack().isEmpty()) {
            ItemStack item = stack.copy();
            if (this.getIndex() != 0) {
                item = this.handler.getInput().getStack(0);
            }
            if (!haveTheSameEnchant(item, this.handler.getFirstItem())) {
                if (this.getIndex() == 0) {
                    removeOneInSlot(1);
                    removeOneInSlot(2);
                } else if (this.getIndex() == 1) {
                    stack.setCount(stack.getCount() - 1);
                    removeOneInSlot(2);
                } else if (this.getIndex() == 2) {
                    stack.setCount(stack.getCount() - 1);
                    removeOneInSlot(1);
                }
                if (this.getIndex() != 0) {
                    this.handler.setFirstItem();
                }
            }
            for (int slot = 0; slot < this.handler.getOutput().size(); slot++) {
                this.handler.getOutput().setStack(slot, ItemStack.EMPTY);
            }
        }
    }

    private boolean haveTheSameEnchant(ItemStack item1, ItemStack item2) {
        Map<Enchantment, Integer> stackEnchant = EnchantmentHelper.get(item1);
        Map<Enchantment, Integer> inputEnchant = EnchantmentHelper.get(item2);
        if (stackEnchant.size() != inputEnchant.size()) {
            return false;
        }
        for (Enchantment enchant : stackEnchant.keySet()) {
            if (!inputEnchant.containsKey(enchant) || !inputEnchant.containsValue(stackEnchant.get(enchant))) {
                return false;
            }
        }
        for (Enchantment enchant : inputEnchant.keySet()) {
            if (!stackEnchant.containsKey(enchant) || !stackEnchant.containsValue(inputEnchant.get(enchant))) {
                return false;
            }
        }

        return true;
    }

    private void removeOneInSlot(int slot) {
        handler.getInput().removeStack(slot, 1);
    }

}