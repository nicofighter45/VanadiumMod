package fr.vana_mod.nicofighter45.main;

import fr.vana_mod.nicofighter45.block.modifiertable.craft.ModifiersRecipe;
import fr.vana_mod.nicofighter45.block.modifiertable.craft.ModifiersRecipeSerializer;
import fr.vana_mod.nicofighter45.block.modifiertable.ModifiersTableScreenHandler;
import fr.vana_mod.nicofighter45.main.server.Command;
import net.minecraft.item.*;
import fr.vana_mod.nicofighter45.block.ModBlocks;
import fr.vana_mod.nicofighter45.items.ModItems;
import fr.vana_mod.nicofighter45.items.enchantment.ModEnchants;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class VanadiumMod implements ModInitializer {


    //the mod id used to create new blocks and items
    public static final String MODID = "vana-mod";
    public static ScreenHandlerType<ModifiersTableScreenHandler> MODIFIERS_TABLE_SCREEN_HANDLER;
    public static RecipeType<ModifiersRecipe> MODIFIERS_RECIPE_TYPE = ModifiersRecipe.Type.INSTANCE;

    @Override
    public void onInitialize() {
        //register all items and blocks
        registers();

        //register screen for modifiers table
        MODIFIERS_TABLE_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MODID, "modifiers_table_screen_handler"), ModifiersTableScreenHandler::new);
        Registry.register(Registry.RECIPE_SERIALIZER, ModifiersRecipeSerializer.ID, ModifiersRecipeSerializer.INSTANCE);
        MODIFIERS_RECIPE_TYPE = Registry.register(Registry.RECIPE_TYPE, new Identifier(VanadiumMod.MODID, ModifiersRecipe.Type.ID), MODIFIERS_RECIPE_TYPE);

        //register command
        Command.registerAllCommands();
    }

    private void registers() {
        ModItems.registerAll();
        ModEnchants.registerAll();
        ModBlocks.registerAll();
        Listeners.registerAll();
    }

    //item group
    public static final ItemGroup VANADIUM_GROUP = FabricItemGroupBuilder.create(new Identifier(MODID, "vanadium"))
            .icon(() -> new ItemStack(ModItems.VANADIUM_INGOT)).build();

}