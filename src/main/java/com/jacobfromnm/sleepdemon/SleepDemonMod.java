package com.jacobfromnm.sleepdemon;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("sleepdemon")
public class SleepDemonMod {
    public static final String MODID = "sleepdemon";

    public SleepDemonMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        ModSounds.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModLoadingContext.get().registerConfig(Type.COMMON, SleepDemonConfig.COMMON_CONFIG);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }
}
