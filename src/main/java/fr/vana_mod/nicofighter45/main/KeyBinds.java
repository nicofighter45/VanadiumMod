package fr.vana_mod.nicofighter45.main;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.lwjgl.glfw.GLFW;
import fr.vana_mod.nicofighter45.items.ModItems;

public class KeyBinds {

    public static void registerAll(){
        String category = "category." + VanadiumMod.MODID + ".vanadium";

        KeyBinding keybinding_night_vision = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + VanadiumMod.MODID + ".night_vision", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, category));
        KeyBinding keybinding_hat = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + VanadiumMod.MODID + ".hat", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, category));
        KeyBinding keybinding_fire_protection = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + VanadiumMod.MODID + ".armor_effect", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_COMMA, category));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(keybinding_hat.isPressed()){
                ClientPlayerEntity player = client.player;
                assert player != null;
                ItemStack hand = player.getMainHandStack();
                if(hand.getItem() == Items.AIR || hand.getCount() != 1){
                    return;
                }
                ItemStack helmet = player.getInventory().armor.get(3);
                player.getInventory().removeOne(hand);
                if(helmet.getItem() != Items.AIR){
                    player.getInventory().insertStack(helmet);
                }
                player.getInventory().insertStack(39, hand);
            }
            if(keybinding_night_vision.isPressed()){
                ClientPlayerEntity player = client.player;
                assert player != null;
                if(player.hasStatusEffect(StatusEffects.NIGHT_VISION)){
                    player.removeStatusEffect(StatusEffects.NIGHT_VISION);
                }else{
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false, true));
                }
            }
            if(keybinding_fire_protection.isPressed()){
                ClientPlayerEntity player = client.player;
                assert player != null;
                if(player.getInventory().getArmorStack(2).getItem() == ModItems.TUNGSTEN_CHESTPLATE){
                    if(!player.hasStatusEffect(StatusEffects.SLOWNESS)){
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 600, 0, false, false, true));
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1200, 0, false, false, true));
                    }
                }else if(player.getInventory().getArmorStack(3).getItem() == ModItems.STEEL_HELMET &&
                        player.getInventory().getArmorStack(2).getItem() == ModItems.STEEL_CHESTPLATE &&
                        player.getInventory().getArmorStack(1).getItem() == ModItems.STEEL_LEGGINGS &&
                        player.getInventory().getArmorStack(0).getItem() == ModItems.STEEL_BOOTS){
                    if(!player.hasStatusEffect(StatusEffects.SLOWNESS)){
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 0, false, false, true));
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1200, 0, false, false, true));
                    }
                }
            }
        });
    }

}