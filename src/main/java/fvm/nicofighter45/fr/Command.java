package fvm.nicofighter45.fr;

import fvm.nicofighter45.fr.database.DataBaseItem;
import fvm.nicofighter45.fr.database.Economic;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

import static com.mojang.brigadier.arguments.FloatArgumentType.floatArg;
import static com.mojang.brigadier.arguments.FloatArgumentType.getFloat;
import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static net.minecraft.command.argument.EntityArgumentType.*;
import static net.minecraft.command.argument.ItemStackArgumentType.getItemStackArgument;
import static net.minecraft.command.argument.ItemStackArgumentType.itemStack;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class Command {

    public static void registerAllCommands(){
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {

            dispatcher.register(literal("hat")
                    .executes(c -> {
                        ServerPlayerEntity player = c.getSource().getPlayer();
                        ItemStack hand = player.getMainHandStack();
                        ItemStack helmet = player.inventory.armor.get(0);
                        player.inventory.armor.set(0, hand);
                        player.inventory.setStack(player.inventory.getSlotWithStack(hand), helmet);
                        sendMsg(c.getSource().getPlayer(), "Your hand and your helmet have been exchange");
                        return 1;
                    })
            );

            dispatcher.register(literal("eco")
                    .executes(c -> {
                        ServerPlayerEntity player = c.getSource().getPlayer();
                        sendMsg(c.getSource().getPlayer(), "Your hand and your helmet have been exchange");
                        return 1;
                    })
            );

            dispatcher.register(literal("pay")
                    .then(argument("player", player())
                            .executes(c -> {
                                sendMsg(c.getSource().getPlayer(), "Correct usage /pay <player> <value>");
                                return 1;
                            })
                            .then(argument("value", floatArg(0f))
                                    .executes(c -> {
                                        Economic.pay(c.getSource().getPlayer(), getPlayer(c, "player"), getFloat(c, "value"));
                                        return 1;
                                    })
                            )
                    )
                    .executes(c -> {
                        sendMsg(c.getSource().getPlayer(), "Correct usage /pay <player> <value>");
                        return 1;
                    })
            );

            dispatcher.register(literal("money")
                    .then(argument("player", player())
                            .executes(c -> {
                                ServerPlayerEntity player = c.getSource().getPlayer();
                                sendMsg(player, "He got " + FVM.dataBaseManager.getPlayer(getPlayer(c, "player").getEntityName()).getMoney());
                                return 1;
                            })
                    )
                    .executes(c -> {
                        ServerPlayerEntity player = c.getSource().getPlayer();
                        sendMsg(player, "You got " + FVM.dataBaseManager.getPlayer(player.getEntityName()).getMoney());
                        return 1;
                    })
            );

            dispatcher.register(literal("shop")
                    .then(literal("info")
                            .executes(c -> {
                                sendMsg(c.getSource().getPlayer(), "Correct usage /shop info <item>");
                                return 1;
                            })
                            .then(argument("item", itemStack())
                                    .executes(c -> {
                                        Item item = getItemStackArgument(c, "item").getItem();
                                        DataBaseItem dbitem = FVM.dataBaseManager.dataBaseItems.get(item);
                                        if(dbitem == null){
                                            sendMsg(c.getSource().getPlayer(), "This item isn't register in the database. Sorry :(");
                                        }else{
                                            sendMsg(c.getSource().getPlayer(), "Stock : " + dbitem.getStock() + "/1000");
                                            sendMsg(c.getSource().getPlayer(), "BuyValue : " + dbitem.getBuyValue());
                                            sendMsg(c.getSource().getPlayer(), "SellValue : " + dbitem.getSellValue());
                                            sendMsg(c.getSource().getPlayer(), "InstantValue : " + dbitem.getComplexValue());
                                        }
                                        return 1;
                                    })
                            )
                    )
                    .then(literal("buy")
                            .executes(c -> {
                                sendMsg(c.getSource().getPlayer(), "Correct usage /shop buy <item>");
                                return 1;
                            })
                            .then(argument("item", itemStack())
                                    .executes(c -> {
                                        if(FVM.dataBaseManager.dataBaseItems.get(getItemStackArgument(c, "item").getItem()) == null){
                                            sendMsg(c.getSource().getPlayer(), "This item isn't register in the database. Sorry :(");
                                        }else{
                                            Economic.buy(getItemStackArgument(c, "item").getItem(), 1, c.getSource().getPlayer());
                                        }
                                        return 1;
                                    })
                                    .then(argument("amount", integer(1))
                                        .executes(c -> {
                                            if(FVM.dataBaseManager.dataBaseItems.get(getItemStackArgument(c, "item").getItem()) == null){
                                                sendMsg(c.getSource().getPlayer(), "This item isn't register in the database. Sorry :(");
                                            }else{
                                                Economic.buy(getItemStackArgument(c, "item").getItem(), getInteger(c, "amount"), c.getSource().getPlayer());
                                            }
                                            return 1;
                                        })
                                    )
                            )
                    )
                    .then(literal("sell")
                            .executes(c -> {
                                sendMsg(c.getSource().getPlayer(), "Correct usage /shop sell <item>");
                                return 1;
                            })
                            .then(argument("item", itemStack())
                                    .executes(c -> {
                                        if(FVM.dataBaseManager.dataBaseItems.get(getItemStackArgument(c, "item").getItem()) == null){
                                            sendMsg(c.getSource().getPlayer(), "This item isn't register in the database. Sorry :(");
                                        }else{
                                            Economic.sell(getItemStackArgument(c, "item").getItem(), 1, c.getSource().getPlayer());
                                        }
                                        return 1;
                                    })
                                    .then(argument("amount", integer(1))
                                            .executes(c -> {
                                                if(FVM.dataBaseManager.dataBaseItems.get(getItemStackArgument(c, "item").getItem()) == null){
                                                    sendMsg(c.getSource().getPlayer(), "This item isn't register in the database. Sorry :(");
                                                }else{
                                                    Economic.sell(getItemStackArgument(c, "item").getItem(), getInteger(c, "amount"), c.getSource().getPlayer());
                                                }
                                                return 1;
                                            })
                                    )
                            )
                    )
                    .then(literal("instant")
                            .executes(c -> {
                                sendMsg(c.getSource().getPlayer(), "Correct usage /shop instant <buy/sell>");
                                return 1;
                            })
                            .then(literal("buy")
                                    .executes(c -> {
                                        sendMsg(c.getSource().getPlayer(), "Correct usage /shop instant buy <item>");
                                        return 1;
                                    })
                                    .then(argument("item", itemStack())
                                            .executes(c -> {
                                                if(FVM.dataBaseManager.dataBaseItems.get(getItemStackArgument(c, "item").getItem()) == null){
                                                    sendMsg(c.getSource().getPlayer(), "This item isn't register in the database. Sorry :(");
                                                }else{
                                                    Economic.buyInstant(getItemStackArgument(c, "item").getItem(), 1, c.getSource().getPlayer());
                                                }
                                                return 1;
                                            })
                                            .then(argument("amount", integer(1))
                                                    .executes(c -> {
                                                        if(FVM.dataBaseManager.dataBaseItems.get(getItemStackArgument(c, "item").getItem()) == null){
                                                            sendMsg(c.getSource().getPlayer(), "This item isn't register in the database. Sorry :(");
                                                        }else{
                                                            Economic.buyInstant(getItemStackArgument(c, "item").getItem(), getInteger(c, "amount"), c.getSource().getPlayer());
                                                        }
                                                        return 1;
                                                    })
                                            )
                                    )
                            )
                            .then(literal("sell")
                                    .executes(c -> {
                                        sendMsg(c.getSource().getPlayer(), "Correct usage /shop instant sell <item>");
                                        return 1;
                                    })
                                    .then(argument("item", itemStack())
                                            .executes(c -> {
                                                if(FVM.dataBaseManager.dataBaseItems.get(getItemStackArgument(c, "item").getItem()) == null){
                                                    sendMsg(c.getSource().getPlayer(), "This item isn't register in the database. Sorry :(");
                                                }else{
                                                    Economic.sellInstant(getItemStackArgument(c, "item").getItem(), 1, c.getSource().getPlayer());
                                                }
                                                return 1;
                                            })
                                            .then(argument("amount", integer(1))
                                                    .executes(c -> {
                                                        if(FVM.dataBaseManager.dataBaseItems.get(getItemStackArgument(c, "item").getItem()) == null){
                                                            sendMsg(c.getSource().getPlayer(), "This item isn't register in the database. Sorry :(");
                                                        }else{
                                                            Economic.sellInstant(getItemStackArgument(c, "item").getItem(), getInteger(c, "amount"), c.getSource().getPlayer());
                                                        }
                                                        return 1;
                                                    })
                                            )
                                    )
                            )
                    )
                    .executes(c -> {
                        sendMsg(c.getSource().getPlayer(), "Correct usage /shop <info/buy/sell/instant>");
                        return 1;
                    })
            );

            dispatcher.register(literal("nv")
                    .executes(c -> {
                        ServerPlayerEntity player = c.getSource().getPlayer();
                        if(player.hasStatusEffect(StatusEffects.NIGHT_VISION)){
                            player.removeStatusEffect(StatusEffects.NIGHT_VISION);
                            sendMsg(player, "Night vision désactivé");
                        }else{
                            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false, true));
                            sendMsg(player, "Night vision activité");
                        }
                        return 1;
                    })
            );

        });
    }

    private static void sendMsg(ServerPlayerEntity player, String text){
        player.sendMessage(new TranslatableText(text), false);
    }

}