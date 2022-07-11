package fr.vana_mod.nicofighter45.block.modifiertable.craft;

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

public record ModifiersRecipe(Ingredient input1, Ingredient input2, Ingredient input3, Ingredient input4, ItemStack result, Identifier id) implements Recipe<Inventory> {

    @Override
    public boolean matches(@NotNull Inventory inventory, World world) {
        if (inventory.size() != 4) return false;
        return input1.test(inventory.getStack(0)) && input2.test(inventory.getStack(1)) && input3.test(inventory.getStack(2)) && input4.test(inventory.getStack(3));
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
        return result;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModifiersRecipeSerializer.INSTANCE;
    }

    public static class Type implements RecipeType<ModifiersRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();

        public static final String ID = "modifiers_table_recipe_type";
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public Ingredient getInput1() {
        return input1;
    }

    public Ingredient getInput2() {
        return input2;
    }

    public Ingredient getInput3() {
        return input3;
    }

    public Ingredient getInput4() {
        return input4;
    }

    @Override
    public @NotNull DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(4);
        list.add(this.input1);
        list.add(this.input2);
        list.add(this.input3);
        list.add(this.input4);
        return list;
    }
}
