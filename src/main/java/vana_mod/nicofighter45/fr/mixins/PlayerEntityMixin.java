package vana_mod.nicofighter45.fr.mixins;

import vana_mod.nicofighter45.fr.items.ModItems;
import vana_mod.nicofighter45.fr.items.enchantment.ModEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin extends LivingEntity {

    private final PlayerEntity player = (PlayerEntity) (Object) this;
    private int timer = 100;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    public ItemStack getEquippedStack(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return player.inventory.getMainHandStack();
        } else if (slot == EquipmentSlot.OFFHAND) {
            return (ItemStack)player.inventory.offHand.get(0);
        } else {
            return slot.getType() == EquipmentSlot.Type.ARMOR ? (ItemStack)player.inventory.armor.get(slot.getEntitySlotId()) : ItemStack.EMPTY;
        }
    }

    @Inject(at = @At("HEAD"), method = "equipStack")
    public void equipStack(EquipmentSlot slot, ItemStack stack, CallbackInfo info) {
        if (getEquippedStack(EquipmentSlot.CHEST).getItem() == ModItems.TUNGSTEN_CHESTPLATE) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 140, 0, false, false, true));
        }
    }

    @Inject(at = @At("HEAD"), method = "tick")              //inject lign 1 of tick method of PlayerEntity class
    private void tick(CallbackInfo info) {
        if(timer > 0){
            timer --;
        }else if(timer == 0){
            timer --;
            ItemStack helmet = getEquippedStack(EquipmentSlot.HEAD);
            ItemStack chestplate = getEquippedStack(EquipmentSlot.CHEST);
            ItemStack leggings = getEquippedStack(EquipmentSlot.LEGS);
            ItemStack boots = getEquippedStack(EquipmentSlot.FEET);

            Map<Enchantment, Integer> enchantHelmet = EnchantmentHelper.get(helmet);
            Map<Enchantment, Integer> enchantLeggings = EnchantmentHelper.get(leggings);
            Map<Enchantment, Integer> enchantBoots = EnchantmentHelper.get(boots);

            //check helmet
            if (helmet.getItem() == ModItems.EMERALD_HELMET) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 140, 1, false, false, true));
            }else {
                if(enchantHelmet.containsKey(ModEnchants.HASTER)){
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 140, enchantHelmet.get(ModEnchants.HASTER) - 1, false, false, true));
                }
                if(enchantHelmet.containsKey(ModEnchants.STRENGHTER)){
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 140, enchantHelmet.get(ModEnchants.STRENGHTER) - 1, false, false, true));
                }
            }

            //check chesplate
            if (chestplate.getItem() == ModItems.EMERALD_CHESTPLATE) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 140, 0, false, false, true));
            }else if (chestplate.getItem() == ModItems.TUNGSTEN_CHESTPLATE) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 140, 0, false, false, true));
            }

            //check leggings
            if (leggings.getItem() == ModItems.EMERALD_LEGGINGS) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 140, 0, false, false, true));
            }else{
                if(enchantLeggings.containsKey(ModEnchants.FASTER)){
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 140, enchantLeggings.get(ModEnchants.FASTER) - 1, false, false, true));
                }
                if(enchantLeggings.containsKey(ModEnchants.RESISTANCER)){
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 140, enchantLeggings.get(ModEnchants.RESISTANCER) - 1, false, false, true));
                }
            }

            //check boots
            if (boots.getItem() == ModItems.EMERALD_BOOTS) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 140, 1, false, false, true));
            }else{
                if(enchantBoots.containsKey(ModEnchants.JUMPER)){
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 140, enchantBoots.get(ModEnchants.JUMPER) - 1, false, false, true));
                }
            }

            //check full vanadium
            if (helmet.getItem() == ModItems.VANADIUM_HELMET && leggings.getItem() == ModItems.VANADIUM_LEGGINGS &&
                    boots.getItem() == ModItems.VANADIUM_BOOTS) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 140, 1, false, false, true));
            }
            timer = 100;
        }
    }
}