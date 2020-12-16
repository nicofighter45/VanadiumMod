package fr.nicofighter45.fvm;

import fr.nicofighter45.fvm.block.modifiertable.ModifierTableRegister;
import fr.nicofighter45.fvm.block.modifiertable.ModifiersTableBlockEntity;
import fr.nicofighter45.fvm.block.modifiertable.ModifiersTableGuiDesciption;
import fr.nicofighter45.fvm.block.modifiertable.ModifiersTableBlock;
import fr.nicofighter45.fvm.block.ore.ModOres;
import fr.nicofighter45.fvm.items.ModItems;
import fr.nicofighter45.fvm.items.enchantment.ModEnchants;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FVM implements ModInitializer {

    //the mod id used to create new blocks and items
    public static final String MODID = "fvm";

    //screen handler for modifiers table
    public static ScreenHandlerType<ModifiersTableGuiDesciption> SCREEN_HANDLER_TYPE;

    @Override
    public void onInitialize() {

        //register screen for modifiers table
        SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(ModifiersTableBlock.ID, (syncId, inventory) ->
                new ModifiersTableGuiDesciption(syncId, inventory, ScreenHandlerContext.EMPTY));

        //register all items and blocks
        ModItems.registerAll();
        ModEnchants.registerAll();
        ModOres.registerAll();
        Listeners.blockBreakEventRegister();
        ModifierTableRegister.registerAll();

        //register commands (in dev)
        Commands.registerTestCommand();
    }
}