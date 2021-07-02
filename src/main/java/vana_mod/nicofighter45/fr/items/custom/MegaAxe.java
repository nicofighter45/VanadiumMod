package vana_mod.nicofighter45.fr.items.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class MegaAxe extends AxeItem {

    public MegaAxe(ToolMaterial material, Settings settings) {
        super(material, 4,2, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if(miner instanceof PlayerEntity) {
            Block block = state.getBlock();
            if (block instanceof PillarBlock) {

                int previous = -1;
                ArrayList<BlockPos> checkBlocks = new ArrayList<>();
                ArrayList<BlockPos> woodBlocks = new ArrayList<>();
                ArrayList<BlockPos> addBlocks = new ArrayList<>();
                woodBlocks.add(pos);

                while(previous < checkBlocks.size()){
                    previous = checkBlocks.size();
                    System.out.println(woodBlocks.size());
                    for(BlockPos posWood : woodBlocks){
                        if(!checkBlocks.contains(posWood)){

                            //check in a 3 by 3 grid
                            System.out.println(block.getTranslationKey());
                            for(int x = -1; x < 2; x++){
                                for(int y = -1; y < 2; y++){
                                    for(int z = -1; z < 2; z++){
                                        BlockPos newPos = posWood.add(x,y,z);
                                        if(world.getBlockState(newPos).getBlock() == block){
                                            if(!woodBlocks.contains(newPos) && !addBlocks.contains(newPos)){
                                                addBlocks.add(newPos);
                                            }
                                        }
                                    }
                                }
                            }
                            checkBlocks.add(posWood);
                        }
                    }
                    woodBlocks.addAll(addBlocks);
                    addBlocks.clear();
                }
                final int max_block_break = 50;
                int blockbroken = 0;
                for(BlockPos posWood : woodBlocks){
                    if(stack.getDamage() > 0){
                        if(blockbroken <= max_block_break){
                            world.breakBlock(posWood, true, miner);
                            stack.damage(1, new Random(), (ServerPlayerEntity) miner);
                            blockbroken++;
                        }else{
                            break;
                        }
                    }
                }
            }
        }
        return super.postMine(stack, world, state, pos, miner);
    }

}