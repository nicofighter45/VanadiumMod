package fr.vana_mod.nicofighter45.mixins;

import net.minecraft.client.option.ChatVisibility;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.message.MessageSender;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import fr.vana_mod.nicofighter45.main.server.CustomPlayer;
import fr.vana_mod.nicofighter45.items.enchantment.ModEnchants;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import fr.vana_mod.nicofighter45.main.server.VanadiumModServer;

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
    public void onDeath(DamageSource damageSource, CallbackInfo ci) {
        EnderChestInventory inventory = player.getEnderChestInventory();
        if(inventory.removeItem(Items.EMERALD_BLOCK, 2).getCount() == 2){
            player.setHealth(player.getMaxHealth());
            BlockPos pos = player.getSpawnPointPosition();
            ServerWorld world = Objects.requireNonNull(player.getServer()).getOverworld();
            if(pos == null){
                pos = world.getSpawnPos();
            }
            player.teleport(world, pos.getX(), pos.getY(), pos.getZ(), 0, 0);
            world.spawnParticles(ParticleTypes.CLOUD, pos.getX(), pos.getY(), pos.getZ(), 10000, 0, 0, 0, 2);
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE,
                    SoundCategory.BLOCKS, 1f, 1f);
            for(ServerPlayerEntity players : player.getServer().getPlayerManager().getPlayerList()){
                players.sendMessage(Text.of("§8[§6Server§8] §fPlayer " + player.getEntityName() + " died and was respawn"));
            }
            ci.cancel();
        }
    }


    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(source == DamageSource.FALL && EnchantmentHelper.get(getEquippedStack(EquipmentSlot.FEET)).containsKey(ModEnchants.NO_FALL)){
            cir.setReturnValue(false);
        }else if(source == DamageSource.FALL && VanadiumModServer.jump && Math.sqrt(Math.pow(player.getX(), 2) +
                Math.pow(player.getY(), 2)) <= 100){
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "readCustomDataFromNbt")
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if(VanadiumModServer.players.containsKey(player.getUuid())){
            CustomPlayer pl = VanadiumModServer.players.get(player.getUuid());
            pl.setHeart(nbt.getInt("heart"));
            pl.setRegen(nbt.getInt("regen"));
            pl.setCraft(nbt.getBoolean("craft"));
            pl.setEnder_chest(nbt.getBoolean("ender_chest"));
            pl.setBase(new BlockPos( nbt.getInt("baseX"),  nbt.getInt("baseY"),  nbt.getInt("baseZ")));
        }else{
            VanadiumModServer.players.put(player.getUuid(), new CustomPlayer(nbt.getInt("heart"),
                    nbt.getInt("regen"), nbt.getBoolean("craft"), nbt.getBoolean("ender_chest"),
                    new BlockPos( nbt.getInt("baseX"),  nbt.getInt("baseY"),  nbt.getInt("baseZ"))));
        }
    }

    @Inject(at = @At("HEAD"), method = "writeCustomDataToNbt")
    public void writeCustomDataToNbt(@NotNull NbtCompound nbt, CallbackInfo ci) {
        CustomPlayer pl = VanadiumModServer.players.get(player.getUuid());
        nbt.putInt("heart", pl.getHeart());
        nbt.putInt("regen", pl.getRegen());
        nbt.putBoolean("craft", pl.isCraft());
        nbt.putBoolean("ender_chest", pl.isEnder_chest());
        nbt.putInt("baseX", pl.getBase().getX());
        nbt.putInt("baseY", pl.getBase().getX());
        nbt.putInt("baseZ", pl.getBase().getX());
    }

    @Inject(at = @At("HEAD"), method = "sendChatMessage", cancellable = true)
    public void sendChatMessage(SignedMessage message, MessageSender sender, RegistryKey<MessageType> typeKey, CallbackInfo ci) {
        if (acceptsMessage(typeKey)) {
            String pre_msg = "§8[§9Player§8] §9" + sender.name().getString() + " §8: §f";
            for(ServerPlayerEntity pl : Objects.requireNonNull(player.getServer()).getPlayerManager().getPlayerList()){
                if(pl.getUuid().equals(sender.uuid()) && Objects.requireNonNull(player.getServer()).getPlayerManager().isOperator(pl.getGameProfile())){
                    pre_msg = "§8[§4Admin§8] §4" + sender.name().getString() + " §8: §f";
                }
            }
            Text msg = Text.of(pre_msg + message.getContent().getString());
            player.sendMessage(msg);
            ci.cancel();
        }
    }

    private boolean acceptsMessage(RegistryKey<MessageType> typeKey) {
        ChatVisibility clientChatVisibility = player.getClientChatVisibility();
        if (clientChatVisibility == ChatVisibility.HIDDEN) {
            return typeKey == MessageType.GAME_INFO;
        } else if (clientChatVisibility == ChatVisibility.SYSTEM) {
            return typeKey == MessageType.SYSTEM || typeKey == MessageType.GAME_INFO;
        }
        return true;
    }

}