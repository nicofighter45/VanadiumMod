package fr.nicofighter45.fvm.mixins;

import fr.nicofighter45.fvm.ModEnchants;
import fr.nicofighter45.fvm.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Random;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Shadow
    @Final
    private PlayerInventory inventory;

    @Inject(at = @At("HEAD"), method = "tick")              //inject lign 1 of tick method of PlayerEntity class
    private void tick(CallbackInfo info) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack helmet = inventory.getArmorStack(3);
        ItemStack chestplate = inventory.getArmorStack(2);
        ItemStack leggings = inventory.getArmorStack(1);
        ItemStack boots = inventory.getArmorStack(0);

        //check emerald
        if (helmet.getItem() == ModItems.EMERALD_HELMET) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 60, 0, false, false, true));
        }
        if (chestplate.getItem() == ModItems.EMERALD_CHESTPLATE) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
        }
        if (leggings.getItem() == ModItems.EMERALD_LEGGINGS) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 60, 0, false, false, true));
        }
        if (boots.getItem() == ModItems.EMERALD_BOOTS) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
        }

        //check vanadium
        if (helmet.getItem() == ModItems.VANADIUM_HELMET && leggings.getItem() == ModItems.VANADIUM_LEGGINGS &&
                boots.getItem() == ModItems.VANADIUM_BOOTS) {
            int r = new Random().nextInt(20);
            if (r == 1) {
                player.setHealth(player.getHealth() + 1);
            }
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