package cn.nutminds.eidolonedoni.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.util.TriState;
import vectorwing.farmersdelight.common.block.RichSoilBlock;

import javax.annotation.Nullable;

public class RichSoilPlanterBlock extends RichSoilBlock {
    public RichSoilPlanterBlock(Properties properties) {
        super(properties);
    }

    @Override
    @Nullable
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility toolAction, boolean simulate) {
        return null;
    }

    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, BlockState plantState) {
        return TriState.DEFAULT;
    }
}
