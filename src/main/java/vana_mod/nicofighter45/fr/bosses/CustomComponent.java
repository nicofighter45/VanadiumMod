package vana_mod.nicofighter45.fr.bosses;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;

interface WorldComponent extends ComponentV3{
    boolean addNewBoss(int key, double x, double y, double z);
}

public class CustomComponent implements WorldComponent{

    public Map<Integer, CustomBossConfig> bosses = new HashMap<>();

    @Override
    public void readFromNbt(CompoundTag tag) {
        for(String key : tag.getKeys()){
            if(key.contains("name")){
                int nb = Integer.parseInt(key.split("_")[0]);
                addNewBoss(tag.getInt(key), tag.getDouble(nb + "_x"), tag.getDouble(nb + "_y"), tag.getDouble(nb + "_z"));
            }
        }
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        int nb = 1;
        for(CustomBossConfig boss : bosses.values()){
            tag.putInt(nb + "_name", boss.getKey());
            tag.putDouble(nb + "_x", boss.getX());
            tag.putDouble(nb + "_y", boss.getY());
            tag.putDouble(nb + "_z", boss.getZ());
        }
    }

    @Override
    public boolean addNewBoss(int key, double x, double y, double z){
        if(!bosses.containsKey(key)){
            bosses.put(key, new CustomBossConfig(key, simplify(x), simplify(y), simplify(z)));
            return true;
        }
        return false;
    }

    private static double simplify(double db){
        return (double) ((int) (db*1000)) /1000;
    }

}