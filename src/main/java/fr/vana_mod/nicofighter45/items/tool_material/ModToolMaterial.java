package fr.vana_mod.nicofighter45.items.tool_material;

import fr.vana_mod.nicofighter45.items.ModItems;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;

public class ModToolMaterial {

    public static ToolMaterial CopperToolMaterial = new BasicToolMaterial(1200, 7, 1,
            2, 12, Items.COPPER_INGOT);

    public static ToolMaterial TinToolMaterial = new BasicToolMaterial(3000, 10, 1,
            3, 15, ModItems.TIN_INGOT);

    public static ToolMaterial TungstenToolMaterial = new BasicToolMaterial(4000, 12, 1,
            4, 15, ModItems.TUNGSTEN_INGOT);

    public static ToolMaterial VanadiumToolMaterial = new BasicToolMaterial(5000, 15, 3,
            5, 22, ModItems.VANADIUM_INGOT);

}