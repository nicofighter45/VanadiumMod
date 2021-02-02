package fr.nicofighter45.fvm;

import fr.nicofighter45.fvm.block.modifiertable.ModifierTableRegister;
import fr.nicofighter45.fvm.block.modifiertable.ModifiersTableGuiDescription;
import fr.nicofighter45.fvm.block.modifiertable.ModifiersTableBlock;
import fr.nicofighter45.fvm.block.ore.ModOres;
import fr.nicofighter45.fvm.database.DataBaseManager;
import fr.nicofighter45.fvm.items.ModItems;
import fr.nicofighter45.fvm.items.enchantment.ModEnchants;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FVM implements ModInitializer {


    //the mod id used to create new blocks and items
    public static final String MODID = "fvm";

    //screen handler for modifiers table
    public static ScreenHandlerType<ModifiersTableGuiDescription> SCREEN_HANDLER_TYPE;

    //player who got less than 5 hearts
    public static Map<PlayerEntity, Integer> TickNumberForHeal = new HashMap<>();

    //servers world
    public static List<ServerWorld> serverworld = new ArrayList<>();

    //minecraftServer
    public static MinecraftServer minecraftServer;

    //database mysql
    public static DataBaseManager dataBaseManager;

    @Override
    public void onInitialize() {

        //connect to data base
        dataBaseManager = new DataBaseManager();

        //register screen for modifiers table
        SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(ModifiersTableBlock.ID, (syncId, inventory) ->
                new ModifiersTableGuiDescription(syncId, inventory, ScreenHandlerContext.EMPTY)
        );

        //register all items and blocks
        ModItems.registerAll();
        ModEnchants.registerAll();
        ModOres.registerAll();
        Listeners.blockBreakEventRegister();
        ModifierTableRegister.registerAll();

        //register commands (in dev)
        Commands.registerTestCommand();
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
            })
            .build();

    //play sound method
    public static void playSound(World world, PlayerEntity player, BlockPos pos, SoundEvent event, SoundCategory category){
        world.playSound(player, pos, event, category, 1, 1);
    }

}