package cn.nutminds.eidolonedoni.item;

import cn.nutminds.eidolonedoni.EEConfig;
import cn.nutminds.eidolonedoni.EidolonEdoni;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.utility.TextUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class MerammerFoodItem extends ConsumableItem {

    public static final TagKey<MobEffect> MERAMMER_FOOD_BLACKLIST = create("merammer_food_blacklist");

    private static TagKey<MobEffect> create(String string) {
        return TagKey.create(Registries.MOB_EFFECT, EidolonEdoni.modid(string));
    }

    public MerammerFoodItem(Properties properties) {
        super(properties);
    }

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        Iterator<MobEffectInstance> itr = consumer.getActiveEffects().iterator();
        ArrayList<Holder<MobEffect>> compatibleEffects = new ArrayList<>();


        while (itr.hasNext()) {
            MobEffectInstance effect = itr.next();
            if (effect.getEffect().value().getCategory().equals(MobEffectCategory.BENEFICIAL)) {
                compatibleEffects.add(effect.getEffect());
            }
        }
        level.registryAccess().registryOrThrow(Registries.MOB_EFFECT).getTagOrEmpty(MERAMMER_FOOD_BLACKLIST).forEach(compatibleEffects::remove);

        if (!compatibleEffects.isEmpty()) {
            MobEffectInstance selectedEffect = consumer.getEffect(compatibleEffects.get(level.random.nextInt(compatibleEffects.size())));
            if (selectedEffect != null) {
                if (selectedEffect.getDuration() >= 0){
                    consumer.removeEffect(selectedEffect.getEffect());
                }
                consumer.addEffect(new MobEffectInstance(selectedEffect.getEffect(),
                        selectedEffect.getDuration() / (EEConfig.MERAMMER_FOOD_DURATION_RATE.get()),
                        selectedEffect.getAmplifier() + 1));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag isAdvanced) {
        if (Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            MutableComponent textEmpty = Component.translatable(EidolonEdoni.MODID + ".tooltip.merammer_food", new Object[0]);
            tooltip.add(textEmpty.withStyle(ChatFormatting.BLUE));
            Objects.requireNonNull(tooltip);
            TextUtils.addFoodEffectTooltip(stack, tooltip::add, 1.0F, context.tickRate());
        }
    }
}
