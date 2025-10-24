package cn.nutminds.eidolonedoni.incense;

import alexthw.eidolon_repraised.client.particle.Particles;
import alexthw.eidolon_repraised.common.incense.GenericPotionIncense;
import cn.nutminds.eidolonedoni.registry.EEEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class ChorusIncense extends GenericPotionIncense {
    public ChorusIncense(ResourceLocation registryName) {
        super(24000, registryName);
    }

    @Override
    public MobEffectInstance getEffect(Level level, BlockPos blockPos, LivingEntity livingEntity) {
        return new MobEffectInstance(EEEffects.FAITHFUL, 6000, 2);
    }

    @Override
    public float getRed() {
        return 186 / 255.0f;
    }

    @Override
    public float getGreen() {
        return 155 / 255.0f;
    }

    @Override
    public float getBlue() {
        return 186 / 255.0f;
    }

    @Override
    public void animateParticles(int burnCounter, BlockPos blockPos, Level level) {
        super.animateParticles(burnCounter, blockPos, level);
        double x = blockPos.getX();
        double y = blockPos.getY() + 1;
        double z = blockPos.getZ();
        if (level.random.nextInt(4) == 0)
            for (int i = 0; i < 5; i++) {
                Particles.spawnParticle(level, ParticleTypes.END_ROD,
                        x, y - .5, z,
                        0, 0.01, 0,
                        range(), 0, range());
            }
    }
}
