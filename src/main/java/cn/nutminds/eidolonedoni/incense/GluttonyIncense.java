package cn.nutminds.eidolonedoni.incense;

import alexthw.eidolon_repraised.api.ritual.IncenseRitual;
import alexthw.eidolon_repraised.client.particle.Particles;
import cn.nutminds.eidolonedoni.EidolonEdoni;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class GluttonyIncense extends IncenseRitual {
    public GluttonyIncense(ResourceLocation registryName) {
        super(36000, registryName);
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

    public static final TagKey<MobEffect> GLUTTONY_INCENSE_WHITELIST = create("gluttony_incense_whitelist");

    private static TagKey<MobEffect> create(String string) {
        return TagKey.create(Registries.MOB_EFFECT, EidolonEdoni.modid(string));
    }

    @Override
    public void tickEffect(int age) {
        if (age % 10 == 0) {
            Level level = censer.getLevel();
            BlockPos pos = censer.getBlockPos();
            assert level != null;
            for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(range()))) {
                entity.getActiveEffects().removeIf(effect -> effect.getEffect().is(GLUTTONY_INCENSE_WHITELIST) && !effect.getCures().isEmpty());
                if (entity instanceof Player player && !player.getFoodData().needsFood()) {
                    player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() - 1);
                }
            }
        }
    }

    @Override
    public void animateParticles(int burnCounter, BlockPos blockPos, Level level) {
        super.animateParticles(burnCounter, blockPos, level);
        double x = blockPos.getX();
        double y = blockPos.getY() + 1;
        double z = blockPos.getZ();
        if (level.random.nextInt(4) == 0)
            for (int i = 0; i < 5; i++) {
                Particles.spawnParticle(level, ParticleTypes.FALLING_NECTAR,
                        x, y - .5, z,
                        0, 0, 0,
                        range(), 0, range());
            }
    }
}
