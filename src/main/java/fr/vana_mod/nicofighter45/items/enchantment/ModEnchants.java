package fr.vana_mod.nicofighter45.items.enchantment;

import fr.vana_mod.nicofighter45.items.armor.ModArmorsMaterial;
import fr.vana_mod.nicofighter45.main.CommonInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ModEnchants {

    //test
    public static Enchantment MINI_HASTER = new BasicEffectEnchantment(Enchantment.Rarity.UNCOMMON,
            EquipmentSlot.HEAD, false, true, StatusEffects.HASTE,
            ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, 1);
    public static Enchantment MINI_RESISTANCER = new BasicEffectEnchantment(Enchantment.Rarity.UNCOMMON,
            EquipmentSlot.CHEST, false, true, StatusEffects.RESISTANCE,
            ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, 1);
    public static Enchantment MINI_STRENGTHER = new BasicEffectEnchantment(Enchantment.Rarity.UNCOMMON,
            EquipmentSlot.LEGS, false, true, StatusEffects.STRENGTH,
            ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, 1);
    public static Enchantment MINI_FASTER = new BasicEffectEnchantment(Enchantment.Rarity.UNCOMMON,
            EquipmentSlot.FEET, false, true, StatusEffects.SPEED,
            ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, 1);

    //enchants
    public static Enchantment HASTER = new BasicEffectEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.HEAD,
            false, false, StatusEffects.HASTE, ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL, 3);
    public static Enchantment STRENGHTER = new BasicEffectEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.HEAD,
            false, false, StatusEffects.STRENGTH, ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL, 2);
    public static Enchantment RESISTANCER = new BasicEffectEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.LEGS,
            false, false, StatusEffects.RESISTANCE, ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL, 2);
    public static Enchantment FASTER = new BasicEffectEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.LEGS,
            false, false, StatusEffects.SPEED, ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL, 5);
    public static Enchantment JUMPER = new BasicEffectEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.FEET,
            false, false, StatusEffects.JUMP_BOOST, ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL, 5);

    public static Enchantment NO_FALL = new NoFallEnchantment();
    public static Enchantment EXPERIENCE = new ExperienceEnchantment();

    public static void registerAll() {
        Registry.register(Registries.ENCHANTMENT, new Identifier(CommonInitializer.MODID, "faster"), FASTER);
        Registry.register(Registries.ENCHANTMENT, new Identifier(CommonInitializer.MODID, "jumper"), JUMPER);
        Registry.register(Registries.ENCHANTMENT, new Identifier(CommonInitializer.MODID, "haster"), HASTER);
        Registry.register(Registries.ENCHANTMENT, new Identifier(CommonInitializer.MODID, "strenghter"), STRENGHTER);
        Registry.register(Registries.ENCHANTMENT, new Identifier(CommonInitializer.MODID, "resistancer"), RESISTANCER);
        Registry.register(Registries.ENCHANTMENT, new Identifier(CommonInitializer.MODID, "no_fall"), NO_FALL);
        Registry.register(Registries.ENCHANTMENT, new Identifier(CommonInitializer.MODID, "experience"), EXPERIENCE);
    }

    public static @Nullable Enchantment getEnchant(String name) {
        Enchantment[] table = {FASTER, JUMPER, HASTER, STRENGHTER, RESISTANCER, NO_FALL, EXPERIENCE};
        for (Enchantment enchantment : table) {
            if (enchantment.getTranslationKey().equals(name)) {
                return enchantment;
            }
        }
        return null;
    }

}