package fr.vana_mod.nicofighter45.main;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public enum HeartType {
    CONTAINER(0, false),
    NORMAL(2, true),
    POISONED(4, true),
    WITHERED(6, true),
    ABSORBING(8, false),
    FROZEN(9, false);

    private final int textureIndex;
    private final boolean hasBlinkingTexture;

    HeartType(int textureIndex, boolean hasBlinkingTexture) {
        this.textureIndex = textureIndex;
        this.hasBlinkingTexture = hasBlinkingTexture;
    }

    public static HeartType fromPlayerState(@NotNull PlayerEntity player) {
        HeartType heartType;
        if (player.hasStatusEffect(StatusEffects.POISON)) {
            heartType = POISONED;
        } else if (player.hasStatusEffect(StatusEffects.WITHER)) {
            heartType = WITHERED;
        } else if (player.isFrozen()) {
            heartType = FROZEN;
        } else {
            heartType = NORMAL;
        }

        return heartType;
    }

    public int getU(boolean halfHeart, boolean blinking) {
        int i;
        if (this == CONTAINER) {
            i = blinking ? 1 : 0;
        } else {
            int j = halfHeart ? 1 : 0;
            int k = this.hasBlinkingTexture && blinking ? 2 : 0;
            i = j + k;
        }

        return 16 + (this.textureIndex * 2 + i) * 9;
    }
}
