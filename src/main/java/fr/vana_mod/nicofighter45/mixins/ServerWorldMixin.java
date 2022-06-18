package fr.vana_mod.nicofighter45.mixins;

import fr.vana_mod.nicofighter45.bosses.BossesManagement;
import fr.vana_mod.nicofighter45.main.CustomPlayer;
import fr.vana_mod.nicofighter45.main.VanadiumModServer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;
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
    public void onPlayerConnected(@NotNull ServerPlayerEntity player, CallbackInfo info) {
        if(!VanadiumModServer.players.containsKey(player.getUuid())){
            VanadiumModServer.players.put(player.getUuid(), new CustomPlayer(10, 0, false, false));
        }
        CustomPlayer customPlayer = VanadiumModServer.players.get(player.getUuid());
        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(customPlayer.getHeart());
        player.setHealth(customPlayer.getHeart());
    }

    @Inject(at = @At("HEAD"), method = "onPlayerRespawned")
    public void onPlayerRespawned(@NotNull ServerPlayerEntity player, CallbackInfo info) {
        int heart = VanadiumModServer.players.get(player.getUuid()).getHeart();
        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(heart);
        player.setHealth(heart);
    }

    private int timer_heal = 40; // 40 ticks = 2 sec
    private boolean first_tick = true;

    @Inject(at= @At("HEAD"), method = "tick")
    public void tick(BooleanSupplier shouldKeepTicking, CallbackInfo info) {
        if(first_tick){
            if(world == world.getServer().getOverworld()){
                VanadiumModServer.bossesManagement = new BossesManagement(world);
            }
            first_tick = false;
        }
        if(timer_heal > 0){
            timer_heal--;
        }else if(timer_heal == 0){
            timer_heal--;
            for(ServerPlayerEntity player : world.getPlayers()){
                if(player.getHealth() < VanadiumModServer.players.get(player.getUuid()).getRegen()){
                    player.heal(0.5f);
                }else if(player.getHealth() < player.getMaxHealth() && getDistFromCenter(player) < 100
                        && world.getServer().getOverworld() == world && player.getY() < 150){
                    player.heal(0.5f);
                }
            }
            timer_heal = 40;
        }
    }

    private double getDistFromCenter(@NotNull ServerPlayerEntity player) {
        double x = player.getX();
        double z = player.getZ();
        return Math.sqrt(x*x+z*z);
    }
}