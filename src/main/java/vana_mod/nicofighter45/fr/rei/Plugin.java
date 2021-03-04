package vana_mod.nicofighter45.fr.rei;

import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeHelper;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import net.minecraft.util.Identifier;
import vana_mod.nicofighter45.fr.block.modifiertable.ModifierTableRegister;
import vana_mod.nicofighter45.fr.block.modifiertable.craft.ModifiersRecipeSerializer;
import vana_mod.nicofighter45.fr.main.VanadiumMod;

public class Plugin implements REIPluginV0 {
    @Override
    public Identifier getPluginIdentifier() {
        return new Identifier(VanadiumMod.MODID, "rei_plugin");
    }
    @Override
    public void registerOthers(RecipeHelper recipeHelper) {
        recipeHelper.registerWorkingStations(ModifiersRecipeSerializer.ID, EntryStack.create(ModifierTableRegister.MODIFIERS_TABLE_ITEM));
    }
}