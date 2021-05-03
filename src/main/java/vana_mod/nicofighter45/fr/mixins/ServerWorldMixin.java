package vana_mod.nicofighter45.fr.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import vana_mod.nicofighter45.fr.items.ModItems;
import vana_mod.nicofighter45.fr.main.CustomPlayer;
import vana_mod.nicofighter45.fr.main.VanadiumModServer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {

    private final ServerWorld world = (ServerWorld) (Object) this;

    @Inject(at = @At("HEAD"), method = "onPlayerConnected")
    public void onPlayerConnected(ServerPlayerEntity player, CallbackInfo info) {
        VanadiumModServer.addNewPlayer(player.getEntityName(), player.getUuid());
        CustomPlayer cp = VanadiumModServer.players.get(player.getEntityName());
        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(cp.getHeart());
    }

    @Inject(at = @At("HEAD"), method = "onPlayerRespawned")
    public void onPlayerRespawned(ServerPlayerEntity player, CallbackInfo info) {
        int heart = VanadiumModServer.players.get(player.getEntityName()).getHeart();
        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(heart);
        player.setHealth(heart);
    }

    private int timer_heal = 40; // 40 ticks = 2 sec
    private int timer_boss = 20; // 72000 ticks = 1 hour

    @Inject(at= @At("HEAD"), method = "tick")
    public void tick(BooleanSupplier shouldKeepTicking, CallbackInfo info) {
        if(timer_heal > 0){
            timer_heal--;
        }else if(timer_heal == 0){
            timer_heal--;
            for(ServerPlayerEntity player : world.getPlayers()){
                if(player.getHealth() < VanadiumModServer.players.get(player.getEntityName()).getRegen()){
                    player.heal(0.5f);
                }
            }
            timer_heal = 40;
        }

        if(timer_boss > 0){
            timer_boss--;
        }else if(timer_boss == 0){
            for(ServerPlayerEntity player : world.getPlayers()){
                //CompoundTag ct = new CompoundTag();
                //ZombieEntity zb = EntityType.ZOMBIE.create(world, ct, new TranslatableText("Boss"), player, player.getBlockPos(), SpawnReason.COMMAND, true, true);
                timer_boss--;
                ZombieEntity zb = EntityType.ZOMBIE.create(world);
                assert zb != null;
                zb.refreshPositionAndAngles(player.getBlockPos(), 0, 0);
                zb.setStackInHand(Hand.MAIN_HAND, new ItemStack(ModItems.VANADIUM_SWORD));
                zb.setCustomName(new TranslatableText("§6Boss"));
                zb.setAttacking(player);
                zb.setGlowing(true);
                zb.equipStack(EquipmentSlot.CHEST, new ItemStack(ModItems.VANADIUM_CHESTPLATE));
                zb.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0);
                zb.setEquipmentDropChance(EquipmentSlot.CHEST, 0);
                world.spawnEntity(zb);
                player.sendMessage(new LiteralText("A boss just spawn !"), false);
                timer_boss = 20;
            }
        }
    }
}