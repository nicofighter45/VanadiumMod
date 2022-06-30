package fr.vana_mod.nicofighter45.items.enchantment;

import fr.vana_mod.nicofighter45.items.armor.ModArmorsMaterial;
import fr.vana_mod.nicofighter45.main.VanadiumMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
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
    public static Enchantment FASTER = new FasterEnchantment(
            Enchantment.Rarity.RARE,
            EnchantmentTarget.ARMOR,
            new EquipmentSlot[]{
                    EquipmentSlot.LEGS
            }
    );
    public static Enchantment JUMPER = new JumperEnchantment(
            Enchantment.Rarity.RARE,
            EnchantmentTarget.ARMOR,
            new EquipmentSlot[]{
                    EquipmentSlot.FEET
            }
    );
    public static Enchantment HASTER = new HasterEnchantment(
            Enchantment.Rarity.RARE,
            EnchantmentTarget.ARMOR,
            new EquipmentSlot[]{
                    EquipmentSlot.HEAD
            }
    );
    public static Enchantment STRENGHTER = new StrenghterEnchantment(
            Enchantment.Rarity.RARE,
            EnchantmentTarget.ARMOR,
            new EquipmentSlot[]{
                    EquipmentSlot.HEAD
            }
    );
    public static Enchantment RESISTANCER = new ResistancerEnchantment(
            Enchantment.Rarity.RARE,
            EnchantmentTarget.ARMOR,
            new EquipmentSlot[]{
                    EquipmentSlot.LEGS
            }
    );
    public static Enchantment NO_FALL = new No_FallEnchantment(
            Enchantment.Rarity.RARE,
            EnchantmentTarget.ARMOR,
            new EquipmentSlot[]{
                    EquipmentSlot.FEET
            }
    );
    public static Enchantment EXPERIENCE = new ExperienceEnchantment(
            Enchantment.Rarity.RARE,
            EnchantmentTarget.WEAPON,
            new EquipmentSlot[]{
                    EquipmentSlot.MAINHAND
            }
    );

    public static void registerAll(){
        Registry.register(Registry.ENCHANTMENT, new Identifier(VanadiumMod.MODID, "faster"), FASTER);
        Registry.register(Registry.ENCHANTMENT, new Identifier(VanadiumMod.MODID, "jumper"), JUMPER);
        Registry.register(Registry.ENCHANTMENT, new Identifier(VanadiumMod.MODID, "haster"), HASTER);
        Registry.register(Registry.ENCHANTMENT, new Identifier(VanadiumMod.MODID, "strenghter"), STRENGHTER);
        Registry.register(Registry.ENCHANTMENT, new Identifier(VanadiumMod.MODID, "resistancer"), RESISTANCER);
        Registry.register(Registry.ENCHANTMENT, new Identifier(VanadiumMod.MODID, "no_fall"), NO_FALL);
        Registry.register(Registry.ENCHANTMENT, new Identifier(VanadiumMod.MODID, "experience"), EXPERIENCE);
    }

    public static @Nullable Enchantment getEnchant(String name){
        Enchantment[] table = {FASTER, JUMPER, HASTER, STRENGHTER, RESISTANCER, NO_FALL, EXPERIENCE};
        for(Enchantment enchantment : table){
            if(enchantment.getTranslationKey().equals(name)){
                return enchantment;
            }
        }
        return null;
    }

}