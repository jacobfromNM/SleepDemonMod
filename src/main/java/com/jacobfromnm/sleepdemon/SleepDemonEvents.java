package com.jacobfromnm.sleepdemon;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@EventBusSubscriber(modid = "sleepdemon")
public class SleepDemonEvents {
    private static final Logger LOGGER = LogManager.getLogger();

    public SleepDemonEvents() {
    }

    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event) {
        Player var2 = event.getEntity();
        if (var2 instanceof ServerPlayer player) {
            if (!player.getLevel().isClientSide) {
                BlockPos bedPos = event.getPos();
                double chance = (Double) SleepDemonConfig.SLEEP_DEMON_CHANCE.get();
                if (player.getRandom().nextDouble() < chance) {
                    ScheduledTaskManager.schedule(() -> {
                        if (player.isSleeping()) {
                            ScheduledTaskManager.schedule(() -> {
                                if ((Boolean) SleepDemonConfig.ENABLE_LOGGING.get()) {
                                    LOGGER.info("[Sleep Demon Mod] Rolled for teleportation...");
                                }

                                SleepDemonLogic.triggerSleepDemonEvent(player, bedPos);
                            }, 40);
                        }

                    }, 20);
                } else {
                    double secondRoll = player.getRandom().nextDouble();
                    if (secondRoll < chance) {
                        ScheduledTaskManager.schedule(() -> {
                            if (player.isSleeping()) {
                                ScheduledTaskManager.schedule(() -> {
                                    if ((Boolean) SleepDemonConfig.ENABLE_LOGGING.get()) {
                                        LOGGER.info("[Sleep Demon Mod] Rolled for bedside spawn...");
                                    }

                                    SleepDemonLogic.spawnBedsideDemon(player, bedPos);
                                }, 0);
                            }

                        }, 0);
                    } else {
                        LOGGER.info("[Sleep Demon Mod] No scare triggered. Sweet dreams!");
                    }
                }

            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == Phase.END) {
            ServerLevel serverLevel = ServerLifecycleHooks.getCurrentServer().getLevel(ServerLevel.NETHER);
            if (serverLevel != null) {
                ScheduledTaskManager.tick(serverLevel.getServer());
            }
        }

    }
}
