package fr.nicofighter45.fvm.block.modifiertable;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class ModifiersTableScreen extends CottonInventoryScreen<ModifiersTableGuiDesciption> {
    public ModifiersTableScreen(ModifiersTableGuiDesciption gui, PlayerEntity player, Text title) {
        super(gui, player, title);
    }
}