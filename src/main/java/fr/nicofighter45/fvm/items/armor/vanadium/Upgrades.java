package fr.nicofighter45.fvm.items.armor.vanadium;

public enum Upgrades {

    HAST("hast", 3),
    STRENGTH("strength", 2),
    RESISTANCE("resistance", 2),
    SPEED("speed", 5),
    JUMP("jump", 5),
    NO_FALL("no fall", 1);

    private final String name;
    private final int maxValue;

    Upgrades(String name, int maxvalue) {
        this.name = name;
        this.maxValue = maxvalue;
    }

    public String getName(){return name; }

    public int getMaxValue(){return maxValue; }


}