package fr.vana_mod.nicofighter45.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

public class HighFurnaceDisplay implements Display {

    private final List<EntryIngredient> inputs;
    private final List<EntryIngredient> outputs;

    private final Identifier recipeId;

    public HighFurnaceDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Identifier recipeId) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.recipeId = recipeId;
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return inputs;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return outputs;
    }

    @Override
    public CategoryIdentifier<HighFurnaceDisplay> getCategoryIdentifier() {
        return REIClientPlugin.HIGH_FURNACE;
    }

    @Override
    public Optional<Identifier> getDisplayLocation() {
        return Optional.ofNullable(this.recipeId);
    }

}