package fr.vana_mod.nicofighter45.items;

import fr.vana_mod.nicofighter45.block.ModBlocks;
import fr.vana_mod.nicofighter45.items.armor.ModArmorsMaterial;
import fr.vana_mod.nicofighter45.items.custom.*;
import fr.vana_mod.nicofighter45.items.tool_material.ModToolMaterial;
import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.main.CommonInitializer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModItems {

    public static final Item STEEL_INGOT = new Item(CommonInitializer.settings());
    public static final Item TIN_INGOT = new Item(CommonInitializer.settings());
    public static final Item SILVER_INGOT = new Item((CommonInitializer.settings()));
    public static final Item TUNGSTEN_INGOT = new Item(CommonInitializer.settings());
    public static final Item VANADIUM_INGOT = new Item(CommonInitializer.settings().fireproof());
    public static final Item RAW_VANADIUM = new Item(CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_DUST = new Item(CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_NUGGET = new Item(CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_PLATE = new Item(CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_SWORD = new SwordItem(ModToolMaterial.VanadiumToolMaterial, 5, -2, CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_HAMMER = new Hammer(ModToolMaterial.VanadiumToolMaterial, 4, CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_AXE = new MegaAxe(ModToolMaterial.VanadiumToolMaterial, CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_SHOVEL = new Excavator(ModToolMaterial.VanadiumToolMaterial, 1, 1, CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_HOE = new SuperHoe(ModToolMaterial.VanadiumToolMaterial, 1, -3F, CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_BOW = new VanadiumBow(CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_HELMET = new ArmorItem(ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL, ArmorItem.Type.HELMET, CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_CHESTPLATE = new ArmorItem(ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_LEGGINGS = new ArmorItem(ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_BOOTS = new ArmorItem(ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_ELYTRA = new ElytraItem(CommonInitializer.settings().fireproof().maxDamage(500));
    public static final Item RAW_EMERALD = new Item(CommonInitializer.settings());
    public static final Item EMERALD_DUST = new Item(CommonInitializer.settings());
    public static final Item EMERALD_PLATE = new Item(CommonInitializer.settings());
    public static final Item EMERALD_HELMET = new ArmorItem(ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, ArmorItem.Type.HELMET, CommonInitializer.settings().fireproof());
    public static final Item EMERALD_CHESTPLATE = new ArmorItem(ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, CommonInitializer.settings().fireproof());
    public static final Item EMERALD_LEGGINGS = new ArmorItem(ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, CommonInitializer.settings().fireproof());
    public static final Item EMERALD_BOOTS = new ArmorItem(ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, CommonInitializer.settings().fireproof());
    public static final Item TUNGSTEN_DUST = new Item(CommonInitializer.settings());
    public static final Item RAW_TUNGSTEN = new Item(CommonInitializer.settings());
    public static final Item TUNGSTEN_PLATE = new Item(CommonInitializer.settings());
    public static final Item TUNGSTEN_CHESTPLATE = new ArmorItem(ModArmorsMaterial.TUNGSTEN_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, CommonInitializer.settings().fireproof());
    public static final Item TUNGSTEN_HAMMER = new Hammer(ModToolMaterial.TungstenToolMaterial, 3, CommonInitializer.settings().fireproof());
    public static final Item SILVER_DUST = new Item(CommonInitializer.settings());
    public static final Item RAW_SILVER = new Item(CommonInitializer.settings());
    public static final Item COPPER_DUST = new Item(CommonInitializer.settings());
    public static final Item COPPER_HAMMER = new Hammer(ModToolMaterial.CopperToolMaterial, 1, CommonInitializer.settings());
    public static final Item COPPER_PLATE = new Item(CommonInitializer.settings());
    public static final Item TIN_DUST = new Item(CommonInitializer.settings());
    public static final Item RAW_TIN = new Item(CommonInitializer.settings());
    public static final Item TIN_PLATE = new Item(CommonInitializer.settings());
    public static final Item TIN_HAMMER = new Hammer(ModToolMaterial.TinToolMaterial, 2, CommonInitializer.settings());
    public static final Item TRANSISTOR = new Item(CommonInitializer.settings().maxCount(16));
    public static final Item PROCESSOR = new Item(CommonInitializer.settings().maxCount(16));
    public static final Item POWER_CELL = new Item(CommonInitializer.settings().food(new FoodComponent.Builder()
            .alwaysEdible().saturationModifier(10).hunger(6).statusEffect(
                    new StatusEffectInstance(StatusEffects.REGENERATION, 120, 1, true, true, true), 100).build()));
    public static final Item SUPER_POWER_CELL = new Item(CommonInitializer.settings().food(new FoodComponent.Builder()
            .alwaysEdible().saturationModifier(10).hunger(6).statusEffect(
                    new StatusEffectInstance(StatusEffects.REGENERATION, 300, 1, true, true, true), 100).build()));
    public static final Item VANADIUM_APPLE = new Item(CommonInitializer.settings().food(new FoodComponent.Builder()
            .alwaysEdible().saturationModifier(20).hunger(20).statusEffect(
                    new StatusEffectInstance(StatusEffects.REGENERATION, 440, 2, true, true, true), 100)
            .statusEffect(
                    new StatusEffectInstance(StatusEffects.ABSORPTION, 3600, 4, true, true, true), 100).build()));
    public static final Item SIMPLE_HEALTH_BOOSTER = new Item(CommonInitializer.settings().maxCount(8));
    public static final Item BASE_HEALTH_BOOSTER = new Item(CommonInitializer.settings().maxCount(8));
    public static final Item ADVANCE_HEALTH_BOOSTER = new Item(CommonInitializer.settings().maxCount(8));
    public static final Item SIMPLE_REGEN_BOOSTER = new Item(CommonInitializer.settings().maxCount(8));
    public static final Item BASE_REGEN_BOOSTER = new Item(CommonInitializer.settings().maxCount(8));
    public static final Item ADVANCE_REGEN_BOOSTER = new Item(CommonInitializer.settings().maxCount(8));
    public static final Item STEEL_PLATE = new Item(CommonInitializer.settings());
    public static final Item STEEL_HELMET = new ArmorItem(ModArmorsMaterial.STEEL_ARMOR_MATERIAL, ArmorItem.Type.HELMET, CommonInitializer.settings());
    public static final Item STEEL_CHESTPLATE = new ArmorItem(ModArmorsMaterial.STEEL_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, CommonInitializer.settings());
    public static final Item STEEL_LEGGINGS = new ArmorItem(ModArmorsMaterial.STEEL_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, CommonInitializer.settings());
    public static final Item STEEL_BOOTS = new ArmorItem(ModArmorsMaterial.STEEL_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, CommonInitializer.settings());
    public static final Item RAW_DIAMOND = new Item(CommonInitializer.settings());
    public static final Item DIAMOND_DUST = new Item(CommonInitializer.settings());
    public static final Item DIAMOND_PLATE = new Item(CommonInitializer.settings());
    public static final Item IRON_DUST = new Item(CommonInitializer.settings());
    public static final Item GOLD_DUST = new Item(CommonInitializer.settings());
    public static final Item STEEL_UPGRADE_SMITHING_TEMPLATE = newSmithingArmorTemplateItem("steel");
    public static final Item TIN_UPGRADE_SMITHING_TEMPLATE = newSmithingHammerTemplateItem("tin");
    public static final Item TUNGSTEN_UPGRADE_SMITHING_TEMPLATE = newSmithingHammerTemplateItem("tungsten");
    public static final Item DIAMOND_UPGRADE_SMITHING_TEMPLATE = newSmithingArmorTemplateItem("diamond");
    public static final Item EMERALD_UPGRADE_SMITHING_TEMPLATE = newSmithingArmorTemplateItem("emerald");
    public static final Item VANADIUM_UPGRADE_SMITHING_TEMPLATE = newSmithingTemplateItem("vanadium");

    public static void registerIngots() {
        registerNewItem("steel_ingot", STEEL_INGOT);
        registerNewItem("tin_ingot", TIN_INGOT);
        registerNewItem("silver_ingot", SILVER_INGOT);
        registerNewItem("tungsten_ingot", TUNGSTEN_INGOT);
        registerNewItem("vanadium_ingot", VANADIUM_INGOT);
    }

    public static void registerAll() {

        registerNewItem("copper_dust", COPPER_DUST);
        registerNewItem("copper_plate", COPPER_PLATE);
        registerNewItem("copper_hammer", COPPER_HAMMER);
        CommonInitializer.addBlockInGroup(ModBlocks.TIN_ORE);
        registerNewItem("raw_tin", RAW_TIN);
        registerNewItem("tin_dust", TIN_DUST);
        CommonInitializer.addBlockInGroup(ModBlocks.TIN_BLOCK);
        registerNewItem("tin_plate", TIN_PLATE);
        registerNewItem("tin_hammer", TIN_HAMMER);
        registerNewItem("iron_dust", IRON_DUST);
        CommonInitializer.addBlockInGroup(ModBlocks.STEEL_BLOCK);
        registerNewItem("steel_plate", STEEL_PLATE);
        registerNewItem("steel_helmet", STEEL_HELMET);
        registerNewItem("steel_chestplate", STEEL_CHESTPLATE);
        registerNewItem("steel_leggings", STEEL_LEGGINGS);
        registerNewItem("steel_boots", STEEL_BOOTS);
        registerNewItem("gold_dust", GOLD_DUST);
        CommonInitializer.addBlockInGroup(ModBlocks.SILVER_ORE);
        registerNewItem("raw_silver", RAW_SILVER);
        registerNewItem("silver_dust", SILVER_DUST);
        CommonInitializer.addBlockInGroup(ModBlocks.SILVER_BLOCK);
        CommonInitializer.addBlockInGroup(ModBlocks.TUNGSTEN_ORE);
        registerNewItem("raw_tungsten", RAW_TUNGSTEN);
        registerNewItem("tungsten_dust", TUNGSTEN_DUST);
        CommonInitializer.addBlockInGroup(ModBlocks.TUNGSTEN_BLOCK);
        registerNewItem("tungsten_plate", TUNGSTEN_PLATE);
        registerNewItem("tungsten_chestplate", TUNGSTEN_CHESTPLATE);
        registerNewItem("tungsten_hammer", TUNGSTEN_HAMMER);
        registerNewItem("raw_diamond", RAW_DIAMOND);
        registerNewItem("diamond_dust", DIAMOND_DUST);
        registerNewItem("diamond_plate", DIAMOND_PLATE);
        registerNewItem("raw_emerald", RAW_EMERALD);
        registerNewItem("emerald_dust", EMERALD_DUST);
        registerNewItem("emerald_plate", EMERALD_PLATE);
        registerNewItem("emerald_helmet", EMERALD_HELMET);
        registerNewItem("emerald_chestplate", EMERALD_CHESTPLATE);
        registerNewItem("emerald_leggings", EMERALD_LEGGINGS);
        registerNewItem("emerald_boots", EMERALD_BOOTS);
        CommonInitializer.addBlockInGroup(ModBlocks.VANADIUM_ORE);
        registerNewItem("raw_vanadium", RAW_VANADIUM);
        registerNewItem("vanadium_dust", VANADIUM_DUST);
        registerNewItem("vanadium_nugget", VANADIUM_NUGGET);
        CommonInitializer.addBlockInGroup(ModBlocks.VANADIUM_BLOCK);
        registerNewItem("vanadium_plate", VANADIUM_PLATE);
        registerNewItem("vanadium_sword", VANADIUM_SWORD);
        registerNewItem("vanadium_hammer", VANADIUM_HAMMER);
        registerNewItem("vanadium_axe", VANADIUM_AXE);
        registerNewItem("vanadium_shovel", VANADIUM_SHOVEL);
        registerNewItem("vanadium_hoe", VANADIUM_HOE);
        registerNewItem("vanadium_bow", VANADIUM_BOW);
        registerNewItem("vanadium_apple", VANADIUM_APPLE);
        registerNewItem("vanadium_helmet", VANADIUM_HELMET);
        registerNewItem("vanadium_chestplate", VANADIUM_CHESTPLATE);
        registerNewItem("vanadium_leggings", VANADIUM_LEGGINGS);
        registerNewItem("vanadium_boots", VANADIUM_BOOTS);
        registerNewItem("vanadium_elytra", VANADIUM_ELYTRA);
        registerNewItem("transistor", TRANSISTOR);
        registerNewItem("processor", PROCESSOR);
        CommonInitializer.addBlockInGroup(ModMachines.PIPE_BLOCK, ModMachines.PURIFICATOR_BLOCK,
                ModMachines.HIGH_FURNACE_BLOCK, ModMachines.ENCHANTER_BLOCK, ModMachines.MODIFIERS_TABLE_BLOCK);
        registerNewItem("simple_health_booster", SIMPLE_HEALTH_BOOSTER);
        registerNewItem("base_health_booster", BASE_HEALTH_BOOSTER);
        registerNewItem("advance_health_booster", ADVANCE_HEALTH_BOOSTER);
        registerNewItem("simple_regen_booster", SIMPLE_REGEN_BOOSTER);
        registerNewItem("base_regen_booster", BASE_REGEN_BOOSTER);
        registerNewItem("advance_regen_booster", ADVANCE_REGEN_BOOSTER);
        registerNewItem("power_cell", POWER_CELL);
        registerNewItem("super_power_cell", SUPER_POWER_CELL);
        registerNewItem("tin_upgrade_smithing_template", TIN_UPGRADE_SMITHING_TEMPLATE);
        registerNewItem("tungsten_upgrade_smithing_template", TUNGSTEN_UPGRADE_SMITHING_TEMPLATE);
        registerNewItem("steel_upgrade_smithing_template", STEEL_UPGRADE_SMITHING_TEMPLATE);
        registerNewItem("diamond_upgrade_smithing_template", DIAMOND_UPGRADE_SMITHING_TEMPLATE);
        registerNewItem("emerald_upgrade_smithing_template", EMERALD_UPGRADE_SMITHING_TEMPLATE);
        registerNewItem("vanadium_upgrade_smithing_template", VANADIUM_UPGRADE_SMITHING_TEMPLATE);

    }

    private static @NotNull SmithingTemplateItem newSmithingTemplateItem(String material){
        return newSmithingTemplateItem(material, List.of(
                new Identifier("item/empty_armor_slot_helmet"), new Identifier("item/empty_armor_slot_chestplate"),
                new Identifier("item/empty_armor_slot_leggings"), new Identifier("item/empty_armor_slot_boots"),
                new Identifier("item/empty_slot_sword"), new Identifier("item/empty_slot_pickaxe"),
                new Identifier("item/empty_slot_hammer"), new Identifier("item/empty_slot_axe"),
                new Identifier("item/empty_slot_shovel"), new Identifier("item/empty_slot_hoe")
        ));
    }

    private static @NotNull SmithingTemplateItem newSmithingHammerTemplateItem(String material){
        return newSmithingTemplateItem(material, List.of(new Identifier("item/empty_slot_hammer")));
    }

    private static @NotNull SmithingTemplateItem newSmithingArmorTemplateItem(String material){
        return newSmithingTemplateItem(material, List.of(new Identifier("item/empty_armor_slot_helmet"),
                new Identifier("item/empty_armor_slot_chestplate"), new Identifier("item/empty_armor_slot_leggings"),
                new Identifier("item/empty_armor_slot_boots")));
    }

    private static @NotNull SmithingTemplateItem newSmithingTemplateItem(String material, List<Identifier> list){
        String templateName = "smithing_template." + material + ".";
        return new SmithingTemplateItem(
                Text.translatable(Util.createTranslationKey("item", identifier(templateName + "applies_to"))).formatted(Formatting.BLUE),
                Text.translatable(Util.createTranslationKey("item",identifier(templateName + "ingredients"))).formatted(Formatting.BLUE),
                Text.translatable(Util.createTranslationKey("upgrade", identifier(material + "_upgrade"))).formatted(Formatting.GRAY),
                Text.translatable(Util.createTranslationKey("item", identifier(templateName + "base_slot_description"))),
                Text.translatable(Util.createTranslationKey("item", identifier(templateName + "additions_slot_description"))),
                list, List.of(new Identifier("item/empty_slot_plate")));
    }

    private static void registerNewItem(String name, Item item) {
        Registry.register(Registries.ITEM, identifier(name), item);
        CommonInitializer.addItemInGroup(item);
    }

    @Contract("_ -> new")
    private static @NotNull Identifier identifier(String name){
        return new Identifier(CommonInitializer.MODID, name);
    }

}