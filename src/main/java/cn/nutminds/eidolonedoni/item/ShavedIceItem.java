package cn.nutminds.eidolonedoni.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.ConsumableItem;

public class ShavedIceItem extends ConsumableItem
{
    public ShavedIceItem(Properties properties, boolean hasFoodEffectTooltip) {
        super(properties, hasFoodEffectTooltip);
    }

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        consumer.clearFire();
    }
}