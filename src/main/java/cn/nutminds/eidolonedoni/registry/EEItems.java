package cn.nutminds.eidolonedoni.registry;

import alexthw.eidolon_repraised.common.item.ItemBase;
import alexthw.eidolon_repraised.common.item.Tiers;
import cn.nutminds.eidolonedoni.EEFoodValues;
import cn.nutminds.eidolonedoni.EidolonEdoni;
import cn.nutminds.eidolonedoni.item.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;
import vectorwing.farmersdelight.common.item.KnifeItem;
import vectorwing.farmersdelight.common.registry.ModItems;

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
    public static Item.Properties curryFoodItem(Item item) {
        Item.Properties properties = new Item.Properties()
                .food(EEFoodValues.curryFood(item))
                .stacksTo(item.getMaxStackSize(item.getDefaultInstance()));
        if (item.hasCraftingRemainingItem()) properties.craftRemainder(item.getCraftingRemainingItem());
        return properties;
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
    public static final Supplier<Item>CURRY_CHUNK = ITEMS.register("curry_chunk", () ->
            new Item(new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> CURRY_POT = ITEMS.register("pot_of_curry",
            () -> new BlockItem(EEBlocks.CURRY_POT.get(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item>CURRY_BREAD = ITEMS.register("bread_with_curry", () ->
            new MerammerFoodItem(curryFoodItem(Items.BREAD)));
    public static final Supplier<Item>CURRY_RICE = ITEMS.register("rice_with_curry", () ->
            new MerammerFoodItem(curryFoodItem(ModItems.COOKED_RICE.get())));
    public static final Supplier<Item> CURRY_FISH_BALL = ITEMS.register("curry_fish_ball", () ->
            new MerammerFoodItem(bowlFoodItem(EEFoodValues.CURRY_FISH_BALL)));
    public static final Supplier<Item> CURRY_PORKCHOP = ITEMS.register("curry_porkchop", () ->
            new MerammerFoodItem(bowlFoodItem(EEFoodValues.CURRY_PORKCHOP)));
    public static final Supplier<Item> SEAFOOD_CURRY = ITEMS.register("seafood_curry", () ->
            new MerammerFoodItem(bowlFoodItem(EEFoodValues.SEAFOOD_CURRY)));
    public static final Supplier<Item> RABBIT_CURRY = ITEMS.register("rabbit_curry", () ->
            new MerammerFoodItem(bowlFoodItem(EEFoodValues.RABBIT_CURRY)));
    public static final Supplier<Item>CREAM_OF_MUSHROOM_SOUP = ITEMS.register("cream_of_mushroom_soup", () ->
            new MerammerFoodItem(bowlFoodItem(EEFoodValues.CREAM_OF_MUSHROOM_SOUP)));
    //Compat Curry Foods
    public static Supplier<Item>CURRY_RUSTIC_LOAF = ITEMS.register("rustic_loaf_slice_with_curry", () ->
            new MerammerFoodItem(curryFoodItem(BuiltInRegistries.ITEM.get(
                    ResourceLocation.fromNamespaceAndPath("cookscollection", "rustic_loaf_slice")))));
    public static Supplier<Item>CURRY_TORTILLA = ITEMS.register("tortilla_with_curry", () ->
            new MerammerFoodItem(curryFoodItem(BuiltInRegistries.ITEM.get(
                    ResourceLocation.fromNamespaceAndPath("culturaldelights", "tortilla")))));
    public static Supplier<Item>CURRY_BREAD_SLICES = ITEMS.register("slices_of_bread_with_curry", () ->
            new MerammerFoodItem(curryFoodItem(BuiltInRegistries.ITEM.get(
                    ResourceLocation.fromNamespaceAndPath("mynethersdelight", "slices_of_bread")))));
    public static Supplier<Item>CURRY_TOASTS = ITEMS.register("toasts_with_curry", () ->
            new MerammerFoodItem(curryFoodItem(BuiltInRegistries.ITEM.get(
                    ResourceLocation.fromNamespaceAndPath("mynethersdelight", "toasts")))));
    public static Supplier<Item>CURRY_FRIED_DOUGH = ITEMS.register("fried_dough_with_curry", () ->
            new MerammerFoodItem(curryFoodItem(BuiltInRegistries.ITEM.get(
                    ResourceLocation.fromNamespaceAndPath("rusticdelight", "fried_dough")))));
    {
        if (!ModList.get().isLoaded("cookscollection")) {
            CURRY_RUSTIC_LOAF = null;
        }
        if (!ModList.get().isLoaded("culturaldelights")) {
            CURRY_TORTILLA = null;
        }
        if (!ModList.get().isLoaded("mynethersdelight")) {
            CURRY_BREAD_SLICES = null;
            CURRY_TOASTS = null;
        }
        if (!ModList.get().isLoaded("rusticdelight")) {
            CURRY_FRIED_DOUGH = null;
        }
    }

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
            () -> new BlockItem(EEBlocks.SILDRIAN_PUDDING.get(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item>SILDRIAN_BEEF = ITEMS.register("sildrian_beef", () ->
            new ConsumableItem(bowlFoodItem(EEFoodValues.SILDRIAN_BEEF), true));
    public static final Supplier<Item>MIXED_PORRIDGE = ITEMS.register("mixed_porridge", () ->
            new ConsumableItem(bowlFoodItem(EEFoodValues.MIXED_PORRIDGE), true));
    public static final Supplier<Item>SILDRIAN_TEA = ITEMS.register("sildrian_tea", () ->
            new DrinkableItem(drinkItem().food(EEFoodValues.SILDRIAN_TEA), true));

    public static final DeferredHolder<Item, BlockItem> GRLLED_AVENNIAN_SPRIG_BALE = ITEMS.register("grilled_avennian_sprig_bale",
            () -> new BlockItem(EEBlocks.GRILLED_AVENNIAN_SPRIG_BALE.get(), new Item.Properties()));
    public static Supplier<Item> GRILLED_AVENNIAN_SPRIG = ITEMS.register("grilled_avennian_sprig", () ->
            new ConsumableItem(foodItem(EEFoodValues.GRLLED_AVENNIAN_SPRIG), true));
    public static final Supplier<Item>AVENNIAN_SCRAMBLED_EGG = ITEMS.register("avennian_scrambled_egg", () ->
            new ConsumableItem(bowlFoodItem(EEFoodValues.AVENNIAN_SCRAMBLED_EGG), true));
    public static final Supplier<Item>AVENNIAN_STEW = ITEMS.register("avennian_stew", () ->
            new ConsumableItem(bowlFoodItem(EEFoodValues.AVENNIAN_STEW), true));
    public static final Supplier<Item>FRIED_AVENNA_DUMPLING = ITEMS.register("fried_avenna_dumpling", () ->
            new ConsumableItem(foodItem(EEFoodValues.FRIED_AVENNA_DUMPLING), true));
    {
        if (ModList.get().isLoaded("barbequesdelight")) {
            GRILLED_AVENNIAN_SPRIG = null;
        }
    }

    public static final DeferredHolder<Item, BlockItem> STUFFED_WITHER_SKELETON_SKULL = ITEMS.register("stuffed_wither_skeleton_skull",
            () -> new BlockItem(EEBlocks.STUFFED_WITHER_SKELETON_SKULL.get(), new Item.Properties().stacksTo(1)));
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

    public static final Supplier<Item>CHERRY_CANDY = ITEMS.register("cherry_candy", () ->
            new CherryCandyItem(foodItem(EEFoodValues.CHERRY_CANDY)));

    public static final Supplier<Item>PREMIUM_DOG_FOOD = ITEMS.register("premium_dog_food", () ->
            new PremiumDogFoodItem(foodItem(EEFoodValues.PREMIUM_DOG_FOOD).craftRemainder(Items.ARMADILLO_SCUTE).stacksTo(16)));
    public static final Supplier<Item>DARK_FEED = ITEMS.register("dark_feed", () ->
            new DarkFeedItem(new Item.Properties().stacksTo(16)));
}
