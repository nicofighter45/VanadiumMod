package fr.vana_mod.nicofighter45.rei;

import fr.vana_mod.nicofighter45.block.modifiertable.craft.ModifiersRecipe;
import fr.vana_mod.nicofighter45.main.CommonInitializer;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;

import java.util.List;

public class ModifiersDisplay extends BasicDisplay {

    private final List<EntryIngredient> inputs, outputs;

    public ModifiersDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
        this.inputs = inputs;
        this.outputs = outputs;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return outputs;
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return inputs;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryIdentifier.of(CommonInitializer.MODID, ModifiersRecipe.Type.ID);
    }
}
