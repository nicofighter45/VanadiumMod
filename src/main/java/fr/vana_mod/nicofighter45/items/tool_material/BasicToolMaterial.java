package fr.vana_mod.nicofighter45.items.tool_material;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class BasicToolMaterial implements ToolMaterial {

    private final int durability, miningLevel, enchantability;
    private final float miningSpeed, attackDamage;
    private final Ingredient repairIngredient;

    public BasicToolMaterial(int durability, float miningSpeed, float attackDamage, int miningLevel,
                             int enchantability, Item repairItem) {
        this.durability = durability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.miningLevel = miningLevel;
        this.enchantability = enchantability;
        this.repairIngredient = Ingredient.ofItems(repairItem);
    }

    @Override
    public int getDurability() {
        return this.durability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }

}