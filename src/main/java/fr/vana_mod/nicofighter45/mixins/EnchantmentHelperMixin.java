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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;
import java.util.List;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @Inject(at = @At("HEAD"), method = "getPossibleEntries", cancellable = true)
    private static void getPossibleEntries(int power, ItemStack stack, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir) {
        List<EnchantmentLevelEntry> list = Lists.newArrayList();
        Item item = stack.getItem();
        boolean bl = stack.isOf(Items.BOOK);
        Iterator var6 = Registries.ENCHANTMENT.iterator();

        while (true) {
            Enchantment enchantment;
            do {
                do {
                    do {
                        if (!var6.hasNext()) {
                            cir.setReturnValue(list);
                        }

                        enchantment = (Enchantment) var6.next();
                    } while (enchantment.isTreasure() && !treasureAllowed);
                } while (!enchantment.isAvailableForRandomSelection());
            } while (!enchantment.target.isAcceptableItem(item) && !bl);

            if (enchantment instanceof BasicEffectEnchantment basicEffectEnchantment && stack.getItem() instanceof ArmorItem) {
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
                    if (power >= enchantment.getMinPower(i) && power <= enchantment.getMaxPower(i)) {
                        list.add(new EnchantmentLevelEntry(enchantment, i));
                        break;
                    }
                }
            }
        }
    }

}
