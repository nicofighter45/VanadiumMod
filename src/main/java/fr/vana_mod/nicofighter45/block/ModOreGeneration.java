package fr.vana_mod.nicofighter45.block;

import fr.vana_mod.nicofighter45.main.CommonInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.function.Predicate;

public class ModOreGeneration {

    //fill ~-8 0 ~-8 ~8 ~ ~8 minecraft:air replace minecraft:stone

    public static void generateOres() {

        generateOre("vanadium", BiomeSelectors.foundInTheEnd());
        generateOre("tungsten", BiomeSelectors.foundInOverworld());
        generateOre("silver", BiomeSelectors.foundInTheNether());
        generateOre("tin", BiomeSelectors.foundInOverworld());  // todo non working gen

    }

    private static void generateOre(String name, Predicate<BiomeSelectionContext> biomeSelectionContextPredicate) {
        RegistryKey<PlacedFeature> PLACED_FEATURE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(CommonInitializer.MODID, "ore_" + name));

        BiomeModifications.create(new Identifier(CommonInitializer.MODID, "features"))
                .add(ModificationPhase.ADDITIONS, biomeSelectionContextPredicate,
                        (biomeSelectionContext, biomeModificationContext) ->
                                biomeModificationContext.getGenerationSettings()
                                        .addFeature(GenerationStep.Feature.UNDERGROUND_ORES, PLACED_FEATURE));
    }


}