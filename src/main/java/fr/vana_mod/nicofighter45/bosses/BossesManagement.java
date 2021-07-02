package fr.vana_mod.nicofighter45.bosses;

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
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import fr.vana_mod.nicofighter45.items.ModItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BossesManagement {

    private final ServerWorld world;
    private final List<Entity> boss1 = new ArrayList<>();
    private final List<Entity> boss2 = new ArrayList<>();
    private final List<Entity> boss3 = new ArrayList<>();
    private final List<Entity> boss4 = new ArrayList<>();
    private final List<Entity> boss5 = new ArrayList<>();

    public BossesManagement(ServerWorld world){
        this.world = world;
    }

    public List<Integer> isSpawned(){
        List<Integer> list = new ArrayList<>();
        for(Entity en : boss1){
            list.add(en.getId());
        }
        for(Entity en : boss2){
            list.add(en.getId());
        }
        for(Entity en : boss3){
            list.add(en.getId());
        }
        for(Entity en : boss4){
            list.add(en.getId());
        }
        for(Entity en : boss5){
            list.add(en.getId());
        }
        return list;
    }

    public void addAlreadySpawned(List<Integer> list){
        for(Integer id : list){
            Entity en = world.getEntityById(id);
            assert en != null;
            if(en.getName().toString().contains("1")){
                boss1.add(en);
            }else if(en.getName().toString().contains("2")){
                boss2.add(en);
            }else if(en.getName().toString().contains("3")){
                boss3.add(en);
            }else if(en.getName().toString().contains("4")){
                boss4.add(en);
            }else if(en.getName().toString().contains("5")){
                boss5.add(en);
            }
        }
    }

    public void despawnBosses(int key){
        switch (key){
            case 1:
                if(!boss1.isEmpty()){
                    for(Entity en : boss1){
                        if(en.isAlive()){
                            en.kill();
                        }
                    }
                }
                break;
            case 2:
                if(!boss2.isEmpty()){
                    for(Entity en : boss2){
                        if(en.isAlive()){
                            en.kill();
                        }
                    }
                }
                break;
            case 3:
                if(!boss3.isEmpty()){
                    for(Entity en : boss3){
                        if(en.isAlive()){
                            en.kill();
                        }
                    }
                }
                break;
            case 4:
                if(!boss4.isEmpty()){
                    for(Entity en : boss4){
                        if(en.isAlive()){
                            en.kill();
                        }
                    }
                }
                break;
            case 5:
                if(!boss5.isEmpty()){
                    for(Entity en : boss5){
                        if(en.isAlive()){
                            en.kill();
                        }
                    }
                }
                break;
        }
    }

    public boolean spawnBoss(CustomBossConfig boss, ServerWorld world){
        if(boss.getKey() == 1){
            for(Entity he : boss1){
                if(he.getName().equals(new LiteralText("§6Boss 1")) && he.isAlive()){
                    return false;
                }
            }
            spawn1(boss);
            return true;
        }else if(boss.getKey() == 2){
            for(Entity he : boss2){
                if(he.getName().equals(new LiteralText("§6Boss 2")) && he.isAlive()){
                    return false;
                }
            }
            spawn2(boss);
            return true;
        }else if(boss.getKey() == 3){

        }else if(boss.getKey() == 4){

        }else if(boss.getKey() == 5){

        }
        return false;
    }

    private void spawnEn(List<Entity> boss){
        for(Entity hs : boss){
            world.spawnEntity(hs);
        }
    }

    private void spawn1(CustomBossConfig boss){
        ZombieEntity zb = EntityType.ZOMBIE.create(world);
        boss1.add(zb);
        assert zb != null;
        zb.refreshPositionAndAngles(boss.getX(), boss.getY(), boss.getZ(), 0,0);
        ItemStack main_hand = new ItemStack(Items.DIAMOND_SWORD);
        main_hand.addEnchantment(Enchantments.SHARPNESS, 3);
        ItemStack helmet = new ItemStack(Items.DIAMOND_HELMET);
        helmet.addEnchantment(Enchantments.PROTECTION, 3);
        zb.setStackInHand(Hand.MAIN_HAND, main_hand);
        zb.setStackInHand(Hand.OFF_HAND, boss.getDrop());
        zb.equipStack(EquipmentSlot.HEAD, helmet);
        zb.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0);
        zb.setEquipmentDropChance(EquipmentSlot.OFFHAND, 100);
        zb.setEquipmentDropChance(EquipmentSlot.HEAD, 0);
        zb.setCustomName(new LiteralText("§6Boss 1"));
        zb.setGlowing(true);
        Objects.requireNonNull(zb.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(30);
        Objects.requireNonNull(zb.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.35);
        zb.setHealth(30);
        ItemStack bow = new ItemStack(Items.BOW);
        bow.addEnchantment(Enchantments.POWER, 2);
        SkeletonEntity sk1 = EntityType.SKELETON.create(world);
        assert sk1 != null;
        sk1.refreshPositionAndAngles(boss.getX() + 2, boss.getY(), boss.getZ(), 0,0);
        sk1.setStackInHand(Hand.MAIN_HAND, bow);
        boss1.add(sk1);
        SkeletonEntity sk2 = EntityType.SKELETON.create(world);
        assert sk2 != null;
        sk2.refreshPositionAndAngles(boss.getX() - 2, boss.getY(), boss.getZ(), 0,0);
        sk2.setStackInHand(Hand.MAIN_HAND, bow);
        boss1.add(sk2);
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
            }else if(i==3){
                z = 1;
            }
            zbm.refreshPositionAndAngles(boss.getX() + x, boss.getY() + 1, boss.getZ() + z, 0,0);
            zbm.setCustomName(new LiteralText("§6Mini-Boss 1"));
            Objects.requireNonNull(zb.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(15);
            Objects.requireNonNull(zb.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).setBaseValue(2);
            zb.setHealth(15);
            boss1.add(zbm);
        }
        spawnEn(boss1);
    }

    private void spawn2(CustomBossConfig boss){
        SkeletonEntity sk = EntityType.SKELETON.create(world);
        boss2.add(sk);
        assert sk != null;
        sk.refreshPositionAndAngles(boss.getX(), boss.getY(), boss.getZ(), 0,0);
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
        sk.equipStack(EquipmentSlot.OFFHAND, boss.getDrop());
        sk.equipStack(EquipmentSlot.HEAD, helmet);
        sk.equipStack(EquipmentSlot.CHEST, chestplate);
        sk.equipStack(EquipmentSlot.LEGS, leggings);
        sk.equipStack(EquipmentSlot.FEET, boots);
        sk.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0);
        sk.setEquipmentDropChance(EquipmentSlot.OFFHAND, 100);
        sk.setEquipmentDropChance(EquipmentSlot.HEAD, 0);
        sk.setEquipmentDropChance(EquipmentSlot.CHEST, 0);
        sk.setEquipmentDropChance(EquipmentSlot.LEGS, 0);
        sk.setEquipmentDropChance(EquipmentSlot.FEET, 0);
        sk.setCustomName(new LiteralText("§6Boss 2"));
        sk.setGlowing(true);
        Objects.requireNonNull(sk.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(100);
        sk.setHealth(100);
        TntEntity tnt = EntityType.TNT.create(world);
        boss2.add(tnt);
        assert tnt != null;
        tnt.setFuse(40);
        tnt.setInvisible(false);
        tnt.refreshPositionAndAngles(boss.getX() + 5, boss.getY(), boss.getZ() + 5, 0,0);
        spawnEn(boss2);
    }

}