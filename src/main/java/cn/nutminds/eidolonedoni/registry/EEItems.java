package cn.nutminds.eidolonedoni.registry;

import alexthw.eidolon_repraised.common.item.ItemBase;
import alexthw.eidolon_repraised.common.item.Tiers;
import cn.nutminds.eidolonedoni.EEFoodValues;
import cn.nutminds.eidolonedoni.EidolonEdoni;
import cn.nutminds.eidolonedoni.item.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.function.Supplier;

public class EEItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, EidolonEdoni.MODID);

    public static Item.Properties foodItem(FoodProperties food) {
        return (new Item.Properties()).food(food);
    }
    public static Item.Properties bowlFoodItem(FoodProperties food) {
        return new Item.Properties().food(food).craftRemainder(Items.BOWL).stacksTo(16);
    }
    public static Item.Properties drinkItem() {
        return new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16);
    }

    public static final DeferredHolder<Item, Item> SILVER_KNIFE = ITEMS.register("silver_knife",
            () -> new KnifeItem(Tiers.SilverTier.INSTANCE, new Item.Properties().attributes(
                    KnifeItem.createAttributes(Tiers.SilverTier.INSTANCE, 0.5f, -2.0f))));

    public static final DeferredHolder<Item, Item> ALL_ROUNDER = ITEMS.register("all_rounder",
            () -> new AllRounderItem(Tiers.MagicToolTier.INSTANCE, new Item.Properties().attributes(
                    KnifeItem.createAttributes(Tiers.MagicToolTier.INSTANCE, 1f, -1.6f)).rarity(Rarity.UNCOMMON)));

    public static final DeferredHolder<Item, BlockItem> ELDER_STOVE = ITEMS.register("elder_stove",
            () -> new BlockItem(EEBlocks.ELDER_STOVE.get(), new Item.Properties()));

    public static final Supplier<Item> RELAXING_INCENSE = ITEMS.register("relaxing_incense",
            () -> new ItemBase(new Item.Properties()).setLore("lore.eidolon_edoni.relaxing_incense"));
    public static final Supplier<Item> CHORUS_INCENSE = ITEMS.register("chorus_incense",
            () -> new ItemBase(new Item.Properties()).setLore("lore.eidolon_edoni.chorus_incense"));
    public static final Supplier<Item> STIMULATING_INCENSE = ITEMS.register("stimulating_incense",
            () -> new ItemBase(new Item.Properties()).setLore("lore.eidolon_edoni.stimulating_incense"));
    public static final Supplier<Item> GLUTTONY_INCENSE = ITEMS.register("gluttony_incense",
            () -> new ItemBase(new Item.Properties()).setLore("lore.eidolon_edoni.gluttony_incense"));

    public static final DeferredHolder<Item, BlockItem> SILDRIAN_SEED_BAG = ITEMS.register("sildrian_seed_bag",
            () -> new BlockItem(EEBlocks.SILDRIAN_SEED_BAG.get(), new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> MERAMMER_ROOT_CRATE = ITEMS.register("merammer_root_crate",
            () -> new BlockItem(EEBlocks.MERAMMER_ROOT_CRATE.get(), new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> AVENNIAN_SPRIG_BALE = ITEMS.register("avennian_sprig_bale",
            () -> new BlockItem(EEBlocks.AVENNIAN_SPRIG_BALE.get(), new Item.Properties()));

    public static final DeferredHolder<Item, BlockItem> ILLWOOD_CABINET = ITEMS.register("illwood_cabinet",
            () -> new BlockItem(EEBlocks.ILLWOOD_CABINET.get(), new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> POLISHED_CABINET = ITEMS.register("polished_cabinet",
            () -> new BlockItem(EEBlocks.POLISHED_CABINET.get(), new Item.Properties()));

    public static final DeferredHolder<Item, BlockItem> RICH_SOIL_PLANTER = ITEMS.register("rich_soil_planter",
            () -> new BlockItem(EEBlocks.RICH_SOIL_PLANTER.get(), new Item.Properties()));

    public static final Supplier<Item> FRIED_HEART = ITEMS.register("fried_heart", () ->
            new ConsumableItem(foodItem(EEFoodValues.FRIED_HEART), true));

    public static final Supplier<Item> JELLY_SLUG = ITEMS.register("jelly_slug", () ->
            new ConsumableItem(bowlFoodItem(EEFoodValues.JELLY_SLUG), true));

    public static final DeferredHolder<Item, BlockItem> HEART_TERRINE = ITEMS.register("heart_terrine",
            () -> new BlockItem(EEBlocks.HEART_TERRINE.get(), new Item.Properties()));
    public static final Supplier<Item> HEART_TERRINE_SLICE = ITEMS.register("heart_terrine_slice", () ->
            new ConsumableItem(foodItem(EEFoodValues.HEART_TERRINE_SLICE), true));
    public static final Supplier<Item> COLD_NOODLE = ITEMS.register("cold_noodle", () ->
            new ColdNoodleItem(bowlFoodItem(EEFoodValues.COLD_NOODLE)));
    public static final Supplier<Item> SHAVED_ICE = ITEMS.register("shaved_ice", () ->
            new ShavedIceItem(bowlFoodItem(EEFoodValues.SHAVED_ICE), true));

    public static final Supplier<Item>BECHAMEL = ITEMS.register("bechamel_sauce", () ->
            new ConsumableItem(bowlFoodItem(EEFoodValues.BECHAMEL)));
    public static final DeferredHolder<Item, BlockItem> CURRY_POT = ITEMS.register("pot_of_curry",
            () -> new BlockItem(EEBlocks.CURRY_POT.get(), new Item.Properties()));
    public static final Supplier<Item>CURRY_BREAD = ITEMS.register("bread_with_curry", () ->
            new MerammerFoodItem(foodItem(EEFoodValues.CURRY_BREAD)));
    public static final Supplier<Item>CURRY_RICE = ITEMS.register("rice_with_curry", () ->
            new MerammerFoodItem(bowlFoodItem(EEFoodValues.CURRY_RICE)));
    public static final Supplier<Item>CREAM_OF_MUSHROOM_SOUP = ITEMS.register("cream_of_mushroom_soup", () ->
            new MerammerFoodItem(bowlFoodItem(EEFoodValues.CREAM_OF_MUSHROOM_SOUP)));

    public static final Supplier<Item>CREAM_PASTA_WITH_PETALS = ITEMS.register("cream_pasta_with_petals", () ->
            new MerammerFoodItem(bowlFoodItem(EEFoodValues.CREAM_PASTA_WITH_PETALS)));
    public static final Supplier<Item>OANNA_CHICKEN_CONGEE = ITEMS.register("oanna_chicken_congee", () ->
            new ConsumableItem(bowlFoodItem(EEFoodValues.OANNA_CHICKEN_CONGEE), true));
    public static final DeferredHolder<Item, BlockItem> OANNA_MOONCAKE = ITEMS.register("oanna_mooncake",
            () -> new BlockItem(EEBlocks.OANNA_MOONCAKE.get(), new Item.Properties()));
    public static final Supplier<Item>OANNA_MOONCAKE_SLICE = ITEMS.register("oanna_mooncake_slice", () ->
            new ConsumableItem(foodItem(EEFoodValues.OANNA_MOONCAKE_SLICE)));
    public static final Supplier<Item>OANNA_PETAL_TEA = ITEMS.register("oanna_petal_tea", () ->
            new DrinkableItem(drinkItem().food(EEFoodValues.OANNA_PETAL_TEA), true));

    public static final DeferredHolder<Item, BlockItem> SILDRIAN_PUDDING = ITEMS.register("sildrian_pudding",
            () -> new BlockItem(EEBlocks.SILDRIAN_PUDDING.get(), new Item.Properties()));
    public static final Supplier<Item>SILDRIAN_BEEF = ITEMS.register("sildrian_beef", () ->
            new ConsumableItem(bowlFoodItem(EEFoodValues.SILDRIAN_BEEF), true));
    public static final Supplier<Item>MIXED_PORRIDGE = ITEMS.register("mixed_porridge", () ->
            new ConsumableItem(bowlFoodItem(EEFoodValues.MIXED_PORRIDGE), true));
    public static final Supplier<Item>SILDRIAN_TEA = ITEMS.register("sildrian_tea", () ->
            new DrinkableItem(drinkItem().food(EEFoodValues.SILDRIAN_TEA), true));

    public static final DeferredHolder<Item, BlockItem> ROASTED_AVENNIAN_SPRIG_BALE = ITEMS.register("roasted_avennian_sprig_bale",
            () -> new BlockItem(EEBlocks.ROASTED_AVENNIAN_SPRIG_BALE.get(), new Item.Properties()));
    public static final Supplier<Item>ROASTED_AVENNIAN_SPRIG = ITEMS.register("roasted_avennian_sprig", () ->
            new ConsumableItem(foodItem(EEFoodValues.ROASTED_AVENNIAN_SPRIG), true));
    public static final Supplier<Item>AVENNIAN_SCRAMBLED_EGG = ITEMS.register("avennian_scrambled_egg", () ->
            new ConsumableItem(bowlFoodItem(EEFoodValues.AVENNIAN_SCRAMBLED_EGG), true));
    public static final Supplier<Item>AVENNIAN_STEW = ITEMS.register("avennian_stew", () ->
            new ConsumableItem(bowlFoodItem(EEFoodValues.AVENNIAN_STEW), true));
    public static final Supplier<Item>FRIED_AVENNA_DUMPLING = ITEMS.register("fried_avenna_dumpling", () ->
            new ConsumableItem(foodItem(EEFoodValues.FRIED_AVENNA_DUMPLING), true));

    public static final DeferredHolder<Item, BlockItem> STUFFED_WITHER_SKELETON_SKULL = ITEMS.register("stuffed_wither_skeleton_skull",
            () -> new BlockItem(EEBlocks.STUFFED_WITHER_SKELETON_SKULL.get(), new Item.Properties()));
    public static final Supplier<Item>STUFFED_WITHER_SKELETON_SKULL_BOWL = ITEMS.register("stuffed_wither_skeleton_skull_bowl", () ->
            new StuffedWitherSkeletonSkullBowlItem(bowlFoodItem(EEFoodValues.STUFFED_WITHER_SKELETON_SKULL)));
    public static final DeferredHolder<Item, BlockItem> JOCKEY_PIE = ITEMS.register("jockey_pie",
            () -> new BlockItem(EEBlocks.JOCKEY_PIE.get(), new Item.Properties()));
    public static final Supplier<Item>JOCKEY_PIE_SLICE = ITEMS.register("jockey_pie_slice", () ->
            new JockeyPieSliceItem(foodItem(EEFoodValues.JOCKEY_PIE_SLICE)));
    public static final Supplier<Item>CALX_CROQUETTE = ITEMS.register("calx_croquette", () ->
            new ConsumableItem(bowlFoodItem(EEFoodValues.CALX_CROQUETTE), true));
    public static final Supplier<Item>MIRECHOCO = ITEMS.register("mirechoco", () ->
            new ConsumableItem(foodItem(EEFoodValues.MIRECHOCO), true));
}
