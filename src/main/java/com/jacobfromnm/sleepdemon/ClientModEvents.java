package com.jacobfromnm.sleepdemon;

import com.jacobfromnm.sleepdemon.client.renderer.SleepDemonRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = "sleepdemon", bus = Bus.MOD, value = { Dist.CLIENT })
public class ClientModEvents {
    public ClientModEvents() {
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.SLEEP_DEMON.get(), SleepDemonRenderer::new);
    }
}
