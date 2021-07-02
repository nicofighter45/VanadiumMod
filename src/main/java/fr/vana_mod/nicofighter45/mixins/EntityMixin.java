package fr.vana_mod.nicofighter45.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import fr.vana_mod.nicofighter45.main.VanadiumModServer;

@Mixin(Entity.class)
public class EntityMixin {

    private final Entity entity = (Entity) (Object) this;

    @Inject(at = @At("HEAD"), method = "updateKilledAdvancementCriterion")
    public void updateKilledAdvancementCriterion(Entity killer, int score, DamageSource damageSource, CallbackInfo info) {
        if(entity.getName().toString().contains("§6Boss 1")){
            VanadiumModServer.bossesManagement.despawnBosses(1);
        }
    }

}
