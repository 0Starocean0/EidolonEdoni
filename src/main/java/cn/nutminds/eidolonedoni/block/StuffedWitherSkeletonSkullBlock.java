package cn.nutminds.eidolonedoni.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.FeastBlock;

import java.util.function.Supplier;

public class StuffedWitherSkeletonSkullBlock extends FeastBlock {
    public StuffedWitherSkeletonSkullBlock(Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
        super(properties, servingItem, hasLeftovers);
    }

    protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
