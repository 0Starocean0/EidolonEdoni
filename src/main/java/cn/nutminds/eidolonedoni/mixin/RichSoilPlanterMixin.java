package cn.nutminds.eidolonedoni.mixin;

import alexthw.eidolon_repraised.common.block.HerbBlockBase;
import cn.nutminds.eidolonedoni.registry.EEBlocks;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HerbBlockBase.class)
public class RichSoilPlanterMixin {
    @ModifyReturnValue(
            method = "mayPlaceOn",
            at = @At("RETURN")
    )
    private boolean mayPlaceOn(boolean original, @Local(argsOnly = true) BlockState state) {
        return original || state.is(EEBlocks.RICH_SOIL_PLANTER);
    }
}
