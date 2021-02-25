package vana_mod.nicofighter45.fr.block.modifiertable;

import vana_mod.nicofighter45.fr.MAIN;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModifierTableRegister {

    //modifiers table
    public static BlockEntityType<ModifiersTableBlockEntity> MODIFIERS_TABLE_BLOCK_ENTITY;
    public static final Block MODIFIERS_TABLE = new ModifiersTableBlock();
    public static final BlockItem MODIFIERS_TABLE_ITEM = new BlockItem(MODIFIERS_TABLE,
            new Item.Settings().group(MAIN.VANADIUM_GROUP));

    public static void registerAll(){
        Registry.register(Registry.BLOCK, new Identifier(MAIN.MODID, "modifiers_table"), MODIFIERS_TABLE);
        Registry.register(Registry.ITEM, new Identifier(MAIN.MODID, "modifiers_table_item"), MODIFIERS_TABLE_ITEM);
        MODIFIERS_TABLE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MAIN.MODID,
                BlockEntityType.Builder.create(ModifiersTableBlockEntity::new, MODIFIERS_TABLE).build(null));
    }

}