package fvm.nicofighter45.fr.database;

import net.minecraft.item.Item;

public class DataBaseItem {

    private final Item item;
    private float sellValue;
    private float buyValue;
    private int stock;

    public DataBaseItem(Item item, float sellValue, float buyValue, int stock) {
        this.item = item;
        this.sellValue = sellValue;
        this.buyValue = buyValue;
        this.stock = stock;
    }

    public Item getItem() {
        return item;
    }

    public float getSellValue() {
        return sellValue;
    }

    public void setSellValue(float sellValue) {
        this.sellValue = sellValue;
    }

    public float getBuyValue() {
        return buyValue;
    }

    public void setBuyValue(float buyValue) {
        this.buyValue = buyValue;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}