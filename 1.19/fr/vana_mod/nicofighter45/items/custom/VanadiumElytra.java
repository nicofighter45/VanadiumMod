package fr.vana_mod.nicofighter45.items.custom;

import fr.vana_mod.nicofighter45.items.ModItems;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Wearable;
import org.jetbrains.annotations.NotNull;

public class VanadiumElytra extends ElytraItem implements Wearable {

    public VanadiumElytra(Settings settings) {
        super(settings);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
    }

    @Override
    public boolean canRepair(@NotNull ItemStack stack, ItemStack ingredient) {
        return stack.getItem() == ModItems.VANADIUM_NUGGET;
    }

}