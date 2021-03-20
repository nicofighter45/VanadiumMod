package vana_mod.nicofighter45.fr.main;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VanadiumModServer implements DedicatedServerModInitializer {

    //custom player
    public static Map<String, CustomPlayer> players = new HashMap<>();

    @Override
    public void onInitializeServer() { System.out.println("The Server Side is loading"); }

    public static void addNewPlayer(String name, UUID uuid){
        if(!players.containsKey(name)){
            players.put(name, new CustomPlayer(uuid, 10, 0));
        }
    }

}