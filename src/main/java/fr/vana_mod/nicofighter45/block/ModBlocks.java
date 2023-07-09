package fr.vana_mod.nicofighter45.block;


import fr.vana_mod.nicofighter45.main.CommonInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block VANADIUM_ORE = new Block(FabricBlockSettings.create().strength(50.0F, 0.7F).sounds(BlockSoundGroup.METAL).requiresTool());
    public static final Block TUNGSTEN_ORE = new Block(FabricBlockSettings.create().strength(10, 0.4f).sounds(BlockSoundGroup.METAL).requiresTool());
    public static final Block SILVER_ORE = new Block(FabricBlockSettings.create().strength(20, 0.5f).sounds(BlockSoundGroup.METAL).requiresTool());
    public static final Block TIN_ORE = new Block(FabricBlockSettings.create().strength(8, 0.3f).sounds(BlockSoundGroup.METAL).requiresTool());

    public static final Block VANADIUM_BLOCK = new Block(FabricBlockSettings.create().strength(15, 0.8f).sounds(BlockSoundGroup.METAL).requiresTool());
    public static final Block TUNGSTEN_BLOCK = new Block(FabricBlockSettings.create().strength(10, 0.5f).sounds(BlockSoundGroup.METAL).requiresTool());
    public static final Block TIN_BLOCK = new Block(FabricBlockSettings.create().strength(9, 0.4f).sounds(BlockSoundGroup.METAL).requiresTool());
    public static final Block STEEL_BLOCK = new Block(FabricBlockSettings.create().strength(10, 0.6f).sounds(BlockSoundGroup.METAL).requiresTool());
    public static final Block SILVER_BLOCK = new Block(FabricBlockSettings.create().strength(10, 0.5f).sounds(BlockSoundGroup.METAL).requiresTool());
    public static final Block MAMADE_BLOCK = new Block(FabricBlockSettings.create().strength(18, 0.8f).sounds(BlockSoundGroup.METAL).requiresTool());

    public static void registerAll() {
        registerNewBlock("vanadium_ore", VANADIUM_ORE, true);
        registerNewBlock("tungsten_ore", TUNGSTEN_ORE);
        registerNewBlock("silver_ore", SILVER_ORE);
        registerNewBlock("tin_ore", TIN_ORE);

        registerNewBlock("vanadium_block", VANADIUM_BLOCK, true);
        registerNewBlock("tungsten_block", TUNGSTEN_BLOCK);
        registerNewBlock("tin_block", TIN_BLOCK);
        registerNewBlock("steel_block", STEEL_BLOCK);
        registerNewBlock("silver_block", SILVER_BLOCK);
        registerNewBlock("mamade_block", MAMADE_BLOCK);

    }

    private static void registerNewBlock(String name, Block block) {
        registerNewBlock(name, block, false);
    }

    private static void registerNewBlock(String name, Block block, boolean fireproof) {
        Registry.register(Registries.BLOCK, new Identifier(CommonInitializer.MODID, name), block);
        if (fireproof){
            Registry.register(Registries.ITEM, new Identifier(CommonInitializer.MODID, name),
                    new BlockItem(block, CommonInitializer.settings().fireproof()));
        }else {
            Registry.register(Registries.ITEM, new Identifier(CommonInitializer.MODID, name),
                    new BlockItem(block, CommonInitializer.settings()));
        }
    }

}