package fr.vana_mod.nicofighter45.block;

import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.heightprovider.ConstantHeightProvider;
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
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.RangeFeatureConfig;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;

public class ModOreGeneration {

    //command to delete stone block behind you to see the generation of ores
    //fill ~-8 0 ~-8 ~8 ~ ~8 minecraft:air replace minecraft:stone

    public static void generateOres(){

        //génération du minearais de vanadium
        ConfiguredFeature<?, ?> VANADIUM_ORE_GENERATION = Feature.ORE
                .configure(new OreFeatureConfig(
                        new BlockMatchRuleTest(Blocks.END_STONE), // base block is endstone in the end biomes
                        ModBlocks.VANADIUM_ORE.getDefaultState(),
                        1))
                .range(new RangeFeatureConfig(
                        // You can also use one of the other height providers if you don't want a uniform distribution
                        UniformHeightProvider.create(YOffset.aboveBottom(55), YOffset.fixed(70)))) // Inclusive min and max height
                .spreadHorizontally()
                .repeat(1); // Number of veins per chunk


        RegistryKey<ConfiguredFeature<?, ?>> vanadium_ore = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
                new Identifier(VanadiumMod.MODID, "vanadium_ore_gen"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, vanadium_ore.getValue(), VANADIUM_ORE_GENERATION);
        BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(), GenerationStep.Feature.UNDERGROUND_ORES, vanadium_ore);

        //génération du minearai de tungsten
        ConfiguredFeature<?, ?> TUNGSTEN_ORE_GENERATION = Feature.ORE
                .configure(new OreFeatureConfig(
                        OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                        ModBlocks.TUNGSTEN_ORE.getDefaultState(),
                        8))
                .range(new RangeFeatureConfig(
                        // You can also use one of the other height providers if you don't want a uniform distribution
                        UniformHeightProvider.create(YOffset.aboveBottom(5), YOffset.fixed(15)))) // Inclusive min and max height
                .spreadHorizontally()
                .repeat(2); // Number of veins per chunk

        RegistryKey<ConfiguredFeature<?, ?>> tungsten_ore = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
                new Identifier(VanadiumMod.MODID, "tungsten_ore_gen"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, tungsten_ore.getValue(), TUNGSTEN_ORE_GENERATION);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, tungsten_ore);

        //génération du minerais d'argent
        ConfiguredFeature<?, ?> SILVER_ORE_GENERATION = Feature.ORE
                .configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_NETHER,
                        ModBlocks.SILVER_ORE.getDefaultState(),
                        4))
                .range(new RangeFeatureConfig(
                        UniformHeightProvider.create(YOffset.fixed(30), YOffset.fixed(40))))
                .spreadHorizontally()
                .repeat(4);

        RegistryKey<ConfiguredFeature<?, ?>> silver_ore = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
                new Identifier(VanadiumMod.MODID, "silver_ore_gen"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, silver_ore.getValue(), SILVER_ORE_GENERATION);
        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.UNDERGROUND_ORES, silver_ore);

        //génération minerais étain
        ConfiguredFeature<?, ?> TIN_ORE_GENERATION = Feature.ORE
                .configure(new OreFeatureConfig(
                        OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                        ModBlocks.TIN_ORE.getDefaultState(),
                        8)) // Vein size
                .range(new RangeFeatureConfig(
                        // You can also use one of the other height providers if you don't want a uniform distribution
                        UniformHeightProvider.create(YOffset.aboveBottom(20), YOffset.fixed(50)))) // Inclusive min and max height
                .spreadHorizontally()
                .repeat(6); // Number of veins per chunk

        RegistryKey<ConfiguredFeature<?, ?>> tin_ore = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
                new Identifier(VanadiumMod.MODID, "tin_ore_gen"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, tin_ore.getValue(), TIN_ORE_GENERATION);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, tin_ore);
    }

}