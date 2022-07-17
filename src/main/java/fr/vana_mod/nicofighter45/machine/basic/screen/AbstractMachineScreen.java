package fr.vana_mod.nicofighter45.machine.basic.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.vana_mod.nicofighter45.main.CommonInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public abstract class AbstractMachineScreen<T extends AbstractMachineScreenHandler> extends HandledScreen<T> {

    private final Identifier TEXTURE_BACKGROUND;
    protected final T handler;

    protected AbstractMachineScreen(T handler, PlayerInventory inventory, Text title, String name) {
        super(handler, inventory, title);
        this.TEXTURE_BACKGROUND = new Identifier(CommonInitializer.MODID, "textures/gui/machines/" + name + ".png");
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
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        drawBackground(matrices, delta, mouseX, mouseY);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

}