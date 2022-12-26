package fr.vana_mod.nicofighter45.machine.basic.properties;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MachineInventory extends SidedInventory {
    DefaultedList<ItemStack> getStacks();

    @Override
    default int size() {
        return getStacks().size();
    }

    @Override
    default boolean isEmpty() {
        for (int i = 0; i < size(); i++) {
            ItemStack stack = getStack(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    default ItemStack getStack(int slot) {
        return getStacks().get(slot);
    }

    @Override
    default ItemStack removeStack(int slot, int count) {
        ItemStack result = Inventories.splitStack(getStacks(), slot, count);
        if (!result.isEmpty()) {
            markDirty();
        }
        slotUpdate(slot);
        return result;
    }

    void slotUpdate(int slot);

    @Override
    default ItemStack removeStack(int slot) {
        if (slot >= 0 && slot < getStacks().size()) {
            ItemStack result = getStacks().set(slot, ItemStack.EMPTY);
            slotUpdate(slot);
            return result;
        }
        return null;
    }

    default MachineInventory getInventory(){
        return this;
    }

    @Override
    default void setStack(int slot, ItemStack stack) {
        getStacks().set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
        slotUpdate(slot);
    }

    @Override
    default void clear() {
        getStacks().clear();
    }

    @Override
    default void markDirty() {
    }

    static @NotNull MachineInventory of(DefaultedList<ItemStack> items) {
        return new MachineInventory() {
            @Override
            public int[] getAvailableSlots(Direction side) {
                return new int[0];
            }

            @Override
            public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
                return false;
            }

            @Override
            public boolean canExtract(int slot, ItemStack stack, Direction dir) {
                return false;
            }

            @Override
            public DefaultedList<ItemStack> getStacks() {
                return items;
            }

            @Override
            public void slotUpdate(int slot) {
            }

            @Override
            public boolean canPlayerUse(PlayerEntity player) {
                return true;
            }
        };
    }

    static @NotNull MachineInventory ofSize(int size) {
        return of(DefaultedList.ofSize(size, ItemStack.EMPTY));
    }

}