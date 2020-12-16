package fr.nicofighter45.fvm.items.armor.vanadium;

import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class ModifiableItem extends Item {

    private Map<Upgrades, Integer> upgradesList = new HashMap<>();

    public ModifiableItem(Settings settings) {
        super(settings);
    }

    public void addUpgrade(Upgrades upgrade, int value){
        if(upgrade.getMaxValue() <= value){
            upgradesList.put(upgrade, value);
        }
    }

    public void removeUpgrade(Upgrades upgrade){
        upgradesList.remove(upgrade);
    }

    public int getUpgradeValue(Upgrades upgrade){
        return upgradesList.get(upgrade);
    }

    public Map<Upgrades, Integer> getUpgrades(){ return upgradesList; }

}