package com.jacobfromnm.sleepdemon;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SleepDemonConfigScreen {

    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Sleep Demon Configuration"))
                .setSavingRunnable(SleepDemonConfig::save);

        ConfigEntryBuilder eb = builder.entryBuilder();

        ConfigCategory spawn = builder.getOrCreateCategory(Component.literal("Spawn Settings"));
        spawn.addEntry(eb.startDoubleField(Component.literal("Spawn Chance"), SleepDemonConfig.SLEEP_DEMON_CHANCE.get())
                .setDefaultValue(0.1).setMin(0.01).setMax(1.0)
                .setSaveConsumer(v -> SleepDemonConfig.SLEEP_DEMON_CHANCE.set(v))
                .build());
        spawn.addEntry(eb.startIntField(Component.literal("Teleport Min Distance"), SleepDemonConfig.TELEPORT_MIN.get())
                .setDefaultValue(50).setMin(1).setMax(200)
                .setSaveConsumer(v -> SleepDemonConfig.TELEPORT_MIN.set(v))
                .build());
        spawn.addEntry(eb.startIntField(Component.literal("Teleport Max Distance"), SleepDemonConfig.TELEPORT_MAX.get())
                .setDefaultValue(100).setMin(1).setMax(500)
                .setSaveConsumer(v -> SleepDemonConfig.TELEPORT_MAX.set(v))
                .build());
        spawn.addEntry(eb.startBooleanToggle(Component.literal("Reduce Player Health to 1 Heart"), SleepDemonConfig.REDUCE_PLAYER_HEALTH.get())
                .setDefaultValue(true)
                .setSaveConsumer(v -> SleepDemonConfig.REDUCE_PLAYER_HEALTH.set(v))
                .build());

        ConfigCategory sounds = builder.getOrCreateCategory(Component.literal("Sound Settings"));
        sounds.addEntry(eb.startBooleanToggle(Component.literal("Enable Scary Sounds"), SleepDemonConfig.ENABLE_SOUNDS.get())
                .setDefaultValue(true)
                .setSaveConsumer(v -> SleepDemonConfig.ENABLE_SOUNDS.set(v))
                .build());
        sounds.addEntry(eb.startBooleanToggle(Component.literal("Display Scary Messages"), SleepDemonConfig.DISPLAY_MESSAGES.get())
                .setDefaultValue(true)
                .setSaveConsumer(v -> SleepDemonConfig.DISPLAY_MESSAGES.set(v))
                .build());
        sounds.addEntry(eb.startIntField(Component.literal("Darkness Duration (ticks)"), SleepDemonConfig.DARKNESS_DURATION.get())
                .setDefaultValue(600).setMin(0).setMax(6000)
                .setSaveConsumer(v -> SleepDemonConfig.DARKNESS_DURATION.set(v))
                .build());

        ConfigCategory logging = builder.getOrCreateCategory(Component.literal("Logging"));
        logging.addEntry(eb.startBooleanToggle(Component.literal("Enable Logging"), SleepDemonConfig.ENABLE_LOGGING.get())
                .setDefaultValue(false)
                .setSaveConsumer(v -> SleepDemonConfig.ENABLE_LOGGING.set(v))
                .build());

        return builder.build();
    }
}
