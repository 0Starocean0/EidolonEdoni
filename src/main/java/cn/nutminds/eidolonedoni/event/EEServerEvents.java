package cn.nutminds.eidolonedoni.event;

import alexthw.eidolon_repraised.registries.EidolonPotions;
import cn.nutminds.eidolonedoni.EEConfig;
import cn.nutminds.eidolonedoni.EidolonEdoni;
import cn.nutminds.eidolonedoni.registry.EEEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = EidolonEdoni.MODID)
public class EEServerEvents {

    private static TagKey<EntityType<?>> create(String string) {
        return TagKey.create(Registries.ENTITY_TYPE, EidolonEdoni.modid(string));
    }
    public static final TagKey<EntityType<?>> IMMUNE_TO_FETTERED = create("immune_to_fettered");
    @SubscribeEvent
    static void onLivingDamageEvent(LivingDamageEvent.Post event) {
        if (event.getSource().getDirectEntity() instanceof final LivingEntity source)
            if (event.getEntity().hasEffect(EEEffects.SOULTAKING)) {
                MobEffectInstance instance = event.getEntity().getEffect(EEEffects.SOULTAKING);
                if (instance != null) {
                    int amplifier = instance.getAmplifier();
                    source.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, (EEConfig.SOULTAKING_WEAKNESS_DURATION_BASE.get() * (amplifier + 1)), 1));
                    source.addEffect(new MobEffectInstance(EidolonPotions.VULNERABLE_EFFECT, (EEConfig.SOULTAKING_VULNERABLE_DURATION_BASE.get() * (amplifier + 1)), amplifier + 1));
                }
            }
        if (event.getSource().getDirectEntity() instanceof final LivingEntity source)
            if (source.hasEffect(EEEffects.CLINGING)) {
                MobEffectInstance instance = source.getEffect(EEEffects.CLINGING);
                if (instance != null) {
                    int amplifier = instance.getAmplifier();
                    if (!event.getEntity().getType().is(IMMUNE_TO_FETTERED)) {
                        event.getEntity().addEffect(new MobEffectInstance(EEEffects.FETTERED, (EEConfig.CLINGING_FETTERED_DURATION_BASE.get() * (amplifier + 1))));
                    }
                }
            }
    }

    @SubscribeEvent
    public static void onMobSpawnEvent(FinalizeSpawnEvent event) {
        Mob mob = event.getEntity();
        MobSpawnType type = event.getSpawnType();
        if (mob instanceof Enemy) {
            if (type == MobSpawnType.NATURAL || type == MobSpawnType.PATROL || type == MobSpawnType.REINFORCEMENT || type == MobSpawnType.JOCKEY) {
                Player player = event.getLevel().getNearestPlayer(event.getX(), event.getY(), event.getZ(), 64, true);
                if (player != null && player.hasEffect(EEEffects.FAITHFUL)) {
                    MobEffectInstance instance = player.getEffect(EEEffects.FAITHFUL);
                    double distance = mob.distanceTo(player);
                    if (instance != null && distance <= (24 + (8 * (instance.getAmplifier() + 1)))) {
                        event.setSpawnCancelled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity().hasEffect(EEEffects.EXCITED)) {
            MobEffectInstance instance = event.getEntity().getEffect(EEEffects.EXCITED);
            if (instance != null && event.getOriginalAmount() >= 0) {
                event.setAmount(event.getOriginalAmount() + (event.getOriginalAmount() * (instance.getAmplifier() + 1)));
            }
        }
    }

    @SubscribeEvent
    static void onEntityTickPre(EntityTickEvent.Pre event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity livingEntity)
            if (livingEntity.hasEffect(EEEffects.FETTERED)) {
                Vec3 deltaMovement = entity.getDeltaMovement();
                entity.setDeltaMovement(deltaMovement.x * 0, deltaMovement.y * 0, deltaMovement.z * 0);
            }
    }
}