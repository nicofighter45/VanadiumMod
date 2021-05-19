package vana_mod.nicofighter45.fr.mixins;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vana_mod.nicofighter45.fr.bosses.BossComponent;
import vana_mod.nicofighter45.fr.bosses.CustomBossConfig;
import vana_mod.nicofighter45.fr.main.CustomPlayer;
import vana_mod.nicofighter45.fr.items.enchantment.ModEnchants;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vana_mod.nicofighter45.fr.main.VanadiumModServer;

import java.util.Objects;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    private final ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

    public ItemStack getEquippedStack(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return player.inventory.getMainHandStack();
        } else if (slot == EquipmentSlot.OFFHAND) {
            return (ItemStack)player.inventory.offHand.get(0);
        } else {
            return slot.getType() == EquipmentSlot.Type.ARMOR ? (ItemStack)player.inventory.armor.get(slot.getEntitySlotId()) : ItemStack.EMPTY;
        }
    }

    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(source == DamageSource.FALL && EnchantmentHelper.get(getEquippedStack(EquipmentSlot.FEET)).containsKey(ModEnchants.NO_FALL)){
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "readCustomDataFromTag")
    public void readCustomDataFromTag(CompoundTag tag, CallbackInfo ci) {
        if(VanadiumModServer.players.containsKey(player.getEntityName())){
            CustomPlayer pl = VanadiumModServer.players.get(player.getEntityName());
            pl.setHeart(tag.getInt("heart"));
            pl.setRegen(tag.getInt("regen"));
        }else{
            VanadiumModServer.players.put(player.getEntityName(), new CustomPlayer(player.getUuid(), tag.getInt("heart"), tag.getInt("regen")));
        }
    }

    @Inject(at = @At("HEAD"), method = "writeCustomDataToTag")
    public void writeCustomDataToTag(CompoundTag tag, CallbackInfo ci) {
        CustomPlayer pl = VanadiumModServer.players.get(player.getEntityName());
        tag.putInt("heart", pl.getHeart());
        tag.putInt("regen", pl.getRegen());
        tag.putUuid("uuid", player.getUuid());
    }

    @Inject(at = @At("HEAD"), method = "dropItem")
    public void dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> info) {
        if(player.getEntityWorld().getDimension() == Objects.requireNonNull(player.getServer()).getOverworld().getDimension()){
            for(CustomBossConfig boss : BossComponent.WORLD_COMPONENT_KEY.get(player.getServer().getOverworld()).bosses.values()){
                if(isNearTo(player.getX(), boss.getX(), player.getY(), boss.getY(), player.getZ(), boss.getZ()) && stack.getItem() == boss.getToSpawn()){
                    if(VanadiumModServer.bossesManagement.spawnBoss(boss)){
                        stack.setCount(0);
                        player.getServerWorld().spawnParticles(ParticleTypes.CLOUD, boss.getX(), boss.getY(), boss.getZ(), 10000, 0, 0, 0, 1);
                        player.getServerWorld().playSound(null, boss.getX(), boss.getY(), boss.getZ(),
                                SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.HOSTILE, 1f, 1f);
                        player.setVelocity(calc(player.getX()-boss.getX()), 0.5, calc(player.getZ()-boss.getZ()));
                        //actualize velocity changes
                        player.damage(DamageSource.MAGIC, 0.5f);
                        player.heal(0.5f);
                    }
                }
            }
        }
    }
    private double calc(double nb){
        return 5/Math.exp(0.3 * nb);
    }

    private boolean isNearTo(double x1, double x2, double y1, double y2, double z1, double z2){
        double dist = Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2) + Math.pow(z1-z2, 2));
        return dist <= 10;
    }
}