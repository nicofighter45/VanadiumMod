package fvm.nicofighter45.fr;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import fvm.nicofighter45.fr.block.modifiertable.*;
import fvm.nicofighter45.fr.block.ore.ModOres;
import fvm.nicofighter45.fr.database.DataBaseManager;
import fvm.nicofighter45.fr.items.ModItems;
import fvm.nicofighter45.fr.items.enchantment.ModEnchants;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.*;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static net.minecraft.command.argument.EntityArgumentType.getPlayer;
import static net.minecraft.command.argument.EntityArgumentType.player;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

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
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {

            dispatcher.register(literal("pay")
                    .then(argument("item", string()))
                    .executes(context -> {
                        System.out.println(getString(context, "item"));
                        return 1;
                    })
            ).createBuilder();

        });
//        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
//            dispatcher.register(
//                    literal("setshop")
//                    .then(CommandManager.argument("item", StringArgumentType.string()))
//                    .executes(context -> {
//                        ServerPlayerEntity player = context.getSource().getPlayer();
//                        player.sendMessage(new TranslatableText("working"), false);
//                return 1;
//            }));
//        });
//
//        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
//            dispatcher.register(literal("health_scale")
//                    .then(CommandManager.argument("value", IntegerArgumentType.integer(0, 40)))
//                    .executes(context -> {
//                        int value = IntegerArgumentType.getInteger(context,"value");
//                        ServerPlayerEntity player = context.getSource().getPlayer();
//                        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(value);
//                        return 0;
//                    })
//            );
//        });
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

    //play sound method
//    public static void playSound(World world, PlayerEntity player, BlockPos pos, SoundEvent event, SoundCategory category){
//        world.playSound(player, pos, event, category, 1, 1);
//    }

}