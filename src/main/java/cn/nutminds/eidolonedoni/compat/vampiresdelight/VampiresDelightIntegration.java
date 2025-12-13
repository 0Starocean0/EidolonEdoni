package cn.nutminds.eidolonedoni.compat.vampiresdelight;

import cn.nutminds.eidolonedoni.EidolonEdoni;
import cn.nutminds.eidolonedoni.item.MerammerFoodItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static cn.nutminds.eidolonedoni.registry.EEItems.curryFoodItem;

public class VampiresDelightIntegration {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, EidolonEdoni.MODID);

    public static final Supplier<Item> CURRY_RICE_BREAD = ITEMS.register("rice_bread_with_curry", () ->
            new MerammerFoodItem(curryFoodItem(BuiltInRegistries.ITEM.get(
                    ResourceLocation.fromNamespaceAndPath("vampiresdelight", "rice_bread")))));
    //WIP
}
