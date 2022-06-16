package fr.vana_mod.nicofighter45.items;

import fr.vana_mod.nicofighter45.items.armor.SteelArmorMaterials;
import fr.vana_mod.nicofighter45.items.custom.Excavator;
import fr.vana_mod.nicofighter45.items.custom.Hammer;
import fr.vana_mod.nicofighter45.items.custom.MegaAxe;
import fr.vana_mod.nicofighter45.main.VanadiumMod;
import fr.vana_mod.nicofighter45.items.armor.EmeraldArmorMaterials;
import fr.vana_mod.nicofighter45.items.armor.TungstenArmorMaterials;
import fr.vana_mod.nicofighter45.items.armor.VanadiumArmorMaterials;
import fr.vana_mod.nicofighter45.items.enchantment.UpgradeItem;
import fr.vana_mod.nicofighter45.items.tool_material.CopperToolMaterial;
import fr.vana_mod.nicofighter45.items.tool_material.TinToolMaterial;
import fr.vana_mod.nicofighter45.items.tool_material.TungstenToolMaterial;
import fr.vana_mod.nicofighter45.items.tool_material.VanadiumToolMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    private static Item.Settings settings(){
        return new Item.Settings().group(VanadiumMod.VANADIUM_GROUP);
    }

    //vanadium
    public static final Item VANADIUM_NUGGET = new Item(settings().fireproof());
    public static final Item VANADIUM_INGOT = new Item(settings().fireproof());
    public static final Item VANADIUM_PLATE = new Item(settings().maxCount(64).fireproof());
    public static final Item VANADIUM_SWORD = new SwordItem(VanadiumToolMaterial.INSTANCE, 5, -1.0F, settings().maxCount(1).fireproof());
    public static final Item VANADIUM_HAMMER = new Hammer(VanadiumToolMaterial.INSTANCE, 4, settings().maxCount(1).fireproof());
    public static final Item VANADIUM_AXE = new MegaAxe(VanadiumToolMaterial.INSTANCE, settings().maxCount(1).fireproof());
    public static final Item VANADIUM_SHOVEL = new Excavator(VanadiumToolMaterial.INSTANCE, 1, 1, settings().maxCount(1).fireproof());

    //armure en vanadium
    public static final Item VANADIUM_HELMET = new ArmorItem(VanadiumArmorMaterials.VANADIUM, EquipmentSlot.HEAD, settings().maxCount(1).fireproof());
    public static final Item VANADIUM_CHESTPLATE = new ArmorItem(VanadiumArmorMaterials.VANADIUM, EquipmentSlot.CHEST, settings().maxCount(1).fireproof());
    public static final Item VANADIUM_LEGGINGS = new ArmorItem(VanadiumArmorMaterials.VANADIUM, EquipmentSlot.LEGS, settings().maxCount(1).fireproof());
    public static final Item VANADIUM_BOOTS = new ArmorItem(VanadiumArmorMaterials.VANADIUM, EquipmentSlot.FEET, settings().maxCount(1).fireproof());

    //armure en emeraude
    public static final Item EMERALD_PLATE = new Item(settings().maxCount(64));
    public static final Item EMERALD_HELMET = new ArmorItem(EmeraldArmorMaterials.EMERALD, EquipmentSlot.HEAD, new Item.Settings().group(VanadiumMod.VANADIUM_GROUP));
    public static final Item EMERALD_CHESTPLATE = new ArmorItem(EmeraldArmorMaterials.EMERALD, EquipmentSlot.CHEST, new Item.Settings().group(VanadiumMod.VANADIUM_GROUP));
    public static final Item EMERALD_LEGGINGS = new ArmorItem(EmeraldArmorMaterials.EMERALD, EquipmentSlot.LEGS, new Item.Settings().group(VanadiumMod.VANADIUM_GROUP));
    public static final Item EMERALD_BOOTS = new ArmorItem(EmeraldArmorMaterials.EMERALD, EquipmentSlot.FEET, new Item.Settings().group(VanadiumMod.VANADIUM_GROUP));

    //tungsten
    public static final Item TUNGSTEN_INGOT = new Item(settings().maxCount(64));
    public static final Item TUNGSTEN_PLATE = new Item(settings().maxCount(64));
    public static final Item TUNGSTEN_CHESTPLATE = new ArmorItem(TungstenArmorMaterials.TUNGSTEN, EquipmentSlot.CHEST, new Item.Settings().group(VanadiumMod.VANADIUM_GROUP));
    public static final Item TUNGSTEN_HAMMER = new Hammer(TungstenToolMaterial.INSTANCE, 3, new Item.Settings().group(VanadiumMod.VANADIUM_GROUP));

    //argent
    public static final Item SILVER_INGOT = new Item((settings()).maxCount(64));

    //copper
    public static final Item COPPER_HAMMER = new Hammer(CopperToolMaterial.INSTANCE, 1, new Item.Settings().group(VanadiumMod.VANADIUM_GROUP));
    public static final Item COPPER_PLATE = new Item(settings().maxCount(64));

    //tin
    public static final Item TIN_INGOT = new Item(settings().maxCount(64));
    public static final Item TIN_PLATE = new Item(settings().maxCount(64));
    public static final Item TIN_HAMMER = new Hammer(TinToolMaterial.INSTANCE, 2, new Item.Settings().group(VanadiumMod.VANADIUM_GROUP));

    //processeur
    public static final Item TRANSISTOR = new Item(settings().maxCount(16));
    public static final Item PROCESSOR = new Item(settings().maxCount(16));

    //enchant stone
    public static final Item HASTE_STONE = new UpgradeItem(settings().maxCount(1));
    public static final Item STRENGTH_STONE = new UpgradeItem(settings().maxCount(1));
    public static final Item RESISTANCE_STONE = new UpgradeItem(settings().maxCount(1));
    public static final Item SPEED_STONE = new UpgradeItem(settings().maxCount(1));
    public static final Item JUMP_STONE = new UpgradeItem(settings().maxCount(1));
    public static final Item NO_FALL_STONE = new UpgradeItem(settings().maxCount(1));

    //healing item
    public static final Item POWER_CELL = new Item(settings().food(new FoodComponent.Builder()
            .alwaysEdible().saturationModifier(10).hunger(6).statusEffect(
                    new StatusEffectInstance(StatusEffects.REGENERATION, 120, 1, true, true, true), 100).build()));

    //health boost
    public static final Item SIMPLE_HEALTH_BOOSTER = new Item(settings().maxCount(8));
    public static final Item BASE_HEALTH_BOOSTER = new Item(settings().maxCount(8));
    public static final Item ADVANCE_HEALTH_BOOSTER = new Item(settings().maxCount(8));

    //regen boost
    public static final Item SIMPLE_REGEN_BOOSTER = new Item(settings().maxCount(8));
    public static final Item BASE_REGEN_BOOSTER = new Item(settings().maxCount(8));
    public static final Item ADVANCE_REGEN_BOOSTER = new Item(settings().maxCount(8));

    //craft and ec features
    public static final Item CRAFTING_STONE = new Item(settings().maxCount(1));
    public static final Item ENDER_CHEST_STONE = new Item(settings().maxCount(1));

    //hat
    public static final Item HAT = new Item(settings().maxCount(1));

    //steam
    public static final Item STEEL_INGOT = new Item(settings().maxCount(64));
    public static final Item STEEL_PLATE = new Item(settings().maxCount(64));
    public static final Item STEEL_HELMET = new ArmorItem(SteelArmorMaterials.STEEL, EquipmentSlot.HEAD, new Item.Settings().group(VanadiumMod.VANADIUM_GROUP));
    public static final Item STEEL_CHESTPLATE = new ArmorItem(SteelArmorMaterials.STEEL, EquipmentSlot.CHEST, new Item.Settings().group(VanadiumMod.VANADIUM_GROUP));
    public static final Item STEEL_LEGGINGS = new ArmorItem(SteelArmorMaterials.STEEL, EquipmentSlot.LEGS, new Item.Settings().group(VanadiumMod.VANADIUM_GROUP));
    public static final Item STEEL_BOOTS = new ArmorItem(SteelArmorMaterials.STEEL, EquipmentSlot.FEET, new Item.Settings().group(VanadiumMod.VANADIUM_GROUP));

    //plate
    public static final Item DIAMOND_PLATE = new Item(settings().maxCount(64));

    public static void registerAll() {

        registerNewItem("vanadium_nugget", VANADIUM_NUGGET);
        registerNewItem("vanadium_ingot", VANADIUM_INGOT);

        registerNewItem("vanadium_plate", VANADIUM_PLATE);
        registerNewItem("emerald_plate", EMERALD_PLATE);
        registerNewItem("vanadium_sword", VANADIUM_SWORD);
        registerNewItem("vanadium_hammer", VANADIUM_HAMMER);
        registerNewItem("vanadium_axe", VANADIUM_AXE);
        registerNewItem("vanadium_shovel", VANADIUM_SHOVEL);

        registerNewItem("vanadium_helmet", VANADIUM_HELMET);
        registerNewItem("vanadium_chestplate", VANADIUM_CHESTPLATE);
        registerNewItem("vanadium_leggings", VANADIUM_LEGGINGS);
        registerNewItem("vanadium_boots", VANADIUM_BOOTS);


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

        registerNewItem("haste_stone", HASTE_STONE);
        registerNewItem("strength_stone", STRENGTH_STONE);
        registerNewItem("resistance_stone", RESISTANCE_STONE);
        registerNewItem("speed_stone", SPEED_STONE);
        registerNewItem("jump_stone", JUMP_STONE);
        registerNewItem("no_fall_stone", NO_FALL_STONE);

        registerNewItem("power_cell", POWER_CELL);

        registerNewItem("simple_health_booster", SIMPLE_HEALTH_BOOSTER);
        registerNewItem("base_health_booster", BASE_HEALTH_BOOSTER);
        registerNewItem("advance_health_booster", ADVANCE_HEALTH_BOOSTER);

        registerNewItem("simple_regen_booster", SIMPLE_REGEN_BOOSTER);
        registerNewItem("base_regen_booster", BASE_REGEN_BOOSTER);
        registerNewItem("advance_regen_booster", ADVANCE_REGEN_BOOSTER);

        registerNewItem("crafting_stone", CRAFTING_STONE); // #todo add textures and name for ec and crafting stone
        registerNewItem("ender_chest_stone", ENDER_CHEST_STONE);

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
        Registry.register(Registry.ITEM, new Identifier(VanadiumMod.MODID, name), item);
    }
}