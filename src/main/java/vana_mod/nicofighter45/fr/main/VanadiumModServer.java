package vana_mod.nicofighter45.fr.main;

import vana_mod.nicofighter45.fr.database.DataBaseManager;
import net.fabricmc.api.DedicatedServerModInitializer;

public class VanadiumModServer implements DedicatedServerModInitializer {

    //database mysql
    public static DataBaseManager dataBaseManager;

    @Override
    public void onInitializeServer() {
        System.out.println("The Server Side is loading");
    }
}