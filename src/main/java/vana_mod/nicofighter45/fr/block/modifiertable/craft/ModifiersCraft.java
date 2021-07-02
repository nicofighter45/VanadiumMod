package vana_mod.nicofighter45.fr.block.modifiertable.craft;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModifiersCraft implements Recipe<Inventory> {

    private final Ingredient input1;
    private final Ingredient input2;
    private final Ingredient input3;
    private final Ingredient input4;
    private final ItemStack result;
    private final Identifier id;

    public ModifiersCraft(Ingredient input1, Ingredient input2, Ingredient input3, Ingredient input4, ItemStack result, Identifier id) {
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.input4 = input4;
        this.result = result;
        this.id = id;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        if(inventory.size() != 4) return false;
        return input1.test(inventory.getStack(0)) && input1.test(inventory.getStack(1)) && input1.test(inventory.getStack(2)) && input1.test(inventory.getStack(3));
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

    public static class Type implements RecipeType<ModifiersCraft> {
        // Define ExampleRecipe.Type as a singleton by making its constructor private and exposing an instance.
        private Type() {}
        public static final Type INSTANCE = new Type();

        // This will be needed in step 4
        public static final String ID = "four_slot_recipe";
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
}
