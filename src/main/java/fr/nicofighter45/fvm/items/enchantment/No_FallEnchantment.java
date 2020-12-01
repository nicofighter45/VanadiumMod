package fr.nicofighter45.fvm.items.enchantment;

import fr.nicofighter45.fvm.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class No_FallEnchantment extends Enchantment {

    public No_FallEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot[] equipementSlot) {
        super(weight, target, equipementSlot);
    }

    @Override
    public int getMinPower(int level) {
        return 30;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() == ModItems.VANADIUM_BOOTS || stack.getItem() == Items.BOOK;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return true;
    }
}