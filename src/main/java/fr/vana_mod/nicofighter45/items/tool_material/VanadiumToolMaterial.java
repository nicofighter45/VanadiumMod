package fr.vana_mod.nicofighter45.items.tool_material;

import fr.vana_mod.nicofighter45.items.ModItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class VanadiumToolMaterial implements ToolMaterial {

    public static final VanadiumToolMaterial INSTANCE = new VanadiumToolMaterial();

    @Override
    public int getDurability() {
        return 5000;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 15f;
    }

    @Override
    public float getAttackDamage() {
        return 3;
    }

    @Override
    public int getMiningLevel() {
        return 5;
    }

    @Override
    public int getEnchantability() {
        return 22;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.VANADIUM_INGOT);
    }
}