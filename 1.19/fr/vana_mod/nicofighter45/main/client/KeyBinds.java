package fr.vana_mod.nicofighter45.main.client;

import fr.vana_mod.nicofighter45.main.CommonInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {

    public static void registerAll(){
        String category = "key.category." + CommonInitializer.MODID + ".vanadium";

        KeyBinding keybinding_night_vision = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + CommonInitializer.MODID + ".night_vision", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, category));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(keybinding_night_vision.isPressed()){
                ClientPlayerEntity player = client.player;
                assert player != null;
                if(player.hasStatusEffect(StatusEffects.NIGHT_VISION)){
                    player.removeStatusEffect(StatusEffects.NIGHT_VISION);
                }else{
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false, false));
                }
            }
        });
    }

}