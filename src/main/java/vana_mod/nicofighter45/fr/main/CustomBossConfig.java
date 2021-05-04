package vana_mod.nicofighter45.fr.main;

public class CustomBossConfig {

    private final String key;
    private double x;
    private double y;
    private double z;

    public CustomBossConfig(String key){
        this.key = key;
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public String getKey() {
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