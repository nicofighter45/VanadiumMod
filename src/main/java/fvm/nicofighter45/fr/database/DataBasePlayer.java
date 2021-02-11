package fvm.nicofighter45.fr.database;

public class DataBasePlayer {

    private final String name;
    private int heart;
    private int regen;
    private float money;

    protected DataBasePlayer(String name, int heart, int regen, float money){
        this.name = name;
        this.heart = heart;
        this.regen = regen;
        this.money = money;
    }

    public String getName() {
        return name;
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

    public float getMoney() {
        return money;
    }

    public void addMoney(float money) {
        this.money += money;
    }
}