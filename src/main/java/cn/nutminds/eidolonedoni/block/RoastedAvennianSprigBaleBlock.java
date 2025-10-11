package cn.nutminds.eidolonedoni.block;

import cn.nutminds.eidolonedoni.EidolonEdoni;
import cn.nutminds.eidolonedoni.registry.EEItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.utility.ItemUtils;

public class RoastedAvennianSprigBaleBlock extends AvennianSprigBaleBlock{

    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 3);

    protected static final VoxelShape SHAPE_UD_1  = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
    protected static final VoxelShape SHAPE_UD_2  = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    protected static final VoxelShape SHAPE_UD_3  = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
    protected static final VoxelShape SHAPE_N_1  = Block.box(0.0D, 0.0D, 4.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPE_N_2  = Block.box(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPE_N_3  = Block.box(0.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPE_S_1  = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
    protected static final VoxelShape SHAPE_S_2  = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    protected static final VoxelShape SHAPE_S_3  = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 12.0D);
    protected static final VoxelShape SHAPE_W_1  = Block.box(4.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPE_W_2  = Block.box(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPE_W_3  = Block.box(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPE_E_1  = Block.box(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPE_E_2  = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPE_E_3  = Block.box(0.0D, 0.0D, 0.0D, 12.0D, 16.0D, 16.0D);

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        if ((direction == Direction.UP || direction == Direction.DOWN)){
            if (state.getValue(STAGE) == 1) {return SHAPE_UD_1;}
            if (state.getValue(STAGE) == 2) {return SHAPE_UD_2;}
            if (state.getValue(STAGE) == 3) {return SHAPE_UD_3;}
        }
        if ((direction == Direction.NORTH)){
            if (state.getValue(STAGE) == 1) {return SHAPE_N_1;}
            if (state.getValue(STAGE) == 2) {return SHAPE_N_2;}
            if (state.getValue(STAGE) == 3) {return SHAPE_N_3;}
        }
        if ((direction == Direction.SOUTH)){
            if (state.getValue(STAGE) == 1) {return SHAPE_S_1;}
            if (state.getValue(STAGE) == 2) {return SHAPE_S_2;}
            if (state.getValue(STAGE) == 3) {return SHAPE_S_3;}
        }
        if ((direction == Direction.WEST)){
            if (state.getValue(STAGE) == 1) {return SHAPE_W_1;}
            if (state.getValue(STAGE) == 2) {return SHAPE_W_2;}
            if (state.getValue(STAGE) == 3) {return SHAPE_W_3;}
        }
        if ((direction == Direction.EAST)){
            if (state.getValue(STAGE) == 1) {return SHAPE_E_1;}
            if (state.getValue(STAGE) == 2) {return SHAPE_E_2;}
            if (state.getValue(STAGE) == 3) {return SHAPE_E_3;}
        }
        return Shapes.block();
    }

    public RoastedAvennianSprigBaleBlock(Properties properties) {
        super(properties);
    }

    public ItemInteractionResult useItemOn(ItemStack heldStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (heldStack.is(Items.STICK)) {
            return takeServing(level, pos, state, player);
        } else player.displayClientMessage(Component.translatable(EidolonEdoni.MODID + ".block.roasted_avennian_sprig_bale.use_container"), true);

        return
                ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    protected ItemInteractionResult takeServing(Level level, BlockPos pos, BlockState state, Player player) {
        int bites = state.getValue(STAGE);
        if (bites < 4 - 1) {
            level.setBlock(pos, state.setValue(STAGE, bites + 1), 3);
        } else {
            level.removeBlock(pos, false);
        }

        Direction direction = player.getDirection().getOpposite();
        ItemUtils.spawnItemEntity(level, new ItemStack(EEItems.ROASTED_AVENNIAN_SPRIG.get()), pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5,
                direction.getStepX() * 0.15, 0.05, direction.getStepZ() * 0.15);
        level.playSound(null, pos, SoundEvents.GRASS_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
        return ItemInteractionResult.SUCCESS;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, STAGE);
    }

    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }
}
