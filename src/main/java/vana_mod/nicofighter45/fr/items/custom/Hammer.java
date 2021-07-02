package vana_mod.nicofighter45.fr.items.custom;

import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

public class Hammer extends PickaxeItem {

    private final int mining_range;

    public Hammer(ToolMaterial material, int mining_range, Settings settings) {
        super(material, 1, 2, settings);
        this.mining_range = mining_range;
    }

    public int getMiningRange(){
        return mining_range;
    }
}