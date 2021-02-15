package fvm.nicofighter45.fr;

import fvm.nicofighter45.fr.block.modifiertable.*;
import fvm.nicofighter45.fr.block.ore.ModOres;
import fvm.nicofighter45.fr.database.DataBaseManager;
import fvm.nicofighter45.fr.items.ModItems;
import fvm.nicofighter45.fr.items.enchantment.ModEnchants;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import java.util.*;

public class FVM implements ModInitializer {


    //the mod id used to create new blocks and items
    public static final String MODID = "fvm";

    //screen handler for modifiers table
    public static ScreenHandlerType<ModifiersTableGuiDescription> SCREEN_HANDLER_TYPE;

    //player who got less than 5 hearts
    public static Map<PlayerEntity, Integer> TickNumberForHeal = new HashMap<>();

    //database mysql
    public static DataBaseManager dataBaseManager;

    //new crafts
    public static Map<ModifierCraft, Item> crafts = new HashMap<>();

    //max stock value
    public static final int maxStockForItem = 1000;
    public static final float commissionValue = 0.02f;

    @Override
    public void onInitialize() {
        //register all items and blocks
        ModItems.registerAll();
        ModEnchants.registerAll();
        ModOres.registerAll();
        Listeners.blockBreakEventRegister();
        Listeners.onItemRightClickRegister();
        ModifierTableRegister.registerAll();

        //creating the new crafts
        newCraft(ModItems.VANADIUM_NUGGET, ModItems.VANADIUM_NUGGET, ModItems.VANADIUM_NUGGET, ModItems.VANADIUM_NUGGET, ModItems.VANADIUM_INGOT);
        newCraft(ModItems.VANADIUM_INGOT, ModItems.VANADIUM_INGOT, ModItems.VANADIUM_INGOT, ModItems.VANADIUM_INGOT, ModItems.VANADIUM_BLOCK_ITEM);
        newCraft(ModItems.VANADIUM_BLOCK_ITEM, Items.DIAMOND, Items.DIAMOND, ModItems.VANADIUM_BLOCK_ITEM, ModItems.VANADIUM_HEART);
        newCraft(ModItems.VANADIUM_STICK, ModItems.VANADIUM_HEART, ModItems.VANADIUM_BLOCK_ITEM, ModItems.VANADIUM_INGOT, ModItems.VANADIUM_SWORD);
        newCraft(ModItems.TRANSISTOR, ModItems.TRANSISTOR, ModItems.TRANSISTOR, ModItems.TRANSISTOR, ModItems.PROCESSOR);
        newCraft(ModItems.VANADIUM_INGOT, Items.AIR, Items.AIR, Items.AIR, ModItems.VANADIUM_STICK);
        newCraft(Items.AIR, ModItems.VANADIUM_INGOT, Items.AIR, Items.AIR, ModItems.VANADIUM_STICK);
        newCraft(Items.AIR, Items.AIR, ModItems.VANADIUM_INGOT, Items.AIR, ModItems.VANADIUM_STICK);
        newCraft(Items.AIR, Items.AIR, Items.AIR, ModItems.VANADIUM_INGOT, ModItems.VANADIUM_STICK);

        //register screen for modifiers table
        SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(ModifiersTableBlock.ID, (syncId, inventory) ->
                new ModifiersTableGuiDescription(syncId, inventory, ScreenHandlerContext.EMPTY)
        );

        //register command
        Command.registerAllCommands();
    }

    private static void newCraft(Item item0, Item item1, Item item2, Item item3, Item result){
        crafts.put(new ModifierCraft(item0, item1, item2, item3), result);
    }

    //item group
    public static final ItemGroup VANADIUM_GROUP = FabricItemGroupBuilder.create(
            new Identifier(MODID, "vanadium"))
            .icon(() -> new ItemStack(ModItems.VANADIUM_INGOT))
            .appendItems(stacks -> {

                stacks.add(new ItemStack(ModOres.COPPER_ORE_ITEM));
                stacks.add(new ItemStack(ModItems.COPPER_INGOT));

                stacks.add(new ItemStack(ModOres.TIN_ORE_ITEM));
                stacks.add(new ItemStack(ModItems.TIN_INGOT));

                stacks.add(new ItemStack(ModOres.TUNGSTEN_ORE_ITEM));
                stacks.add(new ItemStack(ModItems.TUNGSTEN_INGOT));
                stacks.add(new ItemStack(ModItems.TUNGSTEN_CHESTPLATE));

                stacks.add(new ItemStack(ModOres.SILVER_ORE_ITEM));
                stacks.add(new ItemStack(ModItems.SILVER_INGOT));

                stacks.add(new ItemStack(ModItems.TRANSISTOR));
                stacks.add(new ItemStack(ModItems.PROCESSOR));
                stacks.add(new ItemStack(ModifierTableRegister.MODIFIERS_TABLE_ITEM));

                stacks.add(new ItemStack(ModItems.SIMPLE_HEALTH_BOOSTER));
                stacks.add(new ItemStack(ModItems.BASE_HEALTH_BOOSTER));
                stacks.add(new ItemStack(ModItems.ADVANCE_HEALTH_BOOSTER));

                stacks.add(new ItemStack(ModItems.SIMPLE_REGEN_BOOSTER));
                stacks.add(new ItemStack(ModItems.BASE_REGEN_BOOSTER));
                stacks.add(new ItemStack(ModItems.ADVANCE_REGEN_BOOSTER));

                stacks.add(new ItemStack(ModItems.EMERALD_HEART));
                stacks.add(new ItemStack(ModItems.EMERALD_HELMET));
                stacks.add(new ItemStack(ModItems.EMERALD_CHESTPLATE));
                stacks.add(new ItemStack(ModItems.EMERALD_LEGGINGS));
                stacks.add(new ItemStack(ModItems.EMERALD_BOOTS));

                stacks.add(new ItemStack(ModOres.VANADIUM_ORE_ITEM));
                stacks.add(new ItemStack(ModItems.VANADIUM_NUGGET));
                stacks.add(new ItemStack(ModItems.VANADIUM_INGOT));
                stacks.add(new ItemStack(ModItems.VANADIUM_BLOCK_ITEM));
                stacks.add(new ItemStack(ModItems.VANADIUM_HEART));
                stacks.add(new ItemStack(ModItems.VANADIUM_STICK));
                stacks.add(new ItemStack(ModItems.VANADIUM_SWORD));
                stacks.add(new ItemStack(ModItems.VANADIUM_HELMET));
                stacks.add(new ItemStack(ModItems.VANADIUM_CHESTPLATE));
                stacks.add(new ItemStack(ModItems.VANADIUM_LEGGINGS));
                stacks.add(new ItemStack(ModItems.VANADIUM_BOOTS));

                stacks.add(new ItemStack(ModItems.HASTE_STONE));
                stacks.add(new ItemStack(ModItems.STRENGTH_STONE));
                stacks.add(new ItemStack(ModItems.RESISTANCE_STONE));
                stacks.add(new ItemStack(ModItems.SPEED_STONE));
                stacks.add(new ItemStack(ModItems.JUMP_STONE));
                stacks.add(new ItemStack(ModItems.NO_FALL_STONE));

                stacks.add(new ItemStack(ModItems.BURGER));
            })
            .build();

}