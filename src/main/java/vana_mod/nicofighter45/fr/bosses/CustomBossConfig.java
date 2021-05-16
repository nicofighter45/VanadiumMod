package vana_mod.nicofighter45.fr.bosses;

public class CustomBossConfig {

    private final int key;
    private double x;
    private double y;
    private double z;

    public CustomBossConfig(int key, double x, double y, double z){
        this.key = key;
        this.x = x;
        this.y = y;
        this.z = z;
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