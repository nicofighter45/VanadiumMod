package fvm.nicofighter45.fr.block.modifiertable;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class ModifiersTableScreen extends CottonInventoryScreen<ModifiersTableGuiDescription> {
    public ModifiersTableScreen(ModifiersTableGuiDescription gui, PlayerEntity player, Text title) {
        super(gui, player, title);
    }
}