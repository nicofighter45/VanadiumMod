package fr.vana_mod.nicofighter45.block.modifiertable;

import net.minecraft.item.Item;

public record ModifierCraft(Item item0, Item item1, Item item2,
                            Item item3) {

    public Item getItem0() {
        return item0;
    }

    public Item getItem1() {
        return item1;
    }

    public Item getItem2() {
        return item2;
    }

    public Item getItem3() {
        return item3;
    }
}