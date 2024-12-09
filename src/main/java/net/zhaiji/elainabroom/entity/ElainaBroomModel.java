package net.zhaiji.elainabroom.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.zhaiji.elainabroom.ElainaBroom;

public class ElainaBroomModel extends EntityModel<ElainaBroomEntity> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(ElainaBroom.MOD_ID, "main"), "broom");
    private final ModelPart broom;
    private final ModelPart handle;
    private final ModelPart head;
    private final ModelPart decorative;
    private final ModelPart ribbon;
    private final ModelPart bow;

    public ElainaBroomModel(ModelPart root) {
        this.broom = root.getChild("broom");
        this.handle = this.broom.getChild("handle");
        this.head = this.broom.getChild("head");
        this.decorative = this.broom.getChild("decorative");
        this.ribbon = this.broom.getChild("ribbon");
        this.bow = this.broom.getChild("bow");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition broom = partdefinition.addOrReplaceChild("broom", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition handle = broom.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -9.0F, -27.0F, 4.0F, 4.0F, 52.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition handle4_r1 = handle.addOrReplaceChild("handle4_r1", CubeListBuilder.create().texOffs(0, 224).addBox(-2.0F, -2.0F, -5.0F, 4.0F, 2.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, -3.5F, -69.3F, 0.1745F, 0.0F, 0.0F));

        PartDefinition handle3_r1 = handle.addOrReplaceChild("handle3_r1", CubeListBuilder.create().texOffs(0, 56).addBox(-3.0F, -4.0F, -7.0F, 4.0F, 4.0F, 37.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(1.0F, -4.5F, -67.7F, -0.1309F, 0.0F, 0.0F));

        PartDefinition handle2_r1 = handle.addOrReplaceChild("handle2_r1", CubeListBuilder.create().texOffs(136, 222).addBox(-3.0F, -4.0F, -11.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(1.0F, -4.8F, -27.1F, 0.3927F, 0.0F, 0.0F));

        PartDefinition head = broom.addOrReplaceChild("head", CubeListBuilder.create().texOffs(112, 26).addBox(-4.9572F, -11.266F, 24.3F, 10.0F, 9.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head16_r1 = head.addOrReplaceChild("head16_r1", CubeListBuilder.create().texOffs(142, 125).addBox(-3.7372F, -0.1011F, -14.8279F, 5.0F, 3.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.8564F, 50.1854F, 0.0698F, -0.0175F, 0.0F));

        PartDefinition head15_r1 = head.addOrReplaceChild("head15_r1", CubeListBuilder.create().texOffs(72, 125).addBox(-1.784F, -1.9632F, -14.6536F, 5.0F, 3.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.8564F, 50.1854F, 0.0698F, -0.0262F, 0.5672F));

        PartDefinition head14_r1 = head.addOrReplaceChild("head14_r1", CubeListBuilder.create().texOffs(0, 131).addBox(-3.0184F, -5.7465F, -14.0958F, 5.0F, 3.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.8564F, 50.1854F, 0.0873F, 0.0F, -1.3963F));

        PartDefinition head13_r1 = head.addOrReplaceChild("head13_r1", CubeListBuilder.create().texOffs(68, 194).addBox(-2.9489F, 2.9529F, -14.4161F, 5.0F, 3.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.8564F, 50.1854F, 0.0436F, -0.0436F, 0.3927F));

        PartDefinition head12_r1 = head.addOrReplaceChild("head12_r1", CubeListBuilder.create().texOffs(136, 190).addBox(-1.5035F, 2.9601F, -14.3616F, 5.0F, 3.0F, 29.0F, new CubeDeformation(0.0F)).texOffs(0, 196).addBox(-2.3323F, 5.6735F, -13.7713F, 6.0F, 3.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.8564F, 50.1854F, 0.0436F, 0.0436F, -0.3054F));

        PartDefinition head11_r1 = head.addOrReplaceChild("head11_r1", CubeListBuilder.create().texOffs(0, 164).addBox(-1.7263F, -5.4152F, -13.3151F, 5.0F, 3.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.8564F, 50.1854F, 0.0262F, -0.0698F, 1.5359F));

        PartDefinition head10_r1 = head.addOrReplaceChild("head10_r1", CubeListBuilder.create().texOffs(0, 97).addBox(-4.0193F, -4.0532F, -14.071F, 5.0F, 3.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.8564F, 50.1854F, 0.1396F, 0.0262F, -0.4363F));

        PartDefinition head9_r1 = head.addOrReplaceChild("head9_r1", CubeListBuilder.create().texOffs(82, 91).addBox(-1.913F, -4.1888F, -13.6383F, 5.0F, 3.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.8564F, 50.1854F, 0.1309F, 0.0F, 0.3491F));

        PartDefinition head8_r1 = head.addOrReplaceChild("head8_r1", CubeListBuilder.create().texOffs(204, 190).addBox(-4.0489F, 5.7944F, -13.7599F, 6.0F, 3.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.8564F, 50.1854F, 0.0436F, -0.0436F, 0.3054F));

        PartDefinition head6_r1 = head.addOrReplaceChild("head6_r1", CubeListBuilder.create().texOffs(154, 91).addBox(-3.5069F, -7.4588F, -13.0532F, 6.0F, 3.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.8564F, 50.1854F, 0.1396F, 0.1396F, -0.9948F));

        PartDefinition head5_r1 = head.addOrReplaceChild("head5_r1", CubeListBuilder.create().texOffs(70, 158).addBox(-7.9048F, -1.464F, -16.2746F, 3.0F, 6.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.8564F, 50.1854F, 0.0873F, -0.0698F, -0.1745F));

        PartDefinition head4_r1 = head.addOrReplaceChild("head4_r1", CubeListBuilder.create().texOffs(158, 26).addBox(4.9048F, -1.464F, -16.2746F, 3.0F, 6.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.8564F, 50.1854F, 0.0873F, 0.0698F, 0.1745F));

        PartDefinition head3_r1 = head.addOrReplaceChild("head3_r1", CubeListBuilder.create().texOffs(136, 158).addBox(-2.4011F, -7.3127F, -13.0868F, 6.0F, 3.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.8564F, 50.1854F, 0.1396F, -0.1396F, 0.9599F));

        PartDefinition head2_r1 = head.addOrReplaceChild("head2_r1", CubeListBuilder.create().texOffs(82, 56).addBox(-3.2F, -6.6694F, -12.8573F, 6.0F, 3.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.8564F, 50.1854F, 0.2618F, 0.0F, 0.0F));

        PartDefinition decorative = broom.addOrReplaceChild("decorative", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition decorative12_r1 = decorative.addOrReplaceChild("decorative12_r1", CubeListBuilder.create().texOffs(168, 222).addBox(-9.0F, -3.0F, -1.0F, 10.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 0.0F, 27.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition decorative11_r1 = decorative.addOrReplaceChild("decorative11_r1", CubeListBuilder.create().texOffs(26, 224).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -2.0F, 26.5F, 0.0F, -0.3054F, 0.0F));

        PartDefinition decorative10_r1 = decorative.addOrReplaceChild("decorative10_r1", CubeListBuilder.create().texOffs(225, 25).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -2.0F, 26.5F, 0.0F, 0.3054F, 0.0F));

        PartDefinition decorative9_r1 = decorative.addOrReplaceChild("decorative9_r1", CubeListBuilder.create().texOffs(212, 148).addBox(-5.0F, -1.0F, -4.0F, 10.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.0F, 29.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition decorative8_r1 = decorative.addOrReplaceChild("decorative8_r1", CubeListBuilder.create().texOffs(206, 158).addBox(0.0F, -6.0F, -1.0F, 1.0F, 6.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.1F, -1.3F, 31.1F, 0.0F, 0.0349F, 0.0698F));

        PartDefinition decorative7_r1 = decorative.addOrReplaceChild("decorative7_r1", CubeListBuilder.create().texOffs(212, 123).addBox(-1.0F, -6.0F, -1.0F, 1.0F, 6.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5F, -1.3F, 31.1F, 0.0F, -0.0349F, -0.0698F));

        PartDefinition decorative6_r1 = decorative.addOrReplaceChild("decorative6_r1", CubeListBuilder.create().texOffs(204, 218).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 6.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.2F, -11.4F, 30.8F, 0.3054F, 0.0646F, -0.0873F));

        PartDefinition decorative5_r1 = decorative.addOrReplaceChild("decorative5_r1", CubeListBuilder.create().texOffs(190, 0).addBox(0.0F, -1.0F, -1.0F, 1.0F, 6.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.6F, -11.4F, 30.9F, 0.3054F, -0.0646F, 0.0873F));

        PartDefinition decorative4_r1 = decorative.addOrReplaceChild("decorative4_r1", CubeListBuilder.create().texOffs(224, 40).addBox(-3.0F, -0.5F, -2.5F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2F, -1.7451F, 56.3231F, 0.0436F, 0.0F, 0.0F));

        PartDefinition decorative3_r1 = decorative.addOrReplaceChild("decorative3_r1", CubeListBuilder.create().texOffs(158, 62).addBox(-7.2F, 8.7564F, -20.2854F, 14.0F, 1.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.8564F, 50.1854F, 0.0F, 0.0F, 0.0F));

        PartDefinition decorative2_r1 = decorative.addOrReplaceChild("decorative2_r1", CubeListBuilder.create().texOffs(206, 183).addBox(-3.2F, -8.2014F, 5.528F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.7564F, 49.6854F, 0.2182F, 0.0F, 0.0F));

        PartDefinition decorative1_r1 = decorative.addOrReplaceChild("decorative1_r1", CubeListBuilder.create().texOffs(112, 0).addBox(-7.2F, -7.5961F, -18.794F, 14.0F, 1.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.8564F, 49.6854F, 0.3054F, 0.0F, 0.0F));

        PartDefinition ribbon = broom.addOrReplaceChild("ribbon", CubeListBuilder.create().texOffs(112, 48).addBox(-8.1F, -13.4F, 29.4F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(72, 97).addBox(-8.1F, -13.1F, 29.4F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(71, 111).addBox(5.9F, -13.1F, 29.4F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(112, 52).addBox(-8.1F, -2.4F, 29.4F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bow = broom.addOrReplaceChild("bow", CubeListBuilder.create().texOffs(148, 48).addBox(-1.1F, -0.996F, 0.5857F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.5F, -7.004F, 29.4143F, 3.1416F, 0.0F, 0.0F));

        PartDefinition bow7_r1 = bow.addOrReplaceChild("bow7_r1", CubeListBuilder.create().texOffs(223, 45).addBox(-1.0F, -2.0F, -2.0F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6F, 2.304F, -3.6143F, 0.2618F, 0.0F, 0.0F));

        PartDefinition bow6_r1 = bow.addOrReplaceChild("bow6_r1", CubeListBuilder.create().texOffs(42, 224).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -0.396F, -4.1143F, -0.2618F, 0.0F, 0.0F));

        PartDefinition bow5_r1 = bow.addOrReplaceChild("bow5_r1", CubeListBuilder.create().texOffs(62, 206).addBox(0.0F, -3.0F, -2.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 3.904F, 1.7857F, -0.2618F, 0.0F, 0.0F));

        PartDefinition bow4_r1 = bow.addOrReplaceChild("bow4_r1", CubeListBuilder.create().texOffs(62, 211).addBox(0.0F, -3.0F, -2.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 3.304F, -0.3143F, -0.6981F, 0.0F, 0.0F));

        PartDefinition bow3_r1 = bow.addOrReplaceChild("bow3_r1", CubeListBuilder.create().texOffs(62, 201).addBox(0.0F, -3.0F, -2.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -0.996F, 1.6857F, 0.6981F, 0.0F, 0.0F));

        PartDefinition bow2_r1 = bow.addOrReplaceChild("bow2_r1", CubeListBuilder.create().texOffs(62, 196).addBox(0.0F, -3.0F, -2.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -0.996F, 2.5857F, 0.2618F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 512, 512);
    }

    @Override
    public void setupAnim(ElainaBroomEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float v, float v1, float v2, float v3) {
        broom.render(poseStack, buffer, packedLight, packedOverlay);
    }
}