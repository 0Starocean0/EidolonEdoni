package cn.nutminds.eidolonedoni.mixin;

import alexthw.eidolon_repraised.api.capability.IReputation;
import alexthw.eidolon_repraised.common.deity.Deities;
import alexthw.eidolon_repraised.registries.EidolonCapabilities;
import cn.nutminds.eidolonedoni.EidolonEdoni;
import cn.nutminds.eidolonedoni.registry.EEItems;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    private static final Logger log = LoggerFactory.getLogger(ItemStackMixin.class);

    @Shadow public abstract boolean isFramed();

    @Shadow public abstract boolean is(Item item);

    @Inject(method = "getTooltipLines",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Item;appendHoverText(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/Item$TooltipContext;Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;)V"
            )
    )
    private void getTooltipLines(Item.TooltipContext context, Player player, TooltipFlag flag, CallbackInfoReturnable<List<Component>> cir, @Local(ordinal = 0) List<Component> list) {
        if (is(EEItems.ALL_ROUNDER.get())) {
            list.add(CommonComponents.EMPTY);
            if (player != null) {
                IReputation reputation = player.getCapability(EidolonCapabilities.REPUTATION_CAPABILITY);
                if (reputation != null) {
                    double darkReputation = reputation.getReputation(Deities.DARK_DEITY.getId());
                    double lightReputation = reputation.getReputation(Deities.LIGHT_DEITY.getId());
                    if (darkReputation > lightReputation) {
                        list.add(Component.translatable("item.eidolon_edoni.all_rounder.lore.dark")
                                .withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.ITALIC));
                    } else if (darkReputation < lightReputation) {
                        list.add(Component.translatable("item.eidolon_edoni.all_rounder.lore.light")
                                .withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.ITALIC));
                    } else if (darkReputation == lightReputation && darkReputation != 0 && lightReputation != 0) {
                        list.add(Component.translatable("item.eidolon_edoni.all_rounder.lore.balance")
                                .withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.ITALIC));
                    } else {
                        list.add(Component.translatable("item.eidolon_edoni.all_rounder.lore.none")
                                .withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.ITALIC));
                    }
                }
            }
        }
    }
}