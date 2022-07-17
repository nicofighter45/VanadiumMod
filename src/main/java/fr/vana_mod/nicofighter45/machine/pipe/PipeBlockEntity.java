package fr.vana_mod.nicofighter45.machine.pipe;

import fr.vana_mod.nicofighter45.machine.ModMachines;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public class PipeBlockEntity extends BlockEntity {

    private int fluidAmount;
    public BlockPos inputBlock;
    public BlockPos outputBlock;

    public PipeBlockEntity(BlockPos pos, BlockState state) {
        super(ModMachines.PIPE_BLOCK_ENTITY_TYPE, pos, state);
    }

    @Override
    public void readNbt(@NotNull NbtCompound nbt) {
        fluidAmount = nbt.getInt("fluidAmount");
    }

    @Override
    protected void writeNbt(@NotNull NbtCompound nbt) {
        nbt.putInt("fluidAmount", fluidAmount);

    }
}