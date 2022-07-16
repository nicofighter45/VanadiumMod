package fr.vana_mod.nicofighter45.machine.purificator;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.vana_mod.nicofighter45.main.CommonInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PurificatorScreen extends HandledScreen<PurificatorScreenHandler> {

    private static final Identifier TEXTURE_BACKGROUND = new Identifier(CommonInitializer.MODID, "textures/gui/machines/purificator.png");
    private final PurificatorScreenHandler handler;

    public PurificatorScreen(PurificatorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.handler = handler;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE_BACKGROUND);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
        int crafting = handler.getCraftingTime();
        int filling = handler.getFillingTime();
        int water_pixel = handler.getWater() * 2/25;
        if (crafting > 0) {
            int crafting_pixel;
            if(crafting > 89){
                crafting_pixel = (int) (2.8 * (100-crafting));
                drawTexture(matrices, x + 37, y + 69, 2, 200, crafting_pixel, 1);
            }else if(crafting > 78){
                crafting_pixel = 2 * (89-crafting);
                drawTexture(matrices, x + 37, y + 69, 1, 200, 28, 1);
                drawTexture(matrices, x + 64, y + 69 - crafting_pixel, 1, 203, 1, crafting_pixel);
            }else{
                crafting_pixel = (int) (4.8 * (78 - crafting));
                drawTexture(matrices, x + 37, y + 69, 1, 200, 28, 1);
                drawTexture(matrices, x + 64, y + 50, 1, 203, 1, 20);
                drawTexture(matrices, x + 64, y + 39, 1, 223, crafting_pixel, 15);
            }
        }
        if(filling > 0){
            int filling_pixel;
            if(filling > 79){
                filling_pixel = (int) (2.1 * (100-filling));
                drawTexture(matrices, x + 17, y + 31, 34, 167, 14, filling_pixel);
            }else if(filling < 19){
                filling_pixel = 21/9 * (18-filling);
                drawTexture(matrices, x + 17, y + 31 + filling_pixel, 34, 167 + filling_pixel, 14, 42 - filling_pixel);
            }else{
                drawTexture(matrices, x + 17, y + 31, 34, 167, 14, 42);
            }
        }
        drawTexture(matrices, x + 12, y + 73 - water_pixel, 1, 199 - water_pixel, 24, water_pixel);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        drawBackground(matrices, delta, mouseX, mouseY);
        textRenderer.draw(matrices, Integer.toString(handler.getWater()), x - 12, y + 5, 0);
        textRenderer.draw(matrices, Integer.toString(handler.getFillingTime()), x - 12, y + 15, 0);
        textRenderer.draw(matrices, Integer.toString(handler.getCraftingTime()), x - 12, y + 25, 0);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        titleY += 8;
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        playerInventoryTitleX = backgroundWidth - textRenderer.getWidth(playerInventoryTitle) - 7;
    }

}