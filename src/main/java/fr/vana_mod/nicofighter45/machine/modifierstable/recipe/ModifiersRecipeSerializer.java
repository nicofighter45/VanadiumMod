package fr.vana_mod.nicofighter45.machine.modifierstable.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import fr.vana_mod.nicofighter45.items.enchantment.ModEnchants;
import fr.vana_mod.nicofighter45.main.CommonInitializer;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ModifiersRecipeSerializer implements RecipeSerializer<ModifiersRecipe> {

    public static final ModifiersRecipeSerializer INSTANCE = new ModifiersRecipeSerializer();
    public static final Identifier ID = new Identifier(CommonInitializer.MODID, "modifiers_recipe");

    private ModifiersRecipeSerializer() {
    }

    @Override
    public ModifiersRecipe read(Identifier id, JsonObject json) {
        ModifiersRecipeJsonFormat recipeJson = new Gson().fromJson(json, ModifiersRecipeJsonFormat.class);

        if (recipeJson.input1 == null || recipeJson.input2 == null || recipeJson.input3 == null ||
                recipeJson.input4 == null || recipeJson.result == null) {
            throw new JsonSyntaxException("A required attribute is missing!");
        }

        Ingredient input1 = Ingredient.ofStacks(new ItemStack(Registries.ITEM.getOrEmpty(new Identifier(recipeJson.input1))
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.input1))));
        Ingredient input2 = Ingredient.ofStacks(new ItemStack(Registries.ITEM.getOrEmpty(new Identifier(recipeJson.input2))
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.input2))));
        Ingredient input3 = Ingredient.ofStacks(new ItemStack(Registries.ITEM.getOrEmpty(new Identifier(recipeJson.input3))
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.input3))));
        Ingredient input4 = Ingredient.ofStacks(new ItemStack(Registries.ITEM.getOrEmpty(new Identifier(recipeJson.input4))
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.input4))));
        ItemStack output = Ingredient.fromJson(recipeJson.result).getMatchingStacks()[0];
        if (output.getItem() == Items.ENCHANTED_BOOK) {
            JsonObject storedEnchantments = recipeJson.result.getAsJsonObject().get("nbt").getAsJsonObject().get("StoredEnchantments").getAsJsonArray().get(0).getAsJsonObject();
            if (storedEnchantments == null) {
                throw new JsonSyntaxException("Enchantment Not Detected");
            }
            EnchantedBookItem.addEnchantment(output, new EnchantmentLevelEntry(Objects.requireNonNull(ModEnchants.getEnchant("enchantment." + storedEnchantments.get("id").getAsString()
                    .replace("\"", "").replace(":", "."))), Integer.parseInt(storedEnchantments.get("lvl").getAsString())));
        }
        return new ModifiersRecipe(input1, input2, input3, input4, output, id);
    }

    @Override
    public void write(PacketByteBuf packetData, @NotNull ModifiersRecipe recipe) {
        recipe.getInput1().write(packetData);
        recipe.getInput2().write(packetData);
        recipe.getInput3().write(packetData);
        recipe.getInput4().write(packetData);
        packetData.writeItemStack(recipe.getOutput());
    }

    @Override
    public ModifiersRecipe read(Identifier id, PacketByteBuf packetData) {
        Ingredient input1 = Ingredient.fromPacket(packetData);
        Ingredient input2 = Ingredient.fromPacket(packetData);
        Ingredient input3 = Ingredient.fromPacket(packetData);
        Ingredient input4 = Ingredient.fromPacket(packetData);
        ItemStack output = packetData.readItemStack();
        return new ModifiersRecipe(input1, input2, input3, input4, output, id);
    }
}