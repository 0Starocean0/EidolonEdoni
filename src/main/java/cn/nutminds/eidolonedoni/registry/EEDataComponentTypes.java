package cn.nutminds.eidolonedoni.registry;

import cn.nutminds.eidolonedoni.EidolonEdoni;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Unit;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EEDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, EidolonEdoni.MODID);

    public static final Supplier<DataComponentType<Integer>> MERAMMER_FOOD = DATA_COMPONENTS.register(
            "merammer_food", () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.POSITIVE_INT).build());
}
