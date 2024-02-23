package fr.vana_mod.nicofighter45.main.server;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CustomPlayer {

    private int heart, regen;
    private BlockPos base;

    public CustomPlayer(int heart, int regen, BlockPos base) {
        this.heart = heart;
        this.regen = regen;
        this.base = base;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public int getRegen() {
        return regen;
    }

    public void setRegen(int regen) {
        this.regen = regen;
    }

    public BlockPos getBase() {
        return this.base;
    }

    public void setBase(BlockPos base) {
        this.base = base;
    }

    @Contract("_ -> new")
    public static @NotNull CustomPlayer defaultPlayer(MinecraftServer server){
        if(server != null){
            return new CustomPlayer(10, 0, server.getOverworld().getSpawnPos());
        }
        return new CustomPlayer(10, 0, new BlockPos(0, 70, 0));
    }

}