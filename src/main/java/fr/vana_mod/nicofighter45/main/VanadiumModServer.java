package fr.vana_mod.nicofighter45.main;

import net.fabricmc.api.DedicatedServerModInitializer;
import fr.vana_mod.nicofighter45.bosses.BossesManagement;
import fr.vana_mod.nicofighter45.bosses.CustomBossConfig;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VanadiumModServer implements DedicatedServerModInitializer {

    //custom player
    public static Map<UUID, CustomPlayer> players = new HashMap<>();
    public static BossesManagement bossesManagement;
    public static Map<Integer, CustomBossConfig> bosses = new HashMap<>();

    @Override
    public void onInitializeServer() {
        System.out.println("The Server Side is loading");
    }

}