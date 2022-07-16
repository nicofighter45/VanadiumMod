package fr.vana_mod.nicofighter45.machine.purificator;

import fr.vana_mod.nicofighter45.machine.basic.AbstractMachineScreenHandler;
import fr.vana_mod.nicofighter45.machine.basic.ArrayMachinePropertyDelegate;
import fr.vana_mod.nicofighter45.machine.basic.MachinePropertyDelegate;
import fr.vana_mod.nicofighter45.machine.basic.slot.ItemOutputSlot;
import fr.vana_mod.nicofighter45.machine.basic.MachineInventory;
import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.machine.basic.slot.WaterInputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;

public class PurificatorScreenHandler extends AbstractMachineScreenHandler {

    public PurificatorScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY, MachineInventory.ofSize(3), new ArrayMachinePropertyDelegate(3));
    }

    public PurificatorScreenHandler(int syncId, @NotNull PlayerInventory playerInventory, ScreenHandlerContext context,
                                    @NotNull MachineInventory inventory, @NotNull MachinePropertyDelegate propertyDelegate) {
        super(ModMachines.PURIFICATOR_SCREEN_HANDLER, syncId, playerInventory, context, inventory, propertyDelegate);

        this.addSlot(new WaterInputSlot(inventory, 0, 16, 12));
        this.addSlot(new Slot(inventory, 1, 44, 39));
        this.addSlot(new ItemOutputSlot(inventory, 2, 116,39));
    }

    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ModMachines.PURIFICATOR_BLOCK);
    }

    public int getWater(){
        return this.propertyDelegate.get(0);
    }

    public int getFillingTime(){
        return this.propertyDelegate.get(1);
    }

    public int getCraftingTime(){
        return this.propertyDelegate.get(2);
    }

}