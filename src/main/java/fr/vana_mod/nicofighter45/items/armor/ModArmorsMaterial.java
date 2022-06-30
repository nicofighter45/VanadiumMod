package fr.vana_mod.nicofighter45.items.armor;

import fr.vana_mod.nicofighter45.items.ModItems;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;

public class ModArmorsMaterial {

    public static ArmorMaterial VANADIUM_ARMOR_MATERIAL = new BasicArmorMaterial(new int[]{13, 15, 16, 11},
            200, new int[] {4, 10, 20, 6}, 22, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,
            ModItems.VANADIUM_INGOT, "vanadium", 3, 2);

    public static ArmorMaterial EMERALD_ARMOR_MATERIAL = new BasicArmorMaterial(new int[]{13, 15, 16, 11},
            100, new int[] {3, 6, 8, 3}, 15, SoundEvents.ENTITY_VILLAGER_TRADE,
            Items.EMERALD, "emerald", 2, 1);

    public static ArmorMaterial TUNGSTEN_ARMOR_MATERIAL = new BasicArmorMaterial(new int[]{13, 15, 16, 11},
            20, new int[] {0, 0, 8, 0}, 10, SoundEvents.ENTITY_WITHER_DEATH,
            ModItems.TUNGSTEN_INGOT, "tungsten", 1, 5);

    public static ArmorMaterial STEEL_ARMOR_MATERIAL = new BasicArmorMaterial(new int[]{13, 15, 16, 11},
            20, new int[] {3, 5, 5, 4}, 8, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            ModItems.STEEL_INGOT, "steel", 1, 0.5f);

}