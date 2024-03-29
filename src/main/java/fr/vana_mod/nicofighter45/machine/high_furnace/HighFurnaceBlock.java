package fr.vana_mod.nicofighter45.machine.high_furnace;

import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.machine.basic.block.AbstractMachineBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class HighFurnaceBlock extends AbstractMachineBlock {
    public final static int lavaLevelToTransform = 100;
    public final static int lavaLevelTotal = 4000;
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HighFurnaceBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModMachines.HIGH_FURNACE_BLOCK_ENTITY_TYPE, HighFurnaceBlockEntity::tick);
    }

}
