package fr.vana_mod.nicofighter45.rei;

import fr.vana_mod.nicofighter45.block.ModBlocks;
import fr.vana_mod.nicofighter45.main.VanadiumMod;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;


public class ReiPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(@NotNull CategoryRegistry registry) {
        registry.add(new ModifiersTableDisplay<>());
        registry.addWorkstations(CategoryIdentifier.of(new Identifier(VanadiumMod.MODID, "modifiers_table")), EntryStacks.of(ModBlocks.MODIFIERS_TABLE_BLOCK));
    }

    @Override
    public void registerEntries(@NotNull EntryRegistry registry) {
        registry.addEntries();
    }

    @Override
    public void registerDisplays(@NotNull DisplayRegistry registry) {
        registry.add(ModifiersDisplay.class);
    }
}