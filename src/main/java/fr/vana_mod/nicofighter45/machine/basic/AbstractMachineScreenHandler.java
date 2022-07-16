package fr.vana_mod.nicofighter45.machine.basic;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractMachineScreenHandler extends ScreenHandler {

    private final ScreenHandlerContext context;
    private final MachinePropertyDelegate propertyDelegate;
    private final MachineInventory inventory;

    protected AbstractMachineScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId,
                                           @NotNull PlayerInventory playerInventory, ScreenHandlerContext context,
                                           @NotNull MachineInventory inventory, @NotNull MachinePropertyDelegate propertyDelegate) {
        super(type, syncId);

        this.context = context;
        this.propertyDelegate = propertyDelegate;
        this.addProperties(propertyDelegate);
        this.inventory = inventory;

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

}