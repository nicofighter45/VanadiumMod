package fr.vana_mod.nicofighter45.machine.basic;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeUnlocker;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MachineInventory implements Inventory, RecipeInputProvider, RecipeUnlocker {

    private final DefaultedList<ItemStack> stacks;
    private final AbstractMachineBlockEntity blockEntity;
    private final boolean isClient;

    protected MachineInventory(AbstractMachineBlockEntity blockEntity, DefaultedList<ItemStack> items) {
        this.stacks = items;
        this.blockEntity = blockEntity;
        this.isClient = blockEntity == null;
    }

    public DefaultedList<ItemStack> getItems() {
        return stacks;
    }

    public void provideRecipeInputs(RecipeMatcher finder) {
        for (ItemStack itemStack : this.stacks) {
            finder.addUnenchantedInput(itemStack);
        }
    }

    @Override
    public int size() {
        return getItems().size();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < size(); i++) {
            ItemStack stack = getStack(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return getItems().get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int count) {
        ItemStack result = Inventories.splitStack(getItems(), slot, count);
        if (!result.isEmpty()) {
            markDirty();
        }
        if(!this.isClient){
            this.blockEntity.slotUpdate(slot);
        }
        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        if (slot >= 0 && slot < stacks.size()) {
            ItemStack result = stacks.set(slot, ItemStack.EMPTY);
            if(!this.isClient){
                this.blockEntity.slotUpdate(slot);
            }
            return result;
        }
        return null;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        getItems().set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
        if(!this.isClient){
            this.blockEntity.slotUpdate(slot);
        }
    }

    @Override
    public void clear() {
        getItems().clear();
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void setLastRecipe(@Nullable Recipe<?> recipe) {

    }

    @Nullable
    @Override
    public Recipe<?> getLastRecipe() {
        return null;
    }

    public static @NotNull MachineInventory of(AbstractMachineBlockEntity blockEntity, DefaultedList<ItemStack> items) {
        return new MachineInventory(blockEntity, items);
    }

    public static @NotNull MachineInventory ofSize(AbstractMachineBlockEntity blockEntity, int size) {
        return of(blockEntity, DefaultedList.ofSize(size, ItemStack.EMPTY));
    }

    public static @NotNull MachineInventory ofClientSize(int size) {
        return of(null, DefaultedList.ofSize(size, ItemStack.EMPTY));
    }

}