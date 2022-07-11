package fr.vana_mod.nicofighter45.machine.purificator.craft;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import fr.vana_mod.nicofighter45.main.CommonInitializer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

public class PurificatorRecipeSerializer implements RecipeSerializer<PurificatorRecipe> {

    private PurificatorRecipeSerializer() {}

    public static final PurificatorRecipeSerializer INSTANCE = new PurificatorRecipeSerializer();

    public static final Identifier ID = new Identifier(CommonInitializer.MODID, "purificator_recipe");

    @Override
    public PurificatorRecipe read(Identifier id, JsonObject json) {
        PurificatorRecipeJsonFormat recipeJson = new Gson().fromJson(json, PurificatorRecipeJsonFormat.class);

        if (recipeJson.input == null || recipeJson.result == null) {
            throw new JsonSyntaxException("A required attribute is missing!");
        }

        Ingredient input = Ingredient.ofStacks(new ItemStack(Registry.ITEM.getOrEmpty(new Identifier(recipeJson.input))
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.input))));
        ItemStack output = Ingredient.fromJson(recipeJson.result).getMatchingStacks()[0];
        return new PurificatorRecipe(input, output, id);
    }

    @Override
    public void write(PacketByteBuf packetData, @NotNull PurificatorRecipe recipe) {
        recipe.getInput().write(packetData);
        packetData.writeItemStack(recipe.getOutput());
    }

    @Override
    public PurificatorRecipe read(Identifier id, PacketByteBuf packetData) {
        Ingredient input = Ingredient.fromPacket(packetData);
        ItemStack output = packetData.readItemStack();
        return new PurificatorRecipe(input, output, id);
    }
}