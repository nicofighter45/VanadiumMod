package fr.vana_mod.nicofighter45.machine.purificator.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public record PurificatorRecipe(Ingredient input, ItemStack result, Identifier id) implements Recipe<Inventory> {

    @Override
    public boolean matches(@NotNull Inventory inventory, World world) {
        if (inventory.size() != 1) return false;
        return input.test(inventory.getStack(0));
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
        return PurificatorRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public Ingredient getInput() {
        return input;
    }

    @Override
    public @NotNull DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(1);
        list.add(this.input);
        return list;
    }

    public Item getInputItem() {
        return getInput().getMatchingStacks()[0].getItem();
    }

    public static class Type implements RecipeType<PurificatorRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "purificator_recipe_type";

        private Type() {
        }
    }
}
