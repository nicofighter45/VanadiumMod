package fr.nicofighter45.fvm;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;

public class Listeners {

    public static void blockBreakEventRegister(){
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
            if (state.getBlock() == Blocks.WHEAT && player.getMainHandStack().getItem() == Items.NETHERITE_HOE){
            }
        });
    }

}