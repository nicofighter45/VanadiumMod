package fvm.nicofighter45.fr.block.modifiertable;

import fvm.nicofighter45.fr.FVM;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
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
                FabricBlockEntityTypeBuilder.create(ModifiersTableBlockEntity::new, MODIFIERS_TABLE).build(null));
    }

    @FunctionalInterface
    interface BlockEntityFactory<T extends BlockEntity> {
        T create(BlockPos pos, BlockState state);
    }

}