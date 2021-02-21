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

            dispatcher.register(literal("eco")
                    .requires(source -> source.hasPermissionLevel(2))
                    .executes(c -> {
                        sendMsg(c.getSource().getPlayer(), "Correct usage /eco <add|set|remove> <item>");
                        return 1;
                    })
                    .then(literal("add")
                            .executes(c -> {
                                sendMsg(c.getSource().getPlayer(), "Correct usage /eco add <item> <buyvalue> <sellvalue> <stock>");
                                return 1;
                            })
                            .then(argument("item", itemStack())
                                    .executes(c -> {
                                        sendMsg(c.getSource().getPlayer(), "Correct usage /eco add <item> <buyvalue> <sellvalue> <stock>");
                                        return 1;
                                    })
                                    .then(argument("buyvalue", floatArg(0))
                                            .executes(c -> {
                                                sendMsg(c.getSource().getPlayer(), "Correct usage /eco add <item> <buyvalue> <sellvalue> <stock>");
                                                return 1;
                                            })
                                            .then(argument("sellvalue", floatArg(0))
                                                    .executes(c -> {
                                                        sendMsg(c.getSource().getPlayer(), "Correct usage /eco add <item> <buyvalue> <sellvalue> <stock>");
                                                        return 1;
                                                    })
                                                    .then(argument("stock", integer(0))
                                                            .executes(c -> {
                                                                ServerPlayerEntity player = c.getSource().getPlayer();
                                                                Item item = getItemStackArgument(c, "item").getItem();
                                                                float buyvalue = getFloat(c, "buyvalue");
                                                                float sellvalue = getFloat(c, "sellvalue");
                                                                int stock = getInteger(c, "stock");
                                                                if(buyvalue <= sellvalue){
                                                                    sendMsg(player, "The buyvalue is inferior to the sellvalue");
                                                                    return 1;
                                                                }
                                                                if(stock > FVM.maxStockForItem){
                                                                    sendMsg(player, "The stock is superior to the max stock");
                                                                    return 1;
                                                                }
                                                                if (FVMServer.dataBaseManager.dataBaseItems.containsKey(item)){
                                                                    sendMsg(player, "This item is already register");
                                                                    return 1;
                                                                }
                                                                FVMServer.dataBaseManager.dataBaseItems.put(item, new DataBaseItem(item, sellvalue, buyvalue, stock));
                                                                sendMsg(player, "This item has been register");
                                                                return 1;
                                                            })
                                                    )
                                            )
                                    )
                            )
                    )
                    .then(literal("set")
                            .executes(c -> {
                                sendMsg(c.getSource().getPlayer(), "Correct usage /eco set <item> <buyvalue|sellvalue|stock> <value>");
                                return 1;
                            })
                            .then(argument("item", itemStack())
                                    .executes(c -> {
                                        sendMsg(c.getSource().getPlayer(), "Correct usage /eco set <item> <buyvalue|sellvalue|stock> <value>");
                                        return 1;
                                    })
                                    .then(literal("buyvalue")
                                            .executes(c -> {
                                                sendMsg(c.getSource().getPlayer(), "Correct usage /eco set <item> <buyvalue|sellvalue|stock> <value>");
                                                return 1;
                                            })
                                            .then(argument("value", floatArg(0))
                                                    .executes(c -> {
                                                        ServerPlayerEntity player = c.getSource().getPlayer();
                                                        Item item = getItemStackArgument(c, "item").getItem();
                                                        float value = getFloat(c, "value");
                                                        if (!FVMServer.dataBaseManager.dataBaseItems.containsKey(item)){
                                                            sendMsg(player, "Need a register item to change values");
                                                            return 1;
                                                        }
                                                        DataBaseItem dbitem = FVMServer.dataBaseManager.dataBaseItems.get(item);
                                                        dbitem.setBuyValue(value);
                                                        sendMsg(player, "The buyvalue of the item have been change");
                                                        return 1;
                                                    })
                                            )
                                    )
                                    .then(literal("sellvalue")
                                            .executes(c -> {
                                                sendMsg(c.getSource().getPlayer(), "Correct usage /eco set <item> <buyvalue|sellvalue|stock> <value>");
                                                return 1;
                                            })
                                            .then(argument("value", floatArg(0))
                                                    .executes(c -> {
                                                        ServerPlayerEntity player = c.getSource().getPlayer();
                                                        Item item = getItemStackArgument(c, "item").getItem();
                                                        float value = getFloat(c, "value");
                                                        if (!FVMServer.dataBaseManager.dataBaseItems.containsKey(item)){
                                                            sendMsg(player, "Need a register item to change values");
                                                            return 1;
                                                        }
                                                        DataBaseItem dbitem = FVMServer.dataBaseManager.dataBaseItems.get(item);
                                                        dbitem.setSellValue(value);
                                                        sendMsg(player, "The sellvalue of the item have been change");
                                                        return 1;
                                                    })
                                            )
                                    )
                                    .then(literal("stock")
                                            .executes(c -> {
                                                sendMsg(c.getSource().getPlayer(), "Correct usage /eco set <item> <buyvalue|sellvalue|stock> <value>");
                                                return 1;
                                            })
                                            .then(argument("value", integer(0))
                                                    .executes(c -> {
                                                        ServerPlayerEntity player = c.getSource().getPlayer();
                                                        Item item = getItemStackArgument(c, "item").getItem();
                                                        int value = getInteger(c, "value");
                                                        if(value > FVM.maxStockForItem){
                                                            sendMsg(player, "The stock is superior to the max stock");
                                                            return 1;
                                                        }
                                                        if (!FVMServer.dataBaseManager.dataBaseItems.containsKey(item)){
                                                            sendMsg(player, "Need a register item to change values");
                                                            return 1;
                                                        }
                                                        DataBaseItem dbitem = FVMServer.dataBaseManager.dataBaseItems.get(item);
                                                        dbitem.setStock(value);
                                                        sendMsg(player, "The stock of the item have been change");
                                                        return 1;
                                                    })
                                            )
                                    )
                            )
                    )
                    .then(literal("remove")
                            .executes(c -> {
                                sendMsg(c.getSource().getPlayer(), "Correct usage /eco remove <item>");
                                return 1;
                            })
                            .then(argument("item", itemStack())
                                    .executes(c -> {
                                        Item item = getItemStackArgument(c, "item").getItem();
                                        DataBaseItem dbitem = FVMServer.dataBaseManager.dataBaseItems.get(item);
                                        if(dbitem == null){
                                            sendMsg(c.getSource().getPlayer(), "This item isn't register in the database. Sorry :(");
                                        }else{
                                            FVMServer.dataBaseManager.dataBaseItems.remove(item);
                                            sendMsg(c.getSource().getPlayer(), "This item has been delete");
                                        }
                                        return 1;
                                    })
                            )
                    )
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
                                sendMsg(player, "He got " + FVMServer.dataBaseManager.getPlayer(getPlayer(c, "player").getEntityName()).getMoney());
                                return 1;
                            })
                    )
                    .executes(c -> {
                        ServerPlayerEntity player = c.getSource().getPlayer();
                        sendMsg(player, "You got " + FVMServer.dataBaseManager.getPlayer(player.getEntityName()).getMoney());
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
                                        DataBaseItem dbitem = FVMServer.dataBaseManager.dataBaseItems.get(item);
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
                                        if(FVMServer.dataBaseManager.dataBaseItems.get(getItemStackArgument(c, "item").getItem()) == null){
                                            sendMsg(c.getSource().getPlayer(), "This item isn't register in the database. Sorry :(");
                                        }else{
                                            Economic.buy(getItemStackArgument(c, "item").getItem(), 1, c.getSource().getPlayer());
                                        }
                                        return 1;
                                    })
                                    .then(argument("amount", integer(1))
                                        .executes(c -> {
                                            if(FVMServer.dataBaseManager.dataBaseItems.get(getItemStackArgument(c, "item").getItem()) == null){
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
                                        if(FVMServer.dataBaseManager.dataBaseItems.get(getItemStackArgument(c, "item").getItem()) == null){
                                            sendMsg(c.getSource().getPlayer(), "This item isn't register in the database. Sorry :(");
                                        }else{
                                            Economic.sell(getItemStackArgument(c, "item").getItem(), 1, c.getSource().getPlayer());
                                        }
                                        return 1;
                                    })
                                    .then(argument("amount", integer(1))
                                            .executes(c -> {
                                                if(FVMServer.dataBaseManager.dataBaseItems.get(getItemStackArgument(c, "item").getItem()) == null){
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
                                                if(FVMServer.dataBaseManager.dataBaseItems.get(getItemStackArgument(c, "item").getItem()) == null){
                                                    sendMsg(c.getSource().getPlayer(), "This item isn't register in the database. Sorry :(");
                                                }else{
                                                    Economic.buyInstant(getItemStackArgument(c, "item").getItem(), 1, c.getSource().getPlayer());
                                                }
                                                return 1;
                                            })
                                            .then(argument("amount", integer(1))
                                                    .executes(c -> {
                                                        if(FVMServer.dataBaseManager.dataBaseItems.get(getItemStackArgument(c, "item").getItem()) == null){
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
                                                if(FVMServer.dataBaseManager.dataBaseItems.get(getItemStackArgument(c, "item").getItem()) == null){
                                                    sendMsg(c.getSource().getPlayer(), "This item isn't register in the database. Sorry :(");
                                                }else{
                                                    Economic.sellInstant(getItemStackArgument(c, "item").getItem(), 1, c.getSource().getPlayer());
                                                }
                                                return 1;
                                            })
                                            .then(argument("amount", integer(1))
                                                    .executes(c -> {
                                                        if(FVMServer.dataBaseManager.dataBaseItems.get(getItemStackArgument(c, "item").getItem()) == null){
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