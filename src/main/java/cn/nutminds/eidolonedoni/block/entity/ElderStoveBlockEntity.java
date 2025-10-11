package cn.nutminds.eidolonedoni.block.entity;

import cn.nutminds.eidolonedoni.registry.EEBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.entity.StoveBlockEntity;

public class ElderStoveBlockEntity extends StoveBlockEntity {
    public ElderStoveBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public boolean isValidBlockState(BlockState blockState) {
        return this.getType().isValid(blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return EEBlockEntityTypes.STOVE.get();
    }
}
