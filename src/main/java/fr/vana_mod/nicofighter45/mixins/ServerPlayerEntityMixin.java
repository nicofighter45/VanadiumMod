package fr.vana_mod.nicofighter45.mixins;

import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import fr.vana_mod.nicofighter45.bosses.CustomBossConfig;
import fr.vana_mod.nicofighter45.main.CustomPlayer;
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
import fr.vana_mod.nicofighter45.main.VanadiumModServer;

import java.util.Objects;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @Shadow public abstract void sendMessage(Text message);

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

    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(source == DamageSource.FALL && EnchantmentHelper.get(getEquippedStack(EquipmentSlot.FEET)).containsKey(ModEnchants.NO_FALL)){
            cir.setReturnValue(false);
        }else if(player.getHealth() <= amount){
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
                sendMessage(Text.of("You were respawn against 2 emerald block in your ender chest"));
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "readCustomDataFromNbt")
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if(VanadiumModServer.players.containsKey(player.getEntityName())){
            CustomPlayer pl = VanadiumModServer.players.get(player.getEntityName());
            pl.setHeart(nbt.getInt("heart"));
            pl.setRegen(nbt.getInt("regen"));
            pl.setCraft(nbt.getBoolean("craft"));
            pl.setEnder_chest(nbt.getBoolean("ender_chest"));
        }else{
            VanadiumModServer.players.put(player.getEntityName(), new CustomPlayer(player.getUuid(), nbt.getInt("heart"),
                    nbt.getInt("regen"), nbt.getBoolean("craft"), nbt.getBoolean("ender_chest")));
        }
    }

    @Inject(at = @At("HEAD"), method = "writeCustomDataToNbt")
    public void writeCustomDataToNbt(@NotNull NbtCompound nbt, CallbackInfo ci) {
        CustomPlayer pl = VanadiumModServer.players.get(player.getEntityName());
        nbt.putInt("heart", pl.getHeart());
        nbt.putInt("regen", pl.getRegen());
        nbt.putUuid("uuid", player.getUuid());
        nbt.putBoolean("craft", pl.isCraft());
        nbt.putBoolean("ender_chest", pl.isEnder_chest());
    }

    @Inject(at = @At("HEAD"), method = "dropItem")
    public void dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> info) {
        if(player.getEntityWorld().getDimension() == Objects.requireNonNull(player.getServer()).getOverworld().getDimension()){
            for(CustomBossConfig boss : VanadiumModServer.bosses.values()){
                if(isNearTo(player.getX(), boss.getX(), player.getY(), boss.getY(), player.getZ(), boss.getZ()) && stack.getItem() == boss.getToSpawn()){
                    ServerWorld world = player.getWorld();
                    if(VanadiumModServer.bossesManagement.spawnBoss(boss, world)){
                        stack.setCount(0);
                        for(ServerPlayerEntity pl : world.getPlayers()){
                            if(isNearTo(pl.getX(), boss.getX(), pl.getY(), boss.getY(), pl.getZ(), boss.getZ())){
                                pl.setVelocity(calc(pl.getX()-boss.getX()), 0.5, calc(pl.getZ()-boss.getZ()));
                                //actualize velocity changes
                                pl.damage(DamageSource.MAGIC, 0.5f);
                                pl.heal(0.5f);
                            }
                        }
                        world.spawnParticles(ParticleTypes.CLOUD, boss.getX(), boss.getY(), boss.getZ(), 10000, 0, 0, 0, 1);
                        world.playSound(null, boss.getX(), boss.getY(), boss.getZ(),
                                SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.HOSTILE, 1f, 1f);
                    }
                }
            }
        }
    }

    private double calc(double nb){
        if(nb < 0){
            return -5/Math.exp(0.3 * -nb);
        }else if(nb > 0){
            return 5/Math.exp(0.3 * nb);
        }else{
            return 5;
        }
    }

    private boolean isNearTo(double x1, double x2, double y1, double y2, double z1, double z2){
        return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2) + Math.pow(z1-z2, 2)) <= 10; }
}