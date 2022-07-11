package fr.vana_mod.nicofighter45.machine.purificator.craft;

import net.minecraft.item.Item;

public record PurificatorCraft(Item item) {

    public Item getItem() {
        return item;
    }

}