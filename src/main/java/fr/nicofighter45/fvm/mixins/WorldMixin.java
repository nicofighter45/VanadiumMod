package fr.nicofighter45.fvm.mixins;

import fr.nicofighter45.fvm.FVM;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
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
            if(FVM.TickNumberForHeal.containsKey(player)){
                if(FVM.TickNumberForHeal.get(player) == 0) {
                    FVM.TickNumberForHeal.remove(player);
                    player.sendMessage(new TranslatableText(String.valueOf(player.getHealth())), true);
                    player.heal(0.5f);
                    if (player.getHealth() <= 9.5f) {
                        FVM.TickNumberForHeal.put(player, 60);
                    }
                }else {
                    int ticks = FVM.TickNumberForHeal.get(player);
                    FVM.TickNumberForHeal.remove(player);
                    FVM.TickNumberForHeal.put(player,  ticks - 1);
                }
            }
        }
    }

}