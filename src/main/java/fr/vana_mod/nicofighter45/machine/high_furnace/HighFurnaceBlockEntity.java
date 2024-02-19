package fr.vana_mod.nicofighter45.machine.high_furnace;

import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.machine.basic.block.AbstractMachineBlockEntity;
import fr.vana_mod.nicofighter45.machine.high_furnace.recipe.HighFurnaceRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class HighFurnaceBlockEntity extends AbstractMachineBlockEntity {

    private static HighFurnaceRecipe currentRecipe;
    private static boolean notEnoughLava = false;

    public HighFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModMachines.HIGH_FURNACE_BLOCK_ENTITY_TYPE, pos, state, 4, 3, 0);
        getPropertyDelegate().set(2, HighFurnaceBlock.lavaLevelToTransform);
    }

    public static void tick(World world, BlockPos pos, BlockState state, @NotNull HighFurnaceBlockEntity blockEntity) {
        boolean updateLava = false;
        if (blockEntity.getPropertyDelegate().get(1) >= 10 && blockEntity.getPropertyDelegate().get(0) <= HighFurnaceBlock.lavaLevelTotal - 10) {
            blockEntity.getPropertyDelegate().add(0, 10);
            blockEntity.getPropertyDelegate().add(1, -10);
            updateLava = true;
            notEnoughLava = false;
        }
        ItemStack input1 = blockEntity.getInventory().getStack(1);
        ItemStack input2 = blockEntity.getInventory().getStack(2);
        Optional<HighFurnaceRecipe> optional = world.getRecipeManager().getFirstMatch(ModMachines.HIGH_FURNACE_RECIPE_TYPE, new SimpleInventory(input1, input2), world);
        if (optional.isPresent()) {
            ItemStack result = blockEntity.getInventory().getStack(3);
            ItemStack output = optional.get().getOutput(DynamicRegistryManager.EMPTY);
            if (result.isEmpty() || result.getItem() == output.getItem()) {
                if (blockEntity.getPropertyDelegate().get(2) > 0) {
                    if (currentRecipe == optional.get()) {
                        if (blockEntity.getPropertyDelegate().get(0) > 0 && !notEnoughLava) {
                            blockEntity.getPropertyDelegate().add(0, -1);
                            blockEntity.getPropertyDelegate().add(2, -1);
                            if (blockEntity.getPropertyDelegate().get(2) == 0) {
                                if (result.getCount() + output.getCount() > result.getItem().getMaxCount()) {
                                    blockEntity.getPropertyDelegate().add(0, 1);
                                    blockEntity.getPropertyDelegate().add(2, 1);
                                } else {
                                    if (input1.getCount() > 1 && input2.getCount() > 1) {
                                        input1.decrement(1);
                                        input2.decrement(1);
                                        blockEntity.getPropertyDelegate().set(2, HighFurnaceBlock.lavaLevelToTransform);
                                    } else {
                                        blockEntity.getInventory().setStack(1, ItemStack.EMPTY);
                                        blockEntity.getInventory().setStack(2, ItemStack.EMPTY);
                                    }
                                    if (!result.isEmpty()) {
                                        result.increment(output.getCount());
                                    } else {
                                        blockEntity.getInventory().setStack(3, output.copy());
                                    }
                                    markDirty(world, pos, state);
                                }
                            }
                        } else if (blockEntity.getPropertyDelegate().get(2) < HighFurnaceBlock.lavaLevelToTransform
                                && blockEntity.getPropertyDelegate().get(0) < HighFurnaceBlock.lavaLevelTotal) {
                            blockEntity.getPropertyDelegate().add(0, 1);
                            blockEntity.getPropertyDelegate().add(2, 1);
                            notEnoughLava = true;
                        }
                    }else if (blockEntity.getPropertyDelegate().get(2) < HighFurnaceBlock.lavaLevelToTransform
                            && blockEntity.getPropertyDelegate().get(0) < HighFurnaceBlock.lavaLevelTotal){
                        blockEntity.getPropertyDelegate().add(0, 1);
                        blockEntity.getPropertyDelegate().add(2, 1);
                    } else {
                        blockEntity.getPropertyDelegate().set(2, HighFurnaceBlock.lavaLevelToTransform);
                    }
                } else {
                    blockEntity.getPropertyDelegate().set(2, HighFurnaceBlock.lavaLevelToTransform);
                }
                currentRecipe = optional.get();
            }else if (blockEntity.getPropertyDelegate().get(2) < HighFurnaceBlock.lavaLevelToTransform
                    && blockEntity.getPropertyDelegate().get(0) < HighFurnaceBlock.lavaLevelTotal){
                blockEntity.getPropertyDelegate().add(0, 1);
                blockEntity.getPropertyDelegate().add(2, 1);
            }
        }else if (blockEntity.getPropertyDelegate().get(2) < HighFurnaceBlock.lavaLevelToTransform
                && blockEntity.getPropertyDelegate().get(0) < HighFurnaceBlock.lavaLevelTotal){
            blockEntity.getPropertyDelegate().add(0, 1);
            blockEntity.getPropertyDelegate().add(2, 1);
        }

        if (updateLava) {
            updateLava(world, pos, blockEntity, state);
        }
    }

    private static void updateLava(World world, BlockPos pos, @NotNull HighFurnaceBlockEntity blockEntity, BlockState state) {
        if (blockEntity.getPropertyDelegate().get(1) == 0 && blockEntity.getPropertyDelegate().get(0) < HighFurnaceBlock.lavaLevelTotal - 10
                && blockEntity.getInventory().getStack(0).getItem().equals(Items.LAVA_BUCKET)) {
            blockEntity.changingInventory = true;
            blockEntity.getInventory().setStack(0, new ItemStack(Items.BUCKET));
            blockEntity.getPropertyDelegate().set(1, 1000);
            blockEntity.changingInventory = false;
            markDirty(world, pos, state);
        }
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, @NotNull PlayerEntity player) {
        return new HighFurnaceScreenHandler(syncId, playerInventory, ScreenHandlerContext.create(player.getEntityWorld(), pos),
                getInventory(), getPropertyDelegate());
    }

    @Override
    public void slotUpdate(int slot) {
        if (changingInventory) {
            return;
        }
        if (slot == 0) {
            updateLava(world, pos, this, getCachedState());
        }
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.UP) {
            return new int[]{0};
        } else if (side == Direction.DOWN) {
            return new int[]{0, 3};
        } else if (side == Direction.NORTH || side == Direction.SOUTH) {
            return new int[]{1};
        } else {
            return new int[]{2};
        }
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        if (slot == 0) {
            return stack.getItem().equals(Items.LAVA_BUCKET);
        }
        return (slot == 1 || slot == 2) && !stack.getItem().equals(Items.LAVA_BUCKET);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        if (slot == 0) {
            return stack.getItem().equals(Items.BUCKET);
        }
        return slot == 3;
    }

}