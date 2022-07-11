package fr.vana_mod.nicofighter45.machine.purificator;

import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.machine.purificator.craft.PurificatorRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PurificatorScreenHandler extends ScreenHandler {

    private PurificatorRecipe recipe;
    private final Inventory inventory;
    private final ScreenHandlerContext context;
    private final PurificatorProperties propertyDelegate;
    private final PlayerEntity player;

    public PurificatorScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new PurificatorProperties(), ScreenHandlerContext.EMPTY,  DefaultedList.ofSize(3, ItemStack.EMPTY));
    }

    public PurificatorScreenHandler(int syncId, @NotNull PlayerInventory playerInventory, PurificatorProperties propertyDelegate, ScreenHandlerContext context, @NotNull DefaultedList<ItemStack> itemStacks) {
        super(ModMachines.PURIFICATOR_SCREEN_HANDLER, syncId);
        this.context = context;
        this.propertyDelegate = propertyDelegate;
        this.player = playerInventory.player;
        inventory = new SimpleInventory(itemStacks.get(0), itemStacks.get(1), itemStacks.get(2));

        this.addSlot(new WaterInputSlot(this, inventory, 0, 16, 12));
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

    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, pos) -> this.dropInventory(player, this.inventory));
    }

    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ModMachines.PURIFICATOR_BLOCK);
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

    public int getFluid(){
        return this.propertyDelegate.get(0);
    }

    public boolean addFluid(){
        if(this.propertyDelegate.isFilling){
            return false;
        }
        this.propertyDelegate.isFilling = true;
        this.propertyDelegate.resetFillTime();
        return true;
    }

    public int getTime(){
        return this.propertyDelegate.get(1);
    }

    public boolean isCrafting(){
        return this.propertyDelegate.isCrafting;
    }

    public void onContentChanged(Inventory inventory) {
        this.context.run((world, pos) -> {
            if (!world.isClient) {
                Optional<PurificatorRecipe> optional = world.getRecipeManager().getFirstMatch(ModMachines.PURIFICATOR_RECIPE_TYPE, inventory, world);
                if(this.propertyDelegate.isCrafting && !this.recipe.getInput().getMatchingStacks()[0].equals(inventory.getStack(0))){
                    this.propertyDelegate.set(0, 0);
                    this.propertyDelegate.set(1, 0);
                }
                if (optional.isPresent()) {
                    if(inventory.getStack(2).getItem().equals(optional.get().getOutput().getItem())){
                        this.recipe = optional.get();
                    }
                    this.propertyDelegate.isCrafting = !this.propertyDelegate.isCrafting;
                }
            }
        });
    }

    public void crafting() {
        if(this.propertyDelegate.isCrafting){
            this.context.run((world, pos) -> {
                if (!world.isClient) {
                    ItemStack input = this.inventory.getStack(1);
                    if(input.getCount() > 1){
                        input.decrement(1);
                    }else{
                        input = ItemStack.EMPTY;
                    }
                    ItemStack result = this.inventory.getStack(2);
                    if(result.getItem().equals(this.recipe.getOutput().getItem())){
                        result.increment(1);
                    }else{
                        result = this.recipe.getOutput().copy();
                    }
                    ((ServerPlayerEntity) this.player).networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(syncId, nextRevision(), 1, input));
                    ((ServerPlayerEntity) this.player).networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(syncId, nextRevision(), 2, result));
                }
            });
        }
    }
}