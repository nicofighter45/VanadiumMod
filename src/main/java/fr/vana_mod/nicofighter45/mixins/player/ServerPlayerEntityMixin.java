package fr.vana_mod.nicofighter45.mixins.player;

import fr.vana_mod.nicofighter45.items.enchantment.ModEnchants;
import fr.vana_mod.nicofighter45.main.server.CustomPlayer;
import fr.vana_mod.nicofighter45.main.server.ServerInitializer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @Unique
    private final ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

    @Unique
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return player.getInventory().getMainHandStack();
        } else if (slot == EquipmentSlot.OFFHAND) {
            return player.getInventory().offHand.get(0);
        } else {
            return slot.getType() == EquipmentSlot.Type.ARMOR ? player.getInventory().armor.get(slot.getEntitySlotId()) : ItemStack.EMPTY;
        }
    }

    @Inject(at = @At("HEAD"), method = "onDeath", cancellable = true)
    private void onDeath(DamageSource damageSource, CallbackInfo ci) {
        EnderChestInventory inventory = player.getEnderChestInventory();
        if (inventory.removeItem(Items.EMERALD_BLOCK, 2).getCount() == 2) {
            player.setHealth(player.getMaxHealth());
            BlockPos pos = player.getSpawnPointPosition();
            ServerWorld world = Objects.requireNonNull(player.getServer()).getOverworld();
            if (pos == null) {
                pos = world.getSpawnPos();
            }
            player.fallDistance = 0;
            player.teleport(world, pos.getX(), pos.getY(), pos.getZ(), 0, 0);
            world.spawnParticles(ParticleTypes.CLOUD, pos.getX(), pos.getY(), pos.getZ(), 10000, 0, 0, 0, 2);
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE.value(),
                    SoundCategory.BLOCKS, 1f, 1f, false);
            for (ServerPlayerEntity players : player.getServer().getPlayerManager().getPlayerList()) {
                players.sendMessage(player.getDamageTracker().getDeathMessage());
            }
            ci.cancel();
        }
    }


    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    private void damage(@NotNull DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.isIn(DamageTypeTags.IS_FALL) && EnchantmentHelper.get(getEquippedStack(EquipmentSlot.FEET)).containsKey(ModEnchants.NO_FALL)) {
            cir.setReturnValue(false);
        } else if (source.isIn(DamageTypeTags.IS_FALL) && ServerInitializer.jump && Math.sqrt(Math.pow(player.getX(), 2) +
                Math.pow(player.getY(), 2)) <= 100) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "readCustomDataFromNbt")
    private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        CustomPlayer customPlayer = ServerInitializer.getNullableCustomPlayer(player.getUuid());
        if (customPlayer != null) {
            customPlayer.setHeart(nbt.getInt("heart"));
            customPlayer.setRegen(nbt.getInt("regen"));
            customPlayer.setBase(new BlockPos(nbt.getInt("baseX"), nbt.getInt("baseY"), nbt.getInt("baseZ")));
        } else {
            ServerInitializer.loadPlayer(player.getUuid(), new CustomPlayer(nbt.getInt("heart"), nbt.getInt("regen"),
                    new BlockPos(nbt.getInt("baseX"), nbt.getInt("baseY"), nbt.getInt("baseZ"))));
        }
    }

    @Inject(at = @At("HEAD"), method = "writeCustomDataToNbt")
    private void writeCustomDataToNbt(@NotNull NbtCompound nbt, CallbackInfo ci) {
        CustomPlayer pl = ServerInitializer.getCustomPlayer(player.getUuid());
        nbt.putInt("heart", pl.getHeart());
        nbt.putInt("regen", pl.getRegen());
        nbt.putInt("baseX", pl.getBase().getX());
        nbt.putInt("baseY", pl.getBase().getX());
        nbt.putInt("baseZ", pl.getBase().getX());
    }

}