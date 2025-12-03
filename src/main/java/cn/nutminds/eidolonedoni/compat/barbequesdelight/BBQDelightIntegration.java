package cn.nutminds.eidolonedoni.compat.barbequesdelight;

import cn.nutminds.eidolonedoni.EEFoodValues;
import cn.nutminds.eidolonedoni.EidolonEdoni;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BBQDelightIntegration {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, EidolonEdoni.MODID);

    public static Supplier<Item> RAW_AVENNIAN_SPRIG_SKEWER = ITEMS.register("raw_avennian_sprig_skewer", () ->
            new com.mao.barbequesdelight.content.item.BBQSkewerItem(new Item.Properties()));
    public static Supplier<Item> GRILLED_AVENNIAN_SPRIG_SKEWER = ITEMS.register("grilled_avennian_sprig_skewer", () ->
            new com.mao.barbequesdelight.content.item.BBQSkewerItem(new Item.Properties().food(EEFoodValues.GRLLED_AVENNIAN_SPRIG).craftRemainder(Items.STICK)));
    public static Supplier<Item> RAW_SPROUT_SKEWER = ITEMS.register("raw_sprout_skewer", () ->
            new com.mao.barbequesdelight.content.item.BBQSkewerItem(new Item.Properties()));
    public static Supplier<Item> GRILLED_SPROUT_SKEWER = ITEMS.register("grilled_sprout_skewer", () ->
            new com.mao.barbequesdelight.content.item.BBQSkewerItem(new Item.Properties().food(EEFoodValues.SPROUT_SKEWER).craftRemainder(Items.STICK)));
}
