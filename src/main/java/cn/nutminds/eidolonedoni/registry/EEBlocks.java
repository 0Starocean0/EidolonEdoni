package cn.nutminds.eidolonedoni.registry;

import alexthw.eidolon_repraised.registries.Registry;
import cn.nutminds.eidolonedoni.EidolonEdoni;
import cn.nutminds.eidolonedoni.block.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.block.CabinetBlock;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.function.ToIntFunction;

public class EEBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EidolonEdoni.MODID);

    private static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
    }

    public static final DeferredBlock<Block> ELDER_STOVE = BLOCKS.register("elder_stove",
            () -> new ElderStoveBlock(Block.Properties.ofFullCopy(Blocks.STONE).strength(3.0f, 3.0f).sound(SoundType.STONE).lightLevel(litBlockEmission(13)))
    );

    public static final DeferredBlock<Block> SILDRIAN_SEED_BAG = BLOCKS.register("sildrian_seed_bag",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.WHITE_WOOL)));
    public static final DeferredBlock<Block> MERAMMER_ROOT_CRATE = BLOCKS.register("merammer_root_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<Block> AVENNIAN_SPRIG_BALE = BLOCKS.register("avennian_sprig_bale",
            () -> new AvennianSprigBaleBlock(Block.Properties.ofFullCopy(Blocks.HAY_BLOCK)));

    public static final DeferredBlock<CabinetBlock> ILLWOOD_CABINET = BLOCKS.register("illwood_cabinet",
            () -> new CabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL)));
    public static final DeferredBlock<CabinetBlock> POLISHED_CABINET = BLOCKS.register("polished_cabinet",
            () -> new CabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL)));

    public static final DeferredBlock<Block> RICH_SOIL_PLANTER = BLOCKS.register("rich_soil_planter",
            () -> new RichSoilPlanterBlock(Block.Properties.ofFullCopy(Registry.PLANTER.get())));

    public static final DeferredBlock<Block> HEART_TERRINE = BLOCKS.register("heart_terrine",
            () -> new HeartTerrineBlock(Block.Properties.ofFullCopy(Blocks.CAKE), EEItems.HEART_TERRINE_SLICE));

    public static final DeferredBlock<Block> CURRY_POT = BLOCKS.register("pot_of_curry",
            () -> new CurryPotBlock(Block.Properties.ofFullCopy(Blocks.CAKE).sound(SoundType.LANTERN), () -> Items.BREAD, ModItems.COOKED_RICE, EEItems.CURRY_BREAD, EEItems.CURRY_RICE, true));

    public static final DeferredBlock<Block> OANNA_MOONCAKE = BLOCKS.register("oanna_mooncake",
            () -> new PieBlock(Block.Properties.ofFullCopy(Blocks.CAKE), EEItems.OANNA_MOONCAKE_SLICE));

    public static final DeferredBlock<Block> SILDRIAN_PUDDING = BLOCKS.register("sildrian_pudding",
            () -> new SildrianPuddingBlock(Block.Properties.ofFullCopy(Blocks.CAKE).sound(SoundType.GLASS), true));

    public static final DeferredBlock<Block> GRILLED_AVENNIAN_SPRIG_BALE = BLOCKS.register("grilled_avennian_sprig_bale",
            () -> new GrilledAvennianSprigBaleBlock(Block.Properties.ofFullCopy(Blocks.HAY_BLOCK)));

    public static final DeferredBlock<Block> STUFFED_WITHER_SKELETON_SKULL = BLOCKS.register("stuffed_wither_skeleton_skull",
            () -> new StuffedWitherSkeletonSkullBlock(Block.Properties.ofFullCopy(Blocks.WITHER_SKELETON_SKULL), EEItems.STUFFED_WITHER_SKELETON_SKULL_BOWL, true));
    public static final DeferredBlock<Block> JOCKEY_PIE = BLOCKS.register("jockey_pie",
            () -> new JockeyPieBlock(Block.Properties.ofFullCopy(Blocks.CAKE), EEItems.JOCKEY_PIE_SLICE));
}
