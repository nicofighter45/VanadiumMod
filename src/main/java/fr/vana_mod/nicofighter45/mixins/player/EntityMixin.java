package fr.vana_mod.nicofighter45.mixins.player;

import fr.vana_mod.nicofighter45.main.client.ClientInitializer;
import fr.vana_mod.nicofighter45.main.server.ServerInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Unique
    private final Entity entity = (Entity) (Object) this;

    @Inject(at=@At("HEAD"), method="canMoveVoluntarily", cancellable = true)
    private void canMoveVoluntarily(CallbackInfoReturnable<Boolean> cir){
        if(entity instanceof PlayerEntity playerEntity){
            if(entity.getWorld().isClient()){
                cir.setReturnValue(ClientInitializer.canMove);
            }else{
                cir.setReturnValue(!ServerInitializer.frozenPlayer.contains(playerEntity.getUuid()));
            }
            cir.cancel();
        }
    }

}