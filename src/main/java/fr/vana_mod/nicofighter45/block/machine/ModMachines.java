package fr.vana_mod.nicofighter45.block.machine;

import fr.vana_mod.nicofighter45.block.enchanter.EnchanterBlock;
import fr.vana_mod.nicofighter45.block.enchanter.EnchanterBlockEntity;
import fr.vana_mod.nicofighter45.block.enchanter.EnchanterScreenHandler;
import fr.vana_mod.nicofighter45.block.machine.high_furnace.HighFurnaceBlock;
import fr.vana_mod.nicofighter45.block.machine.high_furnace.HighFurnaceBlockEntity;
import fr.vana_mod.nicofighter45.block.machine.high_furnace.HighFurnaceScreenHandler;
import fr.vana_mod.nicofighter45.block.modifiertable.ModifiersTableBlock;
import fr.vana_mod.nicofighter45.block.modifiertable.ModifiersTableBlockEntity;
import fr.vana_mod.nicofighter45.block.modifiertable.ModifiersTableScreenHandler;
import fr.vana_mod.nicofighter45.block.modifiertable.craft.ModifiersRecipe;
import fr.vana_mod.nicofighter45.block.modifiertable.craft.ModifiersRecipeSerializer;
import fr.vana_mod.nicofighter45.gui.CustomPlayerManagementScreenHandler;
import fr.vana_mod.nicofighter45.main.CommonInitializer;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModMachines {

    public static Block MODIFIERS_TABLE_BLOCK = new ModifiersTableBlock();
    public static BlockEntityType<ModifiersTableBlockEntity> MODIFIERS_TABLE_BLOCK_ENTITY_TYPE =
            BlockEntityType.Builder.create(ModifiersTableBlockEntity::new, MODIFIERS_TABLE_BLOCK).build(null);
    public static ScreenHandlerType<ModifiersTableScreenHandler> MODIFIERS_TABLE_SCREEN_HANDLER;
    public static RecipeType<ModifiersRecipe> MODIFIERS_RECIPE_TYPE = ModifiersRecipe.Type.INSTANCE;

    public static Block ENCHANTER_BLOCK = new EnchanterBlock();
    public static BlockEntityType<EnchanterBlockEntity> ENCHANTER_BLOCK_ENTITY_TYPE =
            BlockEntityType.Builder.create(EnchanterBlockEntity::new, ENCHANTER_BLOCK).build(null);
    public static ScreenHandlerType<EnchanterScreenHandler> ENCHANTER_SCREEN_HANDLER;

    public static ScreenHandlerType<CustomPlayerManagementScreenHandler> CUSTOM_PLAYER_MANAGER_SCREEN_HANDLER;

    public static Block HIGH_FURNACE_BLOCK = new HighFurnaceBlock();
    public static BlockEntityType<HighFurnaceBlockEntity> HIGH_FURNACE_BLOCK_ENTITY_TYPE =
            BlockEntityType.Builder.create(HighFurnaceBlockEntity::new, HIGH_FURNACE_BLOCK).build(null);

    public static ScreenHandlerType<HighFurnaceScreenHandler> HIGH_FURNACE_SCREEN_HANDLER;

    public static void registerAll(){
        registerNewBlock("modifiers_table_block", MODIFIERS_TABLE_BLOCK);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CommonInitializer.MODID, "modifiers_table_block_entity_type"), MODIFIERS_TABLE_BLOCK_ENTITY_TYPE);
        registerNewBlock("enchanter_block", ENCHANTER_BLOCK);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CommonInitializer.MODID, "enchanter_block_entity_type"), ENCHANTER_BLOCK_ENTITY_TYPE);
        registerNewBlock("high_furnace_block", HIGH_FURNACE_BLOCK);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CommonInitializer.MODID, "high_furnace_block_entity_type"), HIGH_FURNACE_BLOCK_ENTITY_TYPE);

        MODIFIERS_TABLE_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(CommonInitializer.MODID, "modifiers_table_screen_handler"), ModifiersTableScreenHandler::new);
        Registry.register(Registry.RECIPE_SERIALIZER, ModifiersRecipeSerializer.ID, ModifiersRecipeSerializer.INSTANCE);
        MODIFIERS_RECIPE_TYPE = Registry.register(Registry.RECIPE_TYPE, new Identifier(CommonInitializer.MODID, ModifiersRecipe.Type.ID), MODIFIERS_RECIPE_TYPE);

        ENCHANTER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(CommonInitializer.MODID, "enchanter_screen_handler"), EnchanterScreenHandler::new);

        CUSTOM_PLAYER_MANAGER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(CommonInitializer.MODID, "custom_player_manager_screen_handler"), CustomPlayerManagementScreenHandler::new);

        HIGH_FURNACE_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(CommonInitializer.MODID, "high_furnace_screen_handler"), HighFurnaceScreenHandler::new);
    }

    private static void registerNewBlock(String name, Block block){
        Registry.register(Registry.BLOCK, new Identifier(CommonInitializer.MODID, name), block);
    }

}