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
    public Map<Integer, DataBasePlayer> dataBasePlayers = new HashMap<>();
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
                statement.execute("create table players(id int auto_increment primary key not null,name char(16) not null,heart int default 5 not null,regen int default 5 not null,money int default 0 not null);");
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

    public void addPlayer(ServerPlayerEntity player){

        String name = player.getName().toString();
        int id = getIdFromName(name);
        DataBasePlayer dataBasePlayer;
        if(id == 0){
            dataBasePlayer = addNewPlayer(name);
        }else{
            dataBasePlayer = dataBasePlayers.get(id);
        }
        assert dataBasePlayer != null;
        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(dataBasePlayer.getHearts());
    }

    private DataBasePlayer addNewPlayer(String name){
        try {
            statement.execute("insert into players(name) value(\"" + name + "\");");
            int id = getIdFromName("name");
            DataBasePlayer player = new DataBasePlayer(id, name);
            dataBasePlayers.put(id, player);
            return player;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

}