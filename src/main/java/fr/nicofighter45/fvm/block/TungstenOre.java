package fr.nicofighter45.fvm.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class TungstenOre extends Block {
    public TungstenOre() {
        super(FabricBlockSettings.of(Material.METAL).breakByHand(false).sounds(BlockSoundGroup.METAL)
                .strength(10, 0.4f).breakByTool(FabricToolTags.PICKAXES, 2).requiresTool());
    }

}