package fr.nicofighter45.fvm.init;

import fr.nicofighter45.fvm.FVM;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item VANADIUM_INGOT = new Item(new Item.Settings().group(ItemGroup.MATERIALS).maxCount(64));
    public static final Item VANADIUM_STICK = new Item(new Item.Settings().group(ItemGroup.MATERIALS).maxCount(16));

    public static void registerAll(){

        Registry.register(Registry.ITEM, new Identifier(FVM.MODID, "vanadium_ingot"), VANADIUM_INGOT);
        Registry.register(Registry.ITEM, new Identifier(FVM.MODID, "vanadium_stick"), VANADIUM_STICK);

    }

}