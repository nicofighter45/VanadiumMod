package fr.vana_mod.nicofighter45.main.server;

import net.minecraft.util.math.BlockPos;

public class CustomPlayer {

    private int heart, regen;
    private boolean craft, ender_chest;
    private BlockPos base;

    public CustomPlayer(int heart, int regen, boolean craft, boolean ender_chest, BlockPos base){
        this.heart = heart;
        this.regen = regen;
        this.craft = craft;
        this.ender_chest = ender_chest;
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

    public BlockPos getBase(){
        return this.base;
    }

    public void setBase(BlockPos base){
        this.base = base;
    }

}