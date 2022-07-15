package fr.vana_mod.nicofighter45.machine.purificator;

import fr.vana_mod.nicofighter45.machine.basic.AbstractMachineBlockEntity;
import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.machine.purificator.recipe.PurificatorRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PurificatorBlockEntity extends AbstractMachineBlockEntity {

    private PurificatorRecipe currentRecipe;

    public PurificatorBlockEntity(BlockPos pos, BlockState state) {
        super(ModMachines.PURIFICATOR_BLOCK_ENTITY_TYPE, pos, state, 3, 3, 0);
    }

    public static void tick(World world, BlockPos pos, BlockState state, @NotNull PurificatorBlockEntity blockEntity) {
        if(blockEntity.getPropertyDelegate().get(0) > 0 && blockEntity.getPropertyDelegate().get(2) > 0){
            blockEntity.getPropertyDelegate().add(0, -1);
            blockEntity.getPropertyDelegate().add(2, -1);
            if(blockEntity.getPropertyDelegate().get(2) == 0){
                blockEntity.changingInventory = true;
                ItemStack input = blockEntity.getInventory().getStack(1);
                if(input.getCount() > 1){
                    input.decrement(1);
                    blockEntity.getPropertyDelegate().set(2, 100);
                }else{
                    blockEntity.getInventory().setStack(1, ItemStack.EMPTY);
                }
                ItemStack result = blockEntity.getInventory().getStack(2);
                if(!result.isEmpty()){
                    result.increment(1);
                }else{
                    blockEntity.getInventory().setStack(2, blockEntity.currentRecipe.getOutput());
                }
                markDirty(world, pos, state);
                blockEntity.changingInventory = false;
            }
        }
        if(blockEntity.getPropertyDelegate().get(1) > 0 && blockEntity.getPropertyDelegate().get(0) < 400){
            blockEntity.getPropertyDelegate().add(0, 1);
            blockEntity.getPropertyDelegate().add(1, -1);
            if(updateWater(blockEntity)){
                markDirty(world, pos, state);
            }
        }
    }

    private static boolean updateWater(@NotNull PurificatorBlockEntity blockEntity){
        if(blockEntity.getPropertyDelegate().get(1) == 0 && blockEntity.getPropertyDelegate().get(0) < 301
                && blockEntity.getInventory().getStack(0).getItem().equals(Items.WATER_BUCKET)){
            blockEntity.changingInventory = true;
            blockEntity.getInventory().setStack(0, new ItemStack(Items.BUCKET));
            blockEntity.getPropertyDelegate().set(1, 100);
            blockEntity.changingInventory = false;
            return true;
        }
        return false;
    }

    @Override
    public void slotUpdate(int slot) {
        if(changingInventory){
            return;
        }
        if(slot == 0) {
            updateWater(this);
        }else if(slot == 1){
            assert world != null;
            Optional<PurificatorRecipe> optional = world.getRecipeManager().getFirstMatch(ModMachines.PURIFICATOR_RECIPE_TYPE, new SimpleInventory(getInventory().getStack(1)), world);
            if (optional.isPresent()) {
                if(currentRecipe == null){
                    currentRecipe = optional.get();
                    getPropertyDelegate().set(2, 100);
                }else{
                    if(getInventory().getStack(1).getItem() != currentRecipe.getInputItem()){
                        currentRecipe = optional.get();
                        getPropertyDelegate().set(2, 100);
                    }
                }
            }else {
                getPropertyDelegate().set(2, 0);
            }
        }
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, @NotNull PlayerEntity player) {
        return new PurificatorScreenHandler(syncId, playerInventory, ScreenHandlerContext.create(player.getEntityWorld(), pos), getInventory(), getPropertyDelegate());
    }

}