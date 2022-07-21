package fr.vana_mod.nicofighter45.machine.pipe;

import fr.vana_mod.nicofighter45.machine.ModMachines;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PipeBlockEntity extends BlockEntity {

    public PipeNetwork network;

    public PipeBlockEntity(BlockPos pos, BlockState state) {
        super(ModMachines.PIPE_BLOCK_ENTITY_TYPE, pos, state);
    }

    @Override
    public void readNbt(@NotNull NbtCompound nbt) {
        if(!nbt.isEmpty()){
            List<BlockPos> pipes = new ArrayList<>();
            int[] pipeTag = nbt.getIntArray("pipes");
            for(int i = 0; i < pipeTag.length; i += 3){
                pipes.add(new BlockPos(pipeTag[i], pipeTag[i + 1], pipeTag[i + 2]));
            }
            network = new PipeNetwork(world, pipes);
            network.fluidAmount = nbt.getInt("fluidAmount");
        }
    }

    @Override
    protected void writeNbt(@NotNull NbtCompound nbt) {
        if(network == null){
            return;
        }
        if(network.pipes.get(0) == getPos()){
            List<Integer> pipeTag = new ArrayList<>();
            for(BlockPos pos : network.pipes.values()){
                pipeTag.add(pos.getX());
                pipeTag.add(pos.getY());
                pipeTag.add(pos.getZ());
            }
            nbt.putIntArray("pipes", pipeTag);
            nbt.putInt("fluidAmount", network.fluidAmount);
        }
    }

    public void setTextureConfiguration(int value){
        Objects.requireNonNull(getWorld()).getBlockState(getPos()).with(PipeBlock.configuration, value);
    }

}