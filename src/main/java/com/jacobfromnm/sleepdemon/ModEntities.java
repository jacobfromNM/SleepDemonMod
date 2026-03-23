package com.jacobfromnm.sleepdemon;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES;
    public static final RegistryObject<EntityType<SleepDemonEntity>> SLEEP_DEMON;

    public ModEntities() {
    }

    static {
        ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "sleepdemon");
        SLEEP_DEMON = ENTITY_TYPES.register("sleep_demon", () -> {
            return EntityType.Builder.of(SleepDemonEntity::new, MobCategory.MONSTER).sized(0.6F, 1.95F)
                    .clientTrackingRange(8)
                    .build("sleepdemon:sleep_demon");
        });
    }
}
