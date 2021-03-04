package vana_mod.nicofighter45.fr.main;

import vana_mod.nicofighter45.fr.database.DataBasePlayer;
import vana_mod.nicofighter45.fr.items.ModItems;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;

import java.util.Objects;

public class Listeners {

//    public static void blockBreakEventRegister(){
//        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
//            if (state.getBlock() == Blocks.WHEAT && player.getMainHandStack().getItem() == Items.NETHERITE_HOE){
//            }
//        });
//    }

    private static final Identifier id = new Identifier("right_click_event");

    public static void onItemRightClickRegister(){
        UseItemCallback.EVENT.register((player, world, hand) -> {
            Item itemb = player.inventory.getMainHandStack().getItem();
            if(itemb == ModItems.SIMPLE_HEALTH_BOOSTER || itemb == ModItems.BASE_HEALTH_BOOSTER || itemb == ModItems.ADVANCE_HEALTH_BOOSTER
                    || itemb == ModItems.SIMPLE_REGEN_BOOSTER || itemb == ModItems.BASE_REGEN_BOOSTER || itemb == ModItems.ADVANCE_REGEN_BOOSTER){

                if(world.isClient) {
                    ClientPlayNetworking.send(id, PacketByteBufs.empty());
                }else{
                    ServerPlayNetworking.registerGlobalReceiver(id,(server, server_player, serverPlayNetworkHandler, packetByteBuf, packetSender) -> {
                        server.execute(() -> {
                            ItemStack it = server_player.getMainHandStack();
                            Item item = it.getItem();
                            DataBasePlayer data_player = VanadiumModServer.dataBaseManager.getPlayer(server_player.getEntityName());
                            PlayerInventory inventory = server_player.inventory;
                            int heart = data_player.getHeart();
                            int regen = data_player.getRegen();
                            boolean give = false;
                            if(item == ModItems.SIMPLE_HEALTH_BOOSTER && heart < 20){
                                data_player.setHeart(heart + 2);
                                Objects.requireNonNull(server_player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(data_player.getHeart());
                                sendMsg(server_player, "You got " + (heart + 2)/2 + " heart");
                                give = true;
                            }else if(item == ModItems.BASE_HEALTH_BOOSTER && heart >= 20 && heart < 30){
                                data_player.setHeart(heart + 2);
                                Objects.requireNonNull(server_player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(data_player.getHeart());
                                sendMsg(server_player, "You got " + (heart + 2)/2 + " heart");
                                give = true;
                            }else if(item == ModItems.ADVANCE_HEALTH_BOOSTER && heart >= 30 && heart < 40){
                                data_player.setHeart(heart + 2);
                                Objects.requireNonNull(server_player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(data_player.getHeart());
                                sendMsg(server_player, "You got " + (heart + 2)/2 + " heart");
                                give = true;
                            }else if(item == ModItems.SIMPLE_REGEN_BOOSTER && regen < 8 && (regen+2) <= heart){
                                data_player.setRegen(regen + 2);
                                sendMsg(server_player, "You got " + (regen + 2)/2 + " regen");
                                give = true;
                            }else if(item == ModItems.BASE_REGEN_BOOSTER && regen >= 8 && regen < 16 && (regen+2) <= heart){
                                data_player.setRegen(regen + 2);
                                sendMsg(server_player, "You got " + (regen + 2)/2 + " regen");
                                give = true;
                            }else if(item == ModItems.ADVANCE_REGEN_BOOSTER && regen >= 16 && regen < 24 && (regen+2) <= heart){
                                data_player.setRegen(regen + 2);
                                sendMsg(server_player, "You got " + (regen + 2)/2 + " regen");
                                give = true;
                            }
                            if(give){
                                for(int slot = 0; slot<9; slot++){
                                    if(inventory.getStack(slot) == it){
                                        inventory.removeStack(slot, 1);
                                    }
                                }
                            }
                        });
                    });
                }
            }
            return TypedActionResult.pass(player.getMainHandStack());
        });
    }

    private static void sendMsg(ServerPlayerEntity player, String text){
        player.sendMessage(new TranslatableText(text), false);
    }

}