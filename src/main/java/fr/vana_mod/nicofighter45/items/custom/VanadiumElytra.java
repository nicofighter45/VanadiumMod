package fr.vana_mod.nicofighter45.items.custom;

import fr.vana_mod.nicofighter45.items.ModItems;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class VanadiumElytra extends ElytraItem {

    public VanadiumElytra(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canRepair(@NotNull ItemStack stack, ItemStack ingredient) {
        return stack.getItem() == ModItems.VANADIUM_NUGGET;
    }

}