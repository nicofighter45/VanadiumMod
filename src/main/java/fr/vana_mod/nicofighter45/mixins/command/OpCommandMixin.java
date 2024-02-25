package fr.vana_mod.nicofighter45.mixins.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.command.OpCommand;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OpCommand.class)
public abstract class OpCommandMixin {

    @Inject(at=@At("HEAD"), method="register", cancellable = true)
    private static void register(@NotNull CommandDispatcher<ServerCommandSource> dispatcher, @NotNull CallbackInfo ci) {
        ci.cancel();
    }

}