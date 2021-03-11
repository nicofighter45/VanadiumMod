package vana_mod.nicofighter45.fr.entity;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import vana_mod.nicofighter45.fr.entity.VanadiumGolem.VanadiumGolemEntity;
import vana_mod.nicofighter45.fr.entity.VanadiumGolem.VanadiumGolemEntityRender;
import vana_mod.nicofighter45.fr.main.VanadiumMod;

public class ModEntity {

    public static final EntityType<VanadiumGolemEntity> VANADIUM_GOLEM = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(VanadiumMod.MODID, "vanadium_golem"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, VanadiumGolemEntity::new).dimensions(EntityDimensions.fixed(2f, 2f)).build()
    );

    public static void registerAll(){
        FabricDefaultAttributeRegistry.register(VANADIUM_GOLEM, VanadiumGolemEntity.createMobAttributes());
        EntityRendererRegistry.INSTANCE.register(VANADIUM_GOLEM, (dispatcher, context) -> new VanadiumGolemEntityRender(dispatcher));
    }

}