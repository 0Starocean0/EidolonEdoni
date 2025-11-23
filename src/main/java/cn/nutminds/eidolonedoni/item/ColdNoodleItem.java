package cn.nutminds.eidolonedoni.item;

import alexthw.eidolon_repraised.registries.EidolonPotions;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class ColdNoodleItem extends ConsumableItem {

    public ColdNoodleItem(Properties properties) {
        super(properties, true, true);
    }

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        if (consumer.hasEffect(EidolonPotions.CHILLED_EFFECT)) {
            MobEffectInstance instance = consumer.getEffect(EidolonPotions.CHILLED_EFFECT);
            if (instance != null) {
                int chilledDuration = instance.getDuration();
                consumer.removeEffect(EidolonPotions.CHILLED_EFFECT);
                consumer.addEffect(new MobEffectInstance(ModEffects.COMFORT, chilledDuration));
            }
        }
    }
}