package fvm.nicofighter45.fr.mixins;

import fvm.nicofighter45.fr.FVM;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {

    @Inject(at = @At("HEAD"), method = "onPlayerConnected")
    public void onPlayerConnected(ServerPlayerEntity player, CallbackInfo info) {
        FVM.dataBaseManager.addNewPlayer(player.getEntityName());
        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(FVM.dataBaseManager.getPlayer(player.getEntityName()).getHeart());
        player.sendMessage(new TranslatableText("Welcome to Vanadium !"), true);
        player.sendMessage(new TranslatableText("Those are the command you must know :"), false);
        player.sendMessage(new TranslatableText("/shop : access to shop"), false);
        player.sendMessage(new TranslatableText("/db : active and desactive night vison"), false);
        player.sendMessage(new TranslatableText("/eco : change the economy"), false);
        player.sendMessage(new TranslatableText("/hat : put a item on your head"), false);
        player.sendMessage(new TranslatableText("/pay : pay someone"), false);
        player.sendMessage(new TranslatableText("/money : get your money"), false);
        player.sendMessage(new TranslatableText("VanadiumInformation").setStyle(Style.EMPTY.withBold(true).withClickEvent(new ClickEvent(ClickEvent.
                Action.OPEN_URL, "https://docs.google.com/spreadsheets/d/1vh1dLGMVESmx2GKTvGbl0EKRVkEmO_VJfQVVSrGwzA0/edit#gid=0"))), false);
        player.sendMessage(new TranslatableText("Have a nice day !"), false);
    }

    @Inject(at = @At("HEAD"), method = "onPlayerRespawned")
    public void onPlayerRespawned(ServerPlayerEntity player, CallbackInfo info) {
        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(FVM.dataBaseManager.getPlayer(player.getEntityName()).getHeart());
    }

}