package fr.nicofighter45.fvm.mixins;

import fr.nicofighter45.fvm.ModItems;
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

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Shadow
    @Final
    private PlayerInventory inventory;

    @Inject(at = @At("HEAD"), method = "tick")              //inject lign 1 of tick method of PlayerEntity class
    private void tick(CallbackInfo info){
        PlayerEntity player = inventory.player;
        ItemStack helmet = inventory.getArmorStack(3);
        ItemStack chestplate = inventory.getArmorStack(2);
        ItemStack leggings = inventory.getArmorStack(1);
        ItemStack boots = inventory.getArmorStack(0);
        if(helmet.getItem() == ModItems.EMERALD_HELMET){
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 60, 0, false, false, true));
        }
        if(chestplate.getItem() == ModItems.EMERALD_CHESTPLATE){
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
        }
        if(leggings.getItem() == ModItems.EMERALD_LEGGINGS){
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 60, 0, false, false, true));
        }
        if(boots.getItem() == ModItems.EMERALD_BOOTS){
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
        }
    }

}