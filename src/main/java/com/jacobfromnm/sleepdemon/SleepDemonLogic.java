package com.jacobfromnm.sleepdemon;

import com.mojang.logging.LogUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class SleepDemonLogic {
    public static final String MODID = "sleepdemon";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final Random RAND = new Random();
    private static final List<String> DEMON_MESSAGES = Arrays.asList("An evil presence wakes you from sleep...",
            "A chilling shadow wrenches you from your dreams.", "Your rest is shattered by a shadowy figure.",
            "The silence breaks... something is watching.", "Your heart races. Something is close.",
            "A suffocating dread forces you from sleep.", "A shadow passes over you... it lingers.",
            "Just beyond the dark, a figure watches.", "You awake to cold, unnatural silence.", "You're not alone.",
            "A dark presence looms over you.", "A whisper in the dark... you are not safe.");

    public SleepDemonLogic() {
    }

    public static void triggerSleepDemonEvent(ServerPlayer player, BlockPos bedPos) {
        int min = (Integer) SleepDemonConfig.TELEPORT_MIN.get();
        int max = (Integer) SleepDemonConfig.TELEPORT_MAX.get();
        BlockPos safePos = findSafeTeleport(player, bedPos, min, max);
        List<SoundEvent> voiceLines = List.of(ModSounds.I_SEE_YOU.get(), ModSounds.RUN.get(),
                ModSounds.WAKE_UP.get());
        SoundEvent randomVoiceLine = voiceLines.get(RAND.nextInt(voiceLines.size()));
        player.stopSleepInBed(false, false);
        player.teleportTo((double) safePos.getX() + 0.5, (double) safePos.getY(),
                (double) safePos.getZ() + 0.5);
        if ((Boolean) SleepDemonConfig.ENABLE_LOGGING.get()) {
            LOGGER.info("[Sleep Demon Mod] Teleporting player {} to {}.", player.getName().getString(), safePos);
        }

        player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, (Integer) SleepDemonConfig.DARKNESS_DURATION.get(),
                1, false, false, false));
        if ((Boolean) SleepDemonConfig.REDUCE_PLAYER_HEALTH.get()) {
            player.hurt(net.minecraft.world.damagesource.DamageSource.GENERIC, 18.0F);
        }

        if ((Boolean) SleepDemonConfig.ENABLE_LOGGING.get()) {
            LOGGER.info("[Sleep Demon Mod] Applied darkness effect and set the player health to 1 heart.");
        }

        Level level = player.getLevel();
        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            serverLevel.setDayTime(18000L);
            if ((Boolean) SleepDemonConfig.ENABLE_LOGGING.get()) {
                LOGGER.info("[Sleep Demon Mod] Set world time to midnight.");
            }
        }

        if ((Boolean) SleepDemonConfig.ENABLE_SOUNDS.get()) {
            // First, play a cave sound to create an unsettling atmosphere...
            player.getLevel().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMBIENT_CAVE,
                    SoundSource.AMBIENT, 1.0F, 0.8F + player.getRandom().nextFloat() * 0.4F);
            player.getLevel().playSound(null, player.getX(), player.getY(), player.getZ(),
                    ModSounds.SLEEP_DEMON_WHISPER.get(),
                    SoundSource.HOSTILE, 1.7F, 1.0F);
            player.getLevel().playSound(null, player.getX(), player.getY(), player.getZ(), randomVoiceLine,
                    SoundSource.HOSTILE, 1.7F,
                    1.0F);
        }

        // Display a scary message to the player if configured...
        if ((Boolean) SleepDemonConfig.DISPLAY_MESSAGES.get()) {
            String demonMessage = DEMON_MESSAGES.get(RAND.nextInt(DEMON_MESSAGES.size()));
            player.displayClientMessage(Component.literal("§4" + demonMessage), true);
            // Re-display the message twice more to make it last longer...
            ScheduledTaskManager.schedule(() -> {
                player.displayClientMessage(Component.literal("§4" + demonMessage), true);
            }, 40);
            ScheduledTaskManager.schedule(() -> {
                player.displayClientMessage(Component.literal("§4" + demonMessage), true);
            }, 80);
        }

        if ((Boolean) SleepDemonConfig.ENABLE_LOGGING.get()) {
            LOGGER.info("[Sleep Demon Mod] Player {} teleported and effects triggered after delay.",
                    player.getName().getString());
        }

        if (!player.getLevel().isClientSide) {
            Vec3 lookVec = player.getLookAngle().scale(10.0);
            BlockPos spawnPos = new BlockPos((int) (player.getX() + lookVec.x), (int) (player.getY() + lookVec.y),
                    (int) (player.getZ() + lookVec.z));
            double dx = player.getX() - (double) spawnPos.getX();
            double dz = player.getZ() - (double) spawnPos.getZ();
            float yaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
            BlockPos groundPos = player.getLevel().getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, spawnPos);
            SleepDemonEntity demon = new SleepDemonEntity(ModEntities.SLEEP_DEMON.get(), player.getLevel());
            demon.setPos((double) groundPos.getX() + 0.5, (double) groundPos.getY(),
                    (double) groundPos.getZ() + 0.5);
            demon.setYRot(yaw);
            player.getLevel().addFreshEntity(demon);
            if ((Boolean) SleepDemonConfig.ENABLE_LOGGING.get()) {
                LOGGER.info("[Sleep Demon Mod] Spawned demon at {}", groundPos);
            }
        }

    }

    private static BlockPos findSafeTeleport(ServerPlayer player, BlockPos center, int min, int max) {
        Level level = player.getLevel();

        for (int i = 0; i < 50; ++i) {
            double angle = player.getRandom().nextDouble() * Math.PI * 2.0;
            double dist = (double) (min + player.getRandom().nextInt(max - min + 1));
            int x = center.getX() + (int) (Math.cos(angle) * dist);
            int z = center.getZ() + (int) (Math.sin(angle) * dist);
            int y = level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, x, z);
            BlockPos pos = new BlockPos(x, y, z);
            if (level.getBlockState(pos).getMaterial().isReplaceable()
                    && level.getBlockState(pos.above()).getMaterial().isReplaceable()) {
                return pos;
            }
        }

        return center;
    }

    public static void spawnTemporaryDemon(ServerPlayer player) {
        Level level = player.getLevel();
        Vec3 forward = player.getLookAngle().scale(5.0);
        BlockPos spawnPos = new BlockPos((int) (player.getX() + forward.x), (int) (player.getY() + forward.y),
                (int) (player.getZ() + forward.z));
        BlockPos groundPos = level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, spawnPos);
        SleepDemonEntity demon = new SleepDemonEntity(ModEntities.SLEEP_DEMON.get(), level);
        demon.setPos((double) groundPos.getX(), (double) groundPos.getY(), (double) groundPos.getZ());
        demon.setYRot(player.getYRot());
        level.addFreshEntity(demon);
        ScheduledTaskManager.schedule(() -> {
            demon.discard();
        }, 40);
    }

    public static void spawnBedsideDemon(ServerPlayer player, BlockPos bedPos) {
        ServerLevel level = (ServerLevel) player.getLevel();
        BlockState state = level.getBlockState(bedPos);
        if (state.getBlock() instanceof BedBlock) {
            Direction facing = state.getValue(BedBlock.FACING);
            BedPart part = state.getValue(BedBlock.PART);
            BlockPos footPos = part == BedPart.HEAD ? bedPos.relative(facing.getOpposite()) : bedPos;
            BlockPos spawnPos = footPos.relative(facing.getOpposite());
            boolean isClear = level.getBlockState(spawnPos).getMaterial().isReplaceable()
                    && level.getBlockState(spawnPos.above()).getMaterial().isReplaceable();
            if (!isClear) {
                if ((Boolean) SleepDemonConfig.ENABLE_LOGGING.get()) {
                    LOGGER.info("[Sleep Demon Mod] Foot of bed at {} is blocked. Skipping spawn.", spawnPos);
                }

            } else {
                SleepDemonEntity demon = new SleepDemonEntity(ModEntities.SLEEP_DEMON.get(), level);
                if (demon != null) {
                    demon.setInvisible(true);
                    demon.setSilent(true);
                    demon.setPos((double) spawnPos.getX() + 0.5, (double) spawnPos.getY(),
                            (double) spawnPos.getZ() + 0.5);
                    Vec3 demonPos = demon.position();
                    Vec3 playerPos = player.position();
                    double dx = playerPos.x - demonPos.x;
                    double dz = playerPos.z - demonPos.z;
                    float yaw = (float) (Mth.atan2(dz, dx) * 57.29577951308232) - 90.0F;
                    demon.setYRot(yaw);
                    demon.setXRot(yaw);
                    demon.setYHeadRot(yaw);
                    player.getLevel().playSound(null, player.getX(), player.getY(), player.getZ(),
                            ModSounds.SLEEP_DEMON_WHISPER.get(), SoundSource.HOSTILE, 1.7F, 1.0F);
                    level.addFreshEntity(demon);
                    if ((Boolean) SleepDemonConfig.ENABLE_LOGGING.get()) {
                        LOGGER.info("[Sleep Demon Mod] Spawned bedside demon at {}", spawnPos);
                    }

                    int despawnDelay = 20 + level.random.nextInt(40);
                    ScheduledTaskManager.schedule(() -> {
                        if (demon.isAlive()) {
                            demon.discard();
                        }

                    }, despawnDelay);
                    ScheduledTaskManager.scheduleRepeating(() -> {
                        if (!player.isSleeping() && demon.isAlive()) {
                            demon.discard();
                            return true;
                        } else {
                            return false;
                        }
                    }, 10);
                }
            }
        }
    }
}
