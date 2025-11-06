package cn.nutminds.eidolonedoni.item;

import alexthw.eidolon_repraised.api.capability.IReputation;
import alexthw.eidolon_repraised.common.block.HerbBlockBase;
import alexthw.eidolon_repraised.common.deity.Deities;
import alexthw.eidolon_repraised.common.item.AthameItem;
import alexthw.eidolon_repraised.registries.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.enchanting.GetEnchantmentLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.item.KnifeItem;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.*;


public class AllRounderItem extends KnifeItem {

    public AllRounderItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLooting(GetEnchantmentLevelEvent event) {
        if (event.getStack().getItem() instanceof AllRounderItem && event.isTargetting(Enchantments.LOOTING)) {
            var mutable = event.getEnchantments();
            event.getHolder(Enchantments.LOOTING).ifPresent(enchantment -> {
                mutable.upgrade(enchantment, mutable.getLevel(enchantment) * 2 + 1);
            });
        }
    }

    @SubscribeEvent
    public void onHurt(LivingDamageEvent.Pre event) {
        if (event.getSource().getEntity() instanceof LivingEntity living
                && living.getMainHandItem().getItem() instanceof AllRounderItem allRounderItem
                && event.getEntity() instanceof EnderMan || event.getEntity() instanceof Endermite || event.getEntity() instanceof EnderDragon) {
            event.setNewDamage(event.getNewDamage() * 4);
        }
    }

    @SubscribeEvent
    public void onTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.is(this)) {
                IReputation reputation = player.getCapability(EidolonCapabilities.REPUTATION_CAPABILITY);
                if (reputation != null) {
                    double darkReputation = reputation.getReputation(Deities.DARK_DEITY.getId());
                    double lightReputation = reputation.getReputation(Deities.LIGHT_DEITY.getId());
                    if (stack.has(EidolonDataComponents.NECROTIC)) {
                        int persistent = stack.getOrDefault(EidolonDataComponents.NECROTIC, 0);
                        if (darkReputation >= lightReputation && darkReputation != 0) {
                            if (persistent <= 50) {
                                stack.set(EidolonDataComponents.NECROTIC, 51);
                            }
                        }
                        if (darkReputation < lightReputation) {
                            if (persistent > 50) {
                                stack.remove(EidolonDataComponents.NECROTIC);
                            }
                        }
                    } else if (darkReputation >= lightReputation && darkReputation != 0) {
                        stack.set(EidolonDataComponents.NECROTIC, 51);
                    }
                    if (stack.has(EidolonDataComponents.CONSECRATED)) {
                        int persistent = stack.getOrDefault(EidolonDataComponents.CONSECRATED, 0);
                        if (darkReputation <= lightReputation && lightReputation != 0) {
                            if (persistent <= 50) {
                                stack.set(EidolonDataComponents.CONSECRATED, 51);
                            }
                        }
                        if (darkReputation > lightReputation) {
                            if (persistent > 50) {
                                stack.remove(EidolonDataComponents.CONSECRATED);
                            }
                        }
                    } else if (darkReputation <= lightReputation && lightReputation != 0) {
                        stack.set(EidolonDataComponents.CONSECRATED, 51);
                    }
                }
            }
        }
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        ItemStack toolStack = ctx.getItemInHand();
        Direction facing = ctx.getClickedFace();
        var random = ctx.getPlayer() != null ? ctx.getPlayer().getRandom() : ctx.getLevel().getRandom();
        float hardness = state.getDestroySpeed(ctx.getLevel(), ctx.getClickedPos());
        if ((block instanceof BushBlock || block instanceof LeavesBlock || state.is(BlockTags.LEAVES) || state.is(BlockTags.CROPS) || state.is(BlockTags.FLOWERS) || block instanceof GrowingPlantBlock || block instanceof HerbBlockBase)
                && hardness < 5.0f && hardness >= 0) {
            if (!ctx.getLevel().isClientSide) {
                Vec3 hit = ctx.getClickLocation();
                ((ServerLevel) ctx.getLevel()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), hit.x, hit.y, hit.z, 3, ((double) random.nextFloat() - 0.5D) * 0.08D, ((double) random.nextFloat() - 0.5D) * 0.08D, ((double) random.nextFloat() - 0.5D) * 0.08D, 0.05F);
                ctx.getLevel().playSound(null, ctx.getClickedPos(), SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 0.5f, 0.9f + random.nextFloat() * 0.2f);
                if (random.nextInt(3) == 0) {
                    if (state.is(Registry.PLANTER_PLANTS)) {
                        if (state.getValue(HerbBlockBase.AGE) >= 2) {
                            ctx.getLevel().setBlockAndUpdate(ctx.getClickedPos(), state.setValue(HerbBlockBase.AGE, 0));
                            ItemStack drop = getHarvestable(block, ctx.getLevel());
                            if (!drop.isEmpty() && !ctx.getLevel().isClientSide) {
                                ctx.getLevel().playSound(null, ctx.getClickedPos(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5f, 0.9f + random.nextFloat() * 0.2f);
                                for (int i = -1; i < random.nextInt(3); i++)
                                    ctx.getLevel().addFreshEntity(new ItemEntity(ctx.getLevel(), ctx.getClickedPos().getX() + 0.5, ctx.getClickedPos().getY() + 0.5, ctx.getClickedPos().getZ() + 0.5, drop.copy()));
                            }
                            if (!ctx.getPlayer().isCreative())
                                ctx.getItemInHand().hurtAndBreak(1, ctx.getPlayer(), LivingEntity.getSlotForHand(ctx.getHand()));

                        }
                    } else {
                        if (block instanceof DoublePlantBlock && state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER)
                            ctx.getLevel().destroyBlock(ctx.getClickedPos().below(), false);
                        else ctx.getLevel().destroyBlock(ctx.getClickedPos(), false);
                        Optional<Holder.Reference<Enchantment>> enchantmentHolder = ctx.getLevel().registryAccess().holder(Enchantments.LOOTING);

                        if (random.nextInt(10) >= 8 - (enchantmentHolder.isPresent() && ctx.getItemInHand().getItem() instanceof AthameItem ? ctx.getItemInHand().getEnchantmentLevel(enchantmentHolder.get()) : 0)) {
                            ItemStack drop = getHarvestable(block, ctx.getLevel());
                            if (!drop.isEmpty() && !ctx.getLevel().isClientSide) {
                                ctx.getLevel().playSound(null, ctx.getClickedPos(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5f, 0.9f + random.nextFloat() * 0.2f);
                                ctx.getLevel().addFreshEntity(new ItemEntity(ctx.getLevel(), ctx.getClickedPos().getX() + 0.5, ctx.getClickedPos().getY() + 0.5, ctx.getClickedPos().getZ() + 0.5, drop.copy()));
                            }
                            if (!ctx.getPlayer().isCreative())
                                ctx.getItemInHand().hurtAndBreak(1, ctx.getPlayer(), LivingEntity.getSlotForHand(ctx.getHand()));
                        }
                    }
                }
            }
            return InteractionResult.SUCCESS;
        }
        if (state.getBlock() == Blocks.PUMPKIN && toolStack.is(ModTags.KNIVES)) {
            Player player = ctx.getPlayer();
            if (player != null && !level.isClientSide) {
                Direction direction = facing.getAxis() == Direction.Axis.Y ? player.getDirection().getOpposite() : facing;
                level.playSound(null, pos, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlock(pos, Blocks.CARVED_PUMPKIN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, direction), 11);
                ItemEntity itemEntity = new ItemEntity(level, (double) pos.getX() + 0.5 + (double) direction.getStepX() * 0.65, (double) pos.getY() + 0.1, (double) pos.getZ() + 0.5 + (double) direction.getStepZ() * 0.65, new ItemStack(Items.PUMPKIN_SEEDS, 4));
                itemEntity.setDeltaMovement(0.05 * (double) direction.getStepX() + level.random.nextDouble() * 0.02, 0.05, 0.05 * (double) direction.getStepZ() + level.random.nextDouble() * 0.02);
                level.addFreshEntity(itemEntity);
                toolStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(ctx.getHand()));
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useOn(ctx);
    }

    public static final Map<Block, ItemStack> harvestables = new HashMap<>();


    public static void initHarvestables() {
        harvestables.put(Registry.MERAMMER_ROOT.get(), new ItemStack(Registry.MERAMMER_ROOT.get()));
        harvestables.put(Registry.OANNA_BLOOM.get(), new ItemStack(Registry.OANNA_BLOOM.get()));
        harvestables.put(Registry.SILDRIAN_SEED.get(), new ItemStack(Registry.SILDRIAN_SEED.get()));
        harvestables.put(Registry.AVENNIAN_SPRIG.get(), new ItemStack(Registry.AVENNIAN_SPRIG.get()));
        harvestables.put(Registry.MIRECAP.get(), new ItemStack(Registry.MIRECAP.get()));
    }

    public static ItemStack getHarvestable(Block block, Level level) {
        ItemStack harvest = level.getRecipeManager().getAllRecipesFor(EidolonRecipes.FORAGING_TYPE.get()).stream().map(RecipeHolder::value).filter(
                foragingRecipe -> foragingRecipe.block.test(new ItemStack(block))
        ).map(f -> f.getResultItem(level.registryAccess())).findFirst().orElse(ItemStack.EMPTY);
        if (!harvest.isEmpty()) return harvest;
        return harvestables.getOrDefault(block, ItemStack.EMPTY);
    }
}
