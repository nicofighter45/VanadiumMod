package fr.vana_mod.nicofighter45.main.server;

import net.fabricmc.api.DedicatedServerModInitializer;

import java.util.*;

public class ServerInitializer implements DedicatedServerModInitializer {

    //custom player
    public static Map<UUID, CustomPlayer> players = new HashMap<>();
    public static boolean isOn = true;

    public static boolean jump = false;

    @Override
    public void onInitializeServer() {
        System.out.println("The Server Side is loading");
    }

}