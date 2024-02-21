package fr.vana_mod.nicofighter45.mixins;

import fr.vana_mod.nicofighter45.items.ModItems;
import fr.vana_mod.nicofighter45.main.server.CustomPlayer;
import fr.vana_mod.nicofighter45.main.server.ServerInitializer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {

    @Unique
    private final ServerWorld world = (ServerWorld) (Object) this;
    @Unique
    private int timer_heal = 40; // 40 ticks = 2 sec

    @Inject(at = @At("HEAD"), method = "onPlayerConnected")
    private void onPlayerConnected(@NotNull ServerPlayerEntity player, CallbackInfo info) {
        int permission = Objects.requireNonNull(player.getServer()).getPermissionLevel(player.getGameProfile());
        if (!ServerInitializer.isOn && permission != 4) {
            Objects.requireNonNull(player.getServer()).getPlayerManager().disconnectAllPlayers();
        }
        CustomPlayer customPlayer = ServerInitializer.getNullableCustomPlayer(player.getUuid());
        if (customPlayer == null) {
            ServerInitializer.newPlayer(player);
            player.setHealth(10);
        }
        customPlayer = ServerInitializer.getCustomPlayer(player.getUuid());
        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(customPlayer.getHeart());
    }

    @Inject(at = @At("HEAD"), method = "onPlayerRespawned")
    private void onPlayerRespawned(@NotNull ServerPlayerEntity player, CallbackInfo info) {
        int heart = ServerInitializer.getCustomPlayer(player.getUuid()).getHeart();
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
                int regen = ServerInitializer.getCustomPlayer(player.getUuid()).getRegen();
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

    @Unique
    private double getDistFromCenter(@NotNull ServerPlayerEntity player) {
        double x = player.getX();
        double z = player.getZ();
        return Math.sqrt(x * x + z * z);
    }
}