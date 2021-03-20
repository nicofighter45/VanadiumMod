package vana_mod.nicofighter45.fr.mixins;

import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    private final PlayerEntity player = (ServerPlayerEntity) (Object) this;

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
}