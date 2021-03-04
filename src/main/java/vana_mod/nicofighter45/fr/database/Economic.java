package vana_mod.nicofighter45.fr.database;

import vana_mod.nicofighter45.fr.main.VanadiumMod;
import vana_mod.nicofighter45.fr.main.VanadiumModServer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

import java.util.HashMap;
import java.util.Map;

public class Economic {

    //mettre la commision pour chaque item échangé

    public static void pay(ServerPlayerEntity giver_player, ServerPlayerEntity receiver_player, float value){
        if(giver_player == receiver_player){
            sendMsg(giver_player, "You can't send money to yourself");
            return;
        }
        DataBasePlayer giver = VanadiumModServer.dataBaseManager.getPlayer(giver_player.getEntityName());
        if(giver.getMoney() >= value){
            DataBasePlayer receiver = VanadiumModServer.dataBaseManager.getPlayer(receiver_player.getEntityName());
            receiver.addMoney(value);
            giver.addMoney(-value);
            sendMsg(giver_player, "You got " + giver.getMoney());
            sendMsg(receiver_player, "You got " + receiver.getMoney());
        }else{
            sendMsg(giver_player, "You don't have enough money");
        }
    }

    public static void buyInstant(Item item, Integer amount, ServerPlayerEntity player){
        DataBasePlayer dbplayer = VanadiumModServer.dataBaseManager.getPlayer(player.getEntityName());
        DataBaseItem dbitem = VanadiumModServer.dataBaseManager.dataBaseItems.get(item);
        float value = dbitem.getBuyValue() * amount;
        if(dbplayer.getMoney() >= value){
            if(player.inventory.insertStack(new ItemStack(item, amount))){
                dbplayer.addMoney(-value*(1+ VanadiumMod.commissionValue));
                sendMsg(player, "You got " + dbplayer.getMoney());
                sendMsg(player, "You buy " + amount);
            }else{
                sendMsg(player, "You don't have enough space in your inventory");
            }
        }else{
            sendMsg(player, "You don't have enough money");
        }
    }

    public static void buy(Item item, Integer amount, ServerPlayerEntity player){
        DataBasePlayer dbplayer = VanadiumModServer.dataBaseManager.getPlayer(player.getEntityName());
        DataBaseItem dbitem = VanadiumModServer.dataBaseManager.dataBaseItems.get(item);
        if(dbitem.getStock() - amount < 0){
            sendMsg(player, "There isn't enough stock : " + dbitem.getStock() + "/" + VanadiumMod.maxStockForItem + " so you will buy in instant not complex");
            buyInstant(item, amount, player);
            return;
        }
        for(int i = 1; i <= amount; i++){
            if(dbplayer.getMoney() >= dbitem.getComplexValue()){
                if(player.inventory.insertStack(new ItemStack(item, 1))){
                    dbplayer.addMoney(-dbitem.getComplexValue()*(1+ VanadiumMod.commissionValue));
                    dbitem.addStock(-1);
                }else{
                    sendMsg(player, "You buy " + i);
                    sendMsg(player, "You got " + dbplayer.getMoney());
                    return;
                }
            }else{
                sendMsg(player, "You buy " + i);
                sendMsg(player, "You got " + dbplayer.getMoney());
                return;
            }
        }
        sendMsg(player, "You buy " + amount);
        sendMsg(player, "You got " + dbplayer.getMoney());
    }

    public static void sell(Item item, Integer amount, ServerPlayerEntity player){
        DataBasePlayer dbplayer = VanadiumModServer.dataBaseManager.getPlayer(player.getEntityName());
        DataBaseItem dbitem = VanadiumModServer.dataBaseManager.dataBaseItems.get(item);
        if(dbitem.getStock() + amount > VanadiumMod.maxStockForItem){
            sendMsg(player, "There is too many stock : " + dbitem.getStock() + "/"  + VanadiumMod.maxStockForItem + " so you will sell on instant");
            sellInstant(item, amount, player);
            return;
        }
        if(deleting(player, item, amount, dbplayer, false)){
            dbitem.addStock(amount);
        }
    }

    public static void sellInstant(Item item, Integer amount, ServerPlayerEntity player){
        DataBasePlayer dbplayer = VanadiumModServer.dataBaseManager.getPlayer(player.getEntityName());
        DataBaseItem dbitem = VanadiumModServer.dataBaseManager.dataBaseItems.get(item);
        deleting(player, item, amount, dbplayer, true);
    }

    private static boolean deleting(ServerPlayerEntity player, Item item, int amount, DataBasePlayer dbplayer, boolean instant){
        int number = 0;
        Map<Integer, Integer> slots = new HashMap<>();
        for(int i = 0; i < 36; i++){
            if(player.inventory.getStack(i).getItem() == item){
                int n = player.inventory.getStack(i).getCount();
                number+=n;
                slots.put(i, n);
            }
        }
        if(number >= amount){
            int delete = 0;
            float money = 0;
            DataBaseItem dbitem = VanadiumModServer.dataBaseManager.dataBaseItems.get(item);
            for(Integer slot : slots.keySet()){
                while(amount > delete){
                    player.inventory.removeStack(slot, 1);
                    delete++;
                    if(instant){
                        money+= dbitem.getBuyValue();
                    }else{
                        money+= dbitem.getComplexValue();
                    }
                    if(player.inventory.getStack(slot).getItem() != item){
                        break;
                    }
                }
            }
            dbplayer.addMoney(money*(1- VanadiumMod.commissionValue));
            sendMsg(player, "You got " + dbplayer.getMoney());
            sendMsg(player, "You sell " + amount);
            return true;
        }else{
            sendMsg(player, "You don't have enough item in your inventory");
        }
        return false;
    }

    private static void sendMsg(ServerPlayerEntity player, String text){
        player.sendMessage(new TranslatableText(text), false);
    }

}