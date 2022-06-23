package fr.vana_mod.nicofighter45.block;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import fr.vana_mod.nicofighter45.main.VanadiumMod;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.Arrays;

public class ModOreGeneration {

    //command to delete stone block behind you to see the generation of ores
    //fill ~-8 0 ~-8 ~8 ~ ~8 minecraft:air replace minecraft:stone

    public static void generateOres(){

        //génération du minearais de vanadium
        ConfiguredFeature<?, ?> VANADIUM_ORE_FEATURE = new ConfiguredFeature<>
                (Feature.ORE, new OreFeatureConfig(new BlockMatchRuleTest(Blocks.END_STONE),
                        ModBlocks.VANADIUM_ORE.getDefaultState(), 3));
        PlacedFeature VANADIUM_ORE_GENERATION = new PlacedFeature(
                RegistryEntry.of(VANADIUM_ORE_FEATURE),
                Arrays.asList(CountPlacementModifier.of(4), // number of veins per chunk
                        SquarePlacementModifier.of(), // spreading horizontally
                        HeightRangePlacementModifier.uniform(YOffset.aboveBottom(50), YOffset.belowTop(70)))); // height

        Identifier VANADIUM_ORE_ID = new Identifier(VanadiumMod.MODID, "vanadium_ore_gen");
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, VANADIUM_ORE_ID, VANADIUM_ORE_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, VANADIUM_ORE_ID, VANADIUM_ORE_GENERATION);
        BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(), GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY, VANADIUM_ORE_ID));

        //génération du minearai de tungsten
        ConfiguredFeature<?, ?> TUNGSTEN_ORE_FEATURE = new ConfiguredFeature<>
                (Feature.ORE, new OreFeatureConfig(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES,
                        ModBlocks.TUNGSTEN_ORE.getDefaultState(), 8));
        PlacedFeature TUNGSTEN_ORE_GENERATION = new PlacedFeature(
                RegistryEntry.of(TUNGSTEN_ORE_FEATURE),
                Arrays.asList(CountPlacementModifier.of(4), // number of veins per chunk
                        SquarePlacementModifier.of(), // spreading horizontally
                        HeightRangePlacementModifier.uniform(YOffset.aboveBottom(-50), YOffset.belowTop(-30)))); // height

        Identifier TUNGSTEN_ORE_ID = new Identifier(VanadiumMod.MODID, "tungsten_ore_gen");
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, TUNGSTEN_ORE_ID, TUNGSTEN_ORE_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, TUNGSTEN_ORE_ID, TUNGSTEN_ORE_GENERATION);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY, TUNGSTEN_ORE_ID));

        //génération du minerais d'argent
        ConfiguredFeature<?, ?> SILVER_ORE_FEATURE = new ConfiguredFeature<>
                (Feature.ORE, new OreFeatureConfig(OreConfiguredFeatures.NETHERRACK,
                        ModBlocks.SILVER_ORE.getDefaultState(), 4));
        PlacedFeature SILVER_ORE_GENERATION = new PlacedFeature(
                RegistryEntry.of(SILVER_ORE_FEATURE),
                Arrays.asList(CountPlacementModifier.of(4), // number of veins per chunk
                        SquarePlacementModifier.of(), // spreading horizontally
                        HeightRangePlacementModifier.uniform(YOffset.aboveBottom(30), YOffset.belowTop(40)))); // height

        Identifier SILVER_ORE_ID = new Identifier(VanadiumMod.MODID, "silver_ore_gen");
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, SILVER_ORE_ID, SILVER_ORE_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, SILVER_ORE_ID, SILVER_ORE_GENERATION);
        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY, SILVER_ORE_ID));

        //génération minerais étain
        ConfiguredFeature<?, ?> TIN_ORE_FEATURE = new ConfiguredFeature<>
                (Feature.ORE, new OreFeatureConfig(OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
                        ModBlocks.TIN_ORE.getDefaultState(), 8));
        PlacedFeature TIN_ORE_GENERATION = new PlacedFeature(
                RegistryEntry.of(TIN_ORE_FEATURE),
                Arrays.asList(CountPlacementModifier.of(25), // number of veins per chunk
                        SquarePlacementModifier.of(), // spreading horizontally
                        HeightRangePlacementModifier.uniform(YOffset.aboveBottom(20), YOffset.belowTop(50)))); // height

        Identifier TIN_ORE_ID = new Identifier(VanadiumMod.MODID, "tin_ore_gen");
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, TIN_ORE_ID, TIN_ORE_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, TIN_ORE_ID, TIN_ORE_GENERATION);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY, TIN_ORE_ID));
    }

}