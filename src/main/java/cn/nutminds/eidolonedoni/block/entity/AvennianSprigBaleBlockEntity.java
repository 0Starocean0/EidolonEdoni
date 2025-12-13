package cn.nutminds.eidolonedoni.block.entity;

import cn.nutminds.eidolonedoni.block.AvennianSprigBaleBlock;
import cn.nutminds.eidolonedoni.registry.EEBlockEntityTypes;
import cn.nutminds.eidolonedoni.registry.EEBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.tag.ModTags;

import static cn.nutminds.eidolonedoni.block.AvennianSprigBaleBlock.COOKED_TICKS;
import static cn.nutminds.eidolonedoni.block.AvennianSprigBaleBlock.cookTime;

public class AvennianSprigBaleBlockEntity extends BlockEntity {

    public AvennianSprigBaleBlockEntity(BlockPos pos, BlockState blockState) {
        super(EEBlockEntityTypes.AVENNIAN_SPRIG_BALE.get(), pos, blockState);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (getBlockState().hasProperty(COOKED_TICKS)) {
        Integer cookedTicks = getBlockState().getValue(COOKED_TICKS);
        cookedTicks = tag.getInt("cookedTime");
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (getBlockState().hasProperty(COOKED_TICKS)) {
            Integer cookedTicks = getBlockState().getValue(COOKED_TICKS);
            tag.putInt("cookedTime", cookedTicks);
        }
    }

    public static void tick(Level level, BlockState state, BlockPos pos, BlockEntity blockEntity) {
        if (!level.isClientSide) {
            BlockState belowState = level.getBlockState(pos.below());
            Integer cookedTicks = state.getValue(COOKED_TICKS);
            if (belowState.is(ModTags.HEAT_SOURCES) || belowState.is(ModTags.HEAT_CONDUCTORS)) {
                if (cookedTicks < cookTime) {
                    cookedTicks++;
                    level.setBlockAndUpdate(pos, state.setValue(COOKED_TICKS, cookedTicks));
                } else {
                    level.setBlockAndUpdate(pos, EEBlocks.GRILLED_AVENNIAN_SPRIG_BALE.get().defaultBlockState().setValue(AvennianSprigBaleBlock.FACING, state.getValue(AvennianSprigBaleBlock.FACING)));
                }
            } else
                level.setBlockAndUpdate(pos, state.setValue(COOKED_TICKS, 0));
        }
    }
}
