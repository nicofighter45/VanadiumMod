package fr.vana_mod.nicofighter45.mixins;

import net.minecraft.server.OperatorList;
import net.minecraft.server.PlayerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerManager.class)
public interface PlayerManagerAccessor {

    @Accessor
    OperatorList getOps();

}