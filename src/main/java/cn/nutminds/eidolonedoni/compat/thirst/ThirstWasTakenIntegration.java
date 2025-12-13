package cn.nutminds.eidolonedoni.compat.thirst;

import cn.nutminds.eidolonedoni.EidolonEdoni;
import cn.nutminds.eidolonedoni.registry.EEItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.lang.reflect.Method;

@EventBusSubscriber(modid = EidolonEdoni.MODID)
public class ThirstWasTakenIntegration {
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        if (ModList.get().isLoaded("thirst")) {
            try {
                Class<?> eventClass = Class.forName("dev.ghen.thirst.foundation.common.event.RegisterThirstValueEvent");
                Method addDrinkMethod = eventClass.getMethod("addDrink", Object.class, int.class, int.class);
                Method addFoodMethod = eventClass.getMethod("addFood", Object.class, int.class, int.class);

                Object instance = eventClass.getDeclaredConstructor().newInstance();

                addDrinkMethod.invoke(instance, EEItems.OANNA_PETAL_TEA.get(), 12, 22);
                addDrinkMethod.invoke(instance, EEItems.SILDRIAN_TEA.get(), 12, 22);
                addFoodMethod.invoke(instance, EEItems.COLD_NOODLE.get(), 4, 6);
                addFoodMethod.invoke(instance, EEItems.SHAVED_ICE.get(), 8, 9);
                addFoodMethod.invoke(instance, EEItems.OANNA_CHICKEN_CONGEE.get(), 4, 5);
                addFoodMethod.invoke(instance, EEItems.MIXED_PORRIDGE.get(), 4, 5);
                addFoodMethod.invoke(instance, EEItems.AVENNIAN_STEW.get(), 4, 5);

            } catch (Exception ignored) {}
        }
    }
}
