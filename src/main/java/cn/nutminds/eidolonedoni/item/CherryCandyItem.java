package cn.nutminds.eidolonedoni.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CherryCandyItem extends Item {
    public CherryCandyItem(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext tooltipContext, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(Component.translatable("lore.eidolon_edoni.cherry_candy").withColor(0xFFFF55AA).withStyle(ChatFormatting.ITALIC));
    }
}
