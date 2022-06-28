package fr.vana_mod.nicofighter45.main;

import fr.vana_mod.nicofighter45.block.enchanter.EnchanterScreen;
import fr.vana_mod.nicofighter45.block.modifiertable.ModifiersTableScreen;
import fr.vana_mod.nicofighter45.items.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import fr.vana_mod.nicofighter45.entity.ModEntity;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class VanadiumModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        //register modifiers table screen on client
        ScreenRegistry.register(VanadiumMod.MODIFIERS_TABLE_SCREEN_HANDLER, ModifiersTableScreen::new);

        //register enchanter on client
        ScreenRegistry.register(VanadiumMod.ENCHANTER_SCREEN_HANDLER, EnchanterScreen::new);

        //register vanadium bow
        ModelPredicateProviderRegistry.register(ModItems.VANADIUM_BOW, new Identifier("pull"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            }
            return entity.getActiveItem() != stack ? 0.0F : (stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / 20.0F;
        });

        ModelPredicateProviderRegistry.register(ModItems.VANADIUM_BOW, new Identifier("pulling"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            }
            return entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F;
        });

        ModelPredicateProviderRegistry.register(ModItems.VANADIUM_ELYTRA, new Identifier("broken"), (stack, world, entity, seed) -> {
            return 1;
        });

        //register entities
        ModEntity.registerAll();

        //add keybinds
        KeyBinds.registerAll();

    }
}