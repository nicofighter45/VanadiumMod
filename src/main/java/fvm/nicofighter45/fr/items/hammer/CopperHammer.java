package fvm.nicofighter45.fr.items.hammer;

import fvm.nicofighter45.fr.items.tool_material.CopperToolMaterial;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class CopperHammer extends PickaxeItem {

    public CopperHammer(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(CopperToolMaterial.INSTANCE, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        Direction direction = miner.getMovementDirection();
        if(world.getBlockState(pos.add(0,-1,0)).getBlock() == state.getBlock()){
            world.breakBlock(pos.add(0,-1,0), true);
        }
        return super.postMine(stack, world, state, pos, miner);
    }
}