package cn.nutminds.eidolonedoni.ritual;

import alexthw.eidolon_repraised.Eidolon;
import alexthw.eidolon_repraised.api.ritual.IRitualItemFocus;
import alexthw.eidolon_repraised.api.ritual.Ritual;
import alexthw.eidolon_repraised.registries.Registry;
import alexthw.eidolon_repraised.util.ColorUtil;
import cn.nutminds.eidolonedoni.EidolonEdoni;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.FakePlayer;

import java.util.List;
import java.util.UUID;

public class ButcheryRitual extends Ritual {
    public ButcheryRitual() {
        super(ResourceLocation.fromNamespaceAndPath(Eidolon.MODID, "particle/absorption_ritual"), ColorUtil.packColor(255, 255, 51, 85));
    }

    private static TagKey<EntityType<?>> create(String path) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID, path));
    }

    public static final TagKey<EntityType<?>> BUTCHERY_BLACKLIST = create("butchery_ritual_blacklist");
    public static final TagKey<EntityType<?>> BUTCHERY_WHITELIST = create("butchery_ritual_whitelist");

    private FakePlayer fakePlayer(ServerLevel serverLevel, ItemStack heldItem, BlockPos ritualPos) {
        UUID fakeUuid = UUID.randomUUID();
        GameProfile gameProfile = new GameProfile(fakeUuid, "ButcheryRitual");
        FakePlayer fakePlayer = new FakePlayer(serverLevel, gameProfile);
        fakePlayer.setPos(ritualPos.getX() + 0.5, ritualPos.getY() + 1.0, ritualPos.getZ() + 0.5);
        fakePlayer.setItemInHand(InteractionHand.MAIN_HAND, heldItem.copy());
        return fakePlayer;
    }

    @Override
    public Ritual cloneRitual() {
        return new ButcheryRitual();
    }

    @Override
    public RitualResult start(Level world, BlockPos pos) {
        List<IRitualItemFocus> tiles = Ritual.getTilesWithinAABB(IRitualItemFocus.class, world, getSearchBounds(pos));
        if (!tiles.isEmpty()) for (IRitualItemFocus tile : tiles) {
            ItemStack stack = tile.provide();
            if (!world.isClientSide) {
                List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, getSearchBounds(pos),
                        livingEntity -> livingEntity.isAlive()
                                && (livingEntity instanceof Animal || livingEntity instanceof WaterAnimal || livingEntity.getType().is(BUTCHERY_WHITELIST))
                                && !livingEntity.isBaby()
                                && !livingEntity.hasCustomName()
                                && !(livingEntity instanceof TamableAnimal tamableAnimal && tamableAnimal.isTame())
                                && !livingEntity.getType().is(BUTCHERY_BLACKLIST)
                );
                FakePlayer fakePlayer = fakePlayer((ServerLevel) world, stack, pos);
                for (LivingEntity e : entities) {
                    e.hurt(Registry.RITUAL_DAMAGE.source(world, fakePlayer), e.getMaxHealth() * 1000);
                }
            }
        }
        return RitualResult.TERMINATE;
    }
}
