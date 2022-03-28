package fr.vana_mod.nicofighter45.main;

import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;
import fr.vana_mod.nicofighter45.block.ModBlocks;
import fr.vana_mod.nicofighter45.block.modifiertable.*;
import fr.vana_mod.nicofighter45.block.modifiertable.craft.ModifiersCraft;
import fr.vana_mod.nicofighter45.block.modifiertable.craft.ModifiersRecipeSerializer;
import fr.vana_mod.nicofighter45.items.ModItems;
import fr.vana_mod.nicofighter45.items.enchantment.ModEnchants;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import java.util.*;

public class VanadiumMod implements ModInitializer {


    //the mod id used to create new blocks and items
    public static final String MODID = "vana-mod";

    //screen handler for modifiers table
    public static ScreenHandlerType<ModifiersTableGuiDescription> SCREEN_HANDLER_TYPE;

    @Override
    public void onInitialize() {
        //register all items and blocks
        registers();

        //register screen for modifiers table
        SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(ModifiersTableBlock.ID, (syncId, inventory) ->
                new ModifiersTableGuiDescription(syncId, inventory, ScreenHandlerContext.EMPTY)
        );

        Registry.register(Registry.RECIPE_SERIALIZER, ModifiersRecipeSerializer.ID, ModifiersRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(MODID, ModifiersCraft.Type.ID), ModifiersCraft.Type.INSTANCE);

        //register command
        Command.registerAllCommands();
    }

    private void registers() {
        ModItems.registerAll();
        ModEnchants.registerAll();
        ModBlocks.registerAll();
        Listeners.registerAll();
        ModifierTableRegister.registerAll();
    }

    //item group
    public static final ItemGroup VANADIUM_GROUP = FabricItemGroupBuilder.create(
            new Identifier(MODID, "vanadium"))
            .icon(() -> new ItemStack(ModItems.VANADIUM_INGOT))
            .build();

}