package fvm.nicofighter45.fr.database;

import fvm.nicofighter45.fr.FVM;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

import java.util.HashMap;
import java.util.Map;

public class Economic {

    public static void pay(ServerPlayerEntity giver_player, ServerPlayerEntity receiver_player, float value){
        if(giver_player == receiver_player){
            sendMsg(giver_player, "You can't send money to yourself");
            //return;
        }
        DataBasePlayer giver = FVM.dataBaseManager.getPlayer(giver_player.getEntityName());
        if(giver.getMoney() >= value){
            DataBasePlayer receiver = FVM.dataBaseManager.getPlayer(receiver_player.getEntityName());
            receiver.addMoney(value);
            giver.addMoney(-value);
            sendMsg(giver_player, "Youg got " + giver.getMoney());
            sendMsg(receiver_player, "You got " + receiver.getMoney());
        }else{
            sendMsg(giver_player, "You don't have enough money");
        }
    }

    public static void buyInstant(Item item, Integer amount, ServerPlayerEntity player){
        DataBasePlayer dbplayer = FVM.dataBaseManager.getPlayer(player.getEntityName());
        DataBaseItem dbitem = FVM.dataBaseManager.dataBaseItems.get(item);
        float value = dbitem.getBuyValue() * amount;
        if(dbplayer.getMoney() >= value){
            if(player.inventory.insertStack(new ItemStack(item, amount))){
                dbplayer.addMoney(-value);
                sendMsg(player, "You got " + dbplayer.getMoney());
            }else{
                sendMsg(player, "You don't have enough space in your inventory");
            }
        }else{
            sendMsg(player, "You don't have enough money");
        }
    }

    public static void buy(Item item, Integer amount, ServerPlayerEntity player){
        DataBasePlayer dbplayer = FVM.dataBaseManager.getPlayer(player.getEntityName());
        DataBaseItem dbitem = FVM.dataBaseManager.dataBaseItems.get(item);
        if(dbitem.getStock() - amount < 0){
            sendMsg(player, "There isn't enough stock : " + dbitem.getStock() + "/1000");
            return;
        }
        float value = dbitem.getInstantValue() * amount;
        if(dbplayer.getMoney() >= value){
            if(player.inventory.insertStack(new ItemStack(item, amount))){
                dbplayer.addMoney(-value);
                sendMsg(player, "You got " + dbplayer.getMoney());
            }else{
                sendMsg(player, "You don't have enough space in your inventory");
            }
        }else{
            sendMsg(player, "You don't have enough money");
        }
    }

    public static void sell(Item item, Integer amount, ServerPlayerEntity player){
        DataBasePlayer dbplayer = FVM.dataBaseManager.getPlayer(player.getEntityName());
        DataBaseItem dbitem = FVM.dataBaseManager.dataBaseItems.get(item);
        if(dbitem.getStock() + amount > FVM.maxStockForItem){
            sendMsg(player, "There is too many stock : " + dbitem.getStock() + "/1000");
            return;
        }
        float value = dbitem.getInstantValue() * amount;
        deleting(player, item, amount, dbplayer, value);
    }

    public static void sellInstant(Item item, Integer amount, ServerPlayerEntity player){
        DataBasePlayer dbplayer = FVM.dataBaseManager.getPlayer(player.getEntityName());
        DataBaseItem dbitem = FVM.dataBaseManager.dataBaseItems.get(item);
        float value = dbitem.getSellValue() * amount;
        deleting(player, item, amount, dbplayer, value);
    }

    private static void deleting(ServerPlayerEntity player, Item item, int amount, DataBasePlayer dbplayer, float value){
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
            for(Integer slot : slots.keySet()){
                player.inventory.removeStack(slot, slots.get(slot));
            }
            dbplayer.addMoney(value);
            sendMsg(player, "You got " + dbplayer.getMoney());
        }else{
            sendMsg(player, "You don't have enough item in your inventory");
        }
    }

    private static void sendMsg(ServerPlayerEntity player, String text){
        player.sendMessage(new TranslatableText(text), false);
    }

}