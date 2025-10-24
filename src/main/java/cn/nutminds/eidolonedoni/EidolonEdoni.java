package cn.nutminds.eidolonedoni;

import alexthw.eidolon_repraised.registries.IncenseRegistry;
import cn.nutminds.eidolonedoni.incense.*;
import cn.nutminds.eidolonedoni.item.AllRounderItem;
import cn.nutminds.eidolonedoni.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(EidolonEdoni.MODID)
public class EidolonEdoni {
    public static final String MODID = "eidolon_edoni";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EidolonEdoni(IEventBus modBus, ModContainer container) {
        modBus.addListener(this::setup);
        EEItems.ITEMS.register(modBus);
        EEBlocks.BLOCKS.register(modBus);
        EEBlockEntityTypes.BLOCK_ENTITIES.register(modBus);
        EECreativeTab.CREATIVE_MODE_TAB.register(modBus);
        EEEffects.EFFECTS.register(modBus);
        container.registerConfig(ModConfig.Type.COMMON, EEConfig.SPEC);
    }

    public static final ResourceLocation RELAXING_INCENSE = ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID,"relaxing_incense");
    public static final ResourceLocation CHORUS_INCENSE = ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID,"chorus_incense");
    public static final ResourceLocation STIMULATING_INCENSE = ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID,"stimulating_incense");
    public static final ResourceLocation GLUTTONY_INCENSE = ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID,"gluttony_incense");

    public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            AllRounderItem.initHarvestables();
            IncenseRegistry.register(EEItems.RELAXING_INCENSE.get(), RELAXING_INCENSE, () -> new RelaxingIncense(RELAXING_INCENSE));
            IncenseRegistry.register(EEItems.CHORUS_INCENSE.get(), CHORUS_INCENSE, () -> new ChorusIncense(CHORUS_INCENSE));
            IncenseRegistry.register(EEItems.STIMULATING_INCENSE.get(), STIMULATING_INCENSE, () -> new StimulatingIncense(STIMULATING_INCENSE));
            IncenseRegistry.register(EEItems.GLUTTONY_INCENSE.get(), GLUTTONY_INCENSE, () -> new GluttonyIncense(GLUTTONY_INCENSE));
        });
    }

    public static ResourceLocation modid(String string) {
        return ResourceLocation.fromNamespaceAndPath(MODID, string);
    }
}
