package fr.nicofighter45.fvm.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class VanadiumOre extends Block {
    public VanadiumOre() {
        super(FabricBlockSettings.of(Material.METAL).breakByHand(false).sounds(BlockSoundGroup.METAL)
                .breakByTool(FabricToolTags.PICKAXES).strength(20, 0.7f));
    }
}