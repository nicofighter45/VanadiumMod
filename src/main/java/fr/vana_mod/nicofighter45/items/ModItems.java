package fr.vana_mod.nicofighter45.items;

import fr.vana_mod.nicofighter45.block.ModBlocks;
import fr.vana_mod.nicofighter45.items.armor.ModArmorsMaterial;
import fr.vana_mod.nicofighter45.items.custom.*;
import fr.vana_mod.nicofighter45.items.tool_material.ModToolMaterial;
import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.main.CommonInitializer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    //vanadium
    public static final Item RAW_VANADIUM = new Item(CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_DUST = new Item(CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_NUGGET = new Item(CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_INGOT = new Item(CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_PLATE = new Item(CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_SWORD = new SwordItem(ModToolMaterial.VanadiumToolMaterial, 5, -2, CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_HAMMER = new Hammer(ModToolMaterial.VanadiumToolMaterial, 4, CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_AXE = new MegaAxe(ModToolMaterial.VanadiumToolMaterial, CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_SHOVEL = new Excavator(ModToolMaterial.VanadiumToolMaterial, 1, 1, CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_HOE = new SuperHoe(ModToolMaterial.VanadiumToolMaterial, 1, -3F, CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_BOW = new VanadiumBow(CommonInitializer.settings().fireproof());
    //armure en vanadium
    public static final Item VANADIUM_HELMET = new ArmorItem(ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL, EquipmentSlot.HEAD, CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_CHESTPLATE = new ArmorItem(ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL, EquipmentSlot.CHEST, CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_LEGGINGS = new ArmorItem(ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL, EquipmentSlot.LEGS, CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_BOOTS = new ArmorItem(ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL, EquipmentSlot.FEET, CommonInitializer.settings().fireproof());
    public static final Item VANADIUM_ELYTRA = new ElytraItem(CommonInitializer.settings().fireproof().maxDamage(500));
    //armure en emeraude*
    public static final Item RAW_EMERALD = new Item(CommonInitializer.settings());
    public static final Item EMERALD_DUST = new Item(CommonInitializer.settings());
    public static final Item EMERALD_PLATE = new Item(CommonInitializer.settings());
    public static final Item EMERALD_HELMET = new ArmorItem(ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, EquipmentSlot.HEAD, CommonInitializer.settings().fireproof());
    public static final Item EMERALD_CHESTPLATE = new ArmorItem(ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, EquipmentSlot.CHEST, CommonInitializer.settings().fireproof());
    public static final Item EMERALD_LEGGINGS = new ArmorItem(ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, EquipmentSlot.LEGS, CommonInitializer.settings().fireproof());
    public static final Item EMERALD_BOOTS = new ArmorItem(ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, EquipmentSlot.FEET, CommonInitializer.settings().fireproof());
    //tungsten
    public static final Item TUNGSTEN_DUST = new Item(CommonInitializer.settings());
    public static final Item RAW_TUNGSTEN = new Item(CommonInitializer.settings());
    public static final Item TUNGSTEN_INGOT = new Item(CommonInitializer.settings());
    public static final Item TUNGSTEN_PLATE = new Item(CommonInitializer.settings());
    public static final Item TUNGSTEN_CHESTPLATE = new ArmorItem(ModArmorsMaterial.TUNGSTEN_ARMOR_MATERIAL, EquipmentSlot.CHEST, CommonInitializer.settings().fireproof());
    public static final Item TUNGSTEN_HAMMER = new Hammer(ModToolMaterial.TungstenToolMaterial, 3, CommonInitializer.settings().fireproof());
    //argent
    public static final Item SILVER_DUST = new Item(CommonInitializer.settings());
    public static final Item RAW_SILVER = new Item(CommonInitializer.settings());
    public static final Item SILVER_INGOT = new Item((CommonInitializer.settings()));
    //copper
    public static final Item COPPER_DUST = new Item(CommonInitializer.settings());
    public static final Item COPPER_HAMMER = new Hammer(ModToolMaterial.CopperToolMaterial, 1, CommonInitializer.settings());
    public static final Item COPPER_PLATE = new Item(CommonInitializer.settings());
    //tin
    public static final Item TIN_DUST = new Item(CommonInitializer.settings());
    public static final Item RAW_TIN = new Item(CommonInitializer.settings());
    public static final Item TIN_INGOT = new Item(CommonInitializer.settings());
    public static final Item TIN_PLATE = new Item(CommonInitializer.settings());
    public static final Item TIN_HAMMER = new Hammer(ModToolMaterial.TinToolMaterial, 2, CommonInitializer.settings());
    //processeur
    public static final Item TRANSISTOR = new Item(CommonInitializer.settings().maxCount(16));
    public static final Item PROCESSOR = new Item(CommonInitializer.settings().maxCount(16));
    //healing item
    public static final Item POWER_CELL = new Item(CommonInitializer.settings().food(new FoodComponent.Builder()
            .alwaysEdible().saturationModifier(10).hunger(6).statusEffect(
                    new StatusEffectInstance(StatusEffects.REGENERATION, 120, 1, true, true, true), 100).build()));
    public static final Item SUPER_POWER_CELL = new Item(CommonInitializer.settings().food(new FoodComponent.Builder()
            .alwaysEdible().saturationModifier(10).hunger(6).statusEffect(
                    new StatusEffectInstance(StatusEffects.REGENERATION, 300, 1, true, true, true), 100).build()));
    public static final Item MAMADE_CHICKEN = new Item(CommonInitializer.settings().food(new FoodComponent.Builder()
            .alwaysEdible().saturationModifier(20).hunger(20).statusEffect(
                    new StatusEffectInstance(StatusEffects.SATURATION, 216000, 0, false, false, true), 100).build()));
    public static final Item VANADIUM_APPLE = new Item(CommonInitializer.settings().food(new FoodComponent.Builder()
            .alwaysEdible().saturationModifier(20).hunger(20).statusEffect(
                    new StatusEffectInstance(StatusEffects.REGENERATION, 440, 2, true, true, true), 100)
            .statusEffect(
                    new StatusEffectInstance(StatusEffects.ABSORPTION, 3600, 4, true, true, true), 100).build()));
    //health boost
    public static final Item SIMPLE_HEALTH_BOOSTER = new Item(CommonInitializer.settings().maxCount(8));
    public static final Item BASE_HEALTH_BOOSTER = new Item(CommonInitializer.settings().maxCount(8));
    public static final Item ADVANCE_HEALTH_BOOSTER = new Item(CommonInitializer.settings().maxCount(8));
    //regen boost
    public static final Item SIMPLE_REGEN_BOOSTER = new Item(CommonInitializer.settings().maxCount(8));
    public static final Item BASE_REGEN_BOOSTER = new Item(CommonInitializer.settings().maxCount(8));
    public static final Item ADVANCE_REGEN_BOOSTER = new Item(CommonInitializer.settings().maxCount(8));
    //hat
    public static final Item HAT = new Item(CommonInitializer.settings());
    //steam
    public static final Item STEEL_INGOT = new Item(CommonInitializer.settings());
    public static final Item STEEL_PLATE = new Item(CommonInitializer.settings());
    public static final Item STEEL_HELMET = new ArmorItem(ModArmorsMaterial.STEEL_ARMOR_MATERIAL, EquipmentSlot.HEAD, CommonInitializer.settings());
    public static final Item STEEL_CHESTPLATE = new ArmorItem(ModArmorsMaterial.STEEL_ARMOR_MATERIAL, EquipmentSlot.CHEST, CommonInitializer.settings());
    public static final Item STEEL_LEGGINGS = new ArmorItem(ModArmorsMaterial.STEEL_ARMOR_MATERIAL, EquipmentSlot.LEGS, CommonInitializer.settings());
    public static final Item STEEL_BOOTS = new ArmorItem(ModArmorsMaterial.STEEL_ARMOR_MATERIAL, EquipmentSlot.FEET, CommonInitializer.settings());
    public static final Item RAW_DIAMOND = new Item(CommonInitializer.settings());
    public static final Item DIAMOND_DUST = new Item(CommonInitializer.settings());
    public static final Item DIAMOND_PLATE = new Item(CommonInitializer.settings());
    public static final Item IRON_DUST = new Item(CommonInitializer.settings());
    public static final Item GOLD_DUST = new Item(CommonInitializer.settings());

    public static void registerAll() {


        registerNewItem("copper_dust", COPPER_DUST);
        registerNewItem("copper_plate", COPPER_PLATE);
        registerNewItem("copper_hammer", COPPER_HAMMER);

        CommonInitializer.addBlockInGroup(ModBlocks.TIN_ORE);
        registerNewItem("raw_tin", RAW_TIN);
        registerNewItem("tin_dust", TIN_DUST);
        registerNewItem("tin_ingot", TIN_INGOT);
        CommonInitializer.addBlockInGroup(ModBlocks.TIN_BLOCK);
        registerNewItem("tin_plate", TIN_PLATE);
        registerNewItem("tin_hammer", TIN_HAMMER);


        registerNewItem("iron_dust", IRON_DUST);


        registerNewItem("steel_ingot", STEEL_INGOT);
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
        registerNewItem("silver_ingot", SILVER_INGOT);
        CommonInitializer.addBlockInGroup(ModBlocks.SILVER_BLOCK);


        CommonInitializer.addBlockInGroup(ModBlocks.TUNGSTEN_ORE);
        registerNewItem("raw_tungsten", RAW_TUNGSTEN);
        registerNewItem("tungsten_dust", TUNGSTEN_DUST);
        registerNewItem("tungsten_ingot", TUNGSTEN_INGOT);
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
        registerNewItem("vanadium_ingot", VANADIUM_INGOT);
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

        registerNewItem("mamade_chicken", MAMADE_CHICKEN);
        CommonInitializer.addBlockInGroup(ModBlocks.MAMADE_BLOCK);


        registerNewItem("hat", HAT);

    }

    private static void registerNewItem(String name, Item item) {
        Registry.register(Registries.ITEM, new Identifier(CommonInitializer.MODID, name), item);
        CommonInitializer.addItemInGroup(item);
    }
}