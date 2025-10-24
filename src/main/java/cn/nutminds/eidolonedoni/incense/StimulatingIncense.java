package cn.nutminds.eidolonedoni.incense;

import alexthw.eidolon_repraised.api.ritual.IncenseRitual;
import alexthw.eidolon_repraised.client.particle.Particles;
import alexthw.eidolon_repraised.common.incense.GenericPotionIncense;
import alexthw.eidolon_repraised.registries.EidolonParticles;
import cn.nutminds.eidolonedoni.registry.EEEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import vectorwing.farmersdelight.common.registry.ModEffects;
import vectorwing.farmersdelight.common.registry.ModParticleTypes;

import java.util.ArrayList;
import java.util.List;

public class StimulatingIncense extends IncenseRitual {
    public StimulatingIncense(ResourceLocation registryName) {
        super(6000, registryName);
    }

    @Override
    public void tickEffect(int age) {
        if (age % 20 == 0) {
            Level level = censer.getLevel();
            BlockPos pos = censer.getBlockPos();
            assert level != null;
            for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(range()))) {
                entity.addEffect(new MobEffectInstance(EEEffects.EXCITED, 6000, 1));
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 6000, 1));
            }
        }
    }
    @Override
    public float getRed() {
        return 1f;
    }

    @Override
    public float getGreen() {
        return 0f;
    }

    @Override
    public float getBlue() {
        return 0f;
    }

    @Override
    public void animateParticles(int burnCounter, BlockPos blockPos, Level level) {
        super.animateParticles(burnCounter, blockPos, level);
        double x = blockPos.getX();
        double y = blockPos.getY() + 1;
        double z = blockPos.getZ();
        if (level.random.nextInt(4) == 0) {
            for (int i = 0; i < 5; i++) {
                Particles.spawnParticle(level, ParticleTypes.CRIMSON_SPORE,
                        x, y - .5, z,
                        0, -0.01, 0,
                        range(), 0, range());
            }

            Particles.create(EidolonParticles.SMOKE_PARTICLE.get())
                    .setAlpha(0.35f, 0).setScale(0.375f, 0.125f).setLifetime(80)
                    .randomOffset(range() * 0.75, 0.1).randomVelocity(0.025f, 0.025f)
                    .addVelocity(0, -0.0125f, 0)
                    .setColor(1F, 0F, 0, 0.75f, 0.1f, 0.1f)
                    .repeat(level, x, y + 0.125, z, 5);
        }
    }
}
