package fr.vana_mod.nicofighter45.main;

import java.util.UUID;

public class CustomPlayer {

    private final UUID uuid;
    private int heart, regen;
    private boolean craft, ender_chest;

    public CustomPlayer(UUID uuid, int heart, int regen, boolean craft, boolean ender_chest){
        this.uuid = uuid;
        this.heart = heart;
        this.regen = regen;
        this.craft = craft;
        this.ender_chest = ender_chest;
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

    public boolean isCraft() {
        return craft;
    }

    public void setCraft(boolean craft) {
        this.craft = craft;
    }

    public boolean isEnder_chest() {
        return ender_chest;
    }

    public void setEnder_chest(boolean ender_chest) {
        this.ender_chest = ender_chest;
    }
}