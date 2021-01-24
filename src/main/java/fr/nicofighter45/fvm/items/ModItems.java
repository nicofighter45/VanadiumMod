package fr.nicofighter45.fvm.items;

import com.google.gson.JsonObject;
import fr.nicofighter45.fvm.FVM;
import fr.nicofighter45.fvm.items.armor.EmeraldArmorMaterials;
import fr.nicofighter45.fvm.items.armor.TungstenArmorMaterials;
import fr.nicofighter45.fvm.items.armor.vanadium.ModifiableItem;
import fr.nicofighter45.fvm.items.armor.vanadium.VanadiumArmorMaterials;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.stat.StatType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    //vanadium
    public static final Item VANADIUM_NUGGET = new Item(new Item.Settings().group(FVM.VANADIUM_GROUP));
    public static final Item VANADIUM_INGOT = new Item(new Item.Settings().group(FVM.VANADIUM_GROUP));
    public static final Item VANADIUM_STICK = new Item(new Item.Settings().group(FVM.VANADIUM_GROUP).maxCount(16));
    public static final Item VANADIUM_HEART = new Item(new Item.Settings().group(FVM.VANADIUM_GROUP).maxCount(8));
    public static ToolItem VANADIUM_SWORD = new SwordItem(VanadiumToolMaterial.INSTANCE, 5, -1.0F, new Item.Settings().group(FVM.VANADIUM_GROUP));

    //armure en vanadium
    public static final Item VANADIUM_HELMET = new ArmorItem(VanadiumArmorMaterials.VANADIUM, EquipmentSlot.HEAD, new Item.Settings().group(FVM.VANADIUM_GROUP));
    public static final Item VANADIUM_CHESTPLATE = new ArmorItem(VanadiumArmorMaterials.VANADIUM, EquipmentSlot.CHEST, new Item.Settings().group(FVM.VANADIUM_GROUP));
    public static final Item VANADIUM_LEGGINGS = new ArmorItem(VanadiumArmorMaterials.VANADIUM, EquipmentSlot.LEGS, new Item.Settings().group(FVM.VANADIUM_GROUP));
    public static final Item VANADIUM_BOOTS = new ModifiableItem(VanadiumArmorMaterials.VANADIUM, EquipmentSlot.FEET, new Item.Settings().group(FVM.VANADIUM_GROUP));

    //armure en emeraude
    public static final Item EMERALD_HEART = new Item(new Item.Settings().group(FVM.VANADIUM_GROUP).maxCount(8));
    public static final Item EMERALD_HELMET = new ArmorItem(EmeraldArmorMaterials.EMERALD, EquipmentSlot.HEAD, new Item.Settings().group(FVM.VANADIUM_GROUP));
    public static final Item EMERALD_CHESTPLATE = new ArmorItem(EmeraldArmorMaterials.EMERALD, EquipmentSlot.CHEST, new Item.Settings().group(FVM.VANADIUM_GROUP));
    public static final Item EMERALD_LEGGINGS = new ArmorItem(EmeraldArmorMaterials.EMERALD, EquipmentSlot.LEGS, new Item.Settings().group(FVM.VANADIUM_GROUP));
    public static final Item EMERALD_BOOTS = new ArmorItem(EmeraldArmorMaterials.EMERALD, EquipmentSlot.FEET, new Item.Settings().group(FVM.VANADIUM_GROUP));

    //tungsten
    public static final Item TUNGSTEN_INGOT = new Item(new Item.Settings().group(FVM.VANADIUM_GROUP));
    public static final Item TUNGSTEN_CHESTPLATE = new ArmorItem(TungstenArmorMaterials.TUNGSTEN, EquipmentSlot.CHEST, new Item.Settings().group(FVM.VANADIUM_GROUP));

    //argent
    public static final Item SILVER_INGOT = new Item(new Item.Settings().group(FVM.VANADIUM_GROUP));

    //cuivre et étain
    public static final Item TIN_INGOT = new Item(new Item.Settings().group(FVM.VANADIUM_GROUP));
    public static final Item COPPER_INGOT = new Item(new Item.Settings().group(FVM.VANADIUM_GROUP));

    //processeur
    public static final Item TRANSISTOR = new Item(new Item.Settings().group(FVM.VANADIUM_GROUP).maxCount(16));
    public static final Item PROCESSOR = new Item(new Item.Settings().group(FVM.VANADIUM_GROUP).maxCount(16));

    public static void registerAll() {

        registerNewItem("vanadium_nugget", VANADIUM_NUGGET);
        registerNewItem("vanadium_ingot", VANADIUM_INGOT);

        registerNewItem("vanadium_stick", VANADIUM_STICK);
        registerNewItem("vanadium_heart", VANADIUM_HEART);
        registerNewItem("emerald_heart", EMERALD_HEART);

        registerNewItem("vanadium_helmet", VANADIUM_HELMET);
        registerNewItem("vanadium_chestplate", VANADIUM_CHESTPLATE);
        registerNewItem("vanadium_leggings", VANADIUM_LEGGINGS);
        registerNewItem("vanadium_boots", VANADIUM_BOOTS);

        registerNewItem("emerald_helmet", EMERALD_HELMET);
        registerNewItem("emerald_chestplate", EMERALD_CHESTPLATE);
        registerNewItem("emerald_leggings", EMERALD_LEGGINGS);
        registerNewItem("emerald_boots", EMERALD_BOOTS);

        registerNewItem("tungsten_ingot", TUNGSTEN_INGOT);
        registerNewItem("tungsten_chestplate", TUNGSTEN_CHESTPLATE);

        registerNewItem("vanadium_sword", VANADIUM_SWORD);

        registerNewItem("silver_ingot", SILVER_INGOT);

        registerNewItem("copper_ingot", COPPER_INGOT);

        registerNewItem("tin_ingot", TIN_INGOT);

        registerNewItem("transistor", TRANSISTOR);
        registerNewItem("processor", PROCESSOR);

        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(FVM.MODID), new RecipeSerializer<Recipe>() {
            @Override
            public Recipe read(Identifier id, JsonObject json) {
                return null;
            }

            @Override
            public Recipe read(Identifier id, PacketByteBuf buf) {
                return null;
            }

            @Override
            public void write(PacketByteBuf buf, Recipe recipe) {

            }
        });
    }

    private static void registerNewItem(String name, Item item){
        Registry.register(Registry.ITEM, new Identifier(FVM.MODID, name), item);
    }
}