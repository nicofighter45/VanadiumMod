package fr.vana_mod.nicofighter45.bosses;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import fr.vana_mod.nicofighter45.main.VanadiumModServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

interface WorldComponent extends ComponentV3{}

public class CustomComponent implements WorldComponent{

    private boolean active;
    private final World world;

    public CustomComponent(World world){
        this.world = world;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        for(String key : tag.getKeys()){
            if(key.contains("name")){
                int nb = Integer.parseInt(key.split("_")[0]);
                VanadiumModServer.bosses.put(tag.getInt(key), new CustomBossConfig(tag.getInt(key), tag.getDouble(nb + "_x"),
                        tag.getDouble(nb + "_y"), tag.getDouble(nb + "_z")));
            }else if (key.contains("spawned")){
                List<Integer> list = new ArrayList<>();
                for(String k : tag.getKeys()){
                    if (key.contains("spawned")){
                        list.add(tag.getInt(k));
                    }
                }
                if(!list.isEmpty()){
                    VanadiumModServer.bossesManagement.addAlreadySpawned(list);
                }
                break;
            }
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        if(Objects.requireNonNull(world.getServer()).getOverworld() == world){
            active = true;
        }
        if(active) {
            for (CustomBossConfig boss : VanadiumModServer.bosses.values()) {
                tag.putInt(boss.getKey() + "_name", boss.getKey());
                tag.putDouble(boss.getKey() + "_x", boss.getX());
                tag.putDouble(boss.getKey() + "_y", boss.getY());
                tag.putDouble(boss.getKey() + "_z", boss.getZ());
            }
            int bs = 1;
            for(int id : VanadiumModServer.bossesManagement.isSpawned()){
                tag.putInt(bs + "_spawned", id);
            }
        }
    }
}