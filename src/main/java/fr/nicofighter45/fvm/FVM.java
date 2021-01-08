package fr.nicofighter45.fvm;

import fr.nicofighter45.fvm.block.modifiertable.ModifierTableRegister;
import fr.nicofighter45.fvm.block.modifiertable.ModifiersTableGuiDescription;
import fr.nicofighter45.fvm.block.modifiertable.ModifiersTableBlock;
import fr.nicofighter45.fvm.block.ore.ModOres;
import fr.nicofighter45.fvm.items.ModItems;
import fr.nicofighter45.fvm.items.enchantment.ModEnchants;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
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

    @Override
    public void onInitialize() {

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

    //play sound method
    public static void playSound(World world, PlayerEntity player, BlockPos pos, SoundEvent event, SoundCategory category){
        world.playSound(player, pos, event, category, 1, 1);
    }

}