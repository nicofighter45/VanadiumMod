package fr.vana_mod.nicofighter45.rei;//package fr.vana_mod.nicofighter45.rei;
//
//import me.shedaniel.math.Rectangle;
//import me.shedaniel.rei.api.*;
//import me.shedaniel.rei.api.plugins.REIPluginV0;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.gui.screen.Screen;
//import net.minecraft.util.Identifier;
//import fr.vana_mod.nicofighter45.block.modifiertable.ModifierTableRegister;
//import fr.vana_mod.nicofighter45.block.modifiertable.ModifiersTableScreen;
//import fr.vana_mod.nicofighter45.main.VanadiumMod;
//
//import java.util.ArrayList;
//
//public class Plugin implements REIPluginV0 {
//
//    @Override
//    public Identifier getPluginIdentifier() {
//        return new Identifier(VanadiumMod.MODID, "rei_plugin");
//    }
//
//    @Override
//    public void registerOthers(RecipeHelper recipeHelper) {
//        recipeHelper.registerWorkingStations(BuiltinPlugin.CRAFTING, EntryStack.create(ModifierTableRegister.MODIFIERS_TABLE));
//    }
//
//    @Override
//    public void registerBounds(DisplayHelper displayHelper) {
//        BaseBoundsHandler baseBoundsHandler = BaseBoundsHandler.getInstance();
//        baseBoundsHandler.registerExclusionZones(ModifiersTableScreen.class, () -> {
//            Screen currentScreen = MinecraftClient.getInstance().currentScreen;
//            ArrayList<Rectangle> list = new ArrayList<>();
//            if (currentScreen instanceof ModifiersTableScreen) {
//                list.add(new Rectangle(100, 100, 10, 10));
//                list.add(new Rectangle(100, 150, 10, 10));
//                list.add(new Rectangle(100, 50, 10, 10));
//                list.add(new Rectangle(50, 100, 10, 10));
//                list.add(new Rectangle(150, 100, 10, 10));
//            }
//            return list;
//        });
//    }
//
//}