package fr.vana_mod.nicofighter45.mixins;


import fr.vana_mod.nicofighter45.main.server.ServerInitializer;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SentMessage;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.Instant;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Unique
    private final PlayerManager manager = (PlayerManager) (Object) this;

    @Inject(at = @At("HEAD"), method = "broadcast(Lnet/minecraft/network/message/SignedMessage;Ljava/util/function/Predicate;Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/network/message/MessageType$Parameters;)V", cancellable = true)
    public void broadcast(SignedMessage message, Predicate<ServerPlayerEntity> shouldSendFiltered, @Nullable ServerPlayerEntity sender, MessageType.@NotNull Parameters messageType, CallbackInfo ci) {
        if (messageType.type().toString().equals("multiplayer.player.joined")) {
            ci.cancel();
        } else if (messageType.type().toString().equals("multiplayer.player.left")) {
            String name = message.getContent().getString().split(" ")[0];
            String color = "§9";
            for (ServerPlayerEntity player : Objects.requireNonNull(manager.getServer()).getPlayerManager().getPlayerList()) {
                if (player.getEntityName().equals(name)) {
                    int permission = Objects.requireNonNull(player.getServer()).getPermissionLevel(player.getGameProfile());
                    if (permission == 4) {
                        color = "§4";
                    } else if (permission == 1) {
                        color = "§e";
                    }
                }
            }
            Text finalMessage = Text.of(ServerInitializer.SERVER_MSG_PREFIX +
                    Text.translatable("mixins.vana-mod.player_leave").toString().replace("{value}",
                            color + name + "§f"));
            manager.broadcast(message.getContent(), (player) -> finalMessage, false);
            ci.cancel();
        } else {
            boolean bl = this.verify(message);
            manager.getServer().logChatMessage(message.getContent(), messageType, bl ? null : "Not Secure");
            SentMessage sentMessage = SentMessage.of(message);
            boolean bl2 = false;

            boolean bl3;
            for (Iterator<ServerPlayerEntity> var8 = manager.getPlayerList().iterator(); var8.hasNext(); bl2 |= bl3 && message.isFullyFiltered()) {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) var8.next();
                bl3 = shouldSendFiltered.test(serverPlayerEntity);
                serverPlayerEntity.sendChatMessage(sentMessage, bl3, messageType);
            }

            if (bl2 && sender != null) {
                sender.sendMessage(PlayerManager.FILTERED_FULL_TEXT);
            }
        }
    }

    @Unique
    private boolean verify(@NotNull SignedMessage message) {
        return message.hasSignature() && !message.isExpiredOnServer(Instant.now());
    }

}