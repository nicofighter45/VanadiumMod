package fr.nicofighter45.fvm.database;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataBaseManager {

    private Connection connection;
    private Statement statement;
    private DatabaseMetaData metadata;
    public Map<Integer, String> dataBasePlayers = new HashMap<>();
    public ResultSet resultSet;

    public DataBaseManager(){

        //connexion information
        final String url = "jdbc:mysql://localhost:3306/vanadium?serverTimeZone=UTC";
        final String userName = "root";
        final String password = "uiadf!895-sqd";

        try {

            //connect to database
            connection = DriverManager.getConnection(url,userName,password);
            statement = connection.createStatement();
            metadata = connection.getMetaData();
            statement = connection.createStatement();

            //log in vanadium database
            statement.executeQuery("use vanadium;");

            //check if the table players exist
            resultSet = metadata.getTables(null, null, "players", null);
            if(!resultSet.next()) {
                //if not create it
                statement.execute("create table players(id int auto_increment primary key not null,name char(16) not null,heart int default 10 not null,regen int default 0 not null,money int default 0 not null);");
                //add player me
                addNewPlayer("nicofighter45");
            }
            resultSet = statement.executeQuery("select * from players");
            while (resultSet.next()){
                int values = resultSet.getInt("id");
                System.out.println(values);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void SaveData(){
    }

    public int getMoney(String name){
        try {
            resultSet = statement.executeQuery("select * from players");
            while (resultSet.next()){
                if(resultSet.getString("name").equals(name)){
                    return resultSet.getInt("money");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getRegen(String name){
        try {
            resultSet = statement.executeQuery("select * from players");
            while (resultSet.next()){
                if(resultSet.getString("name").equals(name)){
                    return resultSet.getInt("regen");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getHeart(String name){
        try {
            resultSet = statement.executeQuery("select * from players");
            while (resultSet.next()){
                if(resultSet.getString("name").equals(name)){
                    return resultSet.getInt("heart");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getIdFromName(String name) {
        try {
            resultSet = statement.executeQuery("select * from players");
            while (resultSet.next()){
                if(resultSet.getString("name").equals(name)){
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void addPlayer(ServerPlayerEntity player){

        String name = player.getEntityName();
        System.out.println(name);
        int id = getIdFromName(name);
        if(id == 0){
            addNewPlayer(name);
        }
        player.getAttributeInstance
                (EntityAttributes.GENERIC_MAX_HEALTH)
                .setBaseValue
                        (getHeart(name));
    }

    private void addNewPlayer(String name){
        try {
            statement.execute("insert into players(name) value(\"" + name + "\");");
            dataBasePlayers.put(getIdFromName(name), name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}