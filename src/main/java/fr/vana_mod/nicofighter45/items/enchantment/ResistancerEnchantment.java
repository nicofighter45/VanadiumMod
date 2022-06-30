package fr.vana_mod.nicofighter45.items.enchantment;

import fr.vana_mod.nicofighter45.items.armor.ModArmorsMaterial;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ResistancerEnchantment extends Enchantment {

    public ResistancerEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot[] equipementSlot) {
        super(weight, target, equipementSlot);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public boolean isAcceptableItem(@NotNull ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armor && armor.getSlotType() == EquipmentSlot.LEGS &&
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