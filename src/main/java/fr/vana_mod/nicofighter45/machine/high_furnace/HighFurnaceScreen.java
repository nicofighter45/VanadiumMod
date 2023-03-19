package fr.vana_mod.nicofighter45.machine.high_furnace;

import fr.vana_mod.nicofighter45.machine.basic.screen.AbstractMachineScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class HighFurnaceScreen extends AbstractMachineScreen<HighFurnaceScreenHandler> {

    public HighFurnaceScreen(HighFurnaceScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, "high_furnace", 0, 8, -7, 0);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);
        int crafting = handler.getCraftingTime();
        int filling = handler.getFillingTime();
        int lava_pixel = handler.getLava() * 2 / 25;
        if (crafting > 0) {
            int crafting_pixel;
            if (crafting > 89) {
                crafting_pixel = (int) (3.8 * (100 - crafting));
                drawTexture(matrices, x + 37, y + 69, 2, 210, crafting_pixel, 1);
            } else if (crafting > 78) {
                crafting_pixel = (int) (2.9 * (89 - crafting));
                drawTexture(matrices, x + 37, y + 69, 1, 210, 38, 1);
                drawTexture(matrices, x + 74, y + 69 - crafting_pixel, 49, 196 - crafting_pixel, 1, crafting_pixel);
            } else {
                crafting_pixel = (78 - crafting) * 50 / 78;
                drawTexture(matrices, x + 37, y + 69, 1, 210, 38, 1);
                drawTexture(matrices, x + 74, y + 40, 49, 167, 1, 29);
                drawTexture(matrices, x + 63, y + 37, 51, 167, crafting_pixel, 20);
            }
        }
        if (filling > 0) {
            int filling_pixel;
            if (filling > 79) {
                filling_pixel = (int) (2.1 * (100 - filling));
                drawTexture(matrices, x + 17, y + 31, 34, 167, 14, filling_pixel);
            } else if (filling < 19) {
                filling_pixel = 21 / 9 * (18 - filling);
                drawTexture(matrices, x + 17, y + 31 + filling_pixel, 34, 167 + filling_pixel, 14, 42 - filling_pixel);
            } else {
                drawTexture(matrices, x + 17, y + 31, 34, 167, 14, 42);
            }
        }
        drawTexture(matrices, x + 12, y + 73 - lava_pixel, 1, 199 - lava_pixel, 24, lava_pixel);
    }

}