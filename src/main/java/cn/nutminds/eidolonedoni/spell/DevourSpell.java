package cn.nutminds.eidolonedoni.spell;

import alexthw.eidolon_repraised.api.spells.Sign;
import alexthw.eidolon_repraised.common.spell.StaticSpell;
import cn.nutminds.eidolonedoni.EEConfig;
import cn.nutminds.eidolonedoni.EidolonEdoni;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Random;

public class DevourSpell extends StaticSpell {
    public DevourSpell(ResourceLocation name, Sign... signs) {
        super(name, signs);
    }

    private static TagKey<EntityType<?>> create(String path) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID, path));
    }

    public static final TagKey<EntityType<?>> DEVOUR_BLACKLIST = create("devour_spell_blacklist");
    public static final TagKey<EntityType<?>> DEVOUR_HURT = create("devour_spell_hurting");
    public static final TagKey<EntityType<?>> DEVOUR_HOT = create("devour_spell_hot");

    @Override
    public boolean canCast(Level world, BlockPos pos, Player player) {
        return true;
    }

    @Override
    public void cast(Level world, BlockPos pos, Player player) {

        HitResult ray = rayTrace(player, player.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE) / 2, 0, false);
        if (ray instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity living && !living.getType().is(DEVOUR_BLACKLIST)) {
            float health = living.getHealth();
            living.hurt(player.damageSources().playerAttack(player), EEConfig.DEVOUR_BASE_DAMAGE.get() + living.getMaxHealth() / 10);
            float actualDamage;
            if (living.isAlive()) {
                actualDamage = health - living.getHealth();
            } else {actualDamage = health;}
            if (living.getType().is(DEVOUR_HURT)) {
                if (living.getType().is(DEVOUR_HOT)) {
                    player.hurt(living.damageSources().onFire(), 1 + living.getMaxHealth() / 100);
                } else {
                    player.hurt(living.damageSources().starve(), 1 + living.getMaxHealth() / 100);
                }
            } else {
                player.playSound(SoundEvents.GENERIC_EAT);
            }
            if (living.getType().is(EntityTypeTags.UNDEAD)) {
                player.getFoodData().eat((int) (actualDamage / 5), 0.1f);
                if (new Random().nextInt(5) != 0) {
                    player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0));
                }
            } else {
                player.getFoodData().eat((int) (actualDamage / 5), living.getMaxHealth() / 100);
            }

        }
    }
}

