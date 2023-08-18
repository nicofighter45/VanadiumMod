package fr.vana_mod.nicofighter45.machine.modifierstable;

import fr.vana_mod.nicofighter45.machine.basic.block.AbstractMachineBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class ModifiersTableBlock extends AbstractMachineBlock {

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ModifiersTableBlockEntity(pos, state);
    }

}
