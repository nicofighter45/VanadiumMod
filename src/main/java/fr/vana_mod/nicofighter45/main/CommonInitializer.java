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
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommonInitializer implements ModInitializer {


    public static final String MODID = "vana-mod";

    public static final ItemGroup VANADIUM_GROUP = FabricItemGroup.builder()
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

        ModItems.registerIngots();
        ModBlocks.registerAll();
        ModMachines.registerAll();
        ModOreGeneration.generateOres();
        ModItems.registerAll();
        ModEnchants.registerAll();

        Listeners.registerAll();

        Command.registerAllCommands();

        RegistryKey<ItemGroup> key = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(MODID, "vanadium"));

        ItemGroupEvents.modifyEntriesEvent(key).register(content -> content.addAll(VANADIUM_GROUP_ITEMS));
        Registry.register(Registries.ITEM_GROUP, key, VANADIUM_GROUP);

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