package fr.vana_mod.nicofighter45.items.tool_material;

import fr.vana_mod.nicofighter45.items.ModItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class TinToolMaterial implements ToolMaterial {

    public static final TinToolMaterial INSTANCE = new TinToolMaterial();

    @Override
    public int getDurability() {
        return 3000;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 10f;
    }

    @Override
    public float getAttackDamage() {
        return 1;
    }

    @Override
    public int getMiningLevel() {
        return 3;
    }

    @Override
    public int getEnchantability() {
        return 15;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.TIN_INGOT);
    }
}