package fr.nicofighter45.fvm.items.enchantment;

import fr.nicofighter45.fvm.items.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class JumperEnchantment extends Enchantment {

    public JumperEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot[] equipementSlot) {
        super(weight, target, equipementSlot);
    }

    @Override
    public int getMinPower(int level) {
        return 30;
    }

    @Override
    public int getMaxLevel() {
        return 5;
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