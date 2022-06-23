package fr.vana_mod.nicofighter45.mixins;


import net.minecraft.network.message.MessageType;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    private final PlayerManager manager = (PlayerManager) (Object) this;

    @Inject(at = @At("HEAD"), method = "broadcast(Lnet/minecraft/text/Text;Lnet/minecraft/util/registry/RegistryKey;)V", cancellable = true)
    void broadcast(Text message, RegistryKey<MessageType> typeKey, CallbackInfo ci) {
        if (message instanceof MutableText MT) {
            if(typeKey == MessageType.SYSTEM){
                String key = ((TranslatableTextContent) MT.getContent()).getKey();
                if (key.equals("multiplayer.player.joined")){
                    ci.cancel();
                }else if (key.equals("multiplayer.player.left")) {
                    String name = (message.getString()).split(" ")[0];
                    String color = "§9";
                    for (ServerPlayerEntity player : Objects.requireNonNull(manager.getServer()).getPlayerManager().getPlayerList()) {
                        if (Objects.equals(player.getEntityName(), name) && Objects.requireNonNull(manager.getServer()).getPlayerManager().isOperator(player.getGameProfile())) {
                            color = "§4";
                        }
                    }
                    Text finalMessage = Text.of("§8[§6Server§8] " + color + name + " §fleft the game");
                    manager.broadcast(message, (player) -> finalMessage, typeKey);
                    ci.cancel();
                }
            }
        }
    }
}