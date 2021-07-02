package fr.vana_mod.nicofighter45.main;

import fr.vana_mod.nicofighter45.block.modifiertable.ModifiersTableGuiDescription;
import fr.vana_mod.nicofighter45.block.modifiertable.ModifiersTableScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import fr.vana_mod.nicofighter45.entity.ModEntity;

public class VanadiumModClient implements ClientModInitializer {

    //launch when client side of the mod is called
    @Override
    public void onInitializeClient() {

        //register modifiers table screen on client
        ScreenRegistry.<ModifiersTableGuiDescription, ModifiersTableScreen>register(VanadiumMod.SCREEN_HANDLER_TYPE,
                (gui, inventory, title) -> new ModifiersTableScreen(gui, inventory.player, title));

        //register entities
        ModEntity.registerAll();

        //add keybinds
        KeyBinds.registerAll();

    }
}