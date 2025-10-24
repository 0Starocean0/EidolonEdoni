package cn.nutminds.eidolonedoni.registry;

import alexthw.eidolon_repraised.registries.EidolonAttributes;
import cn.nutminds.eidolonedoni.EidolonEdoni;
import cn.nutminds.eidolonedoni.effect.*;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EEEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, EidolonEdoni.MODID);

    public static final Holder<MobEffect> SOULTAKING = EFFECTS.register("soultaking",
            () -> new SoultakingEffect().addAttributeModifier(
                    EidolonAttributes.PERSISTENT_SOUL_HEARTS,
                    ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID, "soultaking"),
                    5, AttributeModifier.Operation.ADD_VALUE));
    public static final Holder<MobEffect> FAITHFUL = EFFECTS.register("faithful",
            () -> new FaithfulEffect().addAttributeModifier(
                    EidolonAttributes.CHANTING_SPEED,
                    ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID, "faithful"),
                    0.3, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final Holder<MobEffect> EXCITED = EFFECTS.register("excited",
            () -> new ExcitedEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED,
                    ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID, "excited"),
                    0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .addAttributeModifier(Attributes.ATTACK_SPEED,
                    ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID, "excited"),
                    0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final Holder<MobEffect> CLINGING = EFFECTS.register("clinging", ClingingEffect::new);
    public static final Holder<MobEffect> FETTERED = EFFECTS.register("fettered",
            () -> new SnatchedEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED,
                    ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID, "fettered"),
                    -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.FLYING_SPEED,
                    ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID, "fettered"),
                    -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static final Holder<MobEffect> REANIMATE = EFFECTS.register("reanimate", ReanimateEffect::new);
}
