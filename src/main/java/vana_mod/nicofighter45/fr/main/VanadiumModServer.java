package vana_mod.nicofighter45.fr.main;

import net.fabricmc.api.DedicatedServerModInitializer;
import vana_mod.nicofighter45.fr.bosses.BossesManagement;
import vana_mod.nicofighter45.fr.bosses.CustomBossConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VanadiumModServer implements DedicatedServerModInitializer {

    //custom player
    public static Map<String, CustomPlayer> players = new HashMap<>();
    public static BossesManagement bossesManagement;
    public static Map<Integer, CustomBossConfig> bosses = new HashMap<>();

    @Override
    public void onInitializeServer() {
        System.out.println("The Server Side is loading");
    }

    public static void addNewPlayer(String name, UUID uuid){
        if(!players.containsKey(name)){
            players.put(name, new CustomPlayer(uuid, 10, 0));
        }
    }
}