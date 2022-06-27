package fr.vana_mod.nicofighter45.items.enchantment;

import fr.vana_mod.nicofighter45.items.armor.VanadiumArmorMaterials;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class No_FallEnchantment extends Enchantment {

    public No_FallEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot[] equipementSlot) {
        super(weight, target, equipementSlot);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isAcceptableItem(@NotNull ItemStack stack) {
        if(stack.getItem() instanceof ArmorItem armor){
            return armor.getSlotType() == EquipmentSlot.FEET && armor.getMaterial() instanceof VanadiumArmorMaterials;
        }
        return false;
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