package fr.vana_mod.nicofighter45.block.enchanter_table;

import fr.vana_mod.nicofighter45.main.VanadiumMod;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EnchanterTableRegister {

    public static Block ENCHANTER_TABLE_BLOCK;
    public static BlockItem ENCHANTER_TABLE_BLOCK_ITEM;
    public static BlockEntityType<EnchanterTableBlockEntity> ENCHANTER_TABLE_BLOCK_ENTITY;
    public static ScreenHandlerType<EnchanterTableScreenHandler> ENCHANTER_TABLE_SCREEN_HANDLER;
    private static final Identifier id = new Identifier(VanadiumMod.MODID, "enchanter_table");

    public static void registerAll(){
        ENCHANTER_TABLE_BLOCK = Registry.register(Registry.BLOCK, id, new EnchanterTableBlock(FabricBlockSettings.copyOf(Blocks.CHEST)));
        ENCHANTER_TABLE_BLOCK_ITEM = Registry.register(Registry.ITEM, id, new BlockItem(ENCHANTER_TABLE_BLOCK, new Item.Settings().group(VanadiumMod.VANADIUM_GROUP)));
        //The parameter of build at the very end is always null, do not worry about it
        ENCHANTER_TABLE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.create(EnchanterTableBlockEntity::new, ENCHANTER_TABLE_BLOCK).build(null));
        ENCHANTER_TABLE_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(id, EnchanterTableScreenHandler::new);
    }
}