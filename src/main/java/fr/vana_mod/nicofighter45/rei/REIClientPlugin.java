package fr.vana_mod.nicofighter45.rei;

import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.machine.high_furnace.recipe.HighFurnaceRecipe;
import fr.vana_mod.nicofighter45.machine.modifierstable.recipe.ModifiersRecipe;
import fr.vana_mod.nicofighter45.machine.purificator.recipe.PurificatorRecipe;
import fr.vana_mod.nicofighter45.main.CommonInitializer;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Objects;

public class REIClientPlugin implements me.shedaniel.rei.api.client.plugins.REIClientPlugin {

    public static final CategoryIdentifier<ModifiersTableDisplay> MODIFIERS_TABLE = CategoryIdentifier
            .of(Objects.requireNonNull(Identifier.of(CommonInitializer.MODID, "modifiers_table_category")));

    public static final CategoryIdentifier<PurificatorDisplay> PURIFICATOR = CategoryIdentifier
            .of(Objects.requireNonNull(Identifier.of(CommonInitializer.MODID, "purificator_category")));

    public static final CategoryIdentifier<HighFurnaceDisplay> HIGH_FURNACE = CategoryIdentifier
            .of(Objects.requireNonNull(Identifier.of(CommonInitializer.MODID, "high_furnace_category")));

    @Override
    public void registerCategories(@NotNull CategoryRegistry registry) {
        registry.add(new ModifiersTableCategory(), new PurificatorCategory(), new HighFurnaceCategory());
        registry.addWorkstations(MODIFIERS_TABLE, EntryStacks.of(ModMachines.MODIFIERS_TABLE_BLOCK));
        registry.addWorkstations(PURIFICATOR, EntryStacks.of(ModMachines.PURIFICATOR_BLOCK));
        registry.addWorkstations(HIGH_FURNACE, EntryStacks.of(ModMachines.HIGH_FURNACE_BLOCK));
    }

    @Override
    public void registerDisplays(@NotNull DisplayRegistry registry) {
        registry.registerFiller(ModifiersRecipe.class, recipe -> new ModifiersTableDisplay(EntryIngredients.ofIngredients(recipe.getIngredients()),
                Collections.singletonList(EntryIngredients.of(recipe.getOutput(DynamicRegistryManager.EMPTY))), recipe.id()));
        registry.registerFiller(PurificatorRecipe.class, recipe -> new PurificatorDisplay(EntryIngredients.ofIngredients(recipe.getIngredients()),
                Collections.singletonList(EntryIngredients.of(recipe.getOutput(DynamicRegistryManager.EMPTY))), recipe.id()));
        registry.registerFiller(HighFurnaceRecipe.class, recipe -> new HighFurnaceDisplay(EntryIngredients.ofIngredients(recipe.getIngredients()),
                Collections.singletonList(EntryIngredients.of(recipe.getOutput(DynamicRegistryManager.EMPTY))), recipe.id()));
    }

}