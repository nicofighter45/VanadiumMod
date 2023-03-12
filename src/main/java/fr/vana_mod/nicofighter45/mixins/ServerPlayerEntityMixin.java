package fr.vana_mod.nicofighter45.mixins;

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
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SentMessage;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    private final ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

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
    private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source == DamageSource.FALL && EnchantmentHelper.get(getEquippedStack(EquipmentSlot.FEET)).containsKey(ModEnchants.NO_FALL)) {
            cir.setReturnValue(false);
        } else if (source == DamageSource.FALL && ServerInitializer.jump && Math.sqrt(Math.pow(player.getX(), 2) +
                Math.pow(player.getY(), 2)) <= 100) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "readCustomDataFromNbt")
    private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if (ServerInitializer.players.containsKey(player.getUuid())) {
            CustomPlayer pl = ServerInitializer.players.get(player.getUuid());
            pl.setHeart(nbt.getInt("heart"));
            pl.setRegen(nbt.getInt("regen"));
            pl.setBase(new BlockPos(nbt.getInt("baseX"), nbt.getInt("baseY"), nbt.getInt("baseZ")));
        } else {
            ServerInitializer.players.put(player.getUuid(), new CustomPlayer(nbt.getInt("heart"), nbt.getInt("regen"),
                    new BlockPos(nbt.getInt("baseX"), nbt.getInt("baseY"), nbt.getInt("baseZ"))));
        }
    }

    @Inject(at = @At("HEAD"), method = "writeCustomDataToNbt")
    private void writeCustomDataToNbt(@NotNull NbtCompound nbt, CallbackInfo ci) {
        CustomPlayer pl = ServerInitializer.players.get(player.getUuid());
        nbt.putInt("heart", pl.getHeart());
        nbt.putInt("regen", pl.getRegen());
        nbt.putInt("baseX", pl.getBase().getX());
        nbt.putInt("baseY", pl.getBase().getX());
        nbt.putInt("baseZ", pl.getBase().getX());
    }

    @Inject(at = @At("HEAD"), method = "sendChatMessage", cancellable = true)
    private void sendChatMessage(SentMessage message, boolean filterMaskEnabled, MessageType.@NotNull Parameters messageType, CallbackInfo ci) {
        System.out.println("sendchatmessage method : " + messageType.type().chat().translationKey() + messageType.targetName());
        String prefix = "§8[§9Player§8] §9";
        for (ServerPlayerEntity pl : Objects.requireNonNull(player.getServer()).getPlayerManager().getPlayerList()) {
            assert messageType.targetName() != null;
            if (pl.getEntityName().equals(messageType.targetName().getString())) {
                int permission = Objects.requireNonNull(pl.getServer()).getPermissionLevel(pl.getGameProfile());
                if (permission == 4) {
                    prefix = "§8[§4Admin§8] §4";
                } else if (permission == 1) {
                    prefix = "§8[§eVanadeur§8] §e";
                }
            }
        }
        String pre_msg = prefix + " §8: §f";
        Text msg = Text.of(pre_msg + message.getContent().getString());
        player.sendMessage(msg);
        ci.cancel();
    }

}