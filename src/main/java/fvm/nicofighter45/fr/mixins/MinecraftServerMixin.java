package fvm.nicofighter45.fr.mixins;

import fvm.nicofighter45.fr.FVM;
import fvm.nicofighter45.fr.database.DataBaseManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(at = @At("HEAD"), method = "startServer")
    private static <S extends MinecraftServer> void startServer(Function<Thread, S> serverFactory, CallbackInfoReturnable<S> cir) {
        //connect to data base
        FVM.dataBaseManager = new DataBaseManager();
        System.out.println("opening : loading data base");
    }

    @Inject(at = @At("HEAD"), method = "shutdown")
    protected void shutdown(CallbackInfo info) {
        FVM.dataBaseManager.save();
        System.out.println("closing : saving data base");
    }

}