package net.zhaiji.elainabroom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.client.model.ElainaBroomModel;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;

public class ElainaBroomRender extends EntityRenderer<ElainaBroomEntity, ElainaBroomRenderState> {
    private static final Identifier BROOM_TEXTURE = Identifier.fromNamespaceAndPath(ElainaBroom.MOD_ID, "textures/entity/elaina_broom.png");

    private final ElainaBroomModel broomModel;

    public ElainaBroomRender(EntityRendererProvider.Context context) {
        super(context);
        broomModel = new ElainaBroomModel(context.bakeLayer(ElainaBroomModel.LAYER));
    }

    @Override
    public ElainaBroomRenderState createRenderState() {
        return new ElainaBroomRenderState();
    }

    @Override
    public void extractRenderState(ElainaBroomEntity entity, ElainaBroomRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.yaw = entity.getYRot(partialTicks);
    }


    public Identifier getTextureLocation(ElainaBroomRenderState state) {
        return BROOM_TEXTURE;
    }

    @Override
    public void submit(ElainaBroomRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.scale(-0.3f, -0.3f, 0.3f);
        poseStack.translate(0.0, -2.5, 0.0);
        poseStack.mulPose(Axis.YP.rotationDegrees(state.yaw - 180));
        submitNodeCollector.submitModel(
                broomModel, state, poseStack, getTextureLocation(state), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null
        );
        poseStack.popPose();
        super.submit(state, poseStack, submitNodeCollector, camera);
    }
}
