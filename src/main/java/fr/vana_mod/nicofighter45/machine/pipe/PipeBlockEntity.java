package fr.vana_mod.nicofighter45.machine.pipe;

import fr.vana_mod.nicofighter45.machine.ModMachines;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
        if (!nbt.isEmpty()) {
            System.out.println("Reading Non null nbt");
            List<BlockPos> pipes = new ArrayList<>();
            int[] pipeTag = nbt.getIntArray("pipes");
            for (int i = 0; i < pipeTag.length; i += 3) {
                pipes.add(new BlockPos(pipeTag[i], pipeTag[i + 1], pipeTag[i + 2]));
            }
            network = new PipeNetwork(world, pipes);
            network.fluidAmount = nbt.getInt("fluidAmount");
        }
    }

    @Override
    protected void writeNbt(@NotNull NbtCompound nbt) {
        if (network == null) {
            System.out.println("network is empty");
            return;
        }
        if (network.pipes.isEmpty()) {
            System.out.println("network pipes is empty");
            return;
        }
        if (!network.isActive) {
            System.out.println("Network isn't active");
            return;
        }
        if (world == null) {
            System.out.println("world is null");
            return;
        }
        System.out.println("writing nbt" + world.getBlockState(pos).getBlock());
        if (network.pipes.size() == 1) {
            List<Integer> pipeTag = new ArrayList<>();
            for (BlockPos pos : network.pipes) {
                pipeTag.add(pos.getX());
                pipeTag.add(pos.getY());
                pipeTag.add(pos.getZ());
            }
            nbt.putIntArray("pipes", pipeTag);
            nbt.putInt("fluidAmount", network.fluidAmount);
        } else {
            network.removePipe(this.getPos());
        }
    }

    public void setTextureConfiguration(int value) {
        if (value == -1) {
            System.out.println("An error occurred during face checking for texturing");
            return;
        }
        World world = Objects.requireNonNull(getWorld());
        world.setBlockState(getPos(), world.getBlockState(getPos()).getBlock().getDefaultState().with(PipeBlock.configuration, value));
    }

}