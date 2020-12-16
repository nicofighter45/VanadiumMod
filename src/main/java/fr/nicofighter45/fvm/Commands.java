package fr.nicofighter45.fvm;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.inventory.Inventory;
// Import everything
import static net.minecraft.server.command.CommandManager.*;

public class Commands {

    public static void registerTestCommand(){
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(literal("spawn")
                    .executes(context -> {
                        System.out.println("it's working");
                        return 1;
                    })
            );
        });
    }
}