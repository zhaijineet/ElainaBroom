package net.zhaiji.elainabroom.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.zhaiji.elainabroom.ElainaBroom;
import org.slf4j.Logger;

public class ElainaBroomRender extends EntityRenderer<ElainaBroomEntity> {
    private static final ResourceLocation BROOM_TEXTURE = ResourceLocation.fromNamespaceAndPath(ElainaBroom.MOD_ID, "textures/entity/broom.png");
    private static final Logger LOGGER = LogUtils.getLogger();

    private final EntityModel<ElainaBroomEntity> ElainaBroomEntityEntityModel;

    public ElainaBroomRender(EntityRendererProvider.Context context) {
        super(context);
        ElainaBroomEntityEntityModel = new ElainaBroomModel(context.bakeLayer(ElainaBroomModel.LAYER));
    }

    @Override
    public ResourceLocation getTextureLocation(ElainaBroomEntity entityBroom) {
        return BROOM_TEXTURE;
    }

    @Override
    public void render(ElainaBroomEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(-0.3f, -0.3f, 0.3f);

        poseStack.translate(0.0, -2.5, 0.0);

        poseStack.mulPose(Axis.YP.rotationDegrees(entityYaw - 180));

        RenderType renderType = ElainaBroomEntityEntityModel.renderType(getTextureLocation(entity));
        VertexConsumer buffer = bufferIn.getBuffer(renderType);
        ElainaBroomEntityEntityModel.renderToBuffer(poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }
}