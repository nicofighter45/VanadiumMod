package fr.vana_mod.nicofighter45.mixins;

import fr.vana_mod.nicofighter45.main.server.ServerInitializer;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Calendar;
import java.util.function.BooleanSupplier;


@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

    @Unique
    private final MinecraftServer server = (MinecraftServer) (Object) this;

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        if (server.getTicks() % 1200 == 0 && server.getTicks() != 0) {
            Calendar calendar = Calendar.getInstance();
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            if (hours == 0 && minutes < 15) {
                //ServerInitializer.isOn = false;
                //server.getPlayerManager().disconnectAllPlayers();
                server.setMotd("§6                Vanadium§5 Beta-§6v1.7§r\\u00a7c        Server \\u00a74Down \\u00a72- \\u00a7erestart at 8:00am");
            } else if (hours == 8 && minutes < 15) {
                ServerInitializer.isOn = true;
                server.setMotd("§6                Vanadium§5 Beta-§6v1.7§r\\n§2                  §2§nSurvival§r [§c1.20.1§r]");
            }
        }
    }

}