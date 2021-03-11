package vana_mod.nicofighter45.fr.items.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

public class Hammer extends PickaxeItem {

    private final int mining_range;

    public Hammer(ToolMaterial material, int mining_range, Settings settings) {
        super(material, 1, 2, settings);
        this.mining_range = mining_range;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if(miner instanceof PlayerEntity){
            if(((PickaxeItem) stack.getItem()).isEffectiveOn(state)){
                Block block = state.getBlock();
                if(mining_range >= 1){
                    checkBlock(pos.add(0,-1,0), (ServerPlayerEntity) miner, stack, block, world);
                }
                if(mining_range >= 2){
                    checkBlock(pos.add(0,1,0), (ServerPlayerEntity) miner, stack, block, world);
                }
                Direction direction = miner.getMovementDirection();
                if(mining_range >= 3){
                    if(direction == Direction.NORTH || direction == Direction.SOUTH){
                        checkBlock(pos.add(1,0,0), (ServerPlayerEntity) miner, stack, block, world);
                        checkBlock(pos.add(-1,0,0), (ServerPlayerEntity) miner, stack, block, world);
                    }else{
                        checkBlock(pos.add(0,0,1), (ServerPlayerEntity) miner, stack, block, world);
                        checkBlock(pos.add(0,0,-1), (ServerPlayerEntity) miner, stack, block, world);
                    }
                }
                if(mining_range >= 4){
                    if(direction == Direction.NORTH || direction == Direction.SOUTH){
                        checkBlock(pos.add(1,1,0), (ServerPlayerEntity) miner, stack, block, world);
                        checkBlock(pos.add(-1,1,0), (ServerPlayerEntity) miner, stack, block, world);
                        checkBlock(pos.add(1,-1,0), (ServerPlayerEntity) miner, stack, block, world);
                        checkBlock(pos.add(-1,-1,0), (ServerPlayerEntity) miner, stack, block, world);
                    }else{
                        checkBlock(pos.add(0,1,1), (ServerPlayerEntity) miner, stack, block, world);
                        checkBlock(pos.add(0,1,-1), (ServerPlayerEntity) miner, stack, block, world);
                        checkBlock(pos.add(0,-1,1), (ServerPlayerEntity) miner, stack, block, world);
                        checkBlock(pos.add(0,-1,-1), (ServerPlayerEntity) miner, stack, block, world);
                    }
                }
            }
        }
        return super.postMine(stack, world, state, pos, miner);
    }

    private void checkBlock(BlockPos newPos, ServerPlayerEntity miner, ItemStack stack, Block block, World world) {
        if(world.getBlockState(newPos).getBlock() == block){
            if(stack.getDamage() > 0){
                world.breakBlock(newPos, true, miner);
                stack.damage(1, new Random(), (ServerPlayerEntity) miner);
            }
        }
    }
}