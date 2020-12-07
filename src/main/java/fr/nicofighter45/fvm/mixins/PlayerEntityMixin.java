package fr.nicofighter45.fvm.mixins;

import fr.nicofighter45.fvm.ModEnchants;
import fr.nicofighter45.fvm.ModItems;
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

    @Inject(at = @At("HEAD"), method = "tick")              //inject lign 1 of tick method of PlayerEntity class
    private void tick(CallbackInfo info) {
        ItemStack helmet = getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chestplate = getEquippedStack(EquipmentSlot.CHEST);
        ItemStack leggings = getEquippedStack(EquipmentSlot.LEGS);
        ItemStack boots = getEquippedStack(EquipmentSlot.FEET);

        //check emerald
        if (helmet.getItem() == ModItems.EMERALD_HELMET) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 60, 1, false, false, true));
        }
        if (chestplate.getItem() == ModItems.EMERALD_CHESTPLATE) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
        }
        if (leggings.getItem() == ModItems.EMERALD_LEGGINGS) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 60, 0, false, false, true));
        }
        if (boots.getItem() == ModItems.EMERALD_BOOTS) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 1, false, false, true));
        }

        //check tungsten
        if (chestplate.getItem() == ModItems.TUNGSTEN_CHESTPLATE) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 60, 0, false, false, true));
        }

        //check vanadium
        if (helmet.getItem() == ModItems.VANADIUM_HELMET && leggings.getItem() == ModItems.VANADIUM_LEGGINGS &&
                boots.getItem() == ModItems.VANADIUM_BOOTS) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60, 0, false, false, true));
        }

        //check enchant
        Map<Enchantment, Integer> enchantHelmet = EnchantmentHelper.get(helmet);
        Map<Enchantment, Integer> enchantLeggings = EnchantmentHelper.get(leggings);
        Map<Enchantment, Integer> enchantBoots = EnchantmentHelper.get(boots);

        if(enchantHelmet.containsKey(ModEnchants.HASTER)){
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 60, enchantHelmet.get(ModEnchants.HASTER) - 1, false, false, true));
        }
        if(enchantHelmet.containsKey(ModEnchants.STRENGHTER)){
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 60, enchantHelmet.get(ModEnchants.STRENGHTER) - 1, false, false, true));
        }
        if(enchantLeggings.containsKey(ModEnchants.FASTER)){
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, enchantLeggings.get(ModEnchants.FASTER) - 1, false, false, true));
        }
        if(enchantLeggings.containsKey(ModEnchants.RESISTANCER)){
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, enchantLeggings.get(ModEnchants.RESISTANCER) - 1, false, false, true));
        }
        if(enchantBoots.containsKey(ModEnchants.JUMPER)){
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 60, enchantBoots.get(ModEnchants.JUMPER) - 1, false, false, true));
        }
    }
}