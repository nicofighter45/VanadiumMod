package fr.vana_mod.nicofighter45.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.vana_mod.nicofighter45.main.CommonInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CustomPlayerManagementScreen extends HandledScreen<CustomPlayerManagementScreenHandler> {

    private static final Identifier TEXTURE_BACKGROUND = new Identifier(CommonInitializer.MODID, "textures/gui/player_manager.png");
    private static final Identifier EMPTY_HELMET_SLOT_TEXTURE = new Identifier("textures/item/empty_armor_slot_helmet");
    private static final Identifier EMPTY_CHESTPLATE_SLOT_TEXTURE = new Identifier("textures/item/empty_armor_slot_chestplate");
    private static final Identifier EMPTY_LEGGINGS_SLOT_TEXTURE = new Identifier("textures/item/empty_armor_slot_leggings");
    private static final Identifier EMPTY_BOOTS_SLOT_TEXTURE = new Identifier("textures/item/empty_armor_slot_boots");
    private static final Identifier EMPTY_OFFHAND_ARMOR_SLOT = new Identifier("textures/item/empty_armor_slot_shield");

    public CustomPlayerManagementScreen(CustomPlayerManagementScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(1, TEXTURE_BACKGROUND);
        RenderSystem.setShaderTexture(0, EMPTY_HELMET_SLOT_TEXTURE);
        backgroundHeight = 201;
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
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
        titleX = 102;
        titleY = -6;
        playerInventoryTitleX = 102;
        playerInventoryTitleY = 88;
    }

}