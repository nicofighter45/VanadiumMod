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

    public PurificatorScreen(PurificatorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE_BACKGROUND);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        textRenderer.draw(matrices, "water : " + getScreenHandler().getProperty(PurificatorBlockEntity.Properties.WATER), 0, 0, 65280);
        textRenderer.draw(matrices, "filling : " + getScreenHandler().getProperty(PurificatorBlockEntity.Properties.FILLING), 0, 10, 65280);
        textRenderer.draw(matrices, "crafting : " + getScreenHandler().getProperty(PurificatorBlockEntity.Properties.CRAFTING), 0, 20, 65280);
        renderBackground(matrices);
        int crafting = getScreenHandler().getProperty(PurificatorBlockEntity.Properties.CRAFTING);
        if (crafting > 0) {
            if(crafting > 89){
                drawTexture(matrices, x + 38, y + 70, 1, 201, (int) ((crafting-100)*-2.8), 1);
            }else if(crafting > 79){
                drawTexture(matrices, x + 65, y + 69, 1, 222, 1, 2*(crafting-90));
            }else{
                drawTexture(matrices, x + 65, y + 48, 1, 224, (int) (-0.4*(crafting-80)), 15);
            }
        }
        int fluid = getScreenHandler().getProperty(PurificatorBlockEntity.Properties.WATER) * 2/25;
        drawTexture(matrices, x + 12, y + 74, 1, 199, 24, -fluid);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        titleY += 8;
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        playerInventoryTitleX = backgroundWidth - textRenderer.getWidth(playerInventoryTitle) - 6;
    }

}