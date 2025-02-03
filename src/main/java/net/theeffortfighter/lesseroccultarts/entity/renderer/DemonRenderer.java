package net.theeffortfighter.lesseroccultarts.entity.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.theeffortfighter.lesseroccultarts.LesserOccultArts;
import net.theeffortfighter.lesseroccultarts.entity.DemonEntity;
import net.theeffortfighter.lesseroccultarts.entity.model.DemonModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DemonRenderer extends GeoEntityRenderer<DemonEntity> {

    public DemonRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new DemonModel());
    }

    @Override
    public Identifier getTextureResource(DemonEntity animatable) {
        return new Identifier(LesserOccultArts.MOD_ID, "textures/entity/demon.png");
    }

    @Override
    public RenderLayer getRenderType(DemonEntity animatable, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, VertexConsumer buffer, int packedLight, Identifier texture) {
        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
