package fr.nicofighter45.fvm.items.enchantment;

import fr.nicofighter45.fvm.FVM;
import fr.nicofighter45.fvm.items.enchantment.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEnchants {

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
    public static Enchantment NO_FALL = new No_FallEnchantment(
            Enchantment.Rarity.VERY_RARE,
            EnchantmentTarget.ARMOR,
            new EquipmentSlot[]{
                    EquipmentSlot.FEET
            }
    );

    public static void registerAll(){
        Registry.register(Registry.ENCHANTMENT, new Identifier(FVM.MODID, "faster"), FASTER);
        Registry.register(Registry.ENCHANTMENT, new Identifier(FVM.MODID, "jumper"), JUMPER);
        Registry.register(Registry.ENCHANTMENT, new Identifier(FVM.MODID, "haster"), HASTER);
        Registry.register(Registry.ENCHANTMENT, new Identifier(FVM.MODID, "strenghter"), STRENGHTER);
        Registry.register(Registry.ENCHANTMENT, new Identifier(FVM.MODID, "resistancer"), RESISTANCER);
        Registry.register(Registry.ENCHANTMENT, new Identifier(FVM.MODID, "no_fall"), NO_FALL);
    }

}