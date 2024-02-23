package fr.vana_mod.nicofighter45.mixins;

import fr.vana_mod.nicofighter45.main.client.ClientInitializer;
import net.minecraft.client.network.ClientPlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {

    @Inject(at=@At("HEAD"), method="canMoveVoluntarily", cancellable = true)
    private void canMoveVoluntarily(@NotNull CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(ClientInitializer.canMove);
        cir.cancel();
    }

}