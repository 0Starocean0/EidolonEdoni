package cn.nutminds.eidolonedoni;

import alexthw.eidolon_repraised.registries.EidolonPotions;
import cn.nutminds.eidolonedoni.registry.EEEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class EEFoodValues {
    public static final FoodProperties HEART_TERRINE_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.3F)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(EidolonPotions.UNDEATH_EFFECT, 600, 0), 1.0F)
            .fast().build();
    public static final FoodProperties COLD_NOODLE = (new FoodProperties.Builder())
            .nutrition(14).saturationModifier(0.75F)
            .effect(() -> new MobEffectInstance(EEEffects.SOULTAKING, 4800, 1), 1.0F)
            .build();
    public static final FoodProperties SHAVED_ICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.2F)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 900, 1), 1.0F)
            .effect(() -> new MobEffectInstance(EEEffects.SOULTAKING, 1200, 0), 1.0F)
            .effect(() -> new MobEffectInstance(EidolonPotions.CHILLED_EFFECT, 2400, 0), 1.0F)
            .effect(() -> new MobEffectInstance(EidolonPotions.VULNERABLE_EFFECT, 2400, 0), 1.0F)
            .fast().alwaysEdible().build();
    public static final FoodProperties BECHAMEL = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.4F).build();
    public static final FoodProperties CURRY_BREAD = (new FoodProperties.Builder())
            .nutrition(7).saturationModifier(0.6F)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0F)
            .build();
    public static final FoodProperties CURRY_RICE = (new FoodProperties.Builder())
            .nutrition(8).saturationModifier(0.6F)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0F)
            .build();
    public static final FoodProperties CREAM_OF_MUSHROOM_SOUP = (new FoodProperties.Builder())
            .nutrition(14).saturationModifier(0.75F)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT, 6000, 0), 1.0F)
            .build();
    public static final FoodProperties CREAM_PASTA_WITH_PETALS = (new FoodProperties.Builder())
            .nutrition(12).saturationModifier(0.75F)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 6000, 0), 1.0F)
            .effect(() -> new MobEffectInstance(EEEffects.FAITHFUL, 12000, 0), 1.0F)
            .build();
    public static final FoodProperties OANNA_CHICKEN_CONGEE = (new FoodProperties.Builder())
            .nutrition(12).saturationModifier(0.75F)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT, 6000, 0), 1.0F)
            .effect(() -> new MobEffectInstance(EEEffects.FAITHFUL, 12000, 0), 1.0F)
            .build();
    public static final FoodProperties OANNA_MOONCAKE_SLICE = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.3F)
            .effect(() -> new MobEffectInstance(EEEffects.FAITHFUL, 6000, 0), 1.0F)
            .fast().build();
    public static final FoodProperties OANNA_PETAL_TEA = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(EEEffects.FAITHFUL, 6000, 1), 1.0F)
            .alwaysEdible().build();
    public static final FoodProperties SILDRIAN_PUDDING = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.3F)
            .effect(() -> new MobEffectInstance(EEEffects.EXCITED, 600, 1), 1.0F)
            .build();
    public static final FoodProperties SILDRIAN_BEEF = (new FoodProperties.Builder())
            .nutrition(14).saturationModifier(0.75F)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 6000, 0), 1.0F)
            .effect(() -> new MobEffectInstance(EEEffects.EXCITED, 3600, 0), 1.0F)
            .build();
    public static final FoodProperties MIXED_PORRIDGE = (new FoodProperties.Builder())
            .nutrition(10).saturationModifier(0.75F)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT, 6000, 0), 1.0F)
            .effect(() -> new MobEffectInstance(EEEffects.EXCITED, 3000, 0), 1.0F)
            .build();
    public static final FoodProperties SILDRIAN_TEA = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(EEEffects.EXCITED, 600, 2), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.UNLUCK, 3600, 0), 1.0F)
            .alwaysEdible().build();
    public static final FoodProperties GRLLED_AVENNIAN_SPRIG = (new FoodProperties.Builder())
            .nutrition(7).saturationModifier(0.5F)
            .effect(() -> new MobEffectInstance(EEEffects.CLINGING, 1200, 0), 1.0F)
            .build();
    public static final FoodProperties AVENNIAN_SCRAMBLED_EGG = (new FoodProperties.Builder())
            .nutrition(10).saturationModifier(0.6F)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(EEEffects.CLINGING, 3600, 0), 1.0F)
            .build();
    public static final FoodProperties AVENNIAN_STEW = (new FoodProperties.Builder())
            .nutrition(12).saturationModifier(0.75F)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT, 6000, 0), 1.0F)
            .effect(() -> new MobEffectInstance(EEEffects.CLINGING, 6000, 0), 1.0F)
            .build();
    public static final FoodProperties FRIED_AVENNA_DUMPLING = (new FoodProperties.Builder())
            .nutrition(8).saturationModifier(0.8F)
            .effect(() -> new MobEffectInstance(EEEffects.CLINGING, 3000, 1), 1.0F)
            .build();
    public static final FoodProperties STUFFED_WITHER_SKELETON_SKULL = (new FoodProperties.Builder())
            .nutrition(14).saturationModifier(0.75F)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 6000, 0), 1.0F)
            .effect(() -> new MobEffectInstance(EidolonPotions.UNDEATH_EFFECT, 6000, 0), 1.0F)
            .build();
    public static final FoodProperties CALX_CROQUETTE = (new FoodProperties.Builder())
            .nutrition(9).saturationModifier(0.8F)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(EEEffects.REANIMATE, 3600, 1), 1.0F)
            .effect(() -> new MobEffectInstance(EidolonPotions.ANCHORED_EFFECT, 3600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3600, 1), 1.0F)
            .build();
    public static final FoodProperties JOCKEY_PIE_SLICE = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.3F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(EEEffects.REANIMATE, 600, 0), 1.0F)
            .fast().build();
    public static final FoodProperties MIRECHOCO = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.1F)
            .effect(() -> new MobEffectInstance(EEEffects.REANIMATE, 100, 0), 1.0F)
            .fast().build();
    public static final FoodProperties FRIED_HEART = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(1.5F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 900, 0), 1.0F)
            .effect(() -> new MobEffectInstance(EidolonPotions.UNDEATH_EFFECT, 1800, 0), 1.0F)
            .build();
    public static final FoodProperties JELLY_SLUG = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.2F)
            .effect(() -> new MobEffectInstance(MobEffects.JUMP, 600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 0), 0.5F)
            .fast().alwaysEdible().build();
    public static final FoodProperties CHERRY_CANDY = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(2)
            .effect(() -> new MobEffectInstance(EidolonPotions.SOUL_HARVEST, 1200, 0), 0.5f)
            .build();
    public static final FoodProperties PREMIUM_DOG_FOOD = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.2f).build();
    public static final FoodProperties SPROUT_SKEWER = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.6F)
            .effect(() -> new MobEffectInstance(EidolonPotions.ANCHORED_EFFECT, 1200, 0), 1.0F)
            .build();
}
