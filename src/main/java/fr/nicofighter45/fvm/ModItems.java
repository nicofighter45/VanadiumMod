package fr.nicofighter45.fvm;

import fr.nicofighter45.fvm.block.VanadiumOre;
import fr.nicofighter45.fvm.items.armor.EmeraldArmorMaterials;
import fr.nicofighter45.fvm.items.armor.VanadiumArmorMaterials;
import fr.nicofighter45.fvm.items.enchantment.*;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class ModItems {

    //vanadium
    public static final Block VANADIUM_ORE = new VanadiumOre();
    public static final Item VANADIUM_NUGGET = new Item(new Item.Settings().group(ItemGroup.MATERIALS).maxCount(64));
    public static final Item VANADIUM_INGOT = new Item(new Item.Settings().group(ItemGroup.MATERIALS).maxCount(64));

    //item de craft
    public static final Item VANADIUM_STICK = new Item(new Item.Settings().group(ItemGroup.MATERIALS).maxCount(16));
    public static final Item VANADIUM_HEART = new Item(new Item.Settings().group(ItemGroup.MATERIALS).maxCount(8));
    public static final Item EMERALD_HEART = new Item(new Item.Settings().group(ItemGroup.MATERIALS).maxCount(8));

    //enchants
    public static Enchantment FASTER = new FasterEnchantment(
            Enchantment.Rarity.VERY_RARE,
            EnchantmentTarget.ARMOR,
            new EquipmentSlot[]{
                    EquipmentSlot.LEGS
            }
    );
    public static Enchantment JUMPER = new JumperEnchantment(
            Enchantment.Rarity.VERY_RARE,
            EnchantmentTarget.ARMOR,
            new EquipmentSlot[]{
                    EquipmentSlot.FEET
            }
    );
    public static Enchantment HASTER = new HasterEnchantment(
            Enchantment.Rarity.VERY_RARE,
            EnchantmentTarget.ARMOR,
            new EquipmentSlot[]{
                    EquipmentSlot.HEAD
            }
    );
    public static Enchantment STRENGHTER = new StrenghterEnchantment(
            Enchantment.Rarity.VERY_RARE,
            EnchantmentTarget.ARMOR,
            new EquipmentSlot[]{
                    EquipmentSlot.HEAD
            }
    );
    public static Enchantment RESISTANCER = new ResistancerEnchantment(
            Enchantment.Rarity.VERY_RARE,
            EnchantmentTarget.ARMOR,
            new EquipmentSlot[]{
                    EquipmentSlot.LEGS
            }
    );

    //armure en vanadium
    public static final Item VANADIUM_HELMET = new ArmorItem(VanadiumArmorMaterials.VANADIUM, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item VANADIUM_CHESTPLATE = new ArmorItem(VanadiumArmorMaterials.VANADIUM, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item VANADIUM_LEGGINGS = new ArmorItem(VanadiumArmorMaterials.VANADIUM, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item VANADIUM_BOOTS = new ArmorItem(VanadiumArmorMaterials.VANADIUM, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.TOOLS));

    //armure en emeraude
    public static final Item EMERALD_HELMET = new ArmorItem(EmeraldArmorMaterials.EMERALD, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item EMERALD_CHESTPLATE = new ArmorItem(EmeraldArmorMaterials.EMERALD, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item EMERALD_LEGGINGS = new ArmorItem(EmeraldArmorMaterials.EMERALD, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item EMERALD_BOOTS = new ArmorItem(EmeraldArmorMaterials.EMERALD, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.TOOLS));


    public static void registerAll(){

        Registry.register(Registry.BLOCK, new Identifier(FVM.MODID, "vanadium_ore"), VANADIUM_ORE);
        Registry.register(Registry.ITEM, new Identifier(FVM.MODID, "vanadium_ore"), new BlockItem(VANADIUM_ORE, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier(FVM.MODID, "vanadium_nugget"), VANADIUM_NUGGET);
        Registry.register(Registry.ITEM, new Identifier(FVM.MODID, "vanadium_ingot"), VANADIUM_INGOT);

        Registry.register(Registry.ITEM, new Identifier(FVM.MODID, "vanadium_stick"), VANADIUM_STICK);
        Registry.register(Registry.ITEM, new Identifier(FVM.MODID, "vanadium_heart"), VANADIUM_HEART);
        Registry.register(Registry.ITEM, new Identifier(FVM.MODID, "emerald_heart"), EMERALD_HEART);

        Registry.register(Registry.ENCHANTMENT, new Identifier(FVM.MODID, "faster"), FASTER);
        Registry.register(Registry.ENCHANTMENT, new Identifier(FVM.MODID, "jumper"), JUMPER);
        Registry.register(Registry.ENCHANTMENT, new Identifier(FVM.MODID, "haster"), HASTER);
        Registry.register(Registry.ENCHANTMENT, new Identifier(FVM.MODID, "strenghter"), STRENGHTER);
        Registry.register(Registry.ENCHANTMENT, new Identifier(FVM.MODID, "resistancer"), RESISTANCER);

        Registry.register(Registry.ITEM, new Identifier(FVM.MODID, "vanadium_helmet"), VANADIUM_HELMET);
        Registry.register(Registry.ITEM, new Identifier(FVM.MODID, "vanadium_chestplate"), VANADIUM_CHESTPLATE);
        Registry.register(Registry.ITEM, new Identifier(FVM.MODID, "vanadium_leggings"), VANADIUM_LEGGINGS);
        Registry.register(Registry.ITEM, new Identifier(FVM.MODID, "vanadium_boots"), VANADIUM_BOOTS);

        Registry.register(Registry.ITEM, new Identifier(FVM.MODID, "emerald_helmet"), EMERALD_HELMET);
        Registry.register(Registry.ITEM, new Identifier(FVM.MODID, "emerald_chestplate"), EMERALD_CHESTPLATE);
        Registry.register(Registry.ITEM, new Identifier(FVM.MODID, "emerald_leggings"), EMERALD_LEGGINGS);
        Registry.register(Registry.ITEM, new Identifier(FVM.MODID, "emerald_boots"), EMERALD_BOOTS);

        //génération du minearais de vanadium
        ConfiguredFeature<?, ?> VANADIUM_ORE_GENERATION = Feature.ORE
                .configure(new OreFeatureConfig(
                        new BlockMatchRuleTest(Blocks.END_STONE), // base block is endstone in the end biomes
                        VANADIUM_ORE.getDefaultState(),

                        4))
                .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(
                        55,
                        55,
                        70)))
                .spreadHorizontally()
                .repeat(1);


        RegistryKey<ConfiguredFeature<?, ?>> vanadium_ore = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN,
                new Identifier(FVM.MODID, "vanadium_ore"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, vanadium_ore.getValue(), VANADIUM_ORE_GENERATION);
        BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(), GenerationStep.Feature.UNDERGROUND_ORES, vanadium_ore);
    }

}