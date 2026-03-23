package com.jacobfromnm.sleepdemon;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS;
    public static final RegistryObject<SoundEvent> SLEEP_DEMON_WHISPER;
    public static final RegistryObject<SoundEvent> RUN;
    public static final RegistryObject<SoundEvent> I_SEE_YOU;
    public static final RegistryObject<SoundEvent> WAKE_UP;

    public ModSounds() {
    }

    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }

    static {
        SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "sleepdemon");
        SLEEP_DEMON_WHISPER = SOUNDS.register("sleep_demon_whisper", () -> {
            return new SoundEvent(new ResourceLocation("sleepdemon", "sleep_demon_whisper"));
        });
        RUN = SOUNDS.register("run", () -> {
            return new SoundEvent(new ResourceLocation("sleepdemon", "run"));
        });
        I_SEE_YOU = SOUNDS.register("i_see_you", () -> {
            return new SoundEvent(new ResourceLocation("sleepdemon", "i_see_you"));
        });
        WAKE_UP = SOUNDS.register("wake_up", () -> {
            return new SoundEvent(new ResourceLocation("sleepdemon", "wake_up"));
        });
    }
}
