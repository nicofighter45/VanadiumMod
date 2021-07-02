package fr.vana_mod.nicofighter45.block;

import fr.vana_mod.nicofighter45.main.VanadiumMod;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static final Block VANADIUM_ORE = new Block(FabricBlockSettings.of(Material.METAL).strength(50.0F, 0.7F).sounds(BlockSoundGroup.METAL).breakByTool(FabricToolTags.PICKAXES, 4).requiresTool());
    public static final Block TUNGSTEN_ORE = new Block(FabricBlockSettings.of(Material.METAL).strength(10, 0.4f).sounds(BlockSoundGroup.METAL).breakByTool(FabricToolTags.PICKAXES, 2).requiresTool());
    public static final Block SILVER_ORE = new Block(FabricBlockSettings.of(Material.METAL).strength(20, 0.5f).sounds(BlockSoundGroup.METAL).breakByTool(FabricToolTags.PICKAXES, 3).requiresTool());
    public static final Block COPPER_ORE = new Block(FabricBlockSettings.of(Material.METAL).strength(5, 0.2f).sounds(BlockSoundGroup.METAL).breakByTool(FabricToolTags.PICKAXES, 1).requiresTool());
    public static final Block TIN_ORE = new Block(FabricBlockSettings.of(Material.METAL).strength(8, 0.3f).sounds(BlockSoundGroup.METAL).breakByTool(FabricToolTags.PICKAXES, 2).requiresTool());

    public static final Block VANADIUM_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(15, 0.8f).sounds(BlockSoundGroup.METAL).breakByTool(FabricToolTags.PICKAXES, 3).requiresTool());
    public static final Block TUNGSTEN_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(10, 0.5f).sounds(BlockSoundGroup.METAL).breakByTool(FabricToolTags.PICKAXES, 2).requiresTool());
    public static final Block TIN_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(9, 0.4f).sounds(BlockSoundGroup.METAL).breakByTool(FabricToolTags.PICKAXES, 2).requiresTool());
    public static final Block COPPER_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(8, 0.3f).sounds(BlockSoundGroup.METAL).breakByTool(FabricToolTags.PICKAXES, 2).requiresTool());
    public static final Block STEAM_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(10, 0.6f).sounds(BlockSoundGroup.METAL).breakByTool(FabricToolTags.PICKAXES, 2).requiresTool());

    public static void registerAll() {
        registerNewBlock("vanadium_ore", VANADIUM_ORE);
        registerNewBlock("tungsten_ore", TUNGSTEN_ORE);
        registerNewBlock("silver_ore", SILVER_ORE);
        registerNewBlock("copper_ore", COPPER_ORE);
        registerNewBlock("tin_ore", TIN_ORE);

        registerNewBlock("vanadium_block", VANADIUM_BLOCK);
        registerNewBlock("tungsten_block", TUNGSTEN_BLOCK);
        registerNewBlock("tin_block", TIN_BLOCK);
        registerNewBlock("copper_block", COPPER_BLOCK);
        registerNewBlock("steam_block", STEAM_BLOCK);

        ModOreGeneration.generateOres();
        ModBlocksItem.registerAll();
    }

    private static void registerNewBlock(String name, Block block){
        Registry.register(Registry.BLOCK, new Identifier(VanadiumMod.MODID, name), block);
    }
}