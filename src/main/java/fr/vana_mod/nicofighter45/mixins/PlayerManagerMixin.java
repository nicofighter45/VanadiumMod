package fr.vana_mod.nicofighter45.mixins;

import com.mojang.authlib.GameProfile;
import fr.vana_mod.nicofighter45.main.server.commands.ExtendedGameProfile;
import net.minecraft.server.OperatorEntry;
import net.minecraft.server.OperatorList;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {

    @Unique
    private final PlayerManager playerManager = (PlayerManager) (Object) this;

    @Inject(at = @At("HEAD"), method="addToOperators", cancellable = true)
    public void addToOperators(GameProfile profile, CallbackInfo ci) {
        int opsPermissionLevel = playerManager.getServer().getOpPermissionLevel();
        OperatorList ops = ((PlayerManagerAccessor) playerManager).getOps();
        if(profile instanceof ExtendedGameProfile extendedGameProfile){
            ops.add(new OperatorEntry(profile, extendedGameProfile.getPermissionLevel(),
                    opsPermissionLevel <= extendedGameProfile.getPermissionLevel()));
        }else{
            ops.add(new OperatorEntry(profile, opsPermissionLevel, ops.canBypassPlayerLimit(profile)));
            System.out.println("Not a extendedGameProfile");
        }
        ServerPlayerEntity serverPlayerEntity = playerManager.getPlayer(profile.getId());
        if (serverPlayerEntity != null) {
            playerManager.sendCommandTree(serverPlayerEntity);
        }
        ci.cancel();
    }

}