package fr.nicofighter45.fvm.block.modifiertable;

import fr.nicofighter45.fvm.FVM;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModifierTableRegister {

    //modifiers table
    public static BlockEntityType<ModifiersTableBlockEntity> MODIFIERS_TABLE_BLOCK_ENTITY;
    public static final Block MODIFIERS_TABLE = new ModifiersTableBlock();
    public static final BlockItem MODIFIERS_TABLE_ITEM = new BlockItem(MODIFIERS_TABLE,
            new Item.Settings().group(FVM.VANADIUM_GROUP));

    public static void registerAll(){
        Registry.register(Registry.BLOCK, new Identifier(FVM.MODID, "modifiers_table"), MODIFIERS_TABLE);
        Registry.register(Registry.ITEM, new Identifier(FVM.MODID, "modifiers_table"), MODIFIERS_TABLE_ITEM);
        MODIFIERS_TABLE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, FVM.MODID,
                BlockEntityType.Builder.create(ModifiersTableBlockEntity::new, MODIFIERS_TABLE).build(null));
    }

}