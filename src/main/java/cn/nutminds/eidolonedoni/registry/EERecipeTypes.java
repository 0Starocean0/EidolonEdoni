package cn.nutminds.eidolonedoni.registry;

import cn.nutminds.eidolonedoni.EidolonEdoni;
import cn.nutminds.eidolonedoni.recipe.CurryRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EERecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, EidolonEdoni.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, EidolonEdoni.MODID);

    public static final Supplier<RecipeType<CurryRecipe>> CURRY_POT =
            RECIPE_TYPES.register(
                    "curry_pot",
                    () -> RecipeType.<CurryRecipe>simple(ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID, "curry_pot"))
            );
    public static final Supplier<RecipeSerializer<CurryRecipe>> CURRY_SERIALIZER =
            RECIPE_SERIALIZERS.register("curry_pot", CurryRecipe.Serializer::new);
}
