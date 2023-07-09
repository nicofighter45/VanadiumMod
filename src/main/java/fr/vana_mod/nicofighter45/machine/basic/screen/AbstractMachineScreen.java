package fr.vana_mod.nicofighter45.machine.basic.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.vana_mod.nicofighter45.main.CommonInitializer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractMachineScreen<T extends AbstractMachineScreenHandler> extends HandledScreen<T> {

    protected final T handler;
    private final Identifier TEXTURE_BACKGROUND;

    private final int titleXDephase, titleYDephase, playerInventoryTitleXDephase, playerInventoryTitleYDephase;

    protected AbstractMachineScreen(T handler, PlayerInventory inventory, Text title, String name, int titleXDephase,
                                    int titleYDephase, int playerInventoryTitleXDephase, int playerInventoryTitleYDephase){
        super(handler, inventory, title);
        this.TEXTURE_BACKGROUND = new Identifier(CommonInitializer.MODID, "textures/gui/machines/" + name + ".png");
        this.handler = handler;
        this.titleXDephase = titleXDephase;
        this.titleYDephase = titleYDephase;
        this.playerInventoryTitleXDephase = playerInventoryTitleXDephase;
        this.playerInventoryTitleYDephase = playerInventoryTitleYDephase;
    }

    protected AbstractMachineScreen(T handler, PlayerInventory inventory, Text title, String name) {
        this(handler, inventory, title, name, 0, 0, 0, 0);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        drawBackground(context, delta, mouseX, mouseY);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    protected void draw(@NotNull DrawContext context, int x, int y, int u, int v, int width, int height){
        context.drawTexture(TEXTURE_BACKGROUND, x, y, u, v, width, height);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE_BACKGROUND);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE_BACKGROUND, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2 + titleXDephase;
        titleY += titleYDephase;
        playerInventoryTitleX = backgroundWidth - textRenderer.getWidth(playerInventoryTitle) + playerInventoryTitleXDephase;
        playerInventoryTitleY += playerInventoryTitleYDephase;
    }

}