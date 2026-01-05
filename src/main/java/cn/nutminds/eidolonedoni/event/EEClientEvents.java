package cn.nutminds.eidolonedoni.event;

import alexthw.eidolon_repraised.registries.Registry;
import cn.nutminds.eidolonedoni.EidolonEdoni;
import cn.nutminds.eidolonedoni.client.ElderStoveRenderer;
import cn.nutminds.eidolonedoni.registry.EEBlockEntityTypes;
import cn.nutminds.eidolonedoni.registry.EEDataComponentTypes;
import cn.nutminds.eidolonedoni.registry.EEItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import vectorwing.farmersdelight.common.item.ConsumableItem;

import java.util.List;

@EventBusSubscriber(modid = EidolonEdoni.MODID, value = Dist.CLIENT)
public class EEClientEvents {
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(EEBlockEntityTypes.STOVE.get(), ElderStoveRenderer::new);
    }

    @SubscribeEvent
    public static void onTooltipEvent(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        CustomData blockEntityData = stack.get(DataComponents.BLOCK_ENTITY_DATA);
        List<Component> tooltip = event.getToolTip();
        if (stack.is(Registry.GOBLET.get().asItem()) && blockEntityData != null) {
            CompoundTag compoundTag = blockEntityData.copyTag();
            ResourceLocation entityId = ResourceLocation.tryParse(compoundTag.getString("type"));
            EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(entityId);

            if (compoundTag.getString("type").equals("minecraft:villager")) {
                tooltip.add(1, Component.translatable(entityType.toString()).withStyle(ChatFormatting.DARK_RED));
            } else {
                tooltip.add(1, Component.translatable(entityType.toString()).withStyle(ChatFormatting.DARK_PURPLE));
            }
        }
        if (stack.is(Registry.RED_CANDY) || stack.is(Registry.GRAPE_CANDY) || stack.is(EEItems.CHERRY_CANDY.get())) {
            for (FoodProperties.PossibleEffect effect : stack.getFoodProperties(event.getEntity()).effects()) {
                MobEffectInstance effectInstance = effect.effect();
                MutableComponent effectText = Component.translatable(effectInstance.getDescriptionId());
                Player player = event.getEntity();
                if (effectInstance.getDuration() > 20) {
                    effectText = Component.translatable("potion.withDuration", effectText, MobEffectUtil.formatDuration(effectInstance, 1, player == null ? 20 : player.level().tickRateManager().tickrate()));
                }
                tooltip.add(effectText.withStyle(effectInstance.getEffect().value().getCategory().getTooltipFormatting()));
            }
        }
        if (stack.has(EEDataComponentTypes.MERAMMER_FOOD)) {
            tooltip.add(Component.translatable(EidolonEdoni.MODID + ".tooltip.merammer_food",
                    stack.getComponents().getOrDefault(EEDataComponentTypes.MERAMMER_FOOD.get(), 1))
                    .withStyle(ChatFormatting.BLUE)
            );
        }
    }
}
