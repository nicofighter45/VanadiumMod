package fr.vana_mod.nicofighter45.mixins;

import com.google.common.collect.Lists;
import fr.vana_mod.nicofighter45.items.enchantment.BasicEffectEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @Inject(method = "getPossibleEntries", at = @At(value = "HEAD"), cancellable = true)
    private static void getPossibleEntries(int power, @NotNull ItemStack stack, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir) {
        ArrayList<EnchantmentLevelEntry> list = Lists.newArrayList();
        Item item = stack.getItem();
        boolean bl = stack.isOf(Items.BOOK);
        for (Enchantment enchantment : Registries.ENCHANTMENT) {
            if (enchantment.isTreasure() && !treasureAllowed || !enchantment.isAvailableForRandomSelection() || !enchantment.target.isAcceptableItem(item) && !bl) continue;
            if (enchantment instanceof BasicEffectEnchantment basicEffectEnchantment
                    && stack.getItem() instanceof ArmorItem) {
                if(!basicEffectEnchantment.isAcceptableItem(stack)){
                    System.out.println(enchantment.getTranslationKey());
                    continue;
                }
                if (basicEffectEnchantment.isMinimalAcceptableItem(stack)) {
                   for (int i = basicEffectEnchantment.getMediumLevel(); i > enchantment.getMinLevel() - 1; --i) {
                        if (power >= enchantment.getMinPower(i) && power <= enchantment.getMaxPower(i)) {
                            list.add(new EnchantmentLevelEntry(enchantment, i));
                            break;
                        }
                    }
                } else {
                    for (int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i) {
                        if (power >= enchantment.getMinPower(i) && power <= enchantment.getMaxPower(i)) {
                            list.add(new EnchantmentLevelEntry(enchantment, i));
                            break;
                        }
                    }
                }
            } else {
                for (int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i) {
                    if (power < enchantment.getMinPower(i) || power > enchantment.getMaxPower(i)) continue;
                    list.add(new EnchantmentLevelEntry(enchantment, i));
                    break;
                }
            }
        }
        cir.setReturnValue(list);
        cir.cancel();
    }

}
