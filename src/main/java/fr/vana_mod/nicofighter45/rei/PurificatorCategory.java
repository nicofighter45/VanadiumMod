package fr.vana_mod.nicofighter45.rei;

import fr.vana_mod.nicofighter45.machine.ModMachines;
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

public class PurificatorCategory implements DisplayCategory<PurificatorDisplay> {

    private static final Identifier TEXTURE = new Identifier(CommonInitializer.MODID, "textures/gui/machines/purificator.png");

    @Override
    public List<Widget> setupDisplay(@NotNull PurificatorDisplay display, @NotNull Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        Point startPoint = new Point(bounds.getCenterX() - 8, bounds.getCenterY() - 8);
        widgets.add(Widgets.createTexturedWidget(TEXTURE, bounds));
        widgets.add(Widgets.createSlot(new Point(startPoint.getX() - 18, startPoint.getY())).entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.getX() + 18, startPoint.getY())).entries(display.getOutputEntries().get(0)).markOutput());
        return widgets;
    }

    @Override
    public int getDisplayWidth(PurificatorDisplay display) {
        return 122;
    }

    @Override
    public int getDisplayHeight() {
        return 122;
    }

    @Override
    public CategoryIdentifier<? extends PurificatorDisplay> getCategoryIdentifier() {
        return ClientPlugin.PURIFICATOR;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("rei.category.vana-mod.purificator");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModMachines.PURIFICATOR_BLOCK);
    }
}