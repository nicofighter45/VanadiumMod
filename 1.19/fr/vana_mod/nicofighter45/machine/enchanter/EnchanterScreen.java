package fr.vana_mod.nicofighter45.machine.enchanter;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.vana_mod.nicofighter45.items.ModItems;
import fr.vana_mod.nicofighter45.main.CommonInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class EnchanterScreen extends HandledScreen<EnchanterScreenHandler> {

    private static final Identifier TEXTURE_BACKGROUND = new Identifier(CommonInitializer.MODID, "textures/gui/machines/enchanter.png");

    public EnchanterScreen(EnchanterScreenHandler handler, PlayerInventory inventory, Text title) {
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
        this.itemRenderer.renderInGuiWithOverrides(new ItemStack(ModItems.VANADIUM_NUGGET), x + 22, y + 50);
        this.itemRenderer.renderInGuiWithOverrides(new ItemStack(Items.LAPIS_LAZULI), x + 134, y + 50);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

}