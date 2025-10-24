package cn.nutminds.eidolonedoni.event;

import alexthw.eidolon_repraised.api.capability.IReputation;
import alexthw.eidolon_repraised.api.deity.ReputationEvent;
import alexthw.eidolon_repraised.common.deity.Deities;
import alexthw.eidolon_repraised.registries.EidolonCapabilities;
import alexthw.eidolon_repraised.registries.EidolonDataComponents;
import alexthw.eidolon_repraised.registries.EidolonPotions;
import alexthw.eidolon_repraised.registries.Registry;
import cn.nutminds.eidolonedoni.EEConfig;
import cn.nutminds.eidolonedoni.EidolonEdoni;
import cn.nutminds.eidolonedoni.registry.EEEffects;
import cn.nutminds.eidolonedoni.registry.EEItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = EidolonEdoni.MODID)
public class EEServerEvents {

    private static TagKey<EntityType<?>> create(String string) {
        return TagKey.create(Registries.ENTITY_TYPE, EidolonEdoni.modid(string));
    }

    public static final TagKey<EntityType<?>> IMMUNE_TO_FETTERED = create("immune_to_fettered");

    @SubscribeEvent
    static void onLivingDamageEvent(LivingDamageEvent.Post event) {
        if (event.getSource().getDirectEntity() instanceof final LivingEntity source) {
            if (source.hasEffect(EEEffects.SOULTAKING)) {
                MobEffectInstance instance = event.getEntity().getEffect(EEEffects.SOULTAKING);
                if (instance != null) {
                    int amplifier = instance.getAmplifier();
                    source.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, (EEConfig.SOULTAKING_WEAKNESS_DURATION_BASE.get() * (amplifier + 1)), 1));
                    source.addEffect(new MobEffectInstance(EidolonPotions.VULNERABLE_EFFECT, (EEConfig.SOULTAKING_VULNERABLE_DURATION_BASE.get() * (amplifier + 1)), amplifier + 1));
                }
            }
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

    @SubscribeEvent
    static void onMobKilled(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        Entity source = event.getSource().getDirectEntity();
        if (source instanceof Player player) {
            if (player.getItemInHand(InteractionHand.MAIN_HAND).is(EEItems.ALL_ROUNDER)) {
                Inventory inventory = player.getInventory();
                for (int i = 0; i < inventory.getContainerSize(); i++) {
                    ItemStack itemStack = inventory.getItem(i);
                    if (itemStack.is(Registry.GOBLET.get().asItem()) && !itemStack.has(DataComponents.BLOCK_ENTITY_DATA) && !(entity instanceof Monster)) {
                        ItemStack filledGoblet = createFilledGoblet(entity);
                        player.addItem(filledGoblet);
                        if (!player.hasInfiniteMaterials()) {
                            itemStack.shrink(1);
                        }
                    }
                }
            }
        }
    }

    public static ItemStack createFilledGoblet(Entity entity) {
        ItemStack filledGoblet = new ItemStack(Registry.GOBLET.get().asItem());
        CompoundTag blockEntityData = new CompoundTag();
        blockEntityData.putString("type", EntityType.getKey(entity.getType()).toString());
        BlockItem.setBlockEntityData(filledGoblet, Registry.GOBLET_TILE_ENTITY.get(), blockEntityData);
        return filledGoblet;
    }
}