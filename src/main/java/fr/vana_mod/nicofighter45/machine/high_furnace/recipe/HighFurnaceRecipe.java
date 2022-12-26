package fr.vana_mod.nicofighter45.machine.high_furnace.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public record HighFurnaceRecipe(Ingredient input1, Ingredient input2, ItemStack result,
                                Identifier id) implements Recipe<Inventory> {

    @Override
    public boolean matches(@NotNull Inventory inventory, World world) {
        if (inventory.size() != 2) return false;
        return input1.test(inventory.getStack(0)) && input2.test(inventory.getStack(1));
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int var1, int var2) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return this.result;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return HighFurnaceRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public @NotNull DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(1);
        list.add(this.input1);
        list.add(this.input2);
        return list;
    }

    public Ingredient getInput1() {
        return this.input1;
    }

    public Ingredient getInput2() {
        return this.input2;
    }

    public static class Type implements RecipeType<HighFurnaceRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "high_furnace_recipe_type";

        private Type() {
        }
    }

}
