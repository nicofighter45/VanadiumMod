package fr.vana_mod.nicofighter45.main.server;

import fr.vana_mod.nicofighter45.main.CommonInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ServerInitializer implements DedicatedServerModInitializer {

    public static final String SERVER_MSG_PREFIX = "§8[§6Server§8] §f";
    public static final String BROADCAST_MSG_PREFIX = "§8[§4BROADCAST§8] §f";

    private static final Map<UUID, CustomPlayer> players = new HashMap<>();
    public static boolean isOn = true;
    public static boolean jump = false;

    @Override
    public void onInitializeServer() {
    }

    @Contract(pure = true)
    public static @NotNull Set<UUID> getPlayersUUID(){
        return players.keySet();
    }

    public static void setPlayerHearts(@NotNull ServerPlayerEntity player, int hearts){
        players.get(player.getUuid()).setHeart(hearts);
        sendInfoToClient(player);
    }

    public static void setPlayerRegen(@NotNull ServerPlayerEntity player, int regen){
        players.get(player.getUuid()).setRegen(regen);
        sendInfoToClient(player);
    }

    public static void setPlayerBase(@NotNull ServerPlayerEntity player, BlockPos base){
        players.get(player.getUuid()).setBase(base);
        sendInfoToClient(player);
    }

    public static @Nullable CustomPlayer getNullableCustomPlayer(UUID uuid){
        if(!players.containsKey(uuid)){
            return null;
        }
        return players.get(uuid);
    }

    public static CustomPlayer getCustomPlayer(UUID uuid){
        return players.get(uuid);
    }

    public static void newPlayer(@NotNull ServerPlayerEntity player){
        players.put(player.getUuid(), new CustomPlayer(10, 0, Objects.requireNonNull(player.getServer()).getOverworld().getSpawnPos()));
        sendInfoToClient(player);
    }

    public static void loadPlayer(@NotNull UUID uuid, CustomPlayer customPlayer){
        players.put(uuid, customPlayer);
    }

    private static void sendInfoToClient(@NotNull ServerPlayerEntity player){
        CustomPlayer customPlayer = getCustomPlayer(player.getUuid());
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(customPlayer.getHeart());
        buf.writeInt(customPlayer.getRegen());
        buf.writeBlockPos(customPlayer.getBase());
        ServerPlayNetworking.send(player, CommonInitializer.UPDATE_CUSTOM_PLAYER_PACKET, buf);
    }

}