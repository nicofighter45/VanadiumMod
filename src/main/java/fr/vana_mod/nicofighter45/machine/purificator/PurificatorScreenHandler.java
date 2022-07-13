package fr.vana_mod.nicofighter45.machine.purificator;

import fr.vana_mod.nicofighter45.machine.MachineInventory;
import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.machine.purificator.recipe.PurificatorRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PurificatorScreenHandler extends AbstractRecipeScreenHandler<MachineInventory> {

    private final ScreenHandlerContext context;
    private final MachineInventory inventory;
    private final PropertyDelegate propertyDelegate;
    private final PlayerEntity player;

    public PurificatorScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY, MachineInventory.ofSize(3), new ArrayPropertyDelegate(3));
    }

    public PurificatorScreenHandler(int syncId, @NotNull PlayerInventory playerInventory, ScreenHandlerContext context, MachineInventory inventory, PropertyDelegate propertyDelegate) {
        super(ModMachines.PURIFICATOR_SCREEN_HANDLER, syncId);
        this.context = context;
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.player = playerInventory.player;

        System.out.println("Purificator creation :\nInventory :\n" + inventory.getItems().toString() + "\n\nPropertyDelegate :\n" + propertyDelegate.get(0) + propertyDelegate.get(1) + propertyDelegate.get(2));

        this.addSlot(new WaterInputSlot(propertyDelegate, inventory, 0, 16, 12));
        this.addSlot(new Slot(inventory, 1, 44, 39));
        this.addSlot(new Slot(inventory, 2, 116,39));

        int m;
        int l;
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }

        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    protected void updateResult(@NotNull World world, MachineInventory machineInventory, ItemStack itemStack) {
        if (!world.isClient) {
            Optional<PurificatorRecipe> optional = world.getRecipeManager().getFirstMatch(ModMachines.PURIFICATOR_RECIPE_TYPE, new SimpleInventory(inventory.getStack(1)), world);
            if (optional.isPresent()) {
                if(machineInventory.getStack(1).getItem() != itemStack.getItem()){
                    propertyDelegate.set(2, 0); // 2 -> crafting
                }
            }else if(propertyDelegate.get(2) > 0){
                propertyDelegate.set(2, 0);
            }
        }
    }

    public void onContentChanged(Inventory inventory) {
        this.context.run((world, pos) -> updateResult(this.player.getWorld(), this.inventory, inventory.getStack(1)));
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
    }

    public PropertyDelegate getProperty(){
        return this.propertyDelegate;
    }

    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ModMachines.PURIFICATOR_BLOCK);
    }

    @Override
    protected boolean insertItem(@NotNull ItemStack stack, int startIndex, int endIndex, boolean fromLast) {
        System.out.println("inserting item " + stack.getItem().toString() + startIndex + endIndex);
       if(endIndex == 1){
           this.context.run((world, pos) -> updateResult(this.player.getWorld(), this.inventory, stack));
       }
        return super.insertItem(stack, startIndex, endIndex, fromLast);
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index >= 0 && index < 3) {
                if (!this.insertItem(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (index >= 3 && index < 39) {
                if (!this.insertItem(itemStack2, 0, 3, false)) {
                    if (index < 30) {
                        if (!this.insertItem(itemStack2, 30, 39, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.insertItem(itemStack2, 3, 30, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.insertItem(itemStack2, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, itemStack2);
            if (index >= 0 && index < 3) {
                player.dropItem(itemStack2, false);
            }
        }
        return itemStack;
    }

    @Override
    public void populateRecipeFinder(RecipeMatcher finder) {
        this.inventory.provideRecipeInputs(finder);
    }

    @Override
    public void clearCraftingSlots() {
        this.inventory.setStack(1, ItemStack.EMPTY);
        this.inventory.setStack(2, ItemStack.EMPTY);
    }

    @Override
    public boolean matches(@NotNull Recipe<? super MachineInventory> recipe) {
        return recipe.matches(this.inventory, this.player.getWorld());
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 2;
    }

    @Override
    public int getCraftingWidth() {
        return 1;
    }

    @Override
    public int getCraftingHeight() {
        return 1;
    }

    @Override
    public int getCraftingSlotCount() {
        return 2;
    }

    @Override
    public RecipeBookCategory getCategory() {
        return RecipeBookCategory.CRAFTING;
    }

    @Override
    public boolean canInsertIntoSlot(int index) {
        return index != 2;
    }

}