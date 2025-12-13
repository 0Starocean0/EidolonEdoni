package cn.nutminds.eidolonedoni;

import net.neoforged.neoforge.common.ModConfigSpec;

public class EEConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue MERAMMER_FOOD_DURATION_RATE = BUILDER
            .comment("Controls the reduction ratio of food effect duration. A larger value results in a shorter duration (effect duration is divided by this value)")
            .defineInRange("merammerFoodDurationRate", 3, 2, 20);
    public static final ModConfigSpec.IntValue SOULTAKING_WEAKNESS_DURATION_BASE = BUILDER
            .comment("Base duration of Weakness applied by Soultaking.")
            .defineInRange("soultakingWeaknessDurationBase", 80, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue SOULTAKING_VULNERABLE_DURATION_BASE = BUILDER
            .comment("Base duration of Vulnerable applied by Soultaking.")
            .defineInRange("soultakingVulnerableDurationBase", 80, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue CLINGING_FETTERED_DURATION_BASE = BUILDER
            .comment("Base duration of Fettered applied by Clinging.")
            .defineInRange("clingingFetteredDurationBase", 60, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue FAITHFUL_DISABLE_MOB_SPAWNING_RANGE = BUILDER
            .comment("Base range to disable mob spawning added by Faithful. Adds to the default 24 blocks.")
            .defineInRange("faithfulDisableMobSpawningRange", 8, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue DEVOUR_BASE_DAMAGE = BUILDER
            .comment("Base damage of devour spell.")
            .defineInRange("devourDamageBase", 20, Integer.MIN_VALUE, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();
}
