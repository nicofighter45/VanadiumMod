package fr.vana_mod.nicofighter45.mixins;

import fr.vana_mod.nicofighter45.items.ModItems;
import fr.vana_mod.nicofighter45.main.server.CustomPlayer;
import fr.vana_mod.nicofighter45.main.server.ServerInitializer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
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
    private int timer_heal = 40; // 40 ticks = 2 sec

    @Inject(at = @At("HEAD"), method = "onPlayerConnected")
    private void onPlayerConnected(@NotNull ServerPlayerEntity player, CallbackInfo info) {
        int permission = Objects.requireNonNull(player.getServer()).getPermissionLevel(player.getGameProfile());
        if (!ServerInitializer.isOn && permission != 4) {
            Objects.requireNonNull(player.getServer()).getPlayerManager().disconnectAllPlayers();
        }
        if (!ServerInitializer.players.containsKey(player.getUuid())) {
            ServerInitializer.players.put(player.getUuid(), new CustomPlayer(10, 0, player.getServer().getOverworld().getSpawnPos()));
            player.setHealth(10);
        }
        CustomPlayer customPlayer = ServerInitializer.players.get(player.getUuid());
        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(customPlayer.getHeart());
        String color = "§9";
        if (permission == 4) {
            color = "§4";
        } else if (permission == 1) {
            color = "§e";
        }
        for (ServerPlayerEntity pl : player.getServer().getPlayerManager().getPlayerList()) {
            pl.sendMessage(Text.of(ServerInitializer.SERVER_MSG_PREFIX + Text.translatable("mixins.vana-mod.player_join").getString()
                    .replace("{value}", color + player.getEntityName() + "§f")));
        }
    }

    @Inject(at = @At("HEAD"), method = "onPlayerRespawned")
    private void onPlayerRespawned(@NotNull ServerPlayerEntity player, CallbackInfo info) {
        int heart = ServerInitializer.players.get(player.getUuid()).getHeart();
        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(heart);
        player.setHealth(heart);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(BooleanSupplier shouldKeepTicking, CallbackInfo info) {
        if (timer_heal > 0) {
            timer_heal--;
        } else if (timer_heal == 0) {
            timer_heal--;
            for (ServerPlayerEntity player : world.getPlayers()) {
                int regen = ServerInitializer.players.get(player.getUuid()).getRegen();
                if (player.getInventory().getArmorStack(EquipmentSlot.HEAD.getEntitySlotId()).getItem() == ModItems.VANADIUM_HELMET &&
                        player.getInventory().getArmorStack(EquipmentSlot.LEGS.getEntitySlotId()).getItem() == ModItems.VANADIUM_LEGGINGS &&
                        player.getInventory().getArmorStack(EquipmentSlot.FEET.getEntitySlotId()).getItem() == ModItems.VANADIUM_BOOTS) {
                    regen += 16;
                }
                if (player.getHealth() < regen) {
                    player.heal(0.5f);
                } else if (player.getHealth() < player.getMaxHealth() && getDistFromCenter(player) < 50
                        && world.getServer().getOverworld() == world && player.getY() < 150) {
                    player.heal(0.5f);
                }
            }
            timer_heal = 40;
        }
    }

    private double getDistFromCenter(@NotNull ServerPlayerEntity player) {
        double x = player.getX();
        double z = player.getZ();
        return Math.sqrt(x * x + z * z);
    }
}