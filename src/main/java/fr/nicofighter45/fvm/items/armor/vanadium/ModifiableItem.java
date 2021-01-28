package fr.nicofighter45.fvm.items.armor.vanadium;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class ModifiableItem extends ArmorItem {

    private final Map<Upgrades, Integer> upgradesList = new HashMap<>();

    public ModifiableItem(ArmorMaterial material, EquipmentSlot slot, Item.Settings settings) {
        super(material, slot, settings);
        upgradesList.put(Upgrades.HAST, 0);
        upgradesList.put(Upgrades.STRENGTH, 0);
        upgradesList.put(Upgrades.RESISTANCE, 0);
        upgradesList.put(Upgrades.SPEED, 0);
        upgradesList.put(Upgrades.JUMP, 0);
        upgradesList.put(Upgrades.NO_FALL, 0);
    }

    public boolean addUpgrade(Upgrades upgrade){
        int value = getUpgradeValue(upgrade);
        if(upgrade.getMaxValue() < value){
            upgradesList.remove(upgrade);
            upgradesList.put(upgrade, value + 1);
            return true;
        }
        return false;
    }

    public int getUpgradeValue(Upgrades upgrade){
        return upgradesList.get(upgrade);
    }

    public Map<Upgrades, Integer> getUpgrades(){ return upgradesList; }

}