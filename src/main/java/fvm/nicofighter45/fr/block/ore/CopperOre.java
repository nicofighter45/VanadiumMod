package fvm.nicofighter45.fr.block.ore;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class CopperOre extends Block {
    public CopperOre() {
        super(FabricBlockSettings.of(Material.METAL).breakByHand(false).sounds(BlockSoundGroup.METAL)
                .strength(5, 0.2f).breakByTool(FabricToolTags.PICKAXES, 1).requiresTool());
    }

}