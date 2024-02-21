package fr.vana_mod.nicofighter45.main.client;

import fr.vana_mod.nicofighter45.entity.ModEntity;
import fr.vana_mod.nicofighter45.items.ModItems;
import fr.vana_mod.nicofighter45.items.custom.VanadiumBow;
import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.machine.enchanter.EnchanterScreen;
import fr.vana_mod.nicofighter45.machine.high_furnace.HighFurnaceScreen;
import fr.vana_mod.nicofighter45.machine.modifierstable.ModifiersTableScreen;
import fr.vana_mod.nicofighter45.machine.purificator.PurificatorScreen;
import fr.vana_mod.nicofighter45.main.CommonInitializer;
import fr.vana_mod.nicofighter45.main.gui.CustomPlayerManagementScreen;
import fr.vana_mod.nicofighter45.main.server.CustomPlayer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class ClientInitializer implements ClientModInitializer {

    public static CustomPlayer customPlayer;

    @Override
    public void onInitializeClient() {

        ClientPlayNetworking.registerGlobalReceiver(CommonInitializer.UPDATE_CUSTOM_PLAYER_PACKET, ClientInitializer::receiveCustomPlayerFromServer);

        //register client screens

        HandledScreens.register(ModMachines.MODIFIERS_TABLE_SCREEN_HANDLER, ModifiersTableScreen::new);
        HandledScreens.register(ModMachines.ENCHANTER_SCREEN_HANDLER, EnchanterScreen::new);
        HandledScreens.register(ModMachines.CUSTOM_PLAYER_MANAGER_SCREEN_HANDLER, CustomPlayerManagementScreen::new);
        HandledScreens.register(ModMachines.HIGH_FURNACE_SCREEN_HANDLER, HighFurnaceScreen::new);
        HandledScreens.register(ModMachines.PURIFICATOR_SCREEN_HANDLER, PurificatorScreen::new);

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
            if (entity.isUsingItem() && entity.getActiveItem() == stack && ((VanadiumBow) stack.getItem()).isEnderPearl()) {
                return 1;
            }
            return 0;
        });

        //register vanadium elytra
        ModelPredicateProviderRegistry.register(ModItems.VANADIUM_ELYTRA, new Identifier("broken"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0;
            }

            if (entity.isUsingItem() && entity instanceof PlayerEntity) {
                if (((PlayerEntity) entity).getInventory().getArmorStack(EquipmentSlot.CHEST.getEntitySlotId()) == stack) {
                    if (stack.getDamage() == stack.getMaxDamage()) {
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

    public static void receiveCustomPlayerFromServer(@NotNull MinecraftClient client, ClientPlayNetworkHandler handler, @NotNull PacketByteBuf buf, PacketSender responseSender){
        buf.retain();
        client.execute(() -> {
            customPlayer = new CustomPlayer(buf.readInt(), buf.readInt(), buf.readBlockPos());
            buf.release();
        });
    }

}