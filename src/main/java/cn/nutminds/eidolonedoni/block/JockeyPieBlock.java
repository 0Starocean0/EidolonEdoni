package cn.nutminds.eidolonedoni.block;

import cn.nutminds.eidolonedoni.registry.EEEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.registry.ModParticleTypes;
import vectorwing.farmersdelight.common.utility.MathUtils;

import java.util.Iterator;
import java.util.function.Supplier;

public class JockeyPieBlock extends PieBlock {
    public JockeyPieBlock(Properties properties, Supplier<Item> pieSlice) {
        super(properties, pieSlice);
    }

    @Override
    protected InteractionResult consumeBite(Level level, BlockPos pos, BlockState state, Player playerIn){
        if (!playerIn.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            ItemStack sliceStack = this.getPieSliceItem();
            FoodProperties sliceFood = sliceStack.getItem().getFoodProperties(sliceStack, playerIn);
            if (sliceFood != null) {
                playerIn.getFoodData().eat(sliceFood);
                Iterator var7 = sliceFood.effects().iterator();

                while(var7.hasNext()) {
                    FoodProperties.PossibleEffect effect = (FoodProperties.PossibleEffect)var7.next();
                    if (!level.isClientSide && effect != null && level.random.nextFloat() < effect.probability()) {
                        playerIn.addEffect(effect.effect());
                    }
                }
            }
            if (playerIn.isPassenger()) {
                Entity vehicle = playerIn.getVehicle();
                if (vehicle instanceof LivingEntity entity && entity.isAlive()) {
                    entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1));
                    entity.addEffect(new MobEffectInstance(EEEffects.REANIMATE, 600, 1));
                    for (int i = 0; i < 5; ++i) {
                        double xSpeed = MathUtils.RAND.nextGaussian() * 0.02D;
                        double ySpeed = MathUtils.RAND.nextGaussian() * 0.02D;
                        double zSpeed = MathUtils.RAND.nextGaussian() * 0.02D;
                        entity.level().addParticle(ModParticleTypes.STAR.get(), entity.getRandomX(1.0D), entity.getRandomY() + 0.5D, entity.getRandomZ(1.0D), xSpeed, ySpeed, zSpeed);
                    }
                }
            }

            int bites = state.getValue(BITES);
            if (bites < this.getMaxBites() - 1) {
                level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
            } else {
                level.removeBlock(pos, false);
            }

            level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);
            return InteractionResult.SUCCESS;
        }
    }
}
