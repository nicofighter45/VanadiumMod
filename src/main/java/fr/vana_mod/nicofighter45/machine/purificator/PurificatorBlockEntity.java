package fr.vana_mod.nicofighter45.machine.purificator;

import fr.vana_mod.nicofighter45.machine.basic.AbstractMachineBlockEntity;
import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.machine.purificator.recipe.PurificatorRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PurificatorBlockEntity extends AbstractMachineBlockEntity {

    public PurificatorBlockEntity(BlockPos pos, BlockState state) {
        super(ModMachines.PURIFICATOR_BLOCK_ENTITY_TYPE, pos, state, 3, 3, 0);
    }

    public static void tick(World world, BlockPos pos, BlockState state, @NotNull PurificatorBlockEntity blockEntity) {
        if(blockEntity.getPropertyDelegate().get(0) > 0 && blockEntity.getPropertyDelegate().get(2) > 0){
            blockEntity.getPropertyDelegate().add(0, -1);
            blockEntity.getPropertyDelegate().add(2, -1);
            if(blockEntity.getPropertyDelegate().get(2) == 0){
                ItemStack input = blockEntity.getInventory().getStack(1);
                if(input.getCount() > 1){
                    input.decrement(1);
                }else{
                    blockEntity.getInventory().setStack(1, ItemStack.EMPTY);
                }
                ItemStack result = blockEntity.getInventory().getStack(2);
                Optional<PurificatorRecipe> optional = world.getRecipeManager().getFirstMatch(ModMachines.PURIFICATOR_RECIPE_TYPE, new SimpleInventory(input), world);
                if (optional.isPresent()) {
                    PurificatorRecipe recipe = optional.get();
                    if(!result.isEmpty()){
                        result.increment(1);
                    }else{
                        blockEntity.getInventory().setStack(2, recipe.getOutput().copy());
                    }
                    markDirty(world, pos, state);
                }
            }
        }
        if(blockEntity.getPropertyDelegate().get(1) > 0 && blockEntity.getPropertyDelegate().get(0) < 400){
            blockEntity.getPropertyDelegate().add(0, 1);
            blockEntity.getPropertyDelegate().add(1, -1);
        }
    }

    @Override
    public void slotUpdate(int slot) {
        System.out.println("Slot update on machineInventory " + slot);
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, @NotNull PlayerEntity player) {
        return new PurificatorScreenHandler(syncId, playerInventory, ScreenHandlerContext.create(player.getEntityWorld(), pos), getInventory(), getPropertyDelegate());
    }

}