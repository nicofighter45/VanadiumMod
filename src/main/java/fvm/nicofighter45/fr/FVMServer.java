package fvm.nicofighter45.fr;

import fvm.nicofighter45.fr.database.DataBaseManager;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;

public class FVMServer implements DedicatedServerModInitializer {

    //player who got less than regen heart
    public static Map<PlayerEntity, Integer> TickNumberForHeal = new HashMap<>();

    //database mysql
    public static DataBaseManager dataBaseManager;

    @Override
    public void onInitializeServer() {
        System.out.println("The Server Side is loading");
    }
}