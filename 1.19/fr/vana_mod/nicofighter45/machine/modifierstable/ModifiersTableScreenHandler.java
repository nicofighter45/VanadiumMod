package fr.vana_mod.nicofighter45.machine.modifierstable;

import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.machine.modifierstable.recipe.ModifiersRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ModifiersTableScreenHandler extends AbstractRecipeScreenHandler<CraftingInventory> {

    private final CraftingInventory input;
    private final CraftingResultInventory result;
    private final ScreenHandlerContext context;
    private final PlayerEntity player;

    public ModifiersTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, playerInventory.player);
    }

    public ModifiersTableScreenHandler(int syncId, PlayerInventory inv, PlayerEntity player){
        this(syncId, inv, player, ScreenHandlerContext.EMPTY);
    }

    public ModifiersTableScreenHandler(int syncId, @NotNull PlayerInventory playerInventory, PlayerEntity player, ScreenHandlerContext context) {
        super(ModMachines.MODIFIERS_TABLE_SCREEN_HANDLER, syncId);
        this.context = context;
        this.player = player;
        this.input = new CraftingInventory(this, 2, 2);
        this.result = new CraftingResultInventory();

        this.addSlot(new ModifiersTableResultSlot(player, this.input, this.result, 0, 80, 59));

        int m;
        int l;
        for (m = 0; m < 2; ++m) {
            for (l = 0; l < 2; ++l) {
                this.addSlot(new Slot(this.input, l + m * 2, 44 + l * 72, 23 + m * 72));
            }
        }

        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 126 + m * 18));
            }
        }
        //The player hot bar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 184));
        }
    }

    protected void updateResult(ScreenHandler handler, @NotNull World world, PlayerEntity player, CraftingInventory craftingInventory, CraftingResultInventory resultInventory) {
        if (!world.isClient) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
            ItemStack itemStack = ItemStack.EMPTY;
            Optional<ModifiersRecipe> optional = world.getRecipeManager().getFirstMatch(ModMachines.MODIFIERS_RECIPE_TYPE, craftingInventory, world);
            if (optional.isPresent()) {
                ModifiersRecipe craftingRecipe = optional.get();
                if (resultInventory.shouldCraftRecipe(world, serverPlayerEntity, craftingRecipe)) {
                    itemStack = craftingRecipe.getOutput().copy();
                }
            }
            this.result.setStack(0, itemStack);
            serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, itemStack));
        }
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        this.context.run((world, pos) -> updateResult(this, world, this.player, this.input, this.result));
    }

    public void populateRecipeFinder(RecipeMatcher finder) {
        this.input.provideRecipeInputs(finder);
    }

    public void clearCraftingSlots() {
        this.input.clear();
        this.result.clear();
    }

    public boolean matches(@NotNull Recipe<? super CraftingInventory> recipe) {
        return recipe.matches(this.input, this.player.world);
    }

    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, pos) -> this.dropInventory(player, this.input));
    }

    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ModMachines.MODIFIERS_TABLE_BLOCK);
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index == 0) {
                this.context.run((world, pos) -> itemStack2.getItem().onCraft(itemStack2, world, player));
                if (!this.insertItem(itemStack2, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (index >= 5 && index < 41) {
                if (!this.insertItem(itemStack2, 1, 5, false)) {
                    if (index < 32) {
                        if (!this.insertItem(itemStack2, 32, 41, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.insertItem(itemStack2, 5, 32, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.insertItem(itemStack2, 5, 41, false)) {
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
            if (index == 0) {
                player.dropItem(itemStack2, false);
            }
        }

        return itemStack;
    }

    public boolean canInsertIntoSlot(ItemStack stack, @NotNull Slot slot) {
        return slot.inventory != this.result && super.canInsertIntoSlot(stack, slot);
    }

    public int getCraftingResultSlotIndex() {
        return 0;
    }

    @Override
    public int getCraftingWidth() {
        return 2;
    }

    @Override
    public int getCraftingHeight() {
        return 2;
    }

    public int getCraftingSlotCount() {
        return 5;
    }

    public RecipeBookCategory getCategory() {
        return RecipeBookCategory.CRAFTING;
    }

    public boolean canInsertIntoSlot(int index) {
        return index != this.getCraftingResultSlotIndex();
    }
}
