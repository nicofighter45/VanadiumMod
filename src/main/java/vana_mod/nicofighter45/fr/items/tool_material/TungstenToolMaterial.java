package vana_mod.nicofighter45.fr.items.tool_material;

import vana_mod.nicofighter45.fr.items.ModItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class TungstenToolMaterial implements ToolMaterial {

    public static final TungstenToolMaterial INSTANCE = new TungstenToolMaterial();

    @Override
    public int getDurability() {
        return 4000;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 12f;
    }

    @Override
    public float getAttackDamage() {
        return 1;
    }

    @Override
    public int getMiningLevel() {
        return 4;
    }

    @Override
    public int getEnchantability() {
        return 15;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.TUNGSTEN_INGOT);
    }
}