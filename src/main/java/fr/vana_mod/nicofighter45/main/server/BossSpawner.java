package fr.vana_mod.nicofighter45.main.server;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.util.Hand;
import fr.vana_mod.nicofighter45.items.ModItems;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BossSpawner {

    public static void spawnBoss(int number, ServerWorld world, BlockPos pos){
        if(number == 1){
            spawn1(world, pos);
        }else if(number == 2){
            spawn2(world, pos);
        }else if(number == 3){
            spawn3(world, pos);
        }else if(number == 4){
            spawn4(world, pos);
        }else if(number == 5){
            spawn5(world, pos);
        }
    }

    private static void spawnEn(@NotNull List<Entity> boss, ServerWorld world){
        for(Entity hs : boss){
            world.spawnEntity(hs);
        }
    }

    private static void spawn1(@NotNull ServerWorld world, @NotNull BlockPos pos){
        List<Entity> entities = new ArrayList<>();
        ZombieEntity zb = EntityType.ZOMBIE.create(world);
        entities.add(zb);
        assert zb != null;
        zb.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0,0);
        ItemStack main_hand = new ItemStack(Items.DIAMOND_SWORD);
        main_hand.addEnchantment(Enchantments.SHARPNESS, 3);
        ItemStack helmet = new ItemStack(Items.DIAMOND_HELMET);
        helmet.addEnchantment(Enchantments.PROTECTION, 3);
        zb.setStackInHand(Hand.MAIN_HAND, main_hand);
        zb.equipStack(EquipmentSlot.HEAD, helmet);
        zb.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0);
        zb.setEquipmentDropChance(EquipmentSlot.HEAD, 0);
        zb.setCustomName(MutableText.of(new LiteralTextContent("§6Boss 1")));
        zb.setGlowing(true);
        Objects.requireNonNull(zb.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(30);
        Objects.requireNonNull(zb.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.35);
        zb.setHealth(30);
        ItemStack bow = new ItemStack(Items.BOW);
        bow.addEnchantment(Enchantments.POWER, 2);
        SkeletonEntity sk1 = EntityType.SKELETON.create(world);
        assert sk1 != null;
        sk1.refreshPositionAndAngles(pos.getX() + 2, pos.getY(), pos.getZ(), 0,0);
        sk1.setStackInHand(Hand.MAIN_HAND, bow);
        entities.add(sk1);
        SkeletonEntity sk2 = EntityType.SKELETON.create(world);
        assert sk2 != null;
        sk2.refreshPositionAndAngles(pos.getX() - 2, pos.getY(), pos.getZ(), 0,0);
        sk2.setStackInHand(Hand.MAIN_HAND, bow);
        entities.add(sk2);
        for(int i = 0; i < 4; i++){
            ZombieEntity zbm = EntityType.ZOMBIE.create(world);
            assert zbm != null;
            zbm.setBaby(true);
            int x = 1;
            int z = 1;
            if(i==1){
                z = -1;
            }else if(i==2){
                x = -1;
            }
            zbm.refreshPositionAndAngles(pos.getX() + x, pos.getY() + 1, pos.getZ() + z, 0,0);
            zbm.setCustomName(MutableText.of(new LiteralTextContent("§6Mini-Boss 1")));
            Objects.requireNonNull(zb.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(15);
            Objects.requireNonNull(zb.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).setBaseValue(2);
            zb.setHealth(15);
            entities.add(zbm);
        }
        spawnEn(entities, world);
    }

    private static void spawn2(@NotNull ServerWorld world, @NotNull BlockPos pos){
        List<Entity> entities = new ArrayList<>();
        SkeletonEntity sk = EntityType.SKELETON.create(world);
        entities.add(sk);
        assert sk != null;
        sk.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0,0);
        ItemStack main_hand = new ItemStack(Items.BOW);
        main_hand.addEnchantment(Enchantments.POWER, 4);
        ItemStack helmet = new ItemStack(ModItems.EMERALD_HELMET);
        helmet.addEnchantment(Enchantments.PROTECTION, 4);
        ItemStack chestplate = new ItemStack(ModItems.EMERALD_CHESTPLATE);
        chestplate.addEnchantment(Enchantments.PROTECTION, 4);
        ItemStack leggings = new ItemStack(ModItems.EMERALD_LEGGINGS);
        leggings.addEnchantment(Enchantments.PROTECTION, 4);
        ItemStack boots = new ItemStack(ModItems.EMERALD_BOOTS);
        boots.addEnchantment(Enchantments.PROTECTION, 4);
        sk.equipStack(EquipmentSlot.MAINHAND, main_hand);
        sk.equipStack(EquipmentSlot.HEAD, helmet);
        sk.equipStack(EquipmentSlot.CHEST, chestplate);
        sk.equipStack(EquipmentSlot.LEGS, leggings);
        sk.equipStack(EquipmentSlot.FEET, boots);
        sk.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0);
        sk.setEquipmentDropChance(EquipmentSlot.HEAD, 0);
        sk.setEquipmentDropChance(EquipmentSlot.CHEST, 0);
        sk.setEquipmentDropChance(EquipmentSlot.LEGS, 0);
        sk.setEquipmentDropChance(EquipmentSlot.FEET, 0);
        sk.setCustomName(MutableText.of(new LiteralTextContent("§6Boss 2")));
        sk.setGlowing(true);
        Objects.requireNonNull(sk.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(100);
        sk.setHealth(100);
        TntEntity tnt = EntityType.TNT.create(world);
        entities.add(tnt);
        assert tnt != null;
        tnt.setFuse(40);
        tnt.setInvisible(false);
        tnt.refreshPositionAndAngles(pos.getX() + 5, pos.getY(), pos.getZ() + 5, 0,0);
        spawnEn(entities, world);
    }

    private static void spawn3(@NotNull ServerWorld world, @NotNull BlockPos pos){
        List<Entity> entities = new ArrayList<>();
        ZombieEntity zb = EntityType.ZOMBIE.create(world);
        entities.add(zb);
        assert zb != null;
        zb.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0,0);
        ItemStack main_hand = new ItemStack(Items.BOW);
        main_hand.addEnchantment(Enchantments.POWER, 5);
        ItemStack helmet = new ItemStack(ModItems.VANADIUM_HELMET);
        helmet.addEnchantment(Enchantments.PROTECTION, 4);
        ItemStack chestplate = new ItemStack(ModItems.VANADIUM_CHESTPLATE);
        chestplate.addEnchantment(Enchantments.PROTECTION, 4);
        ItemStack leggings = new ItemStack(ModItems.VANADIUM_LEGGINGS);
        leggings.addEnchantment(Enchantments.PROTECTION, 4);
        ItemStack boots = new ItemStack(ModItems.VANADIUM_BOOTS);
        boots.addEnchantment(Enchantments.PROTECTION, 4);
        zb.equipStack(EquipmentSlot.MAINHAND, main_hand);
        zb.equipStack(EquipmentSlot.HEAD, helmet);
        zb.equipStack(EquipmentSlot.CHEST, chestplate);
        zb.equipStack(EquipmentSlot.LEGS, leggings);
        zb.equipStack(EquipmentSlot.FEET, boots);
        zb.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0);
        zb.setEquipmentDropChance(EquipmentSlot.HEAD, 0);
        zb.setEquipmentDropChance(EquipmentSlot.CHEST, 0);
        zb.setEquipmentDropChance(EquipmentSlot.LEGS, 0);
        zb.setEquipmentDropChance(EquipmentSlot.FEET, 0);
        zb.setCustomName(MutableText.of(new LiteralTextContent("§6Boss 3")));
        zb.setGlowing(true);
        Objects.requireNonNull(zb.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(100);
        Objects.requireNonNull(zb.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).setBaseValue(4);
        Objects.requireNonNull(zb.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)).setBaseValue(2);
        zb.setHealth(100);
        TntEntity tnt = EntityType.TNT.create(world);
        entities.add(tnt);
        assert tnt != null;
        tnt.setFuse(40);
        tnt.setInvisible(false);
        tnt.refreshPositionAndAngles(pos.getX() + 5, pos.getY(), pos.getZ() + 5, 0,0);
        spawnEn(entities, world);
    }

    private static void spawn4(@NotNull ServerWorld world, @NotNull BlockPos pos){
        List<Entity> entities = new ArrayList<>();
        spawnEn(entities, world);
    }

    private static void spawn5(@NotNull ServerWorld world, @NotNull BlockPos pos){
        List<Entity> entities = new ArrayList<>();
        spawnEn(entities, world);
    }

}