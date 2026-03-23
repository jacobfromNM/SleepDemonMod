package com.jacobfromnm.sleepdemon.renderer;

import com.jacobfromnm.sleepdemon.SleepDemonEntity;
import javax.annotation.Nonnull;
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

    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull SleepDemonEntity entity) {
        return TEXTURE;
    }
}
