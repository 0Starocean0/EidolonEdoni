package cn.nutminds.eidolonedoni.item;

import cn.nutminds.eidolonedoni.EidolonEdoni;
import cn.nutminds.eidolonedoni.registry.EEEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.registry.ModParticleTypes;
import vectorwing.farmersdelight.common.utility.MathUtils;

public class JockeyPieSliceItem extends ConsumableItem {
    public JockeyPieSliceItem(Properties properties) {
        super(properties);
    }

    private static TagKey<EntityType<?>> create(String path) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID, path));
    }

    public static final TagKey<EntityType<?>> JOCKEY_PIE_USERS = create("jockey_pie_users");

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        if (consumer.isPassenger()) {
            Entity vehicle = consumer.getVehicle();
            if (vehicle instanceof LivingEntity entity && entity.isAlive()) {
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1));
                entity.addEffect(new MobEffectInstance(EEEffects.REANIMATE, 600, 1));
                for (int i = 0; i < 5; ++i) {
                    double xSpeed = MathUtils.RAND.nextGaussian() * 0.02D;
                    double ySpeed = MathUtils.RAND.nextGaussian() * 0.02D;
                    double zSpeed = MathUtils.RAND.nextGaussian() * 0.02D;
                    entity.level().addParticle(ModParticleTypes.STAR.get(), entity.getRandomX(1.0D), entity.getRandomY() + 0.5D, entity.getRandomZ(1.0D), xSpeed, ySpeed, zSpeed);
                }
            }
        }
    }
}
