package fr.nicofighter45.fvm;

import net.fabricmc.api.ModInitializer;

public class FVM implements ModInitializer {

    public static final String MODID = "fvm";

    @Override
    public void onInitialize() {

        ModItems.registerAll();
        ModEnchants.registerAll();
        ModBlocks.registerAll();
        Listeners.blockBreakEventRegister();

    }
}