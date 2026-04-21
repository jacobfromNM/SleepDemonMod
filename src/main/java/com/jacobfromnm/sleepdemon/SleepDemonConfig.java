package com.jacobfromnm.sleepdemon;

import net.minecraftforge.common.ForgeConfigSpec;

public class SleepDemonConfig {
        private static net.minecraftforge.fml.config.ModConfig activeConfig;

        public static void onLoad(net.minecraftforge.fml.config.ModConfig config) {
                activeConfig = config;
        }

        public static void save() {
                if (activeConfig != null) activeConfig.save();
        }

        public static final ForgeConfigSpec COMMON_CONFIG;
        public static final ForgeConfigSpec.DoubleValue SLEEP_DEMON_CHANCE;
        public static final ForgeConfigSpec.IntValue TELEPORT_MIN;
        public static final ForgeConfigSpec.IntValue TELEPORT_MAX;
        public static final ForgeConfigSpec.BooleanValue REDUCE_PLAYER_HEALTH;
        public static final ForgeConfigSpec.BooleanValue ENABLE_SOUNDS;
        public static final ForgeConfigSpec.BooleanValue DISPLAY_MESSAGES;
        public static final ForgeConfigSpec.IntValue DARKNESS_DURATION;
        public static final ForgeConfigSpec.BooleanValue ENABLE_LOGGING;

        static {
                ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
                builder.comment("Sleep Demon Mod Configuration").push("general");

                builder.comment("Spawn Settings").push("spawn");
                SLEEP_DEMON_CHANCE = builder
                                .comment("Chance for the sleep demon to wake/teleport the player (Default: 0.1, Range: 0.01-1.0)")
                                .defineInRange("sleep_demon_chance", 0.10, 0.01, 1.0);
                TELEPORT_MIN = builder.comment("Minimum teleport distance from bed (Default: 50, Range: 1-200)")
                                .defineInRange("teleport_min", 50, 1, 200);
                TELEPORT_MAX = builder.comment("Maximum teleport distance from bed (Default: 100, Range: 1-500)")
                                .defineInRange("teleport_max", 100, 1, 500);
                REDUCE_PLAYER_HEALTH = builder
                                .comment("Reduce player health to 1 heart when teleported (Default: true)")
                                .define("reduce_player_health", true);
                builder.pop();

                builder.comment("Sound Settings").push("sounds");
                ENABLE_SOUNDS = builder.comment("Enable scary sounds (Default: true)").define("enable_sounds", true);
                DISPLAY_MESSAGES = builder
                                .comment("Display scary messages to the player when they're teleported (Default: true)")
                                .define("display_messages", true);
                DARKNESS_DURATION = builder
                                .comment("Duration (in ticks) of the darkness effect (Default: 600, Range: 0-6000)")
                                .defineInRange("darkness_duration", 600, 0, 6000);
                builder.pop();

                builder.comment("Logging Settings").push("logging");
                ENABLE_LOGGING = builder
                                .comment("Enable detailed debug logging for Sleep Demon events (Default: false)")
                                .define("enable_logging", false);
                builder.pop();

                builder.pop();
                COMMON_CONFIG = builder.build();
        }
}
