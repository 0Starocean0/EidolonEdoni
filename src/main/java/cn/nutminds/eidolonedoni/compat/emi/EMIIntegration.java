package cn.nutminds.eidolonedoni.compat.emi;

import cn.nutminds.eidolonedoni.recipe.CurryRecipe;
import cn.nutminds.eidolonedoni.registry.EEItems;
import cn.nutminds.eidolonedoni.registry.EERecipeTypes;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;
import java.util.function.Function;

@EmiEntrypoint
public class EMIIntegration implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {

        List<RecipeHolder<CurryRecipe>> curryPotRecipes = registry.getRecipeManager().getAllRecipesFor(EERecipeTypes.CURRY_POT.get());

        for (RecipeHolder<CurryRecipe> curryRecipe : curryPotRecipes) {
            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                    .id(curryRecipe.id())
                    .leftInput(EmiIngredient.of(curryRecipe.value().getInputItem()))
                    .rightInput(EmiStack.of(EEItems.CURRY_POT.get()), true, slotWidget -> slotWidget.appendTooltip(Component.translatable("eidolon_edoni.block.curry_pot.emi_tooltip")))
                    .output(EmiStack.of(curryRecipe.value().getResult()))
                    .build());
        }
    }
}
