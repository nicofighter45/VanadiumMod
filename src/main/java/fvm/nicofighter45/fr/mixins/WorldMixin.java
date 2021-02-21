package fvm.nicofighter45.fr.mixins;

import fvm.nicofighter45.fr.FVM;
import fvm.nicofighter45.fr.FVMServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(World.class)
public class WorldMixin {

    @Inject(at = @At("HEAD"), method = "tickEntity")
    public void tickEntity(Consumer<Entity> tickConsumer, Entity entity, CallbackInfo info) {
        if(entity instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) entity;
            if(FVMServer.TickNumberForHeal.containsKey(player)){
                if(FVMServer.TickNumberForHeal.get(player) == 0) {
                    FVMServer.TickNumberForHeal.remove(player);
                    player.heal(0.5f);
                    if (player.getHealth() < FVMServer.dataBaseManager.getPlayer(player.getEntityName()).getRegen()) {
                        FVMServer.TickNumberForHeal.put(player, 60);
                    }
                }else {
                    int ticks = FVMServer.TickNumberForHeal.get(player);
                    FVMServer.TickNumberForHeal.remove(player);
                    FVMServer.TickNumberForHeal.put(player,  ticks - 1);
                }
            }
        }
    }

}