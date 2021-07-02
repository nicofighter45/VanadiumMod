package fr.vana_mod.nicofighter45.main;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import fr.vana_mod.nicofighter45.bosses.CustomBossConfig;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class Command {

    public static void registerAllCommands(){
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> dispatcher.register(literal("hat")
            .executes(c -> {
                ServerPlayerEntity player = c.getSource().getPlayer();
                ItemStack hand = player.getMainHandStack();
                if(hand.getItem() == Items.AIR){
                    ZombieEntity zb = EntityType.ZOMBIE.create(player.getServerWorld());
                    assert zb != null;
                    zb.refreshPositionAndAngles(player.getBlockPos(), 0, 0);
                    player.getServerWorld().spawnEntity(zb);
                    sendMsg(player, "You have nothing in your hand");
                    return 1;
                }
                if(hand.getCount() != 1){
                    sendMsg(player, "You can't put many items in your head");
                    return 1;
                }
                ItemStack helmet = player.getInventory().armor.get(3);
                player.getInventory().removeOne(hand);
                if(helmet.getItem() != Items.AIR){
                    player.getInventory().insertStack(helmet);
                }
                player.getInventory().insertStack(39, hand);
                sendMsg(player, "Your hand and your helmet have been exchange");
                return 1;
            })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> dispatcher.register(literal("boss")
            .requires(source -> source.hasPermissionLevel(2))
            .executes(c -> {
                ServerPlayerEntity player = c.getSource().getPlayer();
                sendMsg(player, "Correct usage /boss remove|set");
                sendMsg(player,"Here is the list of all bosses and there locations :");
                for(int key : VanadiumModServer.bosses.keySet()){
                    sendMsg(player, key + " : " + VanadiumModServer.bosses.get(key).getX() + ";" +
                            VanadiumModServer.bosses.get(key).getY() + ";" +  VanadiumModServer.bosses.get(key).getZ());
                }
                return 1;
            })
            .then(literal("remove")
                .executes(c -> {
                    sendMsg(c.getSource().getPlayer(), "Correct usage /boss remove <key>");
                    return 1;
                })
                    .then(argument("key", IntegerArgumentType.integer(1,5))
                            .executes(c -> {
                                ServerPlayerEntity player = c.getSource().getPlayer();
                                int key = IntegerArgumentType.getInteger(c,"key");
                                if(VanadiumModServer.bosses.containsKey(key)){
                                    VanadiumModServer.bosses.remove(key);
                                    sendMsg(player, "Boss with key " + key + " has been delete");
                                }else{
                                    sendMsg(player, "This boss isn't register");
                                }
                                return 1;
                            })
                    )
            )
            .then(literal("set")
                .executes(c -> {
                    sendMsg(c.getSource().getPlayer(), "Correct usage /boss set <key> (<x>) (<y>) (<z>)");
                    return 1;
                })

                    .then(argument("key", IntegerArgumentType.integer(1,5))
                            .executes(c -> {
                                ServerPlayerEntity player = c.getSource().getPlayer();
                                int key = IntegerArgumentType.getInteger(c,"key");
                                VanadiumModServer.bosses.remove(key);
                                VanadiumModServer.bosses.put(key, new CustomBossConfig(key, player.getX(), player.getY(), player.getZ()));
                                sendMsg(player, "Boss with key " + key + " set on your location");
                                return 1;
                            })
                            .then(argument("x", DoubleArgumentType.doubleArg())
                                    .executes(c -> {
                                        sendMsg(c.getSource().getPlayer(), "Correct usage /boss set <key> (<x>) (<y>) (<z>)");
                                        return 1;
                                    })
                                    .then(argument("y", DoubleArgumentType.doubleArg())
                                            .executes(c -> {
                                                sendMsg(c.getSource().getPlayer(), "Correct usage /boss set <key> (<x>) (<y>) (<z>)");
                                                return 1;
                                            })
                                            .then(argument("z", DoubleArgumentType.doubleArg())
                                                    .executes(c -> {
                                                        ServerPlayerEntity player = c.getSource().getPlayer();
                                                        int key = IntegerArgumentType.getInteger(c,"key");
                                                        double x = DoubleArgumentType.getDouble(c, "x");
                                                        double y = DoubleArgumentType.getDouble(c, "y");
                                                        double z = DoubleArgumentType.getDouble(c, "z");
                                                        VanadiumModServer.bosses.remove(key);
                                                        VanadiumModServer.bosses.put(key, new CustomBossConfig(key, x, y, z));
                                                        sendMsg(player, "Boss with key " + key + " set on " + x + ";" + y + ";" + z);
                                                        return 1;
                                                    })
                                            )
                                    )
                            )
                    )
            )
        ));
    }

    private static void sendMsg(ServerPlayerEntity player, String text){
        player.sendMessage(new TranslatableText(text), false);
    }

}