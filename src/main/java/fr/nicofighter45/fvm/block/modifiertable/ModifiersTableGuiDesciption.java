package fr.nicofighter45.fvm.block.modifiertable;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

public class ModifiersTableGuiDesciption extends SyncedGuiDescription {
    private static final int INVENTORY_SIZE = 1;

    public ModifiersTableGuiDesciption(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ScreenHandlerType.CRAFTING, syncId, playerInventory, getBlockInventory(context, INVENTORY_SIZE), getBlockPropertyDelegate(context));

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(300, 200);

        WItemSlot itemSlot = WItemSlot.of(blockInventory, 0);
        root.add(itemSlot, 4, 1);

        root.add(this.createPlayerInventoryPanel(), 0, 3);

        root.validate(this);
    }
}