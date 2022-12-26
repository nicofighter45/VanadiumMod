package fr.vana_mod.nicofighter45.items.custom;

import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;

public class Hammer extends MiningToolItem {

    private final int mining_range;
    private boolean isActive = true;

    public Hammer(ToolMaterial material, int mining_range, Settings settings) {
        super(1, 2, material, BlockTags.PICKAXE_MINEABLE, settings);
        this.mining_range = mining_range;
    }

    public int getMiningRange() {
        return mining_range;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public boolean changeActivity() {
        this.isActive = !this.isActive;
        return this.isActive;
    }

}