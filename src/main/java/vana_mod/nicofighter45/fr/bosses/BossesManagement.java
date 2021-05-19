package vana_mod.nicofighter45.fr.bosses;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import vana_mod.nicofighter45.fr.items.ModItems;

import java.util.ArrayList;
import java.util.List;

public class BossesManagement {

    private final ServerWorld world;
    private final List<HostileEntity> boss1 = new ArrayList<>();
    private final List<HostileEntity> boss2 = new ArrayList<>();
    private final List<HostileEntity> boss3 = new ArrayList<>();
    private final List<HostileEntity> boss4 = new ArrayList<>();
    private final List<HostileEntity> boss5 = new ArrayList<>();

    public BossesManagement(ServerWorld world){
        this.world = world;
    }

    public void despawnBosses(){
        if(!boss1.isEmpty()){
            for(HostileEntity en : boss1){
                if(en.isAlive()){
                    en.kill();
                }
            }
        }
        if(!boss2.isEmpty()){
            for(HostileEntity en : boss2){
                if(en.isAlive()){
                    en.kill();
                }
            }
        }
        if(!boss3.isEmpty()){
            for(HostileEntity en : boss3){
                if(en.isAlive()){
                    en.kill();
                }
            }
        }
        if(!boss4.isEmpty()){
            for(HostileEntity en : boss4){
                if(en.isAlive()){
                    en.kill();
                }
            }
        }
        if(!boss5.isEmpty()){
            for(HostileEntity en : boss5){
                if(en.isAlive()){
                    en.kill();
                }
            }
        }
    }

    public boolean spawnBoss(CustomBossConfig boss){
        if(boss.getKey() == 1){
            for(HostileEntity he : boss1){
                if(he.getName().equals(new LiteralText("§6Boss 1")) && he.isAlive()){
                    return false;
                }
            }
            spawn1(boss);
            return true;
        }else if(boss.getKey() == 2){
            for(HostileEntity he : boss2){
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

    private void spawnEn(List<HostileEntity> boss){
        for(HostileEntity hs : boss){
            world.spawnEntity(hs);
        }
    }

    private void spawn1(CustomBossConfig boss){
        ZombieEntity zb = EntityType.ZOMBIE.create(world);
        boss1.add(zb);
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
        zb.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, Integer.MAX_VALUE, 2, false, false, false));
        zb.setHealth(30);
        ItemStack bow = new ItemStack(Items.BOW);
        bow.addEnchantment(Enchantments.POWER, 2);
        SkeletonEntity sk1 = EntityType.SKELETON.create(world);
        sk1.refreshPositionAndAngles(boss.getX() + 2, boss.getY(), boss.getZ(), 0,0);
        sk1.setStackInHand(Hand.MAIN_HAND, bow);
        boss1.add(sk1);
        SkeletonEntity sk2 = EntityType.SKELETON.create(world);
        sk2.refreshPositionAndAngles(boss.getX() - 2, boss.getY(), boss.getZ(), 0,0);
        sk2.setStackInHand(Hand.MAIN_HAND, bow);
        boss1.add(sk2);
        for(int i = 0; i < 4; i++){
            ZombieEntity zbm = EntityType.ZOMBIE.create(world);
            assert zbm != null;
            zbm.setBaby(true);
            int x = 0;
            int z = 0;
            if(i==0){
                x = 1;
                z = 1;
            }else if(i==1){
                x = 1;
                z = -1;
            }else if(i==2){
                x = -1;
                z = 1;
            }else if(i==3){
                x = -1;
                z = -1;
            }
            zbm.refreshPositionAndAngles(boss.getX() + x, boss.getY() + 1, boss.getZ() + z, 0,0);
            zbm.setCustomName(new LiteralText("§6Mini-Boss 1"));
            zbm.setHealth(15);
            boss1.add(zbm);
        }
        spawnEn(boss1);
    }

    private void spawn2(CustomBossConfig boss){
        SkeletonEntity sk = EntityType.SKELETON.create(world);
        boss2.add(sk);
        //boss2 creation is #todo
    }

}