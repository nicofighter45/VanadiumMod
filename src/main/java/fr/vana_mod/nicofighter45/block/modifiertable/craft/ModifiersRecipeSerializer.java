package fr.vana_mod.nicofighter45.block.modifiertable.craft;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import fr.vana_mod.nicofighter45.main.VanadiumMod;
import org.jetbrains.annotations.NotNull;

public class ModifiersRecipeSerializer implements RecipeSerializer<ModifiersCraft> {

    // Define ModifiersRecipeSerializer as a singleton by making its constructor private and exposing an instance.
    private ModifiersRecipeSerializer() {}

    public static final ModifiersRecipeSerializer INSTANCE = new ModifiersRecipeSerializer();

    // This will be the "type" field in the json
    public static final Identifier ID = new Identifier(VanadiumMod.MODID, "modifiers_recipe");

    @Override
    // Turns json into Recipe
    public ModifiersCraft read(Identifier id, JsonObject json) {
        ModifiersRecipeJsonFormat recipeJson = new Gson().fromJson(json, ModifiersRecipeJsonFormat.class);

        if (recipeJson.input1 == null || recipeJson.input2 == null || recipeJson.input3 == null ||
                recipeJson.input4 == null || recipeJson.result == null) {
            throw new JsonSyntaxException("A required attribute is missing!");
        }

        // Ingredient easily turns JsonObjects of the correct format into Ingredients
        Ingredient input1 = Ingredient.ofStacks(new ItemStack(Registry.ITEM.getOrEmpty(new Identifier(recipeJson.input1))
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.input1))));
        Ingredient input2 = Ingredient.ofStacks(new ItemStack(Registry.ITEM.getOrEmpty(new Identifier(recipeJson.input2))
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.input2))));
        Ingredient input3 = Ingredient.ofStacks(new ItemStack(Registry.ITEM.getOrEmpty(new Identifier(recipeJson.input3))
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.input3))));
        Ingredient input4 = Ingredient.ofStacks(new ItemStack(Registry.ITEM.getOrEmpty(new Identifier(recipeJson.input4))
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.input4))));
        ItemStack output = Ingredient.fromJson(recipeJson.result).getMatchingStacks()[0];
//        if(output.getItem() == Items.ENCHANTED_BOOK){
//            JsonElement characteristics = recipeJson.result.get("EnchantedBookCharacteristics");
//            if(characteristics != null){
//                String[] strings = characteristics.getAsString().split("\"");
//                System.out.println("enchant " + Arrays.toString(strings));
//                String enchant_element = strings[3];
//                String lvl_element = strings[6];
//                System.out.println("enchant " + enchant_element + lvl_element);
//                output.addEnchantment(ModEnchants.getEnchant(enchant_element), Integer.parseInt(lvl_element));
//            }
//            System.out.println("json null" + recipeJson.result.get("EnchantedBookCharacteristics"));
//        }
        return new ModifiersCraft(input1, input2, input3, input4, output, id);
    }

    @Override
    // Turns Recipe into PacketByteBuf
    public void write(PacketByteBuf packetData, @NotNull ModifiersCraft recipe) {
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