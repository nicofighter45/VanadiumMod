package fr.vana_mod.nicofighter45.main;

import fr.vana_mod.nicofighter45.items.custom.EnderBow;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {

    public static void registerAll(){
        String category = "category." + VanadiumMod.MODID + ".vanadium";

        KeyBinding keybinding_night_vision = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + VanadiumMod.MODID + ".night_vision", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, category));
        KeyBinding keybinding_ender_bow_switch = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + VanadiumMod.MODID + ".ender_bow_switch", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, category));


        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(keybinding_night_vision.isPressed()){
                ClientPlayerEntity player = client.player;
                assert player != null;
                if(player.hasStatusEffect(StatusEffects.NIGHT_VISION)){
                    player.removeStatusEffect(StatusEffects.NIGHT_VISION);
                }else{
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false, false));
                }
            }else if(keybinding_ender_bow_switch.isPressed()){
                ClientPlayerEntity player = client.player;
                assert player != null;
                if(player.getMainHandStack().getItem() instanceof EnderBow){
                    if(((EnderBow) player.getMainHandStack().getItem()).changeEnderPearl()){
                        player.sendMessage(Text.of("§2Ender Pearl mode §4activate"), true);
                    }else{
                        player.sendMessage(Text.of("§2Ender Pearl mode §4deactivate"), true);
                    }
                }
            }
        });
    }

}