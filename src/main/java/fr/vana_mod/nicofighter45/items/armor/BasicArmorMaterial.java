package fr.vana_mod.nicofighter45.items.armor;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.NotNull;

public class BasicArmorMaterial implements ArmorMaterial {

    private final int[] baseDurability, protection;
    private final int durabilityMuliplier, enchantability;
    private final SoundEvent equipSound;
    private final Ingredient repairIngredient;
    private final String name;
    private final float toughness, knockbackResistance;


    public BasicArmorMaterial(int[] baseDurability, int durabilityMuliplier, int[] protection, int enchantability,
                              SoundEvent equipSound, Item repairItem, String name, float toughness, float knockbackResistance) {
        this.baseDurability = baseDurability;
        this.durabilityMuliplier = durabilityMuliplier;
        this.protection = protection;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.repairIngredient = Ingredient.ofItems(repairItem);
        this.name = name;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
    }

    @Override
    public int getDurability(ArmorItem.@NotNull Type type) {
        return this.baseDurability[type.getEquipmentSlot().getEntitySlotId()] * this.durabilityMuliplier;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return this.protection[type.getEquipmentSlot().getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}