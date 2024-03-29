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

    public static Enchantment HASTER = new BasicEffectEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.HEAD,
            true, true, StatusEffects.HASTE, ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL,
            ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, 3, 1);
    public static Enchantment STRENGHTER = new BasicEffectEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.HEAD,
            true, true, StatusEffects.STRENGTH, ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL,
            ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, 2, 1);
    public static Enchantment RESISTANCER = new BasicEffectEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.LEGS,
            true, true, StatusEffects.RESISTANCE, ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL,
            ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, 2, 1);
    public static Enchantment FASTER = new BasicEffectEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.LEGS,
            true, true, StatusEffects.SPEED, ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL,
            ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, 5, 2);
    public static Enchantment JUMPER = new BasicEffectEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.FEET,
            true, true, StatusEffects.JUMP_BOOST, ModArmorsMaterial.VANADIUM_ARMOR_MATERIAL,
            ModArmorsMaterial.EMERALD_ARMOR_MATERIAL, 5, 2);
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