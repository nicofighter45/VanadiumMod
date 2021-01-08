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