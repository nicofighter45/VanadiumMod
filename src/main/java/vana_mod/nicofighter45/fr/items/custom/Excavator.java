package vana_mod.nicofighter45.fr.items.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Excavator extends ShovelItem {

    private final List<Block> list = new ArrayList<>();

    public Excavator(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
        list.add(Blocks.SAND);
        list.add(Blocks.DIRT);
        list.add(Blocks.GRAVEL);
        list.add(Blocks.COARSE_DIRT);
        list.add(Blocks.GRASS_BLOCK);
        list.add(Blocks.GRASS_PATH);
        list.add(Blocks.CLAY);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (miner instanceof PlayerEntity) {
            Block block = state.getBlock();
            if (list.contains(block)) {
                Direction direction = miner.getMovementDirection();
                checkBlock(pos.add(0,-1,0), (ServerPlayerEntity) miner, stack, block, world);
                checkBlock(pos.add(0,1,0), (ServerPlayerEntity) miner, stack, block, world);
                if(direction == Direction.NORTH || direction == Direction.SOUTH){
                    checkBlock(pos.add(1,0,0), (ServerPlayerEntity) miner, stack, block, world);
                    checkBlock(pos.add(-1,0,0), (ServerPlayerEntity) miner, stack, block, world);
                    checkBlock(pos.add(1,1,0), (ServerPlayerEntity) miner, stack, block, world);
                    checkBlock(pos.add(-1,1,0), (ServerPlayerEntity) miner, stack, block, world);
                    checkBlock(pos.add(1,-1,0), (ServerPlayerEntity) miner, stack, block, world);
                    checkBlock(pos.add(-1,-1,0), (ServerPlayerEntity) miner, stack, block, world);
                }else{
                    checkBlock(pos.add(0,0,1), (ServerPlayerEntity) miner, stack, block, world);
                    checkBlock(pos.add(0,0,-1), (ServerPlayerEntity) miner, stack, block, world);
                    checkBlock(pos.add(0,1,1), (ServerPlayerEntity) miner, stack, block, world);
                    checkBlock(pos.add(0,1,-1), (ServerPlayerEntity) miner, stack, block, world);
                    checkBlock(pos.add(0,-1,1), (ServerPlayerEntity) miner, stack, block, world);
                    checkBlock(pos.add(0,-1,-1), (ServerPlayerEntity) miner, stack, block, world);
                }
            }
        }
        return super.postMine(stack, world, state, pos, miner);
    }

    private void checkBlock(BlockPos newPos, ServerPlayerEntity miner, ItemStack stack, Block block, World world) {
        if(world.getBlockState(newPos).getBlock() == block){
            if(stack.getDamage() > 0){
                world.breakBlock(newPos, true, miner);
                stack.damage(1, new Random(), miner);
            }
        }
    }
}