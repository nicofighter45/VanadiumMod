package vana_mod.nicofighter45.fr.bosses;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import net.minecraft.util.Identifier;
import vana_mod.nicofighter45.fr.main.VanadiumMod;

public class BossComponent implements WorldComponentInitializer {
    public static final ComponentKey<CustomComponent> WORLD_COMPONENT_KEY =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(VanadiumMod.MODID, "bosses"), CustomComponent.class);
    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(WORLD_COMPONENT_KEY, CustomComponent::new);
    }
}