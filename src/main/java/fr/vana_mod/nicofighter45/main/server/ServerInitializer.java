package fr.vana_mod.nicofighter45.main.server;

import net.fabricmc.api.DedicatedServerModInitializer;

import java.util.*;

public class ServerInitializer implements DedicatedServerModInitializer {

    public static final String SERVER_MSG_PREFIX = "§8[§6Server§8] §f";
    public static final String BROADCAST_MSG_PREFIX = "§8[§4BROADCAST§8] §f";

    public static Map<UUID, CustomPlayer> players = new HashMap<>();
    public static boolean isOn = true;

    public static boolean jump = false;

    @Override
    public void onInitializeServer() {
        System.out.println("The Server Side is loading");
    }

}