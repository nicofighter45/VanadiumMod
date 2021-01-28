package fr.nicofighter45.fvm.items.armor.vanadium;

import net.minecraft.item.Item;

public class UpgradeItem extends Item {

    private final Upgrades upgrade_type;

    public UpgradeItem(Upgrades upgrade_type, Settings settings) {
        super(settings);
        this.upgrade_type = upgrade_type;
    }

    public Upgrades getUpgrade_type() {
        return upgrade_type;
    }
}