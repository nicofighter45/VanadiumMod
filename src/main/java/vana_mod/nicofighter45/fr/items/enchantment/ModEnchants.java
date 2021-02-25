package vana_mod.nicofighter45.fr.items.enchantment;

import vana_mod.nicofighter45.fr.MAIN;
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
        Registry.register(Registry.ENCHANTMENT, new Identifier(MAIN.MODID, "faster"), FASTER);
        Registry.register(Registry.ENCHANTMENT, new Identifier(MAIN.MODID, "jumper"), JUMPER);
        Registry.register(Registry.ENCHANTMENT, new Identifier(MAIN.MODID, "haster"), HASTER);
        Registry.register(Registry.ENCHANTMENT, new Identifier(MAIN.MODID, "strenghter"), STRENGHTER);
        Registry.register(Registry.ENCHANTMENT, new Identifier(MAIN.MODID, "resistancer"), RESISTANCER);
        Registry.register(Registry.ENCHANTMENT, new Identifier(MAIN.MODID, "no_fall"), NO_FALL);
    }

}