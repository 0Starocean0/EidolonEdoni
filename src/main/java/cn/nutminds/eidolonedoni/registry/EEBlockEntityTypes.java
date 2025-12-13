package cn.nutminds.eidolonedoni.registry;

import cn.nutminds.eidolonedoni.EidolonEdoni;
import cn.nutminds.eidolonedoni.block.entity.AvennianSprigBaleBlockEntity;
import cn.nutminds.eidolonedoni.block.entity.ElderStoveBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;

import java.util.function.Supplier;

@EventBusSubscriber(modid = EidolonEdoni.MODID)
public class EEBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, EidolonEdoni.MODID);

    public static final Supplier<BlockEntityType<ElderStoveBlockEntity>> STOVE = BLOCK_ENTITIES.register("stove",
            () -> BlockEntityType.Builder.of(ElderStoveBlockEntity::new, EEBlocks.ELDER_STOVE.get()).build(null));
    public static final Supplier<BlockEntityType<AvennianSprigBaleBlockEntity>> AVENNIAN_SPRIG_BALE = BLOCK_ENTITIES.register("avennian_sprig_bale",
            () -> BlockEntityType.Builder.of(AvennianSprigBaleBlockEntity::new, EEBlocks.AVENNIAN_SPRIG_BALE.get()).build(null));


    @SubscribeEvent
    public static void addCabinetsBlockEntities(BlockEntityTypeAddBlocksEvent event) {
        event.modify(ModBlockEntityTypes.CABINET.get(),
                EEBlocks.ILLWOOD_CABINET.get(),
                EEBlocks.POLISHED_CABINET.get()
        );
    }
}
