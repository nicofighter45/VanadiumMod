package fvm.nicofighter45.fr;

import fvm.nicofighter45.fr.database.DataBasePlayer;
import fvm.nicofighter45.fr.items.ModItems;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.Blocks;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.TypedActionResult;

import java.util.Objects;

public class Listeners {

    public static void blockBreakEventRegister(){
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
            if (state.getBlock() == Blocks.WHEAT && player.getMainHandStack().getItem() == Items.NETHERITE_HOE){
            }
        });
    }

    public static void onItemRightClickRegister(){
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack itemStack = player.inventory.getMainHandStack();
            Item item = itemStack.getItem();
            String name = player.getEntityName();
            DataBasePlayer data_player = FVM.dataBaseManager.dataBasePlayers.get(name);
            if(player instanceof ServerPlayerEntity){
                ServerPlayerEntity server_player = (ServerPlayerEntity) player;
                int heart = data_player.getHeart();
                int regen = data_player.getRegen();
                if(item == ModItems.SIMPLE_HEALTH_BOOSTER && heart < 20){
                    data_player.setHeart(heart + 2);
                    Objects.requireNonNull(server_player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(data_player.getHeart());
                    return TypedActionResult.consume(itemStack);
                }else if(item == ModItems.BASE_HEALTH_BOOSTER && heart >= 20 && heart < 30){
                    data_player.setHeart(heart + 2);
                    Objects.requireNonNull(server_player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(data_player.getHeart());
                    return TypedActionResult.consume(itemStack);
                }else if(item == ModItems.ADVANCE_HEALTH_BOOSTER && heart >= 30 && heart < 40){
                    data_player.setHeart(heart + 2);
                    Objects.requireNonNull(server_player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(data_player.getHeart());
                    return TypedActionResult.consume(itemStack);
                }else if(item == ModItems.SIMPLE_REGEN_BOOSTER && regen < 8){
                    data_player.setRegen(regen + 2);
                    return TypedActionResult.consume(itemStack);
                }else if(item == ModItems.BASE_REGEN_BOOSTER && regen >= 8 && regen < 16){
                    data_player.setRegen(regen + 2);
                    return TypedActionResult.consume(itemStack);
                }else if(item == ModItems.ADVANCE_REGEN_BOOSTER && regen >= 16 && regen < 24){
                    data_player.setRegen(regen + 2);
                    return TypedActionResult.consume(itemStack);
                }
            }
            return TypedActionResult.pass(itemStack);
        });
    }

}