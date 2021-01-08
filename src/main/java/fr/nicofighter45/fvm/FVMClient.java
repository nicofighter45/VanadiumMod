package fr.nicofighter45.fvm;

import fr.nicofighter45.fvm.block.modifiertable.ModifiersTableGuiDescription;
import fr.nicofighter45.fvm.block.modifiertable.ModifiersTableScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class FVMClient implements ClientModInitializer {

    //launch when client side of the mod is called
    @Override
    public void onInitializeClient() {

        //register modifiers table screen on client
        ScreenRegistry.<ModifiersTableGuiDescription, ModifiersTableScreen>register(FVM.SCREEN_HANDLER_TYPE,
                (gui, inventory, title) -> new ModifiersTableScreen(gui, inventory.player, title));

    }
}