package fr.vana_mod.nicofighter45.machine.purificator;

import fr.vana_mod.nicofighter45.machine.basic.screen.AbstractMachineScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class PurificatorScreen extends AbstractMachineScreen<PurificatorScreenHandler> {

    public PurificatorScreen(PurificatorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, "purificator");
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);
        int crafting = handler.getCraftingTime();
        int filling = handler.getFillingTime();
        int water_pixel = handler.getWater() * 2 / 25;
        if (crafting > 0) {
            int crafting_pixel;
            if (crafting > 89) {
                crafting_pixel = (int) (2.8 * (100 - crafting));
                drawTexture(matrices, x + 37, y + 69, 2, 200, crafting_pixel, 1);
            } else if (crafting > 78) {
                crafting_pixel = 2 * (89 - crafting);
                drawTexture(matrices, x + 37, y + 69, 1, 200, 28, 1);
                drawTexture(matrices, x + 64, y + 68 - crafting_pixel, 1, 202, 1, crafting_pixel);
            } else {
                crafting_pixel = (78 - crafting) * 24 / 39;
                drawTexture(matrices, x + 37, y + 69, 1, 200, 28, 1);
                drawTexture(matrices, x + 64, y + 49, 1, 202, 1, 20);
                drawTexture(matrices, x + 64, y + 39, 1, 223, crafting_pixel, 15);
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
        drawTexture(matrices, x + 12, y + 73 - water_pixel, 1, 199 - water_pixel, 24, water_pixel);
    }

    @Override
    protected void init() {
        super.init();
        titleY += 8;
        playerInventoryTitleX = backgroundWidth - textRenderer.getWidth(playerInventoryTitle) - 7;
    }

}