package fr.vana_mod.nicofighter45.mixins.anvil;

import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AnvilScreenHandler.class)
public interface AnvilScreenHandlerAccessor {

    @Accessor
    Property getLevelCost();

    @Accessor
    String getNewItemName();

    @Accessor("repairItemUsage")
    void setRepairItemUsage(int level);

}