package fr.vana_mod.nicofighter45.main.client;

import fr.vana_mod.nicofighter45.block.ModBlocks;
import fr.vana_mod.nicofighter45.block.enchanter.EnchanterScreen;
import fr.vana_mod.nicofighter45.block.modifiertable.ModifiersTableScreen;
import fr.vana_mod.nicofighter45.gui.CustomPlayerManagementScreen;
import fr.vana_mod.nicofighter45.items.ModItems;
import fr.vana_mod.nicofighter45.items.custom.VanadiumBow;
import net.fabricmc.api.ClientModInitializer;
import fr.vana_mod.nicofighter45.entity.ModEntity;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class ClientInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        //register client screens

        HandledScreens.register(ModBlocks.MODIFIERS_TABLE_SCREEN_HANDLER, ModifiersTableScreen::new);
        HandledScreens.register(ModBlocks.ENCHANTER_SCREEN_HANDLER, EnchanterScreen::new);
        HandledScreens.register(ModBlocks.CUSTOM_PLAYER_MANAGER_SCREEN_HANDLER, CustomPlayerManagementScreen::new);

        //register vanadium bow
        ModelPredicateProviderRegistry.register(ModItems.VANADIUM_BOW, new Identifier("pull"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0;
            }
            return entity.getActiveItem() != stack ? 0 : (stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / 20.0F;
        });

        ModelPredicateProviderRegistry.register(ModItems.VANADIUM_BOW, new Identifier("pulling"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0;
            }
            return entity.isUsingItem() && entity.getActiveItem() == stack ? 1 : 0;
        });

        ModelPredicateProviderRegistry.register(ModItems.VANADIUM_BOW, new Identifier("ender"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0;
            }
            if(entity.isUsingItem() && entity.getActiveItem() == stack && ((VanadiumBow) stack.getItem()).isEnderPearl()){
                return 1;
            }
            return 0;
        });

        //register vanadium elytra
        ModelPredicateProviderRegistry.register(ModItems.VANADIUM_ELYTRA, new Identifier("broken"), (stack, world, entity, seed) -> {
            if(entity == null){
                return 0;
            }

            if(entity.isUsingItem() && entity instanceof PlayerEntity){
                if(((PlayerEntity) entity).getInventory().getArmorStack(EquipmentSlot.CHEST.getEntitySlotId()) == stack){
                    if(stack.getDamage() == stack.getMaxDamage()){
                        return 1;
                    }
                }
            }
            return 0;
        });

        //register entities
        ModEntity.registerAll();

        //add keybinds
        KeyBinds.registerAll();

    }
}