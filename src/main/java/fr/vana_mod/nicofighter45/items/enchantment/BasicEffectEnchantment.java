package fr.vana_mod.nicofighter45.items.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BasicEffectEnchantment extends Enchantment {

    private final StatusEffect effect;
    private final boolean randomSelection, enchantedBook;
    private final EquipmentSlot slot;
    private final ArmorMaterial armor;
    private final int maxLevel;

    public BasicEffectEnchantment(Rarity weight, EquipmentSlot slot, boolean randomSelection,
                                  boolean enchantedBook, StatusEffect effect, ArmorMaterial armor, int maxLevel) {
        super(weight, EnchantmentTarget.ARMOR, new EquipmentSlot[]{slot});
        this.slot = slot;
        this.randomSelection = randomSelection;
        this.enchantedBook = enchantedBook;
        this.effect = effect;
        this.armor = armor;
        this.maxLevel = maxLevel;
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public boolean isAcceptableItem(@NotNull ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armor && armor.getMaterial().equals(this.armor)
                && armor.getSlotType().equals(this.slot);
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return this.enchantedBook;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return this.randomSelection;
    }

    public StatusEffect getEffect() {
        return this.effect;
    }

}