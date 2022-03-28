package fr.vana_mod.nicofighter45.block.modifiertable;

import fr.vana_mod.nicofighter45.block.ModBlocksItem;
import fr.vana_mod.nicofighter45.items.ModItems;
import fr.vana_mod.nicofighter45.items.enchantment.ModEnchants;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import fr.vana_mod.nicofighter45.main.VanadiumMod;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ModifierTableRegister {

    //modifiers table
    public static BlockEntityType<ModifiersTableBlockEntity> MODIFIERS_TABLE_BLOCK_ENTITY;
    public static final Block MODIFIERS_TABLE = new ModifiersTableBlock();
    public static final BlockItem MODIFIERS_TABLE_ITEM = new BlockItem(MODIFIERS_TABLE,
            new Item.Settings().group(VanadiumMod.VANADIUM_GROUP));

    //new crafts
    public static Map<ModifierCraft, ItemStack> crafts = new HashMap<>();

    public static void registerAll(){

        //creating the new crafts
        addCrafts();

        Registry.register(Registry.BLOCK, new Identifier(VanadiumMod.MODID, "modifiers_table"), MODIFIERS_TABLE);
        Registry.register(Registry.ITEM, new Identifier(VanadiumMod.MODID, "modifiers_table_item"), MODIFIERS_TABLE_ITEM);
        MODIFIERS_TABLE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, VanadiumMod.MODID,
                FabricBlockEntityTypeBuilder.create(ModifiersTableBlockEntity::new, MODIFIERS_TABLE).build(null));
    }

    private static void addCrafts() {
        newCraft(ModItems.VANADIUM_NUGGET, ModItems.VANADIUM_NUGGET, ModItems.VANADIUM_NUGGET, ModItems.VANADIUM_NUGGET, new ItemStack(ModItems.VANADIUM_INGOT));
        newCraft(ModItems.VANADIUM_INGOT, ModItems.VANADIUM_INGOT, ModItems.VANADIUM_INGOT, ModItems.VANADIUM_INGOT, new ItemStack(ModBlocksItem.VANADIUM_BLOCK_ITEM));

        newCraft(ModItems.TRANSISTOR, ModItems.TRANSISTOR, ModItems.TRANSISTOR, ModItems.TRANSISTOR, new ItemStack(ModItems.PROCESSOR));

        newCraft(ModItems.VANADIUM_NUGGET, Items.COAL_BLOCK, Items.COAL_BLOCK, ModItems.VANADIUM_NUGGET, enchantBook(ModEnchants.HASTER));
        newCraft(ModItems.VANADIUM_NUGGET, Items.SHULKER_SHELL, Items.SHULKER_SHELL, ModItems.VANADIUM_NUGGET, enchantBook(ModEnchants.HASTER));
        newCraft(ModItems.VANADIUM_NUGGET, ModItems.TUNGSTEN_INGOT, ModItems.TUNGSTEN_INGOT, ModItems.VANADIUM_NUGGET, enchantBook(ModEnchants.RESISTANCER));
        newCraft(ModItems.VANADIUM_NUGGET, Items.BROWN_MUSHROOM_BLOCK, Items.RED_MUSHROOM_BLOCK, ModItems.VANADIUM_NUGGET, enchantBook(ModEnchants.FASTER));
        newCraft(ModItems.VANADIUM_NUGGET, Items.SLIME_BLOCK, Items.SLIME_BLOCK, ModItems.VANADIUM_NUGGET, enchantBook(ModEnchants.JUMPER));
        newCraft(ModItems.VANADIUM_NUGGET, Items.DRAGON_HEAD, Items.NETHER_STAR, ModItems.VANADIUM_NUGGET, enchantBook(ModEnchants.NO_FALL));
    }

    private static void newCraft(Item item0, Item item1, Item item2, Item item3, ItemStack result){
        crafts.put(new ModifierCraft(item0, item1, item2, item3), result);
    }

    private static @NotNull ItemStack enchantBook(Enchantment enchantment){
        ItemStack item = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantedBookItem.addEnchantment(item, new EnchantmentLevelEntry(enchantment, 1));
        return item;
    }

}