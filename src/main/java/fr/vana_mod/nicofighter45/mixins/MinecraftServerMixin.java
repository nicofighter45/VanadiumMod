package fr.vana_mod.nicofighter45.mixins;

import fr.vana_mod.nicofighter45.main.server.ServerInitializer;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Calendar;
import java.util.function.BooleanSupplier;


@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    private final MinecraftServer server = (MinecraftServer) (Object) this;

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        if (server.getTicks() % 1200 == 0 && server.getTicks() != 0) {
            Calendar calendar = Calendar.getInstance();
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            if (hours == 0 && minutes < 15) {
                ServerInitializer.isOn = false;
                server.getPlayerManager().disconnectAllPlayers();
                server.setMotd("\\u00a76\\u00a7l                   Vanadium\\u00a76 v3\\u00a7r\\n\\u00a7c        Server \\u00a74Down \\u00a72- \\u00a7erestart at 8:00am");
            } else if (hours == 8 && minutes < 15) {
                ServerInitializer.isOn = true;
                server.setMotd("\\u00A76\\u00A7l                   Vanadium\\u00A76 v3\\u00A7r\\n\\u00A72                       \\u00A72\\u00A7nSurvival\\u00A77 [\\u00A7c1.19\\u00A77]\n");
            }
        }
    }

}