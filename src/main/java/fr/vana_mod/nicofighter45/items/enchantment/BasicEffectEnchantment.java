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
    private final ArmorMaterial material;
    private final ArmorMaterial minimal_material;
    private final int maxLevel;
    private final int mediumLevel;

    public BasicEffectEnchantment(Rarity weight, EquipmentSlot slot, boolean randomSelection,
                                  boolean enchantedBook, StatusEffect effect, ArmorMaterial material, ArmorMaterial minimal_material, int maxLevel, int mediumLevel) {
        super(weight, EnchantmentTarget.ARMOR, new EquipmentSlot[]{slot});
        this.slot = slot;
        this.randomSelection = randomSelection;
        this.enchantedBook = enchantedBook;
        this.effect = effect;
        this.material = material;
        this.minimal_material = minimal_material;
        this.maxLevel = maxLevel;
        this.mediumLevel = mediumLevel;
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    public int getMediumLevel() {
        return this.mediumLevel;
    }

    public boolean isMinimalAcceptableItem(@NotNull ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armor && armor.getMaterial().equals(this.minimal_material) && armor.getSlotType().equals(this.slot);
    }

    @Override
    public boolean isAcceptableItem(@NotNull ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armor && (armor.getMaterial().equals(this.material) ||
                armor.getMaterial().equals(this.minimal_material)) && armor.getSlotType().equals(this.slot);
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