package fr.nicofighter45.fvm.database;

public class DataBasePlayer {

    private final int id;
    private final String name;
    private int hearts;
    private int regen;
    private float money;

    public DataBasePlayer(int id, String name) {
        this.id = id;
        this.name = name;
        this.hearts = 5;
        this.regen = 0;
        this.money = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHearts() {
        return hearts;
    }

    public void setHearts(int hearts) {
        this.hearts = hearts;
    }

    public int getRegen() {
        return regen;
    }

    public void setRegen(int regen) {
        this.regen = regen;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}