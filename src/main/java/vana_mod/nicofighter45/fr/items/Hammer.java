package vana_mod.nicofighter45.fr.items;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

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
                if(mining_range >= 1){
                    if(world.getBlockState(pos.add(0,-1,0)).getBlock() == state.getBlock()){
                        world.breakBlock(pos.add(0,-1,0), true);
                    }
                }
                if(mining_range >= 2){
                    if(world.getBlockState(pos.add(0,1,0)).getBlock() == state.getBlock()){
                        world.breakBlock(pos.add(0,1,0), true);
                    }
                }
                Direction direction = miner.getMovementDirection();
                if(mining_range >= 3){
                    if(direction == Direction.NORTH || direction == Direction.SOUTH){
                        if(world.getBlockState(pos.add(1,0,0)).getBlock() == state.getBlock()){
                            world.breakBlock(pos.add(1,0,0), true);
                        }
                        if(world.getBlockState(pos.add(-1,0,0)).getBlock() == state.getBlock()){
                            world.breakBlock(pos.add(-1,0,0), true);
                        }
                    }else{
                        if(world.getBlockState(pos.add(0,0,1)).getBlock() == state.getBlock()){
                            world.breakBlock(pos.add(0,0,1), true);
                        }
                        if(world.getBlockState(pos.add(0,0,-1)).getBlock() == state.getBlock()){
                            world.breakBlock(pos.add(0,0,-1), true);
                        }
                    }
                }
                if(mining_range >= 4){
                    if(direction == Direction.NORTH || direction == Direction.SOUTH){
                        if(world.getBlockState(pos.add(1,1,0)).getBlock() == state.getBlock()){
                            world.breakBlock(pos.add(1,1,0), true);
                        }
                        if(world.getBlockState(pos.add(-1,1,0)).getBlock() == state.getBlock()){
                            world.breakBlock(pos.add(-1,1,0), true);
                        }
                        if(world.getBlockState(pos.add(1,-1,0)).getBlock() == state.getBlock()){
                            world.breakBlock(pos.add(1,-1,0), true);
                        }
                        if(world.getBlockState(pos.add(-1,-1,0)).getBlock() == state.getBlock()){
                            world.breakBlock(pos.add(-1,-1,0), true);
                        }
                    }else{
                        if(world.getBlockState(pos.add(0,1,1)).getBlock() == state.getBlock()){
                            world.breakBlock(pos.add(0,1,1), true);
                        }
                        if(world.getBlockState(pos.add(0,1,-1)).getBlock() == state.getBlock()){
                            world.breakBlock(pos.add(0,1,-1), true);
                        }
                        if(world.getBlockState(pos.add(0,-1,1)).getBlock() == state.getBlock()){
                            world.breakBlock(pos.add(0,-1,1), true);
                        }
                        if(world.getBlockState(pos.add(0,-1,-1)).getBlock() == state.getBlock()){
                            world.breakBlock(pos.add(0,-1,-1), true);
                        }
                    }
                }
            }
        }
        return super.postMine(stack, world, state, pos, miner);
    }
}