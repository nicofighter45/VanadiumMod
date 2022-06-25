package fr.vana_mod.nicofighter45.main;

import fr.vana_mod.nicofighter45.block.modifiertable.ModifiersTableScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import fr.vana_mod.nicofighter45.entity.ModEntity;

public class VanadiumModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        //register modifiers table screen on client
        ScreenRegistry.register(VanadiumMod.MODIFIERS_TABLE_SCREEN_HANDLER, ModifiersTableScreen::new);

        //register entities
        ModEntity.registerAll();

        //add keybinds
        KeyBinds.registerAll();

    }
}