package fr.vana_mod.nicofighter45.machine.basic.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;

public class LavaInputSlot extends Slot {

    public LavaInputSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(@NotNull ItemStack stack) {
        return stack.getItem().equals(Items.LAVA_BUCKET) || stack.getItem().equals(Items.BUCKET);
    }

}