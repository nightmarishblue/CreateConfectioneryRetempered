package blue.nightmarish.create_confectionery.entity.model;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.entity.custom.GingerbreadManEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class GingerbreadManModel extends EntityModel<GingerbreadManEntity> {
    public static final ModelLayerLocation GINGERBREAD_MAN_LAYER = new ModelLayerLocation(
            new ResourceLocation(CreateConfectionery.MOD_ID, "gingerbread_man_model"), "main");
    public final ModelPart head;
    public final ModelPart body;
    public final ModelPart left_arm;
    public final ModelPart left_leg;
    public final ModelPart right_arm;
    public final ModelPart right_leg;

    public GingerbreadManModel(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.left_arm = root.getChild("left_arm");
        this.left_leg = root.getChild("left_leg");
        this.right_arm = root.getChild("right_arm");
        this.right_leg = root.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayers() {
        MeshDefinition meshDef = new MeshDefinition();
        PartDefinition partDef = meshDef.getRoot();
        PartDefinition head = partDef
                .addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4F, -5F, -2F, 8F, 6F, 4F, new CubeDeformation(0F))
                        .texOffs(16, 10)
                        .addBox(0F, -5F, -2F, 4F, 3F, 4F, new CubeDeformation(0F)),
                        PartPose.offset(0F, 15F, 0F));
        head.addOrReplaceChild("bone", CubeListBuilder.create()
                .texOffs(0, 26)
                .addBox(-1.5F, -4F, -1.5F, 3F, 3F, 3F, new CubeDeformation(0F))
                .texOffs(12, 26)
                .addBox(-2.5F, -1F, -2.5F, 5F, 1F, 5F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(2.25F, -4.75F, 0F, 0F, 0F, 0.1309F));
        partDef.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 10)
                .addBox(-3F, -8F, -1F, 6F, 5F, 2F, new CubeDeformation(0F))
                .texOffs(20, 0).addBox(-1F, -8F, -1F, 4F, 2F, 2F, new CubeDeformation(0F)),
            PartPose.offset(0F, 24F, 0F));
        PartDefinition left_arm = partDef.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(4F, 16F, 0F));
        left_arm.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                .texOffs(14, 17)
                .mirror()
                .addBox(-1F, -1F, -1F, 2F, 5F, 1F, new CubeDeformation(0F))
                .mirror(false),
            PartPose.offsetAndRotation(0F, 1F, 0.5F, 0F, 0F, -0.0436F));
        partDef.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(6, 17)
                .mirror()
                .addBox(-1F, 0F, -1F, 2F, 3F, 2F, new CubeDeformation(0F))
                .mirror(false),
            PartPose.offset(2F, 21F, 0F));
        PartDefinition right_arm = partDef.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-4F, 16F, 0F));
        right_arm.addOrReplaceChild("cube_r2", CubeListBuilder.create()
                .texOffs(0, 17)
                .addBox(-1F, -1F, -1F, 2F, 5F, 1F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(0F, 1F, 0.5F, 0F, 0F, 0.0436F));
        partDef.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(6, 17)
                .addBox(-1F, 0F, -1F, 2F, 3F, 2F, new CubeDeformation(0F)),
            PartPose.offset(-2F, 21F, 0F));
        return LayerDefinition.create(meshDef, 32, 32);
    }
    
    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.left_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.right_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(GingerbreadManEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw / 57.295776F;
        this.head.xRot = headPitch / 57.295776F;
        this.left_leg.xRot = Mth.cos(limbSwing) * -1F * limbSwingAmount;
        this.right_arm.xRot = Mth.cos(limbSwing * 0.6662F + 3.1415927F) * limbSwingAmount;
        this.left_arm.xRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
        this.right_leg.xRot = Mth.cos(limbSwing) * 1F * limbSwingAmount;
    }
}
