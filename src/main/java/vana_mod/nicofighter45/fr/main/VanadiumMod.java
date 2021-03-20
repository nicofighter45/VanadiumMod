package vana_mod.nicofighter45.fr.main;

import net.minecraft.util.registry.Registry;
import vana_mod.nicofighter45.fr.block.ModBlocks;
import vana_mod.nicofighter45.fr.block.ModBlocksItem;
import vana_mod.nicofighter45.fr.block.modifiertable.*;
import vana_mod.nicofighter45.fr.block.modifiertable.craft.ModifiersCraft;
import vana_mod.nicofighter45.fr.block.modifiertable.craft.ModifiersRecipeSerializer;
import vana_mod.nicofighter45.fr.entity.ModEntity;
import vana_mod.nicofighter45.fr.items.ModItems;
import vana_mod.nicofighter45.fr.items.enchantment.ModEnchants;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import java.util.*;

public class VanadiumMod implements ModInitializer {


    //the mod id used to create new blocks and items
    public static final String MODID = "vana-mod";

    //screen handler for modifiers table
    public static ScreenHandlerType<ModifiersTableGuiDescription> SCREEN_HANDLER_TYPE;

    //new crafts
    public static Map<ModifierCraft, Item> crafts = new HashMap<>();

    //max stock value
    public static final int maxStockForItem = 10000;
    public static final float commissionValue = 0.02f;

    @Override
    public void onInitialize() {
        //register all items and blocks
        registers();

        //add keybinds
        KeyBinds.registerAll();

        //creating the new crafts
        addCrafts();

        //register screen for modifiers table
        SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(ModifiersTableBlock.ID, (syncId, inventory) ->
                new ModifiersTableGuiDescription(syncId, inventory, ScreenHandlerContext.EMPTY)
        );

        Registry.register(Registry.RECIPE_SERIALIZER, ModifiersRecipeSerializer.ID, ModifiersRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(MODID, ModifiersCraft.Type.ID), ModifiersCraft.Type.INSTANCE);

        //register command
        Command.registerAllCommands();
    }

    private void addCrafts() {
        newCraft(ModItems.VANADIUM_NUGGET, ModItems.VANADIUM_NUGGET, ModItems.VANADIUM_NUGGET, ModItems.VANADIUM_NUGGET, ModItems.VANADIUM_INGOT);
        newCraft(ModItems.VANADIUM_INGOT, ModItems.VANADIUM_INGOT, ModItems.VANADIUM_INGOT, ModItems.VANADIUM_INGOT, ModBlocksItem.VANADIUM_BLOCK_ITEM);

        newCraft(ModItems.TRANSISTOR, ModItems.TRANSISTOR, ModItems.TRANSISTOR, ModItems.TRANSISTOR, ModItems.PROCESSOR);

        newCraft(ModItems.VANADIUM_NUGGET, Items.COAL_BLOCK, Items.COAL_BLOCK, ModItems.VANADIUM_NUGGET, ModItems.HASTE_STONE);
        newCraft(ModItems.VANADIUM_NUGGET, Items.SHULKER_SHELL, Items.SHULKER_SHELL, ModItems.VANADIUM_NUGGET, ModItems.STRENGTH_STONE);
        newCraft(ModItems.VANADIUM_NUGGET, ModItems.TUNGSTEN_INGOT, ModItems.TUNGSTEN_INGOT, ModItems.VANADIUM_NUGGET, ModItems.RESISTANCE_STONE);
        newCraft(ModItems.VANADIUM_NUGGET, Items.BROWN_MUSHROOM_BLOCK, Items.RED_MUSHROOM_BLOCK, ModItems.VANADIUM_NUGGET, ModItems.SPEED_STONE);
        newCraft(ModItems.VANADIUM_NUGGET, Items.SLIME_BLOCK, Items.SLIME_BLOCK, ModItems.VANADIUM_NUGGET, ModItems.JUMP_STONE);
        newCraft(ModItems.VANADIUM_NUGGET, Items.DRAGON_HEAD, Items.NETHER_STAR, ModItems.VANADIUM_NUGGET, ModItems.NO_FALL_STONE);
    }

    private void registers() {
        ModItems.registerAll();
        ModEnchants.registerAll();
        ModBlocks.registerAll();
        Listeners.registerAll();
        ModifierTableRegister.registerAll();
        ModEntity.registerAll();
    }

    private static void newCraft(Item item0, Item item1, Item item2, Item item3, Item result){
        crafts.put(new ModifierCraft(item0, item1, item2, item3), result);
    }

    //item group
    public static final ItemGroup VANADIUM_GROUP = FabricItemGroupBuilder.create(
            new Identifier(MODID, "vanadium"))
            .icon(() -> new ItemStack(ModItems.VANADIUM_INGOT))
            .build();

}