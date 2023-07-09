package fr.vana_mod.nicofighter45.machine.high_furnace.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import fr.vana_mod.nicofighter45.main.CommonInitializer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class HighFurnaceRecipeSerializer implements RecipeSerializer<HighFurnaceRecipe> {

    public static final HighFurnaceRecipeSerializer INSTANCE = new HighFurnaceRecipeSerializer();
    public static final Identifier ID = new Identifier(CommonInitializer.MODID, "high_furnace_recipe");

    private HighFurnaceRecipeSerializer() {
    }

    @Override
    public HighFurnaceRecipe read(Identifier id, JsonObject json) {
        HighFurnaceRecipeJsonFormat recipeJson = new Gson().fromJson(json, HighFurnaceRecipeJsonFormat.class);

        if (recipeJson.input1 == null || recipeJson.input2 == null || recipeJson.result == null) {
            throw new JsonSyntaxException("A required attribute is missing!");
        }

        Ingredient input1 = Ingredient.ofStacks(new ItemStack(Registries.ITEM.getOrEmpty(new Identifier(recipeJson.input1))
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.input1))));
        Ingredient input2 = Ingredient.ofStacks(new ItemStack(Registries.ITEM.getOrEmpty(new Identifier(recipeJson.input2))
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.input2))));
        int count = recipeJson.result.get("count").getAsInt();
        ItemStack output = new ItemStack(Registries.ITEM.getOrEmpty(new Identifier(recipeJson.result.get("item").getAsString()))
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.result)), count);
        return new HighFurnaceRecipe(input1, input2, output, id);
    }

    @Override
    public void write(PacketByteBuf packetData, @NotNull HighFurnaceRecipe recipe) {
        recipe.getInput1().write(packetData);
        recipe.getInput2().write(packetData);
        packetData.writeItemStack(recipe.getOutput(DynamicRegistryManager.EMPTY));
    }

    @Override
    public HighFurnaceRecipe read(Identifier id, PacketByteBuf packetData) {
        Ingredient input1 = Ingredient.fromPacket(packetData);
        Ingredient input2 = Ingredient.fromPacket(packetData);
        ItemStack output = packetData.readItemStack();
        return new HighFurnaceRecipe(input1, input2, output, id);
    }
}