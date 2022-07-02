package fr.vana_mod.nicofighter45.block;

import fr.vana_mod.nicofighter45.main.CommonInitializer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocksItem {

    private final static Item.Settings settings = new Item.Settings().group(CommonInitializer.VANADIUM_GROUP);

    public static final BlockItem VANADIUM_ORE_ITEM = new BlockItem(ModBlocks.VANADIUM_ORE, settings.fireproof());
    public static final BlockItem TUNGSTEN_ORE_ITEM = new BlockItem(ModBlocks.TUNGSTEN_ORE, settings);
    public static final BlockItem SILVER_ORE_ITEM = new BlockItem(ModBlocks.SILVER_ORE, settings);
    public static final BlockItem TIN_ORE_ITEM = new BlockItem(ModBlocks.TIN_ORE, settings);

    public static final BlockItem VANADIUM_BLOCK_ITEM = new BlockItem(ModBlocks.VANADIUM_BLOCK, settings.fireproof());
    public static final BlockItem TIN_BLOCK_ITEM = new BlockItem(ModBlocks.TIN_BLOCK, settings);
    public static final BlockItem TUNGSTEN_BLOCK_ITEM = new BlockItem(ModBlocks.TUNGSTEN_BLOCK, settings);
    public static final BlockItem STEEL_BLOCK_ITEM = new BlockItem(ModBlocks.STEEL_BLOCK, settings);
    public static final BlockItem SILVER_BLOCK_ITEM = new BlockItem(ModBlocks.SILVER_BLOCK, settings);
    public static final BlockItem MAMADITE_BLOCK_ITEM = new BlockItem(ModBlocks.MAMADITE_BLOCK, settings);

    public static final BlockItem MODIFIERS_TABLE_BLOCK_ITEM = new BlockItem(ModBlocks.MODIFIERS_TABLE_BLOCK, settings);

    public static final BlockItem ENCHANTER_BLOCK_ITEM = new BlockItem(ModBlocks.ENCHANTER_BLOCK, settings);

    public static void registerAll() {
        registerNewItemBlock("vanadium_ore_item", VANADIUM_ORE_ITEM);
        registerNewItemBlock("tungsten_ore_item", TUNGSTEN_ORE_ITEM);
        registerNewItemBlock("silver_ore_item", SILVER_ORE_ITEM);
        registerNewItemBlock("tin_ore_item", TIN_ORE_ITEM);

        registerNewItemBlock("vanadium_block_item", VANADIUM_BLOCK_ITEM);
        registerNewItemBlock("tin_block_item", TIN_BLOCK_ITEM);
        registerNewItemBlock("tungsten_block_item", TUNGSTEN_BLOCK_ITEM);
        registerNewItemBlock("steel_block_item", STEEL_BLOCK_ITEM);
        registerNewItemBlock("silver_block_item", SILVER_BLOCK_ITEM);
        registerNewItemBlock("mamadite_block_item", MAMADITE_BLOCK_ITEM);

        registerNewItemBlock("modifiers_table_block_item", MODIFIERS_TABLE_BLOCK_ITEM);
        registerNewItemBlock("enchanter_block_item", ENCHANTER_BLOCK_ITEM);
    }

    private static void registerNewItemBlock(String name, BlockItem block){
        Registry.register(Registry.ITEM, new Identifier(CommonInitializer.MODID, name), block);
    }
}