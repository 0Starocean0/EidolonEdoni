package cn.nutminds.eidolonedoni.block;

import cn.nutminds.eidolonedoni.EidolonEdoni;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.FeastBlock;

import java.util.function.Supplier;

public class CurryPotBlock extends FeastBlock {
    public final Supplier<Item> servingItem;
    public final Supplier<Item> altServingItem;
    public final Supplier<Item> containerItem;
    public final Supplier<Item> altContainerItem;

    public CurryPotBlock(Properties properties, Supplier<Item> containerItem, Supplier<Item> altContainerItem, Supplier<Item> servingItem, Supplier<Item> altServingItem, boolean hasLeftovers) {
        super(properties, servingItem, hasLeftovers);
        this.servingItem = servingItem;
        this.altServingItem = altServingItem;
        this.containerItem = containerItem;
        this.altContainerItem = altContainerItem;
    }

    protected static final VoxelShape SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 12.0, 13.0);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public ItemStack getServingItem(BlockState state) {
        return new ItemStack(this.servingItem.get());
    }

    public ItemStack getAltServingItem(BlockState state) {
        return new ItemStack(this.altServingItem.get());
    }

    @Override
    protected ItemInteractionResult takeServing(LevelAccessor level, BlockPos pos, BlockState state, Player player, InteractionHand hand) {
        int servings = state.getValue(getServingsProperty());

        if (servings == 0) {
            level.playSound(null, pos, SoundEvents.LANTERN_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
            level.destroyBlock(pos, true);
            return ItemInteractionResult.SUCCESS;
        }

        ItemStack serving = this.getServingItem(state);
        ItemStack altServing = this.getAltServingItem(state);
        ItemStack heldStack = player.getItemInHand(hand);

        if (servings > 0) {
            if (ItemStack.isSameItem(heldStack, containerItem.get().getDefaultInstance())) {
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
            } else if (ItemStack.isSameItem(heldStack, altContainerItem.get().getDefaultInstance())) {
                level.setBlock(pos, state.setValue(getServingsProperty(), servings - 1), 3);
                if (!player.getAbilities().instabuild) {
                    heldStack.shrink(1);
                }
                if (!player.getInventory().add(altServing)) {
                    player.drop(serving, false);
                }
                if (level.getBlockState(pos).getValue(getServingsProperty()) == 0 && !this.hasLeftovers) {
                    level.removeBlock(pos, false);
                }
                level.playSound(null, pos, SoundEvents.ARMOR_EQUIP_GENERIC.value(), SoundSource.BLOCKS, 1.0F, 1.0F);
                return ItemInteractionResult.SUCCESS;
            } else {
                player.displayClientMessage(Component.translatable(EidolonEdoni.MODID + ".block.curry_pot.use_container"), true);
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}