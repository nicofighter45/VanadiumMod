package vana_mod.nicofighter45.fr.items.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;

import java.util.ArrayList;
import java.util.List;

public class Excavator extends ShovelItem {

    private final List<Block> list = new ArrayList<>();

    public Excavator(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
        list.add(Blocks.SAND);
        list.add(Blocks.DIRT);
        list.add(Blocks.GRAVEL);
        list.add(Blocks.COARSE_DIRT);
        list.add(Blocks.GRASS_BLOCK);
        list.add(Blocks.CLAY);
    }

    @Override
    public boolean isSuitableFor(BlockState state) { return list.contains(state.getBlock()); }

}