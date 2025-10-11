package cn.nutminds.eidolonedoni.event;

import cn.nutminds.eidolonedoni.EidolonEdoni;
import cn.nutminds.eidolonedoni.client.ElderStoveRenderer;
import cn.nutminds.eidolonedoni.registry.EEBlockEntityTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;


@EventBusSubscriber(modid = EidolonEdoni.MODID, value = Dist.CLIENT)
public class EEClientEvents
{
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(EEBlockEntityTypes.STOVE.get(), ElderStoveRenderer::new);
    }
}
