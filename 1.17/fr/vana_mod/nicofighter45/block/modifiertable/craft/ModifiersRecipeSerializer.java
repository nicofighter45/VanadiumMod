package fr.vana_mod.nicofighter45.block.modifiertable.craft;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import fr.vana_mod.nicofighter45.main.VanadiumMod;

public class ModifiersRecipeSerializer implements RecipeSerializer<ModifiersCraft> {

    // Define ModifiersRecipeSerializer as a singleton by making its constructor private and exposing an instance.
    private ModifiersRecipeSerializer() {
    }

    public static final ModifiersRecipeSerializer INSTANCE = new ModifiersRecipeSerializer();

    // This will be the "type" field in the json
    public static final Identifier ID = new Identifier(VanadiumMod.MODID, "modifiers_recipe");

    @Override
    // Turns json into Recipe
    public ModifiersCraft read(Identifier id, JsonObject json) {
        ModifiersRecipeJsonFormat recipeJson = new Gson().fromJson(json, ModifiersRecipeJsonFormat.class);

        if (recipeJson.input1 == null || recipeJson.input2 == null || recipeJson.input3 == null || recipeJson.input4 == null || recipeJson.outputItem == null) {
            throw new JsonSyntaxException("A required attribute is missing!");
        }
        // We'll allow to not specify the output, and default it to 1.
        if (recipeJson.outputAmount == 0) recipeJson.outputAmount = 1;

        // Ingredient easily turns JsonObjects of the correct format into Ingredients
        Ingredient input1 = Ingredient.fromJson(recipeJson.input1);
        Ingredient input2 = Ingredient.fromJson(recipeJson.input2);
        Ingredient input3 = Ingredient.fromJson(recipeJson.input3);
        Ingredient input4 = Ingredient.fromJson(recipeJson.input4);
        // The json will specify the item ID. We can get the Item instance based off of that from the Item registry.
        Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.outputItem))// Validate the inputted item actually exists
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.outputItem));
        ItemStack output = new ItemStack(outputItem, recipeJson.outputAmount);

        return new ModifiersCraft(input1, input2, input3, input4, output, id);
    }

    @Override
    // Turns Recipe into PacketByteBuf
    public void write(PacketByteBuf packetData, ModifiersCraft recipe) {
        recipe.getInput1().write(packetData);
        recipe.getInput2().write(packetData);
        recipe.getInput3().write(packetData);
        recipe.getInput4().write(packetData);
        packetData.writeItemStack(recipe.getOutput());
    }

    @Override
    // Turns PacketByteBuf into Recipe
    public ModifiersCraft read(Identifier id, PacketByteBuf packetData) {
        // Make sure the read in the same order you have written!
        Ingredient input1 = Ingredient.fromPacket(packetData);
        Ingredient input2 = Ingredient.fromPacket(packetData);
        Ingredient input3 = Ingredient.fromPacket(packetData);
        Ingredient input4 = Ingredient.fromPacket(packetData);
        ItemStack output = packetData.readItemStack();
        return new ModifiersCraft(input1, input2, input3, input4, output, id);
    }
}