package fr.vana_mod.nicofighter45.items;

import fr.vana_mod.nicofighter45.items.armor.ModArmorsMaterial;
import fr.vana_mod.nicofighter45.items.custom.*;
import fr.vana_mod.nicofighter45.items.tool_material.ModToolMaterial;
import fr.vana_mod.nicofighter45.main.CommonInitializer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    private static Item.Settings settings(){
        return new Item.Settings().group(CommonInitializer.VANADIUM_GROUP);
    }

    //vanadium
    public static final Item VANADIUM_NUGGET = new Item(settings().fireproof());
    public static final Item VANADIUM_INGOT = new Item(settings().fireproof());
    public static final Item VANADIUM_PLATE = new Item(settings().fireproof());
    public static final Item VANADIUM_SWORD = new SwordItem(ModToolMaterial.VanadiumToolMaterial, 5, -2, settings().fireproof());
    public static final Item VANADIUM_HAMMER = new Hammer(ModToolMaterial.VanadiumToolMaterial, 4, settings().fireproof());
    public static final Item VANADIUM_AXE = new MegaAxe(ModToolMaterial.VanadiumToolMaterial, settings().fireproof());
    public static final Item VANADIUM_SHOVEL = new Excavator(ModToolMaterial.VanadiumToolMaterial, 1, 1, settings().fireproof());
    public static final Item VANADIUM_HOE = new SuperHoe(ModToolMaterial.VanadiumToolMaterial, 1, -3F, settings().fireproof());
    public static final Item VANADIUM_BOW = new VanadiumBow(settings().fireproof());

    //armure en vanadium
    public static final Item VANADIUM_HELMET = new ArmorItem(ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL, EquipmentSlot.HEAD, settings().fireproof());
    public static final Item VANADIUM_CHESTPLATE = new ArmorItem(ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL, EquipmentSlot.CHEST, settings().fireproof());
    public static final Item VANADIUM_LEGGINGS = new ArmorItem(ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL, EquipmentSlot.LEGS, settings().fireproof());
    public static final Item VANADIUM_BOOTS = new ArmorItem(ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL, EquipmentSlot.FEET, settings().fireproof());
    public static final Item VANADIUM_ELYTRA = new ElytraItem(settings().maxCount(1).fireproof().maxDamage(500));


    //armure en emeraude
    public static final Item EMERALD_PLATE = new Item(settings());
    public static final Item EMERALD_HELMET = new ArmorItem(ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, EquipmentSlot.HEAD, settings().fireproof());
    public static final Item EMERALD_CHESTPLATE = new ArmorItem(ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, EquipmentSlot.CHEST, settings().fireproof());
    public static final Item EMERALD_LEGGINGS = new ArmorItem(ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, EquipmentSlot.LEGS, settings().fireproof());
    public static final Item EMERALD_BOOTS = new ArmorItem(ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, EquipmentSlot.FEET, settings().fireproof());

    //tungsten
    public static final Item TUNGSTEN_INGOT = new Item(settings());
    public static final Item TUNGSTEN_PLATE = new Item(settings());
    public static final Item TUNGSTEN_CHESTPLATE = new ArmorItem(ModArmorsMaterial.TUNGSTEN_ARMOR_MATERIAL, EquipmentSlot.CHEST, settings().fireproof());
    public static final Item TUNGSTEN_HAMMER = new Hammer(ModToolMaterial.TungstenToolMaterial, 3, settings().fireproof());

    //argent
    public static final Item SILVER_INGOT = new Item((settings()));

    //copper
    public static final Item COPPER_HAMMER = new Hammer(ModToolMaterial.CopperToolMaterial, 1, settings());
    public static final Item COPPER_PLATE = new Item(settings());

    //tin
    public static final Item TIN_INGOT = new Item(settings());
    public static final Item TIN_PLATE = new Item(settings());
    public static final Item TIN_HAMMER = new Hammer(ModToolMaterial.TinToolMaterial, 2, settings());

    //processeur
    public static final Item TRANSISTOR = new Item(settings().maxCount(16));
    public static final Item PROCESSOR = new Item(settings().maxCount(16));

    //healing item
    public static final Item POWER_CELL = new Item(settings().food(new FoodComponent.Builder()
            .alwaysEdible().saturationModifier(10).hunger(6).statusEffect(
                    new StatusEffectInstance(StatusEffects.REGENERATION, 120, 1, true, true, true), 100).build()));
    public static final Item SUPER_POWER_CELL = new Item(settings().food(new FoodComponent.Builder()
            .alwaysEdible().saturationModifier(10).hunger(6).statusEffect(
                    new StatusEffectInstance(StatusEffects.REGENERATION, 300, 1, true, true, true), 100).build()));
    public static final Item MAMAD_CHICKEN = new Item(settings().food(new FoodComponent.Builder()
            .alwaysEdible().saturationModifier(20).hunger(20).statusEffect(
            new StatusEffectInstance(StatusEffects.SATURATION, 216000, 0, false, false, true), 100).build()));
    public static final Item VANADIUM_APPLE = new Item(settings().food(new FoodComponent.Builder()
            .alwaysEdible().saturationModifier(20).hunger(20).statusEffect(
                    new StatusEffectInstance(StatusEffects.REGENERATION, 440, 2, true, true, true), 100)
            .statusEffect(
                    new StatusEffectInstance(StatusEffects.ABSORPTION, 3600, 4, true, true, true), 100).build()));

    //health boost
    public static final Item SIMPLE_HEALTH_BOOSTER = new Item(settings().maxCount(8));
    public static final Item BASE_HEALTH_BOOSTER = new Item(settings().maxCount(8));
    public static final Item ADVANCE_HEALTH_BOOSTER = new Item(settings().maxCount(8));

    //regen boost
    public static final Item SIMPLE_REGEN_BOOSTER = new Item(settings().maxCount(8));
    public static final Item BASE_REGEN_BOOSTER = new Item(settings().maxCount(8));
    public static final Item ADVANCE_REGEN_BOOSTER = new Item(settings().maxCount(8));

    //hat
    public static final Item HAT = new Item(settings());

    //steam
    public static final Item STEEL_INGOT = new Item(settings());
    public static final Item STEEL_PLATE = new Item(settings());
    public static final Item STEEL_HELMET = new ArmorItem(ModArmorsMaterial.STEEL_ARMOR_MATERIAL, EquipmentSlot.HEAD, settings());
    public static final Item STEEL_CHESTPLATE = new ArmorItem(ModArmorsMaterial.STEEL_ARMOR_MATERIAL, EquipmentSlot.CHEST, settings());
    public static final Item STEEL_LEGGINGS = new ArmorItem(ModArmorsMaterial.STEEL_ARMOR_MATERIAL, EquipmentSlot.LEGS, settings());
    public static final Item STEEL_BOOTS = new ArmorItem(ModArmorsMaterial.STEEL_ARMOR_MATERIAL, EquipmentSlot.FEET, settings());

    //plate
    public static final Item DIAMOND_PLATE = new Item(settings());

    public static void registerAll() {

        registerNewItem("vanadium_nugget", VANADIUM_NUGGET);
        registerNewItem("vanadium_ingot", VANADIUM_INGOT);

        registerNewItem("vanadium_plate", VANADIUM_PLATE);
        registerNewItem("emerald_plate", EMERALD_PLATE);
        registerNewItem("vanadium_sword", VANADIUM_SWORD);
        registerNewItem("vanadium_hammer", VANADIUM_HAMMER);
        registerNewItem("vanadium_axe", VANADIUM_AXE);
        registerNewItem("vanadium_shovel", VANADIUM_SHOVEL);
        registerNewItem("vanadium_hoe", VANADIUM_HOE);

        registerNewItem("vanadium_bow", VANADIUM_BOW);

        registerNewItem("vanadium_helmet", VANADIUM_HELMET);
        registerNewItem("vanadium_chestplate", VANADIUM_CHESTPLATE);
        registerNewItem("vanadium_leggings", VANADIUM_LEGGINGS);
        registerNewItem("vanadium_boots", VANADIUM_BOOTS);
        registerNewItem("vanadium_elytra", VANADIUM_ELYTRA);


        registerNewItem("emerald_helmet", EMERALD_HELMET);
        registerNewItem("emerald_chestplate", EMERALD_CHESTPLATE);
        registerNewItem("emerald_leggings", EMERALD_LEGGINGS);
        registerNewItem("emerald_boots", EMERALD_BOOTS);

        registerNewItem("tungsten_ingot", TUNGSTEN_INGOT);
        registerNewItem("tungsten_plate", TUNGSTEN_PLATE);
        registerNewItem("tungsten_chestplate", TUNGSTEN_CHESTPLATE);
        registerNewItem("tungsten_hammer", TUNGSTEN_HAMMER);

        registerNewItem("silver_ingot", SILVER_INGOT);

        registerNewItem("copper_hammer", COPPER_HAMMER);
        registerNewItem("copper_plate", COPPER_PLATE);

        registerNewItem("tin_ingot", TIN_INGOT);
        registerNewItem("tin_plate", TIN_PLATE);
        registerNewItem("tin_hammer", TIN_HAMMER);

        registerNewItem("transistor", TRANSISTOR);
        registerNewItem("processor", PROCESSOR);

        registerNewItem("power_cell", POWER_CELL);
        registerNewItem("super_power_cell", SUPER_POWER_CELL);
        registerNewItem("mamad_chicken", MAMAD_CHICKEN);
        registerNewItem("vanadium_apple", VANADIUM_APPLE);

        registerNewItem("simple_health_booster", SIMPLE_HEALTH_BOOSTER);
        registerNewItem("base_health_booster", BASE_HEALTH_BOOSTER);
        registerNewItem("advance_health_booster", ADVANCE_HEALTH_BOOSTER);

        registerNewItem("simple_regen_booster", SIMPLE_REGEN_BOOSTER);
        registerNewItem("base_regen_booster", BASE_REGEN_BOOSTER);
        registerNewItem("advance_regen_booster", ADVANCE_REGEN_BOOSTER);

        registerNewItem("hat", HAT);

        registerNewItem("steel_ingot", STEEL_INGOT);
        registerNewItem("steel_plate", STEEL_PLATE);
        registerNewItem("steel_helmet", STEEL_HELMET);
        registerNewItem("steel_chestplate", STEEL_CHESTPLATE);
        registerNewItem("steel_leggings", STEEL_LEGGINGS);
        registerNewItem("steel_boots", STEEL_BOOTS);

        registerNewItem("diamond_plate", DIAMOND_PLATE);
    }

    private static void registerNewItem(String name, Item item){
        Registry.register(Registry.ITEM, new Identifier(CommonInitializer.MODID, name), item);
    }
}