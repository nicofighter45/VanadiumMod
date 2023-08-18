package fr.vana_mod.nicofighter45.mixins;

import fr.vana_mod.nicofighter45.main.HeartType;
import fr.vana_mod.nicofighter45.main.server.ServerInitializer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    private static final Identifier ICONS = new Identifier("textures/gui/icons.png");
    private final Random random = Random.create();

    @Inject(at = @At("HEAD"), method = "renderHealthBar", cancellable = true)
    private void renderHealthBar(DrawContext context, @NotNull PlayerEntity player, int x, int y, int lines,
                                 int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption,
                                 boolean blinking, @NotNull CallbackInfo info) {
        int regenHeart = ServerInitializer.players.get(player.getUuid()).getRegen() / 2;
        HeartType heartType = HeartType.fromPlayerState(player);
        int i = player.getWorld().getLevelProperties().isHardcore() ? 45 : 0;
        int j = MathHelper.ceil((double) maxHealth / 2.0);
        int k = MathHelper.ceil((double) absorption / 2.0);
        int l = j * 2;
        int hardcore = player.getWorld().getLevelProperties().isHardcore() ? 27 : 0;

        for (int m = j + k - 1; m >= 0; --m) {
            int n = m / 10;
            int o = m % 10;
            int p = x + o * 8;
            int q = y - n * lines;
            if (lastHealth + absorption <= 4) {
                q += random.nextInt(2);
            }
            if (m < j && m == regeneratingHeartIndex) {
                q -= 2;
            }

            if (m < regenHeart) {
                drawRegenHeart(context, p, q, 0, hardcore);
            } else {
                drawHeart(context, HeartType.CONTAINER, p, q, i, blinking, false);
            }

            int r = m * 2;
            boolean bl = m >= j;
            if (bl) {
                int s = r - l;
                if (s < absorption) {
                    boolean bl2 = s + 1 == absorption;
                    drawHeart(context, heartType == HeartType.WITHERED ? heartType : HeartType.ABSORBING, p, q, i, false, bl2);
                }
            }

            boolean bl3;
            if (blinking && r < health) {
                bl3 = r + 1 == health;
                if (m < regenHeart) {
                    drawRegenHeart(context, p, q, 18, hardcore);
                } else {
                    drawHeart(context, heartType, p, q, i, true, bl3);
                }
            }

            if (r < lastHealth) {
                bl3 = r + 1 == lastHealth;
                if (m < regenHeart) {
                    drawRegenHeart(context, p, q, 9, hardcore);
                } else {
                    drawHeart(context, heartType, p, q, i, false, bl3);
                }
            }
        }
        info.cancel();
    }

    private void drawHeart(@NotNull DrawContext context, @NotNull HeartType type, int x, int y, int v, boolean blinking, boolean halfHeart) {
        context.drawTexture(ICONS, x, y, type.getU(halfHeart, blinking), v, 9, 9);
    }

    private void drawRegenHeart(@NotNull DrawContext context, int x, int y, int i, int j) {
        context.drawTexture(ICONS, x, y, 70 + i + j, 18, 9, 9);
    }

}