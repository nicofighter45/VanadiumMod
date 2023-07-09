package fr.vana_mod.nicofighter45.machine.enchanter;

import fr.vana_mod.nicofighter45.machine.ModMachines;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EnchanterScreenHandler extends ScreenHandler {

    private final Inventory input;
    private final Inventory output;
    private final ScreenHandlerContext context;
    private final PlayerEntity player;
    private final List<Enchantment> itemEnchantments = new ArrayList<>();
    private ItemStack firstItem = ItemStack.EMPTY;

    public EnchanterScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, playerInventory.player);
    }

    public EnchanterScreenHandler(int syncId, PlayerInventory inv, PlayerEntity player) {
        this(syncId, inv, player, ScreenHandlerContext.EMPTY);
    }

    public EnchanterScreenHandler(int syncId, @NotNull PlayerInventory playerInventory, PlayerEntity player, ScreenHandlerContext context) {
        super(ModMachines.ENCHANTER_SCREEN_HANDLER, syncId);
        this.context = context;
        this.player = player;
        this.input = new SimpleInventory(3);
        this.output = new SimpleInventory(7);

        this.addSlot(new EnchanterInputSlot(this, 0, 80, 50));
        this.addSlot(new EnchanterInputSlot(this, 1, 8, 50));
        this.addSlot(new EnchanterInputSlot(this, 2, 152, 50));


        int m;
        int l;
        for (m = 0; m < 7; ++m) {
            this.addSlot(new EnchanterOutputSlot(this, m, 26 + 18 * m, 16));
        }

        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player hot bar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.context.run((world, pos) -> this.dropInventory(player, this.input));
    }

    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ModMachines.ENCHANTER_BLOCK);
    }

    public ItemStack quickMove(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index >= 3 && index < 10) {
                EnchanterOutputSlot.reloadEnchant(this);
                if (!this.insertItem(itemStack2, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (index >= 10 && index < 46) {
                if (!this.insertItem(itemStack2, 0, 3, false)) {
                    if (!this.insertItem(itemStack2, 3, 10, false)) {
                        if (index < 37) {
                            if (!this.insertItem(itemStack2, 37, 46, false)) {
                                return ItemStack.EMPTY;
                            }
                        } else if (!this.insertItem(itemStack2, 10, 37, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else {
                        EnchanterOutputSlot.reloadEnchant(this);
                    }
                } else {
                    ((EnchanterInputSlot) this.getSlot(0)).checkingWithInsertion();
                }
            } else if (!this.insertItem(itemStack2, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStackNoCallbacks(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, itemStack2);
            if (index >= 3 && index < 10) {
                player.dropItem(itemStack2, false);
            }
        }

        return itemStack;
    }

    public Inventory getInput() {
        return this.input;
    }

    public Inventory getOutput() {
        return this.output;
    }

    public PlayerEntity getPlayer() {
        return this.player;
    }

    public ItemStack getFirstItem() {
        return this.firstItem;
    }

    public void setFirstItem() {
        this.firstItem = this.getInput().getStack(0).copy();
        resetEnchantments();
    }

    public void resetEnchantments() {
        this.itemEnchantments.clear();
        this.itemEnchantments.addAll(EnchantmentHelper.get(this.firstItem).keySet());
    }

    public List<Enchantment> getItemEnchantments() {
        return this.itemEnchantments;
    }

}