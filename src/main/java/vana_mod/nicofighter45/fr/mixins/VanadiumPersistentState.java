package vana_mod.nicofighter45.fr.mixins;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;

public class VanadiumPersistentState extends PersistentState {

    public VanadiumPersistentState(ServerWorld world) {
        super("Vanadium");
    }

    //read
    @Override
    public void fromTag(CompoundTag tag) {
        for(String key : tag.getKeys()){
            double nb = tag.getDouble(key);

        }
    }

    //write
    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putDouble("b1x", 0);
        tag.putDouble("b1y", 0);
        tag.putDouble("b1z", 0);

        tag.putDouble("b2x", 0);
        tag.putDouble("b2y", 0);
        tag.putDouble("b2z", 0);

        tag.putDouble("b3x", 0);
        tag.putDouble("b3y", 0);
        tag.putDouble("b3z", 0);

        tag.putDouble("b4x", 0);
        tag.putDouble("b4y", 0);
        tag.putDouble("b4z", 0);

        tag.putDouble("b5x", 0);
        tag.putDouble("b5y", 0);
        tag.putDouble("b5z", 0);
        return tag;
    }
}