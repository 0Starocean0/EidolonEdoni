package cn.nutminds.eidolonedoni.item;

import alexthw.eidolon_repraised.registries.EidolonPotions;
import cn.nutminds.eidolonedoni.EidolonEdoni;
import cn.nutminds.eidolonedoni.registry.EEItems;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.registry.ModParticleTypes;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.MathUtils;
import vectorwing.farmersdelight.common.utility.TextUtils;

import java.util.List;

@EventBusSubscriber(modid = EidolonEdoni.MODID)
public class DarkFeedItem extends Item {

    public DarkFeedItem(Properties properties) {
        super(properties);
    }

    public static final List<MobEffectInstance> EFFECTS = Lists.newArrayList(
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6000, 3),
            new MobEffectInstance(MobEffects.JUMP, 6000, 1));

    @SubscribeEvent
    public static void onDarkFeedApplied(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Entity target = event.getTarget();
        ItemStack heldStack = event.getItemStack();

        if (target instanceof LivingEntity entity && target.getType().is(ModTags.HORSE_FEED_USERS)) {
            boolean isTameable = entity instanceof AbstractHorse;

            if (entity.isAlive() && (!isTameable || ((AbstractHorse) entity).isTamed()) && heldStack.getItem().equals(EEItems.DARK_FEED.get())) {
                entity.setHealth(entity.getMaxHealth());
                if (entity.getType().is(EntityTypeTags.UNDEAD) || entity.hasEffect(EidolonPotions.UNDEATH_EFFECT)) {
                    for (MobEffectInstance effect : EFFECTS) {
                        entity.addEffect(new MobEffectInstance(effect));
                    }
                    for (int i = 0; i < 5; ++i) {
                        double d0 = MathUtils.RAND.nextGaussian() * 0.02D;
                        double d1 = MathUtils.RAND.nextGaussian() * 0.02D;
                        double d2 = MathUtils.RAND.nextGaussian() * 0.02D;
                        entity.level().addParticle(ModParticleTypes.STAR.get(), entity.getRandomX(1.0D), entity.getRandomY() + 0.5D, entity.getRandomZ(1.0D), d0, d1, d2);
                    }
                } /* else if (entity instanceof Horse horse) {
                    horse.convertTo(EntityType.ZOMBIE_HORSE, true);
                    for (int i = 0; i < 5; ++i) {
                        double d0 = MathUtils.RAND.nextGaussian() * 0.02D;
                        double d1 = MathUtils.RAND.nextGaussian() * 0.02D;
                        double d2 = MathUtils.RAND.nextGaussian() * 0.02D;
                        entity.level().addParticle(ParticleTypes.RAID_OMEN, entity.getRandomX(1.0D), entity.getRandomY() + 0.5D, entity.getRandomZ(1.0D), d0, d1, d2);
                    }
                    entity.level().playSound(null, target.blockPosition(), SoundEvents.ZOMBIE_VILLAGER_CONVERTED, SoundSource.PLAYERS, 0.8F, 0.8F);
                } */ else {
                    entity.addEffect(new MobEffectInstance(EidolonPotions.UNDEATH_EFFECT, -1));
                    for (int i = 0; i < 5; ++i) {
                        double d0 = MathUtils.RAND.nextGaussian() * 0.02D;
                        double d1 = MathUtils.RAND.nextGaussian() * 0.02D;
                        double d2 = MathUtils.RAND.nextGaussian() * 0.02D;
                        entity.level().addParticle(ParticleTypes.RAID_OMEN, entity.getRandomX(1.0D), entity.getRandomY() + 0.5D, entity.getRandomZ(1.0D), d0, d1, d2);
                    }
                    entity.level().playSound(null, target.blockPosition(), SoundEvents.ZOMBIE_VILLAGER_CONVERTED, SoundSource.PLAYERS, 0.8F, 0.8F);
                }
                entity.level().playSound(null, target.blockPosition(), SoundEvents.HORSE_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);

                if (!player.isCreative()) {
                    heldStack.shrink(1);
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
        tooltip.add(TextUtils.getTranslation("tooltip.dark_feed.when_feeding_undead").withStyle(ChatFormatting.GRAY));
        for (MobEffectInstance effectInstance : EFFECTS) {

            MutableComponent undeadEffectDescription = Component.literal(" ");
            undeadEffectDescription.append(Component.translatable(effectInstance.getDescriptionId()));
            MobEffect effect = effectInstance.getEffect().value();

            if (effectInstance.getAmplifier() > 0) {
                undeadEffectDescription.append(" ").append(Component.translatable("potion.potency." + effectInstance.getAmplifier()));
            }

            if (effectInstance.getDuration() > 20) {
                undeadEffectDescription.append(" (").append(MobEffectUtil.formatDuration(effectInstance, 1.0F, context.tickRate())).append(")");
            }
            tooltip.add(undeadEffectDescription.withStyle(effectInstance.getEffect().value().getCategory().getTooltipFormatting()));
        }

        tooltip.add(Component.empty());

        /*
        tooltip.add(TextUtils.getTranslation("tooltip.dark_feed.when_feeding_horse").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal(" ").append(Component.translatable("tooltip.dark_feed.when_feeding_horse_effect").withStyle(ChatFormatting.RED)));

        tooltip.add(Component.empty());
        */

        MobEffectInstance undeathInstance = new MobEffectInstance(EidolonPotions.UNDEATH_EFFECT, -1);
        tooltip.add(TextUtils.getTranslation("tooltip.dark_feed.when_feeding_else").withStyle(ChatFormatting.GRAY));
        MutableComponent elseEffectDescription = Component.literal(" ");
        elseEffectDescription.append(Component.translatable(undeathInstance.getDescriptionId()));
        elseEffectDescription.append(" ").append(Component.translatable("potion.potency." + undeathInstance.getAmplifier()));
        elseEffectDescription.append(" (").append(MobEffectUtil.formatDuration(undeathInstance, 1.0F, context.tickRate())).append(")");
        tooltip.add(elseEffectDescription.withStyle(undeathInstance.getEffect().value().getCategory().getTooltipFormatting()));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target, InteractionHand hand) {
        if (target instanceof Horse horse) {
            if (horse.isAlive() && horse.isTamed()) {
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}