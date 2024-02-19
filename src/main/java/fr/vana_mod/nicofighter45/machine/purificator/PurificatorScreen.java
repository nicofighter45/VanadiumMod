package fr.vana_mod.nicofighter45.machine.purificator;

import fr.vana_mod.nicofighter45.machine.basic.screen.AbstractMachineScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class PurificatorScreen extends AbstractMachineScreen<PurificatorScreenHandler> {

    public PurificatorScreen(PurificatorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, "purificator", 0, 8, -7, 0);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        super.drawBackground(context, delta, mouseX, mouseY);
        int crafting = handler.getCraftingTime();
        int filling = handler.getFillingTime();
        int water_pixel = handler.getWater() * 32 / PurificatorBlock.waterLevelTotal;
        if (crafting > 0) {
            int crafting_pixel;
            if (crafting >= PurificatorBlock.waterLevelToTransform * 0.9) {
                crafting_pixel = (int) (2.8 * (PurificatorBlock.waterLevelToTransform - crafting));
                draw(context, x + 37, y + 69, 2, 200, crafting_pixel, 1);
            }else {
                draw(context, x + 37, y + 69, 1, 200, 28, 1);
                if (crafting >= PurificatorBlock.waterLevelToTransform * 0.8) {
                    crafting_pixel = (int) (2 * (PurificatorBlock.waterLevelToTransform * 0.9 - crafting));
                    draw(context, x + 64, y + 69 - crafting_pixel, 1, 202, 1, crafting_pixel);
                }else{
                    draw(context, x + 64, y + 49, 1, 202, 1, 20);
                    addPixel(crafting, 79, context, 1, 231);
                    addPixel(crafting, 78, context, 3, 231);
                    addPixel(crafting, 77, context, 5, 231);
                    addPixel(crafting, 76, context, 7, 231);
                    addPixel(crafting, 75, context, 7, 229);
                    addPixel(crafting, 74, context, 7, 227);
                    addPixel(crafting, 73, context, 9, 227);
                    addPixel(crafting, 72, context, 11, 227);
                    addPixel(crafting, 71, context, 11, 229);
                    addPixel(crafting, 70, context, 11, 231);
                    addPixel(crafting, 69, context, 11, 233);
                    addPixel(crafting, 68, context, 13, 233);
                    addPixel(crafting, 67, context, 15, 233);
                    addPixel(crafting, 66, context, 15, 231);
                    addPixel(crafting, 65, context, 15, 229);
                    addPixel(crafting, 64, context, 15, 227);
                    addPixel(crafting, 63, context, 15, 225);
                    addPixel(crafting, 62, context, 17, 225);
                    addPixel(crafting, 61, context, 19, 225);
                    addPixel(crafting, 60, context, 19, 227);
                    addPixel(crafting, 59, context, 19, 229);
                    addPixel(crafting, 58, context, 19, 231);
                    addPixel(crafting, 57, context, 19, 233);
                    addPixel(crafting, 56, context, 19, 235);
                    addPixel(crafting, 55, context, 21, 235);
                    if(crafting <= 54 && crafting >= 26){
                        crafting_pixel = (54 - crafting)*5/6;
                        draw(context, x + 86, y + 54 - crafting_pixel, 23, 238 - crafting_pixel, 4, crafting_pixel);
                    }
                    if(crafting <= 25){
                        draw(context, x + 86, y + 39, 23, 223, 4, 15);
                    }
                    addPixel(crafting, 24, context, 28, 225);
                    addPixel(crafting, 23, context, 30, 225);
                    addPixel(crafting, 22, context, 30, 227);
                    addPixel(crafting, 21, context, 30, 229);
                    addPixel(crafting, 20, context, 30, 231);
                    addPixel(crafting, 19, context, 30, 233);
                    addPixel(crafting, 18, context, 30, 235);
                    addPixel(crafting, 17, context, 32, 235);
                    addPixel(crafting, 16, context, 34, 235);
                    addPixel(crafting, 15, context, 34, 233);
                    addPixel(crafting, 14, context, 34, 231);
                    addPixel(crafting, 13, context, 34, 229);
                    addPixel(crafting, 12, context, 34, 227);
                    addPixel(crafting, 11, context, 36, 227);
                    addPixel(crafting, 10, context, 38, 227);
                    addPixel(crafting, 9, context, 38, 229);
                    addPixel(crafting, 8, context, 38, 231);
                    addPixel(crafting, 7, context, 38, 233);
                    addPixel(crafting, 6, context, 40, 233);
                    addPixel(crafting, 5, context, 42, 233);
                    addPixel(crafting, 4, context, 42, 231);
                    addPixel(crafting, 3, context, 42, 229);
                    addPixel(crafting, 2, context, 44, 229);
                    addPixel(crafting, 1, context, 46, 229);
                    addPixel(crafting, 0, context, 48, 229);
                }
            }
        }
        if (filling > 0) {
            int filling_pixel;
            if (filling >= 800) {
                filling_pixel = (int) ((1000 - filling) * 42/200);
                draw(context, x + 17, y + 31, 34, 167, 14, filling_pixel);
            } else if (filling <= 200) {
                filling_pixel = (int) (filling * 42/200);
                draw(context, x + 17, y + 31 + 42 - filling_pixel, 34, 167 + 42 - filling_pixel, 14, filling_pixel);
            } else {
                draw(context, x + 17, y + 31, 34, 167, 14, 42);
            }
        }
        draw(context, x + 12, y + 73 - water_pixel, 1, 199 - water_pixel, 24, water_pixel);
    }

    private void addPixel(int crafting, int c, DrawContext context, int u, int v) {
        if(crafting <= c){
            draw(context, x + 64 + u - 1, y + 47 + v - 231, u, v, 1, 1);
        }
    }

}