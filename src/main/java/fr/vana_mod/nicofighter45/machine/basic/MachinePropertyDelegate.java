package fr.vana_mod.nicofighter45.machine.basic;

import net.minecraft.screen.PropertyDelegate;

public interface MachinePropertyDelegate extends PropertyDelegate {

    default void add(int index, int value){
        set(index, get(index) + value);
    }

}