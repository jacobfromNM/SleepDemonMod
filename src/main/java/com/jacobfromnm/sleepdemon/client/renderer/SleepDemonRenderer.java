package com.jacobfromnm.sleepdemon.client.renderer;

import com.jacobfromnm.sleepdemon.SleepDemonEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SleepDemonRenderer extends MobRenderer<SleepDemonEntity, HumanoidModel<SleepDemonEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("sleepdemon:textures/entity/demon.png");

    public SleepDemonRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
    }

    public ResourceLocation getTextureLocation(SleepDemonEntity entity) {
        return TEXTURE;
    }
}
