package fr.nicofighter45.fvm.block.modifiertable;

import fr.nicofighter45.fvm.FVM;
import fr.nicofighter45.fvm.items.ModItems;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.fabricmc.fabric.mixin.resource.loader.MixinKeyedResourceReloadListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;
import org.intellij.lang.annotations.Identifier;

import java.util.Objects;

public class ModifiersTableGuiDescription extends SyncedGuiDescription {

    //private static final Identifier MESSAGE_ID = new Identifier("my_mod", "some_message");

    // Receiver
    //ScreenNetworking.of(this, NetworkSide.SERVER).receive(MESSAGE_ID, buf -> {
        // Example data: a lucky number as an int
        //System.out.println("Your lucky number is " + buf.readInt() + "!");
   // });

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
        root.add(itemSlot1, 3, 3);
        WItemSlot itemSlot2 = WItemSlot.of(blockInventory, 2);
        root.add(itemSlot2, 4, 3);
        WItemSlot itemSlot3 = WItemSlot.of(blockInventory, 3);
        root.add(itemSlot3, 5, 3);
        WItemSlot itemSlot4 = WItemSlot.of(blockInventory, 4);
        root.add(itemSlot4, 4, 1);

        //button
        WButton button = new WButton(new LiteralText("Modify"));
        button.setOnClick(
                () -> {
                    //ScreenNetworking.of(this, NetworkSide.CLIENT).send(MESSAGE_ID, buf -> buf.writeInt(123));
                    ServerPlayerEntity serverPlayerEntity = FVM.minecraftServer.getPlayerManager().getPlayer(String.valueOf(player.getName()));
                    assert serverPlayerEntity != null;
                    this.slots.clear();
                    serverPlayerEntity.closeHandledScreen();
                    serverPlayerEntity.giveItemStack(new ItemStack(ModItems.VANADIUM_CHESTPLATE));
                }
        );
        root.add(button, 3, 4, 3, 1);

        //add player inventory
        root.add(this.createPlayerInventoryPanel(), 0, 6);
        root.validate(this);
    }

}