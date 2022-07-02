package fr.vana_mod.nicofighter45.main;

import fr.vana_mod.nicofighter45.block.enchanter.EnchanterScreenHandler;
import fr.vana_mod.nicofighter45.block.modifiertable.craft.ModifiersRecipe;
import fr.vana_mod.nicofighter45.block.modifiertable.craft.ModifiersRecipeSerializer;
import fr.vana_mod.nicofighter45.block.modifiertable.ModifiersTableScreenHandler;
import fr.vana_mod.nicofighter45.main.server.Command;
import net.minecraft.item.*;
import fr.vana_mod.nicofighter45.block.ModBlocks;
import fr.vana_mod.nicofighter45.items.ModItems;
import fr.vana_mod.nicofighter45.items.enchantment.ModEnchants;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CommonInitializer implements ModInitializer {


    //the mod id used to create new blocks and items
    public static final String MODID = "vana-mod";

    @Override
    public void onInitialize() {
        //register all items and blocks
        ModItems.registerAll();
        ModEnchants.registerAll();
        ModBlocks.registerAll();
        Listeners.registerAll();

        //register command
        Command.registerAllCommands();
    }

    //item group
    public static final ItemGroup VANADIUM_GROUP = FabricItemGroupBuilder.create(new Identifier(MODID, "vanadium"))
            .icon(() -> new ItemStack(ModItems.VANADIUM_INGOT)).build();

}