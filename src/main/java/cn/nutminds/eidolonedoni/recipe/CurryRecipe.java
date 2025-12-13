package cn.nutminds.eidolonedoni.recipe;

import cn.nutminds.eidolonedoni.registry.EERecipeTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class CurryRecipe implements Recipe<CurryRecipeInput> {
    private final Ingredient inputItem;
    private final ItemStack result;

    public CurryRecipe(Ingredient inputItem, ItemStack result) {
        this.inputItem = inputItem;
        this.result = result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.inputItem);
        return list;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return EERecipeTypes.CURRY_SERIALIZER.get();
    }

    public Ingredient getInputItem() {
        return inputItem;
    }

    public ItemStack getResult() {
        return result;
    }

    public static class Serializer implements RecipeSerializer<CurryRecipe> {
        public static final MapCodec<CurryRecipe> CODEC = RecordCodecBuilder
                .mapCodec(inst -> inst.group(
                        Ingredient.CODEC.fieldOf("ingredient").forGetter(CurryRecipe::getInputItem),
                        ItemStack.CODEC.fieldOf("result").forGetter(CurryRecipe::getResult)
                ).apply(inst, CurryRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, CurryRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, CurryRecipe::getInputItem,
                        ItemStack.STREAM_CODEC, CurryRecipe::getResult,
                        CurryRecipe::new
                );

        @Override
        public MapCodec<CurryRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CurryRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    @Override
    public RecipeType<?> getType() {
        return EERecipeTypes.CURRY_POT.get();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public boolean matches(CurryRecipeInput input, Level level) {
        return this.inputItem.test(input.stack());
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
    }

    @Override
    public ItemStack assemble(CurryRecipeInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }
}
