package vana_mod.nicofighter45.fr.entity.VanadiumGolem;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class VanadiumGolemEntityModel extends EntityModel<VanadiumGolemEntity> {

    private final ModelPart model = new ModelPart(128, 128, 0, 0);;

    public VanadiumGolemEntityModel(){
        model.setPivot(0.0F, 0.0F, 0.0F);
        model.addCuboid(-16, -16, -16, 32, 32, 32);
    }

    @Override
    public void setAngles(VanadiumGolemEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        // translate model down
        matrices.translate(0, 0.5, 0);

        // render cube
        model.render(matrices, vertices, light, overlay);
    }
}