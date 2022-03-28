package fr.vana_mod.nicofighter45.block.modifiertable;

import fr.vana_mod.nicofighter45.main.VanadiumMod;
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
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

public class ModifiersTableGuiDescription extends SyncedGuiDescription {

    private static final Identifier MESSAGE_ITEM = new Identifier(VanadiumMod.MODID, "item");

    public ModifiersTableGuiDescription(int syncId, PlayerInventory playerinv, ScreenHandlerContext context) {

        super(VanadiumMod.SCREEN_HANDLER_TYPE, syncId, playerinv, getBlockInventory(context, 4), getBlockPropertyDelegate(context));

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
        button.setOnClick(() -> ScreenNetworking.of(this, NetworkSide.CLIENT).send(MESSAGE_ITEM, buf -> buf.writeBoolean(this.isTitleVisible())));
        root.add(button, 3, 3, 3, 1);

        //add player inventory
        root.add(this.createPlayerInventoryPanel(), 0, 5);
        root.validate(this);

        //receiver give item
        ScreenNetworking.of(this, NetworkSide.SERVER).receive(MESSAGE_ITEM, buf -> {
            ServerPlayerEntity server_player = (ServerPlayerEntity) player;
            for(int i = 64; i >= 0; i--){
                ItemStack item0 = this.getSlot(0).getStack();
                ItemStack item1 = this.getSlot(1).getStack();
                ItemStack item2 = this.getSlot(2).getStack();
                ItemStack item3 = this.getSlot(3).getStack();
                ItemStack result = null;
                for(ModifierCraft craft : ModifierTableRegister.crafts.keySet()){
                    if(craft.getItem0() == item0.getItem() && craft.getItem1() == item1.getItem() && craft.getItem2() == item2.getItem() && craft.getItem3() == item3.getItem()){
                        result = ModifierTableRegister.crafts.get(craft);
                        break;
                    }
                }
                if(result == null){
                    break;
                }else{
                    for(int slot = 0; slot < 4; slot++){
                        getBlockInventory(context).removeStack(slot, 1);
                    }
                    server_player.getInventory().offerOrDrop(result);
                }
            }
        });

    }

}