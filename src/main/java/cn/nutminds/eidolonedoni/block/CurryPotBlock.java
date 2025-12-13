package cn.nutminds.eidolonedoni.block;

import cn.nutminds.eidolonedoni.EidolonEdoni;
import cn.nutminds.eidolonedoni.recipe.CurryRecipe;
import cn.nutminds.eidolonedoni.recipe.CurryRecipeInput;
import cn.nutminds.eidolonedoni.registry.EERecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class CurryPotBlock extends Block {
    public static final DirectionProperty FACING;
    public static final IntegerProperty SERVINGS;
    public final boolean hasLeftovers;

    public CurryPotBlock(Properties properties, boolean hasLeftovers) {
        super(properties);
        this.hasLeftovers = hasLeftovers;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH)
                .setValue(getServingsProperty(), getMaxServings()));
    }

    public IntegerProperty getServingsProperty() {
        return SERVINGS;
    }

    public int getMaxServings() {
        return 4;
    }

    protected static final VoxelShape SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 12.0, 13.0);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public ItemInteractionResult useItemOn(ItemStack heldStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return level.isClientSide &&
                this.takeServing(level, pos, state, player, hand).consumesAction() ?
                ItemInteractionResult.SUCCESS :
                this.takeServing(level, pos, state, player, hand);
    }

    protected ItemInteractionResult takeServing(Level level, BlockPos pos, BlockState state, Player player, InteractionHand hand) {
        int servings = state.getValue(getServingsProperty());

        if (servings == 0) {
            level.playSound(null, pos, SoundEvents.LANTERN_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
            level.destroyBlock(pos, true);
            return ItemInteractionResult.SUCCESS;
        }

        ItemStack heldStack = player.getItemInHand(hand);
        CurryRecipeInput input = new CurryRecipeInput(heldStack);
        Optional<RecipeHolder<CurryRecipe>> recipe = level.getRecipeManager().getRecipeFor(EERecipeTypes.CURRY_POT.get(), input, level);

        if (servings > 0) {
            if (recipe.isPresent()) {
                ItemStack serving = recipe.get().value().getResultItem(player.level().registryAccess()).copy();
                level.setBlock(pos, state.setValue(getServingsProperty(), servings - 1), 3);
                if (!player.getAbilities().instabuild) {
                    heldStack.shrink(1);
                }
                if (!player.getInventory().add(serving)) {
                    player.drop(serving, false);
                }
                if (level.getBlockState(pos).getValue(getServingsProperty()) == 0 && !this.hasLeftovers) {
                    level.removeBlock(pos, false);
                }
                level.playSound(null, pos, SoundEvents.ARMOR_EQUIP_GENERIC.value(), SoundSource.BLOCKS, 1.0F, 1.0F);
                return ItemInteractionResult.SUCCESS;
            } else {
                List<RecipeHolder<CurryRecipe>> recipes = level.getRecipeManager().getAllRecipesFor(EERecipeTypes.CURRY_POT.get());
                List<Component> available = new ArrayList<>();
                for (RecipeHolder<CurryRecipe> holder : recipes) {
                    CurryRecipe curryRecipe = holder.value();
                    ItemStack[] itemStack = curryRecipe.getInputItem().getItems();
                    for (ItemStack stack : itemStack) {
                        available.add(stack.getHoverName());
                    }
                }
                if (!available.isEmpty()) {
                    player.displayClientMessage(Component.translatable(EidolonEdoni.MODID + ".block.curry_pot.use_container",
                            available.get(new Random().nextInt(available.size()))), true);
                } else {
                    player.displayClientMessage(Component.translatable(EidolonEdoni.MODID + ".block.curry_pot.no_recipe"), true);
                }
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !stateIn.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, SERVINGS});
    }

    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        return (Integer)blockState.getValue(this.getServingsProperty());
    }

    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        SERVINGS = IntegerProperty.create("servings", 0, 4);
    }
}