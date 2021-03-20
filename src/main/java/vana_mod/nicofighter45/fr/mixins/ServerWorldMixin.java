package vana_mod.nicofighter45.fr.mixins;

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
    private int timer = 40;

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

    @Inject(at= @At("HEAD"), method = "tick")
    public void tick(BooleanSupplier shouldKeepTicking, CallbackInfo info) {
        if(timer > 0){
            timer--;
        }else if(timer == 0){
            timer--;
            for(ServerPlayerEntity player : world.getPlayers()){
                if(player.getHealth() < VanadiumModServer.players.get(player.getEntityName()).getRegen()){
                    player.heal(0.5f);
                }
            }
            timer = 40;
        }
    }

}