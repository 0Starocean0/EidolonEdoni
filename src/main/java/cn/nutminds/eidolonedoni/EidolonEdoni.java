package cn.nutminds.eidolonedoni;

import alexthw.eidolon_repraised.api.ritual.Ritual;
import alexthw.eidolon_repraised.api.spells.Spell;
import alexthw.eidolon_repraised.registries.IncenseRegistry;
import alexthw.eidolon_repraised.registries.RitualRegistry;
import alexthw.eidolon_repraised.registries.Signs;
import alexthw.eidolon_repraised.registries.Spells;
import cn.nutminds.eidolonedoni.compat.vampiresdelight.VampiresDelightIntegration;
import cn.nutminds.eidolonedoni.incense.*;
import cn.nutminds.eidolonedoni.compat.barbequesdelight.BBQDelightIntegration;
import cn.nutminds.eidolonedoni.item.*;
import cn.nutminds.eidolonedoni.registry.*;
import cn.nutminds.eidolonedoni.ritual.*;
import cn.nutminds.eidolonedoni.spell.DevourSpell;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

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
        EERecipeTypes.RECIPE_TYPES.register(modBus);
        EERecipeTypes.RECIPE_SERIALIZERS.register(modBus);
        EEDataComponentTypes.DATA_COMPONENTS.register(modBus);
        container.registerConfig(ModConfig.Type.COMMON, EEConfig.SPEC);

        if (ModList.get().isLoaded("barbequesdelight")) {
            BBQDelightIntegration.ITEMS.register(modBus);
        }
        if (ModList.get().isLoaded("vampiresdelight")) {
            VampiresDelightIntegration.ITEMS.register(modBus);
        }
    }

    public static final ResourceLocation RELAXING_INCENSE = ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID,"relaxing_incense");
    public static final ResourceLocation CHORUS_INCENSE = ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID,"chorus_incense");
    public static final ResourceLocation STIMULATING_INCENSE = ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID,"stimulating_incense");
    public static final ResourceLocation GLUTTONY_INCENSE = ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID,"gluttony_incense");
    public static final ResourceLocation BUTCHERY = ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID,"butchery");
    public static final ResourceLocation DEVOUR = ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID,"devour");

    public static Ritual BUTCHERY_RITUAL;
    public static Spell DEVOUR_SPELL;

    public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            AllRounderItem.initHarvestables();
            IncenseRegistry.register(EEItems.RELAXING_INCENSE.get(), RELAXING_INCENSE, () -> new RelaxingIncense(RELAXING_INCENSE));
            IncenseRegistry.register(EEItems.CHORUS_INCENSE.get(), CHORUS_INCENSE, () -> new ChorusIncense(CHORUS_INCENSE));
            IncenseRegistry.register(EEItems.STIMULATING_INCENSE.get(), STIMULATING_INCENSE, () -> new StimulatingIncense(STIMULATING_INCENSE));
            IncenseRegistry.register(EEItems.GLUTTONY_INCENSE.get(), GLUTTONY_INCENSE, () -> new GluttonyIncense(GLUTTONY_INCENSE));
            BUTCHERY_RITUAL = RitualRegistry.register(BUTCHERY, new ButcheryRitual());
            DEVOUR_SPELL = Spells.register(new DevourSpell(DEVOUR, Signs.BLOOD_SIGN, Signs.FLAME_SIGN, Signs.DEATH_SIGN, Signs.FLAME_SIGN, Signs.BLOOD_SIGN));
        });
    }

    public static ResourceLocation modid(String string) {
        return ResourceLocation.fromNamespaceAndPath(MODID, string);
    }
}
