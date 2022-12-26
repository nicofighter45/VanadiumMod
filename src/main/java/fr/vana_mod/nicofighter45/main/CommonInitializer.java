package fr.vana_mod.nicofighter45.main;

import fr.vana_mod.nicofighter45.block.ModBlocks;
import fr.vana_mod.nicofighter45.block.ModOreGeneration;
import fr.vana_mod.nicofighter45.items.ModItems;
import fr.vana_mod.nicofighter45.items.enchantment.ModEnchants;
import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.main.server.commands.Command;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommonInitializer implements ModInitializer {


    public static final String MODID = "vana-mod";

    public static final ItemGroup VANADIUM_GROUP = FabricItemGroup.builder(new Identifier(MODID, "vanadium"))
            .icon(() -> new ItemStack(ModItems.VANADIUM_INGOT))
            .displayName(Text.translatable("itemGroup."+ MODID + ".vanadium"))
            .build();

    @Contract(value = " -> new", pure = true)
    public static Item.@NotNull Settings settings() {
        return new FabricItemSettings();
    }

    public static final List<ItemStack> VANADIUM_GROUP_ITEMS = new ArrayList<>();

    @Override
    public void onInitialize() {

        ModBlocks.registerAll();
        ModMachines.registerAll();
        ModOreGeneration.generateOres();
        ModItems.registerAll();
        ModEnchants.registerAll();

        Listeners.registerAll();

        Command.registerAllCommands();

        ItemGroupEvents.modifyEntriesEvent(VANADIUM_GROUP).register(content -> content.addAll(VANADIUM_GROUP_ITEMS));

    }

    public static void addItemInGroup(Item  @NotNull ... items){
        for (Item item : items){
            VANADIUM_GROUP_ITEMS.add(new ItemStack(item));
        }
    }

    public static void addBlockInGroup(Block @NotNull ... blocks){
        for (Block block : blocks){
            VANADIUM_GROUP_ITEMS.add(new ItemStack(block.asItem()));
        }
    }

}