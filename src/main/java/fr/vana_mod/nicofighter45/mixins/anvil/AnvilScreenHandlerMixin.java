package fr.vana_mod.nicofighter45.mixins.anvil;

import fr.vana_mod.nicofighter45.items.enchantment.BasicEffectEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.Map;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin {

    @Unique
    private final AnvilScreenHandler anvilScreenHandler = (AnvilScreenHandler) (Object) this;
    @Unique
    private final AnvilScreenHandlerAccessor accessor = (AnvilScreenHandlerAccessor) anvilScreenHandler;
    @Unique
    private final ForgingScreenHandlerAccessor forgingAccessor = (ForgingScreenHandlerAccessor) anvilScreenHandler;

    @Inject(at=@At("HEAD"), method="updateResult")
    public void updateResult(CallbackInfo ci) {
        ItemStack itemStack = anvilScreenHandler.getSlot(0).getStack();
        accessor.getLevelCost().set(1);
        int i = 0;
        int j = 0;
        int k = 0;
        if (itemStack.isEmpty()) {
            anvilScreenHandler.getSlot(2).setStack(ItemStack.EMPTY);
            accessor.getLevelCost().set(0);
        } else {
            ItemStack itemStack2 = itemStack.copy();
            ItemStack itemStack3 = anvilScreenHandler.getSlot(1).getStack();
            Map<Enchantment, Integer> map = EnchantmentHelper.get(itemStack2);
            j += itemStack.getRepairCost() + (itemStack3.isEmpty() ? 0 : itemStack3.getRepairCost());
            accessor.setRepairItemUsage(0);
            if (!itemStack3.isEmpty()) {
                boolean bl = itemStack3.isOf(Items.ENCHANTED_BOOK) && !EnchantedBookItem.getEnchantmentNbt(itemStack3).isEmpty();
                int l;
                int m;
                int n;
                if (itemStack2.isDamageable() && itemStack2.getItem().canRepair(itemStack, itemStack3)) {
                    l = Math.min(itemStack2.getDamage(), itemStack2.getMaxDamage() / 4);
                    if (l <= 0) {
                        anvilScreenHandler.getSlot(2).setStack(ItemStack.EMPTY);
                        accessor.getLevelCost().set(0);
                        return;
                    }

                    for(m = 0; l > 0 && m < itemStack3.getCount(); ++m) {
                        n = itemStack2.getDamage() - l;
                        itemStack2.setDamage(n);
                        ++i;
                        l = Math.min(itemStack2.getDamage(), itemStack2.getMaxDamage() / 4);
                    }
                    accessor.setRepairItemUsage(m);
                } else {
                    if (!bl && (!itemStack2.isOf(itemStack3.getItem()) || !itemStack2.isDamageable())) {
                        anvilScreenHandler.getSlot(2).setStack(ItemStack.EMPTY);
                        accessor.getLevelCost().set(0);
                        return;
                    }

                    if (itemStack2.isDamageable() && !bl) {
                        l = itemStack.getMaxDamage() - itemStack.getDamage();
                        m = itemStack3.getMaxDamage() - itemStack3.getDamage();
                        n = m + itemStack2.getMaxDamage() * 12 / 100;
                        int o = l + n;
                        int p = itemStack2.getMaxDamage() - o;
                        if (p < 0) {
                            p = 0;
                        }

                        if (p < itemStack2.getDamage()) {
                            itemStack2.setDamage(p);
                            i += 2;
                        }
                    }

                    Map<Enchantment, Integer> map2 = EnchantmentHelper.get(itemStack3);
                    boolean bl2 = false;
                    boolean bl3 = false;
                    Iterator<Enchantment> var23 = map2.keySet().iterator();

                    label159:
                    while(true) {
                        Enchantment enchantment;
                        do {
                            if (!var23.hasNext()) {
                                if (bl3 && !bl2) {
                                    anvilScreenHandler.getSlot(2).setStack(ItemStack.EMPTY);
                                    accessor.getLevelCost().set(0);
                                    return;
                                }
                                break label159;
                            }

                            enchantment = var23.next();
                        } while(enchantment == null);

                        int q = map.getOrDefault(enchantment, 0);
                        int r = map2.get(enchantment);
                        r = q == r ? r + 1 : Math.max(r, q);

                        boolean bl4;
                        //todo not working
                        if(enchantment instanceof BasicEffectEnchantment basicEffectEnchantment){
                            if(basicEffectEnchantment.isMinimalAcceptableItem(itemStack)){
                                bl4 = r <= basicEffectEnchantment.getMediumLevel();
                            }else if(basicEffectEnchantment.isAcceptableItem(itemStack)){
                                bl4 = r <= basicEffectEnchantment.getMaxLevel();
                            }else{
                                bl4 = false;
                            }
                        }else{
                            bl4 = enchantment.isAcceptableItem(itemStack);
                        }

                        if (forgingAccessor.getPlayer().getAbilities().creativeMode || itemStack.isOf(Items.ENCHANTED_BOOK)) {
                            bl4 = true;
                        }

                        for (Enchantment enchantment2 : map.keySet()) {
                            if (enchantment2 != enchantment && !enchantment.canCombine(enchantment2)) {
                                bl4 = false;
                                ++i;
                            }
                        }

                        if (!bl4) {
                            bl3 = true;
                        } else {
                            bl2 = true;
                            if (r > enchantment.getMaxLevel()) {
                                r = enchantment.getMaxLevel();
                            }

                            map.put(enchantment, r);
                            int s = switch (enchantment.getRarity()) {
                                case COMMON -> 1;
                                case UNCOMMON -> 2;
                                case RARE -> 4;
                                case VERY_RARE -> 8;
                            };

                            if (bl) {
                                s = Math.max(1, s / 2);
                            }

                            i += s * r;
                            if (itemStack.getCount() > 1) {
                                i = 40;
                            }
                        }
                    }
                }
            }

            if (accessor.getNewItemName() != null && !Util.isBlank(accessor.getNewItemName())) {
                if (!accessor.getNewItemName().equals(itemStack.getName().getString())) {
                    k = 1;
                    i += k;
                    itemStack2.setCustomName(Text.literal(accessor.getNewItemName()));
                }
            } else if (itemStack.hasCustomName()) {
                k = 1;
                i += k;
                itemStack2.removeCustomName();
            }

            accessor.getLevelCost().set(j + i);
            if (i <= 0) {
                itemStack2 = ItemStack.EMPTY;
            }

            if (k == i && k > 0 && accessor.getLevelCost().get() >= 40) {
                accessor.getLevelCost().set(39);
            }

            if (accessor.getLevelCost().get() >= 40 && !forgingAccessor.getPlayer().getAbilities().creativeMode) {
                itemStack2 = ItemStack.EMPTY;
            }

            if (!itemStack2.isEmpty()) {
                int t = itemStack2.getRepairCost();
                if (!itemStack3.isEmpty() && t < itemStack3.getRepairCost()) {
                    t = itemStack3.getRepairCost();
                }

                if (k != i || k == 0) {
                    t = t*2+1;
                }

                itemStack2.setRepairCost(t);
                EnchantmentHelper.set(map, itemStack2);
            }

            anvilScreenHandler.getSlot(2).setStack(itemStack2);
            anvilScreenHandler.sendContentUpdates();
        }
    }

}