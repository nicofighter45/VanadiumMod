package fr.vana_mod.nicofighter45.block;


import fr.vana_mod.nicofighter45.block.enchanter.EnchanterBlock;
import fr.vana_mod.nicofighter45.block.enchanter.EnchanterBlockEntity;
import fr.vana_mod.nicofighter45.block.enchanter.EnchanterScreenHandler;
import fr.vana_mod.nicofighter45.block.modifiertable.ModifiersTableBlock;
import fr.vana_mod.nicofighter45.block.modifiertable.ModifiersTableBlockEntity;
import fr.vana_mod.nicofighter45.block.modifiertable.ModifiersTableScreenHandler;
import fr.vana_mod.nicofighter45.block.modifiertable.craft.ModifiersRecipe;
import fr.vana_mod.nicofighter45.block.modifiertable.craft.ModifiersRecipeSerializer;
import fr.vana_mod.nicofighter45.gui.CustomPlayerManagementScreenHandler;
import fr.vana_mod.nicofighter45.main.CommonInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static final Block VANADIUM_ORE = new Block(FabricBlockSettings.of(Material.METAL).strength(50.0F, 0.7F).sounds(BlockSoundGroup.METAL).requiresTool());
    public static final Block TUNGSTEN_ORE = new Block(FabricBlockSettings.of(Material.METAL).strength(10, 0.4f).sounds(BlockSoundGroup.METAL).requiresTool());
    public static final Block SILVER_ORE = new Block(FabricBlockSettings.of(Material.METAL).strength(20, 0.5f).sounds(BlockSoundGroup.METAL).requiresTool());
    public static final Block TIN_ORE = new Block(FabricBlockSettings.of(Material.METAL).strength(8, 0.3f).sounds(BlockSoundGroup.METAL).requiresTool());

    public static final Block VANADIUM_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(15, 0.8f).sounds(BlockSoundGroup.METAL).requiresTool());
    public static final Block TUNGSTEN_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(10, 0.5f).sounds(BlockSoundGroup.METAL).requiresTool());
    public static final Block TIN_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(9, 0.4f).sounds(BlockSoundGroup.METAL).requiresTool());
    public static final Block STEEL_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(10, 0.6f).sounds(BlockSoundGroup.METAL).requiresTool());
    public static final Block SILVER_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(10, 0.5f).sounds(BlockSoundGroup.METAL).requiresTool());
    public static final Block MAMADITE_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(18, 0.8f).sounds(BlockSoundGroup.METAL).requiresTool());

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

    public static void registerAll() {
        registerNewBlock("vanadium_ore", VANADIUM_ORE);
        registerNewBlock("tungsten_ore", TUNGSTEN_ORE);
        registerNewBlock("silver_ore", SILVER_ORE);
        registerNewBlock("tin_ore", TIN_ORE);

        registerNewBlock("vanadium_block", VANADIUM_BLOCK);
        registerNewBlock("tungsten_block", TUNGSTEN_BLOCK);
        registerNewBlock("tin_block", TIN_BLOCK);
        registerNewBlock("steel_block", STEEL_BLOCK);
        registerNewBlock("silver_block", SILVER_BLOCK);
        registerNewBlock("mamadite_bock", MAMADITE_BLOCK);

        registerNewBlock("modifiers_table_block", MODIFIERS_TABLE_BLOCK);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CommonInitializer.MODID, "modifiers_table_block_entity_type"), MODIFIERS_TABLE_BLOCK_ENTITY_TYPE);
        registerNewBlock("enchanter_block", ENCHANTER_BLOCK);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CommonInitializer.MODID, "enchanter_block_entity_type"), ENCHANTER_BLOCK_ENTITY_TYPE);

        ModOreGeneration.generateOres();
        ModBlocksItem.registerAll();

        MODIFIERS_TABLE_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(CommonInitializer.MODID, "modifiers_table_screen_handler"), ModifiersTableScreenHandler::new);
        Registry.register(Registry.RECIPE_SERIALIZER, ModifiersRecipeSerializer.ID, ModifiersRecipeSerializer.INSTANCE);
        MODIFIERS_RECIPE_TYPE = Registry.register(Registry.RECIPE_TYPE, new Identifier(CommonInitializer.MODID, ModifiersRecipe.Type.ID), MODIFIERS_RECIPE_TYPE);

        ENCHANTER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(CommonInitializer.MODID, "enchanter_screen_handler"), EnchanterScreenHandler::new);

        CUSTOM_PLAYER_MANAGER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(CommonInitializer.MODID, "custom_player_manager_screen_handler"), CustomPlayerManagementScreenHandler::new);

    }

    private static void registerNewBlock(String name, Block block){
        Registry.register(Registry.BLOCK, new Identifier(CommonInitializer.MODID, name), block);
    }

}