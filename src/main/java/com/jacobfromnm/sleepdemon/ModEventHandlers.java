package com.jacobfromnm.sleepdemon;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = "sleepdemon", bus = Bus.MOD)
public class ModEventHandlers {
    public ModEventHandlers() {
    }

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SLEEP_DEMON.get(), SleepDemonEntity.createAttributes().build());
    }
}
