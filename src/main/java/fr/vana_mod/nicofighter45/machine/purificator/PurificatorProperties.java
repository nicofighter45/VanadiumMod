package fr.vana_mod.nicofighter45.machine.purificator;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.screen.PropertyDelegate;

public class PurificatorProperties implements PropertyDelegate {

    private int craftTime, water, fillingTime;
    public boolean isCrafting = false;
    public boolean isFilling = false;

    public int getCraftTime(){
        return this.craftTime;
    }

    public int getFillingTime(){
        return this.fillingTime;
    }

    public int getWater(){
        return this.water;
    }

    public void init(int water, int craftTime, int fillingTime){
        this.water = water;
        this.craftTime = craftTime;
        this.fillingTime = fillingTime;
        updateAPI();
    }

    public void resetCraftTime(){
        this.craftTime = 0;
    }

    public void resetFillTime(){
        this.fillingTime = 0;
    }

    public void removeWater(){
        addWater(-1);
    }

    public void addWater(int value){
        this.water+= value;
        updateAPI();
    }

    public void addCraftTime(){
        this.craftTime+= 1;
    }

    public void addFillingTime(){
        this.fillingTime+= 1;
    }

    private void updateAPI(){
        this.fluidStorage.amount = this.water * 810;
    }

    @Override
    public int get(int index) {
        if (index == 1) {
            return this.craftTime;
        } else {
            return this.water;
        }
    }

    @Override
    public void set(int index, int value) {
        if (index == 1) {
            this.craftTime = value;
        } else {
            this.water = value;
            updateAPI();
        }
    }

    @Override
    public int size() {
        return 2;
    }

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return 4 * FluidConstants.BUCKET;
        }
    };

}