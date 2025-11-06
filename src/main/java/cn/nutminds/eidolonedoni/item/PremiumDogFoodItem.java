package cn.nutminds.eidolonedoni.item;

import alexthw.eidolon_repraised.registries.EidolonPotions;
import cn.nutminds.eidolonedoni.EidolonEdoni;
import cn.nutminds.eidolonedoni.registry.EEItems;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.registry.ModParticleTypes;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.MathUtils;
import vectorwing.farmersdelight.common.utility.TextUtils;

import java.util.List;
import java.util.function.Supplier;

@EventBusSubscriber(modid = EidolonEdoni.MODID)
public class PremiumDogFoodItem extends ConsumableItem {
    public static final Supplier<List<MobEffectInstance>> EFFECTS = Suppliers.memoize (() -> Lists.newArrayList(
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6000, 0),
            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 6000, 1),
            new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0),
            new MobEffectInstance(EidolonPotions.REINFORCED_EFFECT, 6000, 1)));

    public PremiumDogFoodItem(Properties properties) {
        super(properties);
    }

    @SubscribeEvent
    public static void onPremiumDogFoodApplied(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Entity target = event.getTarget();
        ItemStack itemStack = event.getItemStack();

        if (target instanceof LivingEntity entity && target.getType().is(ModTags.DOG_FOOD_USERS)) {
            boolean isTameable = entity instanceof TamableAnimal;

            if (entity.isAlive() && (!isTameable || ((TamableAnimal) entity).isTame()) && itemStack.getItem().equals(EEItems.PREMIUM_DOG_FOOD.get())) {
                entity.setHealth(entity.getMaxHealth());
                for (MobEffectInstance effect : EFFECTS.get()) {
                    entity.addEffect(new MobEffectInstance(effect));
                }
                entity.level().playSound(null, target.blockPosition(), SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);

                for (int i = 0; i < 5; ++i) {
                    double xSpeed = MathUtils.RAND.nextGaussian() * 0.02D;
                    double ySpeed = MathUtils.RAND.nextGaussian() * 0.02D;
                    double zSpeed = MathUtils.RAND.nextGaussian() * 0.02D;
                    entity.level().addParticle(ModParticleTypes.STAR.get(), entity.getRandomX(1.0D), entity.getRandomY() + 0.5D, entity.getRandomZ(1.0D), xSpeed, ySpeed, zSpeed);
                }

                for (int i = 0; i < 5; ++i) {
                    double xSpeed = MathUtils.RAND.nextGaussian() * 0.02D;
                    double ySpeed = MathUtils.RAND.nextGaussian() * 0.02D;
                    double zSpeed = MathUtils.RAND.nextGaussian() * 0.02D;
                    entity.level().addParticle(ParticleTypes.HAPPY_VILLAGER, entity.getRandomX(1.0D), entity.getRandomY() + 0.5D, entity.getRandomZ(1.0D), xSpeed, ySpeed, zSpeed);
                }

                if (itemStack.getCraftingRemainingItem() != ItemStack.EMPTY && !player.isCreative()) {
                    player.addItem(itemStack.getCraftingRemainingItem());
                    itemStack.shrink(1);
                }

                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag isAdvanced) {
        if (!Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            return;
        }

        MutableComponent textWhenFeeding = TextUtils.getTranslation("tooltip.dog_food.when_feeding");
        tooltip.add(textWhenFeeding.withStyle(ChatFormatting.GRAY));

        for (MobEffectInstance effectInstance : EFFECTS.get()) {
            MutableComponent effectDescription = Component.literal(" ");
            MutableComponent effectName = Component.translatable(effectInstance.getDescriptionId());
            effectDescription.append(effectName);
            MobEffect effect = effectInstance.getEffect().value();

            if (effectInstance.getAmplifier() > 0) {
                effectDescription.append(" ").append(Component.translatable("potion.potency." + effectInstance.getAmplifier()));
            }

            if (effectInstance.getDuration() > 20) {
                effectDescription.append(" (").append(MobEffectUtil.formatDuration(effectInstance, 1.0F, context.tickRate())).append(")");
            }

            tooltip.add(effectDescription.withStyle(effect.getCategory().getTooltipFormatting()));
        }
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target, InteractionHand hand) {
        if (target instanceof Wolf wolf) {
            if (wolf.isAlive() && wolf.isTame()) {
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}

