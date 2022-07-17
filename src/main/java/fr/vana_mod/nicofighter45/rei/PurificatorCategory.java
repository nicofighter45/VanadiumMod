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
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PurificatorCategory implements DisplayCategory<PurificatorDisplay> {

    private static final Identifier TEXTURE = new Identifier(CommonInitializer.MODID, "textures/gui/rei/purificator.png");

    @Override
    public List<Widget> setupDisplay(@NotNull PurificatorDisplay display, @NotNull Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        Point startPoint = new Point(bounds.getCenterX(), bounds.getCenterY() - 1);
        widgets.add(Widgets.createTexturedWidget(TEXTURE, bounds));
        widgets.add(Widgets.createSlot(new Point(startPoint.getX() - 54, startPoint.getY() - 27)).entries(EntryIngredients.of(Items.WATER_BUCKET)).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.getX() - 26, startPoint.getY())).entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.getX() + 46, startPoint.getY())).entries(display.getOutputEntries().get(0)).markOutput());
        return widgets;
    }

    @Override
    public int getDisplayWidth(PurificatorDisplay display) {
        return 140;
    }

    @Override
    public int getDisplayHeight() {
        return 81;
    }

    @Override
    public CategoryIdentifier<? extends PurificatorDisplay> getCategoryIdentifier() {
        return REIClientPlugin.PURIFICATOR;
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