package fr.vana_mod.nicofighter45.rei;

import fr.vana_mod.nicofighter45.block.modifiertable.ModifierTableRegister;
import fr.vana_mod.nicofighter45.main.VanadiumMod;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ModifiersTableDisplay<R extends ModifiersDisplay> implements DisplayCategory<Display> {
    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModifierTableRegister.MODIFIERS_TABLE);
    }

    @Override
    public Text getTitle() {
        return MutableText.of(new TranslatableTextContent("rei_category.vana-mod.modifiers_table"));
    }

    @Override
    public CategoryIdentifier getCategoryIdentifier() {
        return CategoryIdentifier.of(new Identifier(VanadiumMod.MODID, "modifiers_table"));
    }

    @Override
    public List<Widget> setupDisplay(@NotNull Display display, @NotNull Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX(), bounds.getCenterY());
        List<Widget> widgets = new ArrayList<>();

        // The base background of the display
        // Please try to not remove this to preserve an uniform style to REI
        widgets.add(Widgets.createRecipeBase(bounds));

        // We create a result slot background AND disable the actual background of the slots, so the result slot can look bigger
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x, startPoint.y - 20)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x, startPoint.y - 20))
                .entries(display.getOutputEntries().get(0)) // Get the first output ingredient
                .disableBackground() // Disable the background because we have our bigger background
                .markOutput()); // Mark this as the output for REI to identify

        // We add the input slot
        widgets.add(Widgets.createSlot(new Point(startPoint.x - 60, startPoint.y))
                .entries(display.getInputEntries().get(0)) // Get the first input ingredient
                .markInput()); // Mark this as the input for REI to identify
        widgets.add(Widgets.createSlot(new Point(startPoint.x - 20, startPoint.y))
                .entries(display.getInputEntries().get(1)) // Get the first input ingredient
                .markInput()); // Mark this as the input for REI to identify
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 20, startPoint.y))
                .entries(display.getInputEntries().get(2)) // Get the first input ingredient
                .markInput()); // Mark this as the input for REI to identify
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 60, startPoint.y))
                .entries(display.getInputEntries().get(3)) // Get the first input ingredient
                .markInput()); // Mark this as the input for REI to identify

        // We return the list of widgets for REI to display
        return widgets;
    }

}