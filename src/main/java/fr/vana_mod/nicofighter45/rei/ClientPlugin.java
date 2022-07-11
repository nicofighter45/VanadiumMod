package fr.vana_mod.nicofighter45.rei;

import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.machine.modifiertable.craft.ModifiersRecipe;
import fr.vana_mod.nicofighter45.main.CommonInitializer;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Objects;

public class ClientPlugin implements REIClientPlugin {

    public static final CategoryIdentifier<ModifiersTableDisplay> MODIFIERS_TABLE = CategoryIdentifier
            .of(Objects.requireNonNull(Identifier.of(CommonInitializer.MODID, "modifiers_table_category")));

    @Override
    public void registerCategories(@NotNull CategoryRegistry registry) {
        registry.add(new ModifiersTableCategory());
        registry.addWorkstations(MODIFIERS_TABLE, EntryStacks.of(ModMachines.MODIFIERS_TABLE_BLOCK));
    }

    @Override
    public void registerDisplays(@NotNull DisplayRegistry registry) {
        registry.registerFiller(ModifiersRecipe.class, recipe -> new ModifiersTableDisplay(EntryIngredients.ofIngredients(recipe.getIngredients()),
                Collections.singletonList(EntryIngredients.of(recipe.getOutput())), recipe.id()));
    }
}