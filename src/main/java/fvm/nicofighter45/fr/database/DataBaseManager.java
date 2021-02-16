package fvm.nicofighter45.fr.database;

import net.minecraft.item.Item;


import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DataBaseManager {

    public Map<String, DataBasePlayer> dataBasePlayers = new HashMap<>();
    public Map<Item, DataBaseItem> dataBaseItems = new HashMap<>();
    private final String url = "jdbc:mysql://localhost:3306/vanadium";
    private final String userName = "root";
    private final String password = "uiadf!895-sqd";

    public DataBaseManager(){
        try {

            //connect to database
            Connection connection = DriverManager.getConnection(url, userName, password);
            Statement statement = connection.createStatement();
            DatabaseMetaData metadata = connection.getMetaData();

            //log in vanadium database
            statement.executeQuery("use vanadium;");

            //check if the table players exist
            ResultSet result = metadata.getTables(null, null, "players", null);
            if(!result.next()) {
                //if not create it
                statement.execute("create table players(name char(16) not null,heart int default 10 not null,regen int default 0 not null,money int default 0 not null);");
            }else{
                result = statement.executeQuery("select * from players;");
                while(result.next()){
                    String name = result.getString("name");
                    dataBasePlayers.put(name, new DataBasePlayer(name, result.getInt("heart"), result.getInt("regen"), result.getFloat("money")));
                }
            }

            //check if the table shop exist
            result = metadata.getTables(null, null, "shop", null);
            if(!result.next()) {
                //if not create it
                statement.execute("create table shop(itemid int not null,sell float not null,buy float not null,stock int);");
            }else{
                result = statement.executeQuery("select * from shop;");
                while(result.next()){
                    Item item = Item.byRawId(result.getInt("itemid"));
                    dataBaseItems.put(item, new DataBaseItem(item, result.getFloat("sell"), result.getFloat("buy"), result.getInt("stock")));
                }
            }

            //delete the value in the database
            statement.execute("delete from players;");
            statement.execute("delete from shop;");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            Connection connection = DriverManager.getConnection(url,userName,password);
            Statement statement = connection.createStatement();
            statement.executeQuery("select * from players;");
            for(String name : dataBasePlayers.keySet()){
                DataBasePlayer player = dataBasePlayers.get(name);
                statement.execute("insert into players(name,heart,regen,money) value(\"" + name + "\"," + player.getHeart() +
                        "," + player.getRegen() + "," + player.getMoney() + ");");
            }
            statement.executeQuery("select * from shop;");
            for(Item item : dataBaseItems.keySet()){
                DataBaseItem data_item = dataBaseItems.get(item);
                statement.execute("insert into shop(itemid,sell,buy,stock) value(" + Item.getRawId(item) + "," + data_item.getSellValue() +
                        "," + data_item.getBuyValue() + "," + data_item.getStock() + ");");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewPlayer(String name){
        if(!dataBasePlayers.containsKey(name)){
            dataBasePlayers.put(name, new DataBasePlayer(name, 10, 0, 100));
        }
    }

    public DataBasePlayer getPlayer(String name){
        return dataBasePlayers.get(name);
    }

}