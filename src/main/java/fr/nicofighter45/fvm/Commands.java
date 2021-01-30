package fr.nicofighter45.fvm;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import fr.nicofighter45.fvm.items.ModItems;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
// Import everything
import java.util.Objects;

import static net.minecraft.server.command.CommandManager.*;

public class Commands {

    public static void registerTestCommand(){
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(literal("health_scale")
                    .then(CommandManager.argument("value", IntegerArgumentType.integer(0, 40)))
                        .executes(context -> {
                            int value = IntegerArgumentType.getInteger(context,"value");
                            ServerPlayerEntity player = context.getSource().getPlayer();
                            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(value);
                            return 1;
                        })
            );
        });
    }
}