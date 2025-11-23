package cn.nutminds.eidolonedoni.item;

import cn.nutminds.eidolonedoni.registry.EEEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.ConsumableItem;

public class StuffedWitherSkeletonSkullBowlItem extends ConsumableItem {

    public StuffedWitherSkeletonSkullBowlItem(Properties properties) {
        super(properties, true, true);
    }

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        if (consumer.hasEffect(MobEffects.WITHER)) {
            MobEffectInstance instance = consumer.getEffect(MobEffects.WITHER);
            if (instance != null) {
                consumer.removeEffect(MobEffects.WITHER);
                consumer.addEffect(new MobEffectInstance(EEEffects.REANIMATE, instance.getDuration(), instance.getAmplifier()));
            }
        }
    }
}