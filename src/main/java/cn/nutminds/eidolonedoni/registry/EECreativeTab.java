package cn.nutminds.eidolonedoni.registry;

import cn.nutminds.eidolonedoni.EidolonEdoni;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EECreativeTab{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EidolonEdoni.MODID);
    public static final Supplier<CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TAB.register("eidolon_edoni", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + EidolonEdoni.MODID + ".eidolon_edoni"))
            .icon(() -> new ItemStack(EEItems.ELDER_STOVE.get()))
            .displayItems((params, output) -> {
                output.accept(EEItems.ELDER_STOVE.get());
                output.accept(EEItems.RICH_SOIL_PLANTER.get());
                output.accept(EEItems.SILDRIAN_SEED_BAG.get());
                output.accept(EEItems.MERAMMER_ROOT_CRATE.get());
                output.accept(EEItems.AVENNIAN_SPRIG_BALE.get());
                output.accept(EEItems.ILLWOOD_CABINET.get());
                output.accept(EEItems.POLISHED_CABINET.get());
                output.accept(EEItems.SILVER_KNIFE.get());
                output.accept(EEItems.ALL_ROUNDER.get());
                output.accept(EEItems.RELAXING_INCENSE.get());
                output.accept(EEItems.CHORUS_INCENSE.get());
                output.accept(EEItems.STIMULATING_INCENSE.get());
                output.accept(EEItems.GLUTTONY_INCENSE.get());
                output.accept(EEItems.JELLY_SLUG.get());
                output.accept(EEItems.CHERRY_CANDY.get());
                output.accept(EEItems.BECHAMEL.get());
                output.accept(EEItems.CURRY_POT.get());
                output.accept(EEItems.CURRY_BREAD.get());
                output.accept(EEItems.CURRY_RICE.get());
                output.accept(EEItems.CREAM_OF_MUSHROOM_SOUP.get());
                output.accept(EEItems.CREAM_PASTA_WITH_PETALS.get());
                output.accept(EEItems.OANNA_MOONCAKE.get());
                output.accept(EEItems.OANNA_MOONCAKE_SLICE.get());
                output.accept(EEItems.OANNA_CHICKEN_CONGEE.get());
                output.accept(EEItems.OANNA_PETAL_TEA.get());
                output.accept(EEItems.SILDRIAN_PUDDING.get());
                output.accept(EEItems.SILDRIAN_BEEF.get());
                output.accept(EEItems.MIXED_PORRIDGE.get());
                output.accept(EEItems.SILDRIAN_TEA.get());
                output.accept(EEItems.ROASTED_AVENNIAN_SPRIG_BALE.get());
                output.accept(EEItems.ROASTED_AVENNIAN_SPRIG.get());
                output.accept(EEItems.AVENNIAN_SCRAMBLED_EGG.get());
                output.accept(EEItems.AVENNIAN_STEW.get());
                output.accept(EEItems.FRIED_AVENNA_DUMPLING.get());
                output.accept(EEItems.JOCKEY_PIE.get());
                output.accept(EEItems.JOCKEY_PIE_SLICE.get());
                output.accept(EEItems.CALX_CROQUETTE.get());
                output.accept(EEItems.MIRECHOCO.get());
                output.accept(EEItems.STUFFED_WITHER_SKELETON_SKULL.get());
                output.accept(EEItems.STUFFED_WITHER_SKELETON_SKULL_BOWL.get());
                output.accept(EEItems.FRIED_HEART.get());
                output.accept(EEItems.HEART_TERRINE.get());
                output.accept(EEItems.HEART_TERRINE_SLICE.get());
                output.accept(EEItems.COLD_NOODLE.get());
                output.accept(EEItems.SHAVED_ICE.get());
                output.accept(EEItems.PREMIUM_DOG_FOOD.get());
                output.accept(EEItems.DARK_FEED.get());
            })
            .build()
    );
}
