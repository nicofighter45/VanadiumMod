package vana_mod.nicofighter45.fr.bosses;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import vana_mod.nicofighter45.fr.items.ModItems;

public class CustomBossConfig {

    private final int key;
    private double x;
    private double y;
    private double z;
    private ItemStack drop;
    private Item toSpawn;

    public CustomBossConfig(int key, double x, double y, double z){
        this.key = key;
        this.x = x;
        this.y = y;
        this.z = z;
        switch(key){
            case 1:
                this.drop = new ItemStack(ModItems.STEAM_PLACK);
                this.toSpawn = Items.IRON_BLOCK;
            case 2:
                this.drop = new ItemStack(ModItems.STEAM_PLACK);
                this.toSpawn = Items.IRON_BLOCK;
            case 3:
                this.drop = new ItemStack(ModItems.STEAM_PLACK);
                this.toSpawn = Items.IRON_BLOCK;
            case 4:
                this.drop = new ItemStack(ModItems.STEAM_PLACK);
                this.toSpawn = Items.IRON_BLOCK;
            case 5:
                this.drop = new ItemStack(ModItems.STEAM_PLACK);
                this.toSpawn = Items.IRON_BLOCK;
        }
    }

    public ItemStack getDrop(){
        return drop;
    }

    public Item getToSpawn(){
        return toSpawn;
    }

    public int getKey() {
        return key;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}