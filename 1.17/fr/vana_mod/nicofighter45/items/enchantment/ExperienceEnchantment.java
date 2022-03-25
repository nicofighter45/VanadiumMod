package fr.vana_mod.nicofighter45.items.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.server.network.ServerPlayerEntity;

public class ExperienceEnchantment extends Enchantment {

    public ExperienceEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot[] equipementSlot) {
        super(weight, target, equipementSlot);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof SwordItem;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return true;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return true;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity attacker, int level) {
        if(!attacker.isAlive()){
            ((ServerPlayerEntity) user).addExperience(level*3);
        }
        super.onUserDamaged(user, attacker, level);
    }
}