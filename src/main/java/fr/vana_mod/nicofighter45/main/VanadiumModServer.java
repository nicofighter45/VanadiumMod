package fr.vana_mod.nicofighter45.main;

import net.fabricmc.api.DedicatedServerModInitializer;
import fr.vana_mod.nicofighter45.bosses.BossesManagement;
import fr.vana_mod.nicofighter45.bosses.CustomBossConfig;

import java.util.*;

public class VanadiumModServer implements DedicatedServerModInitializer {

    //custom player
    public static Map<UUID, CustomPlayer> players = new HashMap<>();
    public static BossesManagement bossesManagement;
    public static Map<Integer, CustomBossConfig> bosses = new HashMap<>();
    public static boolean isOn = true;

    public static List<UUID> tpPlayer = new ArrayList<>();

    public static boolean jump = false;

    public static List<UUID> freezePlayer = new ArrayList<>();

    @Override
    public void onInitializeServer() {
        System.out.println("The Server Side is loading");
    }

}