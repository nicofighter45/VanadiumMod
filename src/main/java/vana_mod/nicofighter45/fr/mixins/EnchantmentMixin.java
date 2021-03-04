package vana_mod.nicofighter45.fr.mixins;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vana_mod.nicofighter45.fr.items.Hammer;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    private final Enchantment enchantment = (Enchantment) (Object) this;

    @Inject(at = @At("HEAD"), method = "isAcceptableItem", cancellable = true)
    public void isAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        if(enchantment == Enchantments.SILK_TOUCH && stack.getItem() instanceof Hammer){
            cir.setReturnValue(false);
        }
        if(enchantment == Enchantments.FORTUNE  && stack.getItem() instanceof Hammer){
            cir.setReturnValue(false);
        }
    }
}