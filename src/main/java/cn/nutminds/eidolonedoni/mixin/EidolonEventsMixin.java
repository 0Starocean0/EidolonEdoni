package cn.nutminds.eidolonedoni.mixin;

import alexthw.eidolon_repraised.event.Events;
import cn.nutminds.eidolonedoni.registry.EEEffects;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Events.class)
public class EidolonEventsMixin {
    @Inject(method = "onTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getArmorSlots()Ljava/lang/Iterable;"
            ),
            remap = false)
    private void onTick(EntityTickEvent.Pre event, CallbackInfo ci, @Local(ordinal = 0) LocalBooleanRef ref){
        if (event.getEntity() instanceof LivingEntity entity) {
            if (entity.hasEffect(EEEffects.SOULTAKING))
                ref.set(true);
        }
    }
}
