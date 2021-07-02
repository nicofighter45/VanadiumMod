package vana_mod.nicofighter45.fr.block.modifiertable;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import vana_mod.nicofighter45.fr.main.VanadiumMod;
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
            new Item.Settings().group(VanadiumMod.VANADIUM_GROUP));

    public static void registerAll(){
        Registry.register(Registry.BLOCK, new Identifier(VanadiumMod.MODID, "modifiers_table"), MODIFIERS_TABLE);
        Registry.register(Registry.ITEM, new Identifier(VanadiumMod.MODID, "modifiers_table_item"), MODIFIERS_TABLE_ITEM);
        MODIFIERS_TABLE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, VanadiumMod.MODID,
                FabricBlockEntityTypeBuilder.create(ModifiersTableBlockEntity::new, MODIFIERS_TABLE).build(null));
    }

}