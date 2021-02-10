package fvm.nicofighter45.fr.block.modifiertable;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;

public class ModifierCraft {

    private final Item item0;
    private final Item item1;
    private final Item item2;
    private final Item item3;

    private boolean item0b = true;
    private boolean item1b = true;
    private boolean item2b = true;
    private boolean item3b = true;


    public ModifierCraft(Item item0, Item item1, Item item2, Item item3) {
        if(item0 == Items.AIR){
            item0b = false;
        }
        if(item1 == Items.AIR){
            item1b = false;
        }if(item2 == Items.AIR){
            item2b = false;
        }
        if(item3 == Items.AIR){
            item3b = false;
        }
        this.item0 = item0;
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
    }

    public Item getItem0() {
        return item0;
    }

    public Item getItem1() {
        return item1;
    }

    public Item getItem2() {
        return item2;
    }

    public Item getItem3() {
        return item3;
    }

    public List<Integer> getNeededItem(){
        List<Integer> list = new ArrayList<>();
        if(item0b){
            list.add(0);
        }
        if(item1b){
            list.add(1);
        }
        if(item2b){
            list.add(2);
        }
        if(item3b){
            list.add(3);
        }
        return list;
    }
}