package fr.vana_mod.nicofighter45.items.custom;

import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;

public class Excavator extends ShovelItem {
    private boolean isActive = true;

    public Excavator(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    public boolean isActive(){
        return this.isActive;
    }

    public boolean changeActivity(){
        this.isActive = !this.isActive;
        return this.isActive;
    }

}