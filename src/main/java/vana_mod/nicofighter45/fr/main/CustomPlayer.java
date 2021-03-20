package vana_mod.nicofighter45.fr.main;

import java.util.UUID;

public class CustomPlayer {

    private final UUID uuid;
    private int heart;
    private int regen;

    public CustomPlayer(UUID uuid, int heart, int regen){
        this.uuid = uuid;
        this.heart = heart;
        this.regen = regen;
    }

    public UUID getName() {
        return uuid;
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
}