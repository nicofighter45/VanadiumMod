package fr.vana_mod.nicofighter45.machine.enchanter;

import fr.vana_mod.nicofighter45.machine.basic.block.AbstractMachineBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class EnchanterBlock extends AbstractMachineBlock {

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EnchanterBlockEntity(pos, state);
    }

}
