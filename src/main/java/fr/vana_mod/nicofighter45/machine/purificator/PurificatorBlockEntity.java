package fr.vana_mod.nicofighter45.machine.purificator;

import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.machine.basic.block.AbstractMachineBlockEntity;
import fr.vana_mod.nicofighter45.machine.purificator.recipe.PurificatorRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PurificatorBlockEntity extends AbstractMachineBlockEntity {

    private static IntProperty configuration;
    private PurificatorRecipe currentRecipe;


    public PurificatorBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, IntProperty.of("configuration", 0, 4));
    }

    public PurificatorBlockEntity(BlockPos pos, BlockState state, IntProperty config) {
        super(ModMachines.PURIFICATOR_BLOCK_ENTITY_TYPE, pos, state, 3, 3, 0);
        configuration = config;
    }

    public static void tick(World world, BlockPos pos, BlockState state, @NotNull PurificatorBlockEntity blockEntity) {
        boolean updateWater = false;
        if (blockEntity.getPropertyDelegate().get(0) > 0 && blockEntity.getPropertyDelegate().get(2) > 0) {
            blockEntity.getPropertyDelegate().add(0, -1);
            blockEntity.getPropertyDelegate().add(2, -1);
            updateWater = true;
            if (blockEntity.getPropertyDelegate().get(2) == 0) {
                ItemStack input = blockEntity.getInventory().getStack(1);
                Optional<PurificatorRecipe> optional = world.getRecipeManager().getFirstMatch(ModMachines.PURIFICATOR_RECIPE_TYPE, new SimpleInventory(input), world);
                if (optional.isPresent()) {
                    blockEntity.changingInventory = true;
                    if (input.getCount() > 1) {
                        input.decrement(1);
                        blockEntity.getPropertyDelegate().set(2, 100);
                    } else {
                        blockEntity.getInventory().setStack(1, ItemStack.EMPTY);
                    }
                    ItemStack result = blockEntity.getInventory().getStack(2);
                    ItemStack output = optional.get().getOutput(DynamicRegistryManager.EMPTY);
                    if (!result.isEmpty()) {
                        result.increment(output.getCount());
                    } else {
                        blockEntity.getInventory().setStack(2, output.copy());
                    }
                    markDirty(world, pos, state);
                    blockEntity.changingInventory = false;
                }
            }
        }
        if (blockEntity.getPropertyDelegate().get(1) > 3 && blockEntity.getPropertyDelegate().get(0) < 1597) {
            blockEntity.getPropertyDelegate().add(0, 4);
            blockEntity.getPropertyDelegate().add(1, -4);
            updateWater = true;
        }
        if (updateWater) {
            updateWater(world, pos, blockEntity, state);
        }
    }

    public static void updateWater(World world, BlockPos pos, @NotNull PurificatorBlockEntity blockEntity, BlockState state) {
        if (blockEntity.getPropertyDelegate().get(1) == 0 && blockEntity.getPropertyDelegate().get(0) < 1501
                && blockEntity.getInventory().getStack(0).getItem().equals(Items.WATER_BUCKET)) {
            blockEntity.changingInventory = true;
            blockEntity.getInventory().setStack(0, new ItemStack(Items.BUCKET));
            blockEntity.getPropertyDelegate().set(1, 400);
            blockEntity.changingInventory = false;
            markDirty(world, pos, state);
        }
        updateTexture(world, pos, state, blockEntity.getPropertyDelegate().get(0));
    }

    private static void updateTexture(World world, BlockPos pos, BlockState state, int water) {
        BlockState blockStateFinal;
        if (water == 0 && state.get(configuration) != 0) {
            blockStateFinal = state.with(configuration, 0);
        } else if (0 <= water && water < 400 && state.get(configuration) != 1) {
            blockStateFinal = state.with(configuration, 1);
        } else if (400 <= water && water < 800 && state.get(configuration) != 2) {
            blockStateFinal = state.with(configuration, 2);
        } else if (800 <= water && water < 1200 && state.get(configuration) != 3) {
            blockStateFinal = state.with(configuration, 3);
        } else if (1200 <= water && water <= 1600 && state.get(configuration) != 4) {
            blockStateFinal = state.with(configuration, 4);
        } else {
            return;
        }
        world.setBlockState(pos, blockStateFinal);
    }

    @Override
    public void slotUpdate(int slot) {
        if (changingInventory) {
            return;
        }
        if (slot == 0) {
            updateWater(world, pos, this, getCachedState());
        } else if (slot == 1) {
            assert world != null;
            Optional<PurificatorRecipe> optional = world.getRecipeManager().getFirstMatch(ModMachines.PURIFICATOR_RECIPE_TYPE, new SimpleInventory(getInventory().getStack(1)), world);
            if (optional.isPresent()) {
                if (currentRecipe == null) {
                    currentRecipe = optional.get();
                    getPropertyDelegate().set(2, 100);
                } else {
                    if (getInventory().getStack(1).getItem() != currentRecipe.getInputItem()) {
                        currentRecipe = optional.get();
                        getPropertyDelegate().set(2, 100);
                    }
                }
            } else {
                getPropertyDelegate().set(2, 0);
            }
        }
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, @NotNull PlayerEntity player) {
        return new PurificatorScreenHandler(syncId, playerInventory, ScreenHandlerContext.create(player.getEntityWorld(), pos), getInventory(), getPropertyDelegate());
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.UP) {
            return new int[]{0};
        } else if (side == Direction.DOWN) {
            return new int[]{0, 2};
        } else {
            return new int[]{1};
        }
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        if (slot == 0) {
            return stack.getItem().equals(Items.WATER_BUCKET);
        }
        return slot == 1 && !stack.getItem().equals(Items.WATER_BUCKET);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        if (slot == 0) {
            return stack.getItem().equals(Items.BUCKET);
        }
        return slot == 2;
    }
}