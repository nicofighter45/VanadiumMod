package fr.vana_mod.nicofighter45.bosses;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import net.minecraft.util.Identifier;
import fr.vana_mod.nicofighter45.main.VanadiumMod;
import org.jetbrains.annotations.NotNull;

public class BossComponent implements WorldComponentInitializer {
    public static final ComponentKey<CustomComponent> WORLD_COMPONENT_KEY =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(VanadiumMod.MODID, "bosses"), CustomComponent.class);
    @Override
    public void registerWorldComponentFactories(@NotNull WorldComponentFactoryRegistry registry) {
        registry.register(WORLD_COMPONENT_KEY, CustomComponent::new);
    }
}