package fr.nicofighter45.fvm.block.modifiertable;

import fr.nicofighter45.fvm.FVM;
import fr.nicofighter45.fvm.items.ModItems;
import fr.nicofighter45.fvm.items.armor.vanadium.ModifiableItem;
import fr.nicofighter45.fvm.items.armor.vanadium.UpgradeItem;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

public class ModifiersTableGuiDescription extends SyncedGuiDescription {

    private static final Identifier MESSAGE_ITEM = new Identifier(FVM.MODID, "item");

    public ModifiersTableGuiDescription(int syncId, PlayerInventory playerinv, ScreenHandlerContext context) {

        super(FVM.SCREEN_HANDLER_TYPE, syncId, playerinv, getBlockInventory(context, 256), getBlockPropertyDelegate(context));

        PlayerEntity player = playerinv.player;

        //panel type
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        //panel size
        root.setSize(100, 200);

        //items slots
        WItemSlot itemSlot1 = WItemSlot.of(blockInventory, 1);
        root.add(itemSlot1, 4, 3);
        WItemSlot itemSlot2 = WItemSlot.of(blockInventory, 2);
        root.add(itemSlot2, 4, 1);

        //button
        WButton button = new WButton(new LiteralText("Modify"));
        button.setOnClick(() -> {
            ItemStack main_item = this.getSlot(2).getStack();
            ItemStack second_item = this.getSlot(1).getStack();
            if(main_item.getItem() instanceof ModifiableItem){
                System.out.println("Détection item en vanadium");
                if(second_item.getItem() instanceof UpgradeItem){
                    if(((ModifiableItem) main_item.getItem()).addUpgrade(((UpgradeItem) second_item.getItem()).getUpgrade_type())){
                        main_item.getOrCreateSubTag("display").putString("Test", "Test");
                        ScreenNetworking.of(this, NetworkSide.CLIENT).send(MESSAGE_ITEM, buf -> buf.writeItemStack(main_item));
                    }
                }
            }
        });
        root.add(button, 3, 4, 3, 1);

        //add player inventory
        root.add(this.createPlayerInventoryPanel(), 0, 6);
        root.validate(this);

        //receiver give item
        ScreenNetworking.of(this, NetworkSide.SERVER).receive(MESSAGE_ITEM, buf -> {
            playerInventory.insertStack(buf.readItemStack());
            updateSlotStacks(null);
            close(playerInventory.player);
        });
    }

}