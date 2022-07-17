package fr.vana_mod.nicofighter45.machine.basic.properties;

public class ArrayMachinePropertyDelegate implements MachinePropertyDelegate{

    private final int[] data;

    public ArrayMachinePropertyDelegate(int size){
        this.data = new int[size];
    }

    @Override
    public int get(int index) {
        return this.data[index];
    }

    @Override
    public void set(int index, int value) {
        this.data[index] = value;
    }

    @Override
    public int size() {
        return this.data.length;
    }
}