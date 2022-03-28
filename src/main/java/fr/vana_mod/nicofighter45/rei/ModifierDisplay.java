package fr.vana_mod.nicofighter45.rei;

import fr.vana_mod.nicofighter45.block.modifiertable.ModifierCraft;
import fr.vana_mod.nicofighter45.block.modifiertable.ModifierTableRegister;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ModifierDisplay extends BasicDisplay {

    public ModifierDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        List<EntryIngredient> list = new ArrayList<>();
        for(ItemStack item : ModifierTableRegister.crafts.values()){
            list.add(EntryIngredient.of(EntryStacks.of(item)));
        }
        return list;
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        List<EntryIngredient> list = new ArrayList<>();
        for(ModifierCraft craft : ModifierTableRegister.crafts.keySet()){
            list.add(EntryIngredient.of(EntryStacks.of(craft.getItem0()), EntryStacks.of(craft.getItem1()), EntryStacks.of(craft.getItem2()), EntryStacks.of(craft.getItem3())));
            System.out.println("Test working " + craft.getItem0());
        }
        return list;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return null;
    }
}
