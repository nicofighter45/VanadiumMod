package fr.vana_mod.nicofighter45.block.enchanter;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnchanterOutputSlot extends Slot {

    private final EnchanterScreenHandler handler;

    public EnchanterOutputSlot(@NotNull EnchanterScreenHandler handler, int index, int x, int y) {
        super(handler.getOutput(), index, x, y);
        this.handler = handler;
    }

    private boolean areInputsFull(){
        for (int i = 0; i < this.handler.getInput().size(); i++){
            if(this.handler.getInput().getStack(i).isEmpty()){
                return false;
            }
        }
        return true;
    }

    public boolean canInsert(@NotNull ItemStack stack) {
        if(stack.getItem() instanceof EnchantedBookItem && areInputsFull()){
            List<Enchantment> itemEnchant = this.handler.getItemEnchantments();
            Map<Enchantment, Integer> bookEnchant = EnchantmentHelper.get(stack);
            if(bookEnchant.size() > 1){
                return false;
            }
            for(Enchantment enchantment : itemEnchant){
                if(itemEnchant.contains(enchantment) || !enchantment.isAcceptableItem(this.handler.getFirstItem()) ||
                        !EnchantmentHelper.isCompatible(itemEnchant, enchantment)){
                    return false;
                }
            }
            return true;
        }
        return false;
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
        if(this.areInputsFull()){
            reloadEnchant(this.handler);
        }
        return stack;
    }

    @Override
    public void onTakeItem(@NotNull PlayerEntity player, @NotNull ItemStack stack) {
        if(this.getStack().isEmpty()){
            reloadEnchant(this.handler);
        }
    }

    public static void reloadEnchant(@NotNull EnchanterScreenHandler handler) {
        ItemStack itemStack = handler.getInput().getStack(0).copy();
        boolean rename = itemStack.hasCustomName();
        Text name = itemStack.getName();
        itemStack = new ItemStack(itemStack.getItem(), itemStack.getCount());
        if(rename){
            itemStack.setCustomName(name);
        }
        List<Integer> slot_empty = new ArrayList<>();
        for(int slot = 0; slot < 7; slot ++){
            if(handler.getOutput().getStack(slot).equals(ItemStack.EMPTY)){
                slot_empty.add(slot);
            }else{
                if(!slot_empty.isEmpty()){
                    if(slot > slot_empty.get(0)){
                        handler.getOutput().setStack(slot_empty.get(0), handler.getOutput().getStack(slot).copy());
                        handler.getOutput().setStack(slot, ItemStack.EMPTY);
                        slot_empty.remove(slot_empty.get(0));
                        slot_empty.add(slot);
                    }
                }
            }
        }
        for(int slot = 0; slot < 7; slot ++){
            if(!slot_empty.contains(slot)){
                NbtCompound nbt = handler.getOutput().getStack(slot).getNbt();
                assert nbt != null;
                Map<Enchantment, Integer> enchantmentMap = EnchantmentHelper.get(handler.getOutput().getStack(slot));
                for(Enchantment enchant : enchantmentMap.keySet()){
                    itemStack.addEnchantment(enchant, enchantmentMap.get(enchant));
                }
            }
        }
        handler.getInput().setStack(0, itemStack);
        handler.resetEnchantments();
    }

}