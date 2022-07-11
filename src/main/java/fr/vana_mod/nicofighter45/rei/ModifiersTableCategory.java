package fr.vana_mod.nicofighter45.rei;

import fr.vana_mod.nicofighter45.block.machine.ModMachines;
import fr.vana_mod.nicofighter45.main.CommonInitializer;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ModifiersTableCategory implements DisplayCategory<ModifiersTableDisplay> {

    private static final Identifier TEXTURE = new Identifier(CommonInitializer.MODID, "textures/gui/rei/modifiers_table.png");

    @Override
    public List<Widget> setupDisplay(@NotNull ModifiersTableDisplay display, @NotNull Rectangle bounds) {
        bounds.height = 122;
        bounds.width = 122;
        bounds.x += 13;
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        Point startPoint = new Point(bounds.getCenterX() - 8, bounds.getCenterY() - 8);
        widgets.add(Widgets.createTexturedWidget(TEXTURE, bounds));
        widgets.add(newWidgetEntry(startPoint, -36, -36, 0, display));
        widgets.add(newWidgetEntry(startPoint, 36, -36, 1, display));
        widgets.add(newWidgetEntry(startPoint, -36, 36, 2, display));
        widgets.add(newWidgetEntry(startPoint, 36, 36, 3, display));
        widgets.add(Widgets.createSlot(new Point(startPoint.getX(), startPoint.getY())).entries(display.getOutputEntries().get(0)).markOutput());

        return widgets;
    }

    private @NotNull Widget newWidgetEntry(@NotNull Point startPoint, int x, int y, int entry, @NotNull ModifiersTableDisplay display){
        return Widgets.createSlot(new Point(startPoint.getX() + x, startPoint.getY() + y)).entries(display.getInputEntries().get(entry)).markInput();
    }

    @Override
    public CategoryIdentifier<? extends ModifiersTableDisplay> getCategoryIdentifier() {
        return ClientPlugin.MODIFIERS_TABLE;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("rei.category.vana-mod.modifiers_table");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModMachines.MODIFIERS_TABLE_BLOCK);
    }
}