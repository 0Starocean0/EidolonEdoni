package cn.nutminds.eidolonedoni.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record CurryRecipeInput (ItemStack stack) implements RecipeInput {
    @Override
    public ItemStack getItem(int slot) {
        return this.stack();
    }
    @Override
    public int size() {
        return 1;
    }
}
