package vana_mod.nicofighter45.fr.bosses;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import vana_mod.nicofighter45.fr.items.ModItems;

public class BossesManagement {

    private final ServerWorld world;
    private ZombieEntity boss1 = null;
    private ZombieEntity boss2 = null;
    private ZombieEntity boss3 = null;
    private ZombieEntity boss4 = null;
    private ZombieEntity boss5 = null;

    public BossesManagement(ServerWorld world){
        this.world = world;
    }

    public void despawnBosses(){
        if(boss1 != null){
            if(boss1.isAlive()){
                boss1.kill();
            }
        }
        if(boss2 != null){
            if(boss2.isAlive()){
                boss2.kill();
            }
        }
        if(boss3 != null){
            if(boss3.isAlive()){
                boss3.kill();
            }
        }
        if(boss4 != null){
            if(boss4.isAlive()){
                boss4.kill();
            }
        }
        if(boss5 != null){
            if(boss5.isAlive()){
                boss5.kill();
            }
        }
    }

    public boolean spawnBoss(CustomBossConfig boss){
        if(boss.getKey() == 1){
            if(boss1 != null && boss1.isAlive()){
                return false;
            }
            boss1 = EntityType.ZOMBIE.create(world);
            boss1.refreshPositionAndAngles(boss.getX(), boss.getY(), boss.getZ(), 0,0);
            ItemStack main_hand = new ItemStack(Items.DIAMOND_SWORD);
            main_hand.addEnchantment(Enchantments.SHARPNESS, 3);
            ItemStack helmet = new ItemStack(Items.DIAMOND_HELMET);
            helmet.addEnchantment(Enchantments.PROTECTION, 3);
            boss1.setStackInHand(Hand.MAIN_HAND, main_hand);
            boss1.setStackInHand(Hand.OFF_HAND, new ItemStack(ModItems.STEAM_PLACK));
            boss1.equipStack(EquipmentSlot.HEAD, helmet);
            boss1.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0);
            boss1.setEquipmentDropChance(EquipmentSlot.OFFHAND, 100);
            boss1.setEquipmentDropChance(EquipmentSlot.HEAD, 0);
            boss1.setCustomName(new LiteralText("§6Boss 1"));
            boss1.setGlowing(true);
            boss1.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, Integer.MAX_VALUE, 2, false, false, false));
            boss1.setHealth(30);
            world.spawnEntity(boss1);
            ItemStack bow = new ItemStack(Items.BOW);
            bow.addEnchantment(Enchantments.POWER, 2);
            SkeletonEntity sk1 = EntityType.SKELETON.create(world);
            sk1.refreshPositionAndAngles(boss.getX() + 2, boss.getY(), boss.getZ(), 0,0);
            sk1.setStackInHand(Hand.MAIN_HAND, bow);
            world.spawnEntity(sk1);
            SkeletonEntity sk2 = EntityType.SKELETON.create(world);
            sk2.refreshPositionAndAngles(boss.getX() - 2, boss.getY(), boss.getZ(), 0,0);
            sk2.setStackInHand(Hand.MAIN_HAND, bow);
            world.spawnEntity(sk2);
            for(int i = 0; i < 4; i++){
                ZombieEntity zb = EntityType.ZOMBIE.create(world);
                assert zb != null;
                zb.setBaby(true);
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
                zb.refreshPositionAndAngles(boss.getX() + x, boss.getY() + 1, boss.getZ() + z, 0,0);
                zb.setCustomName(new LiteralText("§6Mini-Boss 1"));
                zb.setHealth(15);
                world.spawnEntity(zb);
            }
            return true;
        }else if(boss.getKey() == 2){
            if(boss2 == null){
                boss2 = EntityType.ZOMBIE.create(world);
            }
            if(!boss2.isAlive()){
                boss2.refreshPositionAndAngles(boss.getX(), boss.getY(), boss.getZ(), 0,0);
                world.spawnEntity(boss2);
            }
        }else if(boss.getKey() == 3){
            if(boss3 == null){
                boss3 = EntityType.ZOMBIE.create(world);
            }
            if(!boss3.isAlive()){
                boss3.refreshPositionAndAngles(boss.getX(), boss.getY(), boss.getZ(), 0,0);
                world.spawnEntity(boss3);
            }
        }else if(boss.getKey() == 4){
            if(boss4 == null){
                boss4 = EntityType.ZOMBIE.create(world);
            }
            if(!boss4.isAlive()){
                boss4.refreshPositionAndAngles(boss.getX(), boss.getY(), boss.getZ(), 0,0);
                world.spawnEntity(boss4);
            }
        }else if(boss.getKey() == 5){
            if(boss5 == null){
                boss5 = EntityType.ZOMBIE.create(world);
            }
            if(!boss5.isAlive()){
                boss5.refreshPositionAndAngles(boss.getX(), boss.getY(), boss.getZ(), 0,0);
                world.spawnEntity(boss5);
            }
        }
        return false;
    }

}



//                //ZombieEntity zb = EntityType.ZOMBIE.create(world, ct, new TranslatableText("Boss"), player, player.getBlockPos(), SpawnReason.COMMAND, true, true);
//                timer_boss--;
//                ZombieEntity zb = EntityType.ZOMBIE.create(world);
//                assert zb != null;
//                zb.refreshPositionAndAngles(player.getBlockPos(), 0, 0);
//                zb.setStackInHand(Hand.MAIN_HAND, new ItemStack(ModItems.VANADIUM_SWORD));
//                zb.setCustomName(new TranslatableText("§6Boss"));
//                zb.setAttacking(player);
//                zb.setGlowing(true);
//                zb.equipStack(EquipmentSlot.CHEST, new ItemStack(ModItems.VANADIUM_CHESTPLATE));
//                zb.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0);
//                zb.setEquipmentDropChance(EquipmentSlot.CHEST, 0);
//                world.spawnEntity(zb);