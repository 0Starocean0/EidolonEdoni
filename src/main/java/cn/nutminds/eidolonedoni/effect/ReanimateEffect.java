package cn.nutminds.eidolonedoni.effect;

import alexthw.eidolon_repraised.registries.EidolonPotions;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;

public class ReanimateEffect extends MobEffect {
    public ReanimateEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF722D2D);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity.getType().is(EntityTypeTags.UNDEAD) || livingEntity.hasEffect(EidolonPotions.UNDEATH_EFFECT)) {
            if (livingEntity.getHealth() < livingEntity.getMaxHealth()) {
                livingEntity.heal(1);
            }
        }
        return true;
    }


    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 50 >> amplifier;
        return i > 0 ? duration % i == 0 : true;
    }
}