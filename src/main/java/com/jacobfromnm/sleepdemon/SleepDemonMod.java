package com.jacobfromnm.sleepdemon;

import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("sleepdemon")
public class SleepDemonMod {
    public static final String MODID = "sleepdemon";

    public SleepDemonMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::onConfigLoad);
        ModSounds.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModLoadingContext.get().registerConfig(Type.COMMON, SleepDemonConfig.COMMON_CONFIG);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    private void clientSetup(FMLClientSetupEvent event) {
        if (ModList.get().isLoaded("cloth_config")) {
            ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((mc, parent) -> SleepDemonConfigScreen.create(parent))
            );
        }
    }

    private void onConfigLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == SleepDemonConfig.COMMON_CONFIG) {
            SleepDemonConfig.onLoad(event.getConfig());
        }
    }
}
