package fr.vana_mod.nicofighter45.mixins.player;

import fr.vana_mod.nicofighter45.items.ModItems;
import fr.vana_mod.nicofighter45.items.enchantment.BasicEffectEnchantment;
import fr.vana_mod.nicofighter45.main.client.ClientInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Unique
    private final PlayerEntity player = (PlayerEntity) (Object) this;
    @Unique
    private int timer = 100;

    public ItemStack getEquippedStack(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return player.getInventory().getMainHandStack();
        } else if (slot == EquipmentSlot.OFFHAND) {
            return player.getInventory().offHand.get(0);
        } else {
            return slot.getType() == EquipmentSlot.Type.ARMOR ? player.getInventory().armor.get(slot.getEntitySlotId()) : ItemStack.EMPTY;
        }
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo info) {
        if (timer > 0) {
            timer--;
        } else if (timer == 0) {
            timer--;
            ItemStack helmet = getEquippedStack(EquipmentSlot.HEAD);
            ItemStack chestplate = getEquippedStack(EquipmentSlot.CHEST);
            ItemStack leggings = getEquippedStack(EquipmentSlot.LEGS);
            ItemStack boots = getEquippedStack(EquipmentSlot.FEET);

            Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(helmet);
            enchantments.putAll(EnchantmentHelper.get(chestplate));
            enchantments.putAll(EnchantmentHelper.get(leggings));
            enchantments.putAll(EnchantmentHelper.get(boots));

            for (Enchantment enchantment : enchantments.keySet()) {
                if (enchantment instanceof BasicEffectEnchantment basicEffectEnchantment) {
                    player.addStatusEffect(new StatusEffectInstance(basicEffectEnchantment.getEffect(), 140,
                            enchantments.get(enchantment), false, false, true));
                }
            }

            //check helmet
            if (helmet.getItem() == ModItems.EMERALD_HELMET) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 140, 1, false, false, true));
            }

            //check chesplate
            if (chestplate.getItem() == ModItems.EMERALD_CHESTPLATE) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 140, 0, false, false, true));
            } else if (chestplate.getItem() == ModItems.TUNGSTEN_CHESTPLATE) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 140, 0, false, false, true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 140, 1, false, false, true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 140, 0, false, false, true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 140, 2, false, false, true));
            }

            //check leggings
            if (leggings.getItem() == ModItems.EMERALD_LEGGINGS) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 140, 0, false, false, true));
            }

            //check boots
            if (boots.getItem() == ModItems.EMERALD_BOOTS) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 140, 1, false, false, true));
            }

            timer = 100;
        }
    }

    @Inject(at = @At("HEAD"), method="getDisplayName", cancellable = true)
    public void getDisplayName(CallbackInfoReturnable<Text> cir) {
        String name = player.getName().getString();
        int permission;
        if(player.getWorld().isClient){
            permission = ClientInitializer.permissionLevel;
        }else{
            assert player.getServer() != null;
            permission = player.getServer().getPermissionLevel(player.getGameProfile());
        }
        if(permission == 0){
            cir.setReturnValue(Text.of("§8[§9Joueur§8] §f" + name));
        }else if(permission == 1){
            cir.setReturnValue(Text.of("§8[§6Vanadeur§8] §f" + name));
        }else if(permission == 2){
            cir.setReturnValue(Text.of("§8[§2Modo§8] §f" + name));
        }else{
            cir.setReturnValue(Text.of("§8[§cAdmin§8] §f" + name));
        }
        cir.cancel();
    }

}