package com.jacobfromnm.sleepdemon;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class SleepDemonEntity extends Monster {
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 16.0);
    }

    public SleepDemonEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setCanPickUpLoot(true);
    }

    public void tick() {
        super.tick();
        if (!this.getLevel().isClientSide && this.tickCount > 40) {
            this.discard();
        }

    }
}
