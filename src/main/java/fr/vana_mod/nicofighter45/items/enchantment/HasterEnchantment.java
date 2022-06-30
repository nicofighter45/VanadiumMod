package fr.vana_mod.nicofighter45.items.enchantment;

import fr.vana_mod.nicofighter45.items.armor.ModArmorsMaterial;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class HasterEnchantment extends Enchantment {

    public HasterEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot[] equipementSlot) {
        super(weight, target, equipementSlot);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armor && armor.getSlotType() == EquipmentSlot.HEAD &&
                armor.getMaterial().equals(ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL);
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

}