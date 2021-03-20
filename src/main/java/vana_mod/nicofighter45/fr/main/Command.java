package vana_mod.nicofighter45.fr.main;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

import static net.minecraft.server.command.CommandManager.literal;

public class Command {

    public static void registerAllCommands(){
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(literal("hat")
                    .executes(c -> {
                        ServerPlayerEntity player = c.getSource().getPlayer();
                        ItemStack hand = player.getMainHandStack();
                        if(hand.getItem() == Items.AIR){
                            sendMsg(player, "You have nothing in your hand");
                            return 1;
                        }
                        if(hand.getCount() != 1){
                            sendMsg(player, "You can't put many items in your head");
                            return 1;
                        }
                        ItemStack helmet = player.inventory.armor.get(3);
                        player.inventory.removeOne(hand);
                        if(helmet.getItem() != Items.AIR){
                            player.inventory.insertStack(helmet);
                        }
                        player.inventory.insertStack(39, hand);
                        sendMsg(player, "Your hand and your helmet have been exchange");
                        return 1;
                    })
            );
        });
    }

    private static void sendMsg(ServerPlayerEntity player, String text){
        player.sendMessage(new TranslatableText(text), false);
    }

}