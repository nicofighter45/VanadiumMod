package fr.vana_mod.nicofighter45.bosses;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import fr.vana_mod.nicofighter45.block.ModBlocksItem;
import fr.vana_mod.nicofighter45.items.ModItems;

public class CustomBossConfig {

    private final int key;
    private final double x;
    private final double y;
    private final double z;
    private ItemStack drop;
    private Item toSpawn;

    public CustomBossConfig(int key, double x, double y, double z){
        this.key = key;
        this.x = simplify(x);
        this.y = simplify(y);
        this.z = simplify(z);
        switch (key) {
            case 1 -> {
                this.drop = new ItemStack(ModItems.STEEL_PLATE);
                this.toSpawn = Items.IRON_BLOCK;
            }
            case 2 -> {
                this.drop = new ItemStack(ModItems.DIAMOND_PLATE);
                this.toSpawn = ModBlocksItem.STEEL_BLOCK_ITEM;
            }
            case 3 -> {
                this.drop = new ItemStack(ModItems.EMERALD_PLATE);
                this.toSpawn = Items.DIAMOND_BLOCK;
            }
            case 4 -> {
                this.drop = new ItemStack(ModItems.VANADIUM_PLATE);
                this.toSpawn = Items.EMERALD_BLOCK;
            }
            case 5 -> {
                this.drop = new ItemStack(ModItems.VANADIUM_CHESTPLATE);
                this.toSpawn = ModBlocksItem.VANADIUM_BLOCK_ITEM;
            }
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

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    private static double simplify(double db){
        return (double) ((int) (db*1000)) /1000;
    }
}