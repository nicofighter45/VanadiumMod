package fr.vana_mod.nicofighter45.machine.high_furnace;

import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.machine.basic.AbstractMachineScreenHandler;
import fr.vana_mod.nicofighter45.machine.basic.ArrayMachinePropertyDelegate;
import fr.vana_mod.nicofighter45.machine.basic.MachineInventory;
import fr.vana_mod.nicofighter45.machine.basic.MachinePropertyDelegate;
import fr.vana_mod.nicofighter45.machine.basic.slot.ItemOutputSlot;
import fr.vana_mod.nicofighter45.machine.basic.slot.LavaInputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;

public class HighFurnaceScreenHandler extends AbstractMachineScreenHandler {

    public HighFurnaceScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY, MachineInventory.ofSize(4), new ArrayMachinePropertyDelegate(3));
    }
    public HighFurnaceScreenHandler(int syncId, @NotNull PlayerInventory playerInventory, ScreenHandlerContext context, @NotNull MachineInventory inventory, @NotNull MachinePropertyDelegate propertyDelegate) {
        super(ModMachines.HIGH_FURNACE_SCREEN_HANDLER, syncId, playerInventory, context, inventory, propertyDelegate);

        this.addSlot(new LavaInputSlot(inventory, 0, 16, 12));
        this.addSlot(new Slot(inventory, 1, 44, 30));
        this.addSlot(new Slot(inventory, 2, 44, 48));
        this.addSlot(new ItemOutputSlot(inventory, 3, 116,39));

        this.registerPlayerInventory();
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ModMachines.HIGH_FURNACE_BLOCK);
    }

    public int getLava(){
        return this.propertyDelegate.get(0);
    }

    public int getFillingTime(){
        return this.propertyDelegate.get(1);
    }

    public int getCraftingTime(){
        return this.propertyDelegate.get(2);
    }

}