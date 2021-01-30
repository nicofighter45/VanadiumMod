package fr.nicofighter45.fvm.block.modifiertable;

import fr.nicofighter45.fvm.FVM;
import fr.nicofighter45.fvm.items.ModItems;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

public class ModifiersTableGuiDescription extends SyncedGuiDescription {

    private static final Identifier MESSAGE_ITEM = new Identifier(FVM.MODID, "item");

    public ModifiersTableGuiDescription(int syncId, PlayerInventory playerinv, ScreenHandlerContext context) {

        super(FVM.SCREEN_HANDLER_TYPE, syncId, playerinv, getBlockInventory(context, 4), getBlockPropertyDelegate(context));

        PlayerEntity player = playerinv.player;

        //panel type
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        //panel size
        //root.setSize(0, 0);

        //items slots
        WItemSlot itemSlot1 = WItemSlot.of(blockInventory, 0);
        root.add(itemSlot1, 1, 1);
        WItemSlot itemSlot2 = WItemSlot.of(blockInventory, 1);
        root.add(itemSlot2, 3, 1);
        WItemSlot itemSlot3 = WItemSlot.of(blockInventory, 2);
        root.add(itemSlot3, 5, 1);
        WItemSlot itemSlot4 = WItemSlot.of(blockInventory, 3);
        root.add(itemSlot4, 7, 1);

        //button
        WButton button = new WButton(new LiteralText("Modify"));
        button.setOnClick(() -> {
            ItemStack item0 = this.getSlot(0).getStack();
            ItemStack item1 = this.getSlot(1).getStack();
            ItemStack item2 = this.getSlot(2).getStack();
            ItemStack item3 = this.getSlot(3).getStack();
            Item item = null;
            if (item0.getItem() == ModItems.VANADIUM_STICK && item1.getItem() == ModItems.VANADIUM_INGOT
                    && item2.getItem() == ModItems.VANADIUM_HEART && item3.getItem() == ModItems.VANADIUM_BLOCK_ITEM) {
                item = ModItems.VANADIUM_SWORD;
            }else if(item0.getItem() == ModItems.TRANSISTOR && item1.getItem() == ModItems.TRANSISTOR
            && item2.getItem() == ModItems.TRANSISTOR && item3.getItem() == ModItems.TRANSISTOR) {
                item = ModItems.PROCESSOR;
            }else if(item0.getItem() == ModItems.VANADIUM_INGOT && item1.getItem() == ModItems.VANADIUM_INGOT
                    && item2.getItem() == ModItems.VANADIUM_INGOT && item3.getItem() == ModItems.VANADIUM_INGOT) {
                item = ModItems.VANADIUM_BLOCK_ITEM;
            }else if(item0.getItem() == ModItems.VANADIUM_NUGGET && item1.getItem() == ModItems.VANADIUM_NUGGET
                        && item2.getItem() == ModItems.VANADIUM_NUGGET && item3.getItem() == ModItems.VANADIUM_NUGGET){
                    item = ModItems.VANADIUM_INGOT;
            }else if(item0.getItem() == ModItems.VANADIUM_BLOCK_ITEM && item1.getItem() == Items.DIAMOND
                    && item2.getItem() == Items.DIAMOND && item3.getItem() == ModItems.VANADIUM_BLOCK_ITEM){
                item = ModItems.VANADIUM_HEART;
            }else if(item0.getItem() == ModItems.VANADIUM_INGOT || item1.getItem() == ModItems.VANADIUM_INGOT
                    || item2.getItem() == ModItems.VANADIUM_INGOT || item3.getItem() == ModItems.VANADIUM_INGOT){
                item = ModItems.VANADIUM_STICK;
            }
            if(item == null){
                return;
            }
            ItemStack item_stack = new ItemStack(item);
            ScreenNetworking.of(this, NetworkSide.CLIENT).send(MESSAGE_ITEM, buf ->
                    buf.writeItemStack(item_stack)
            );
        });
        root.add(button, 3, 3, 3, 1);

        //add player inventory
        root.add(this.createPlayerInventoryPanel(), 0, 5);
        root.validate(this);

        //receiver give item
        ScreenNetworking.of(this, NetworkSide.SERVER).receive(MESSAGE_ITEM, buf -> {
            ServerPlayerEntity server_player = (ServerPlayerEntity) player;
            getBlockInventory(context).clear();
            server_player.closeHandledScreen();
            server_player.inventory.insertStack(buf.readItemStack());
        });
    }

}