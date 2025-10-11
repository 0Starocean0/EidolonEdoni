package cn.nutminds.eidolonedoni;

import cn.nutminds.eidolonedoni.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(EidolonEdoni.MODID)
public class EidolonEdoni {
    public static final String MODID = "eidolon_edoni";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EidolonEdoni(IEventBus modBus, ModContainer container) {
        EEItems.ITEMS.register(modBus);
        EEBlocks.BLOCKS.register(modBus);
        EEBlockEntityTypes.BLOCK_ENTITIES.register(modBus);
        EECreativeTab.CREATIVE_MODE_TAB.register(modBus);
        EEEffects.EFFECTS.register(modBus);
        container.registerConfig(ModConfig.Type.COMMON, EEConfig.SPEC);
    }

    public static ResourceLocation modid(String string) {
        return ResourceLocation.fromNamespaceAndPath(MODID, string);
    }
}
