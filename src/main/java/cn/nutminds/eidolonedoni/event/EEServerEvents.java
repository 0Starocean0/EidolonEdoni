package cn.nutminds.eidolonedoni.event;

import alexthw.eidolon_repraised.codex.*;
import alexthw.eidolon_repraised.common.entity.WraithEntity;
import alexthw.eidolon_repraised.registries.EidolonPotions;
import alexthw.eidolon_repraised.registries.Registry;
import cn.nutminds.eidolonedoni.EEConfig;
import cn.nutminds.eidolonedoni.EidolonEdoni;
import cn.nutminds.eidolonedoni.registry.EEDataComponentTypes;
import cn.nutminds.eidolonedoni.registry.EEEffects;
import cn.nutminds.eidolonedoni.registry.EEItems;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.ConsumerEventHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

@EventBusSubscriber(modid = EidolonEdoni.MODID)
public class EEServerEvents {

    private static TagKey<EntityType<?>> create(String string) {
        return TagKey.create(Registries.ENTITY_TYPE, EidolonEdoni.modid(string));
    }

    public static final TagKey<EntityType<?>> IMMUNE_TO_FETTERED = create("immune_to_fettered");

    @SubscribeEvent
    public static void onFoodConsume(LivingEntityUseItemEvent.Finish event) {
        if (event.getItem().getComponents().has(EEDataComponentTypes.MERAMMER_FOOD.get())) {
            LivingEntity consumer = event.getEntity();
            Level level = consumer.getCommandSenderWorld();
            Iterator<MobEffectInstance> itr = consumer.getActiveEffects().iterator();
            ArrayList<Holder<MobEffect>> compatibleEffects = new ArrayList<>();


            while (itr.hasNext()) {
                MobEffectInstance effect = itr.next();
                if (effect.getEffect().value().getCategory().equals(MobEffectCategory.BENEFICIAL)) {
                    compatibleEffects.add(effect.getEffect());
                }
            }
            level.registryAccess().registryOrThrow(Registries.MOB_EFFECT).getTagOrEmpty(TagKey.create(Registries.MOB_EFFECT, EidolonEdoni.modid("merammer_food_blacklist"))).forEach(compatibleEffects::remove);

            if (!compatibleEffects.isEmpty()) {
                for (int i = 0; i < event.getItem().getComponents().get(EEDataComponentTypes.MERAMMER_FOOD.get()); i++) {
                    MobEffectInstance selectedEffect = consumer.getEffect(compatibleEffects.get(level.random.nextInt(compatibleEffects.size())));
                    if (selectedEffect != null) {
                        if (selectedEffect.getDuration() > 1) {
                            consumer.removeEffect(selectedEffect.getEffect());
                            consumer.addEffect(new MobEffectInstance(selectedEffect.getEffect(),
                                    selectedEffect.getDuration() / (EEConfig.MERAMMER_FOOD_DURATION_RATE.get()),
                                    selectedEffect.getAmplifier() + 1));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    static void onLivingDamageEvent(LivingDamageEvent.Post event) {
        if (event.getSource().getDirectEntity() instanceof final LivingEntity source) {
            if (event.getEntity().hasEffect(EEEffects.SOULTAKING)) {
                MobEffectInstance instance = event.getEntity().getEffect(EEEffects.SOULTAKING);
                if (instance != null) {
                    int amplifier = instance.getAmplifier();
                    source.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, (EEConfig.SOULTAKING_WEAKNESS_DURATION_BASE.get() * (amplifier + 1)), 1));
                    source.addEffect(new MobEffectInstance(EidolonPotions.VULNERABLE_EFFECT, (EEConfig.SOULTAKING_VULNERABLE_DURATION_BASE.get() * (amplifier + 1)), amplifier + 1));
                }
            }
            if (source.hasEffect(EEEffects.CLINGING)) {
                MobEffectInstance instance = source.getEffect(EEEffects.CLINGING);
                if (instance != null) {
                    int amplifier = instance.getAmplifier();
                    if (!event.getEntity().getType().is(IMMUNE_TO_FETTERED)) {
                        event.getEntity().addEffect(new MobEffectInstance(EEEffects.FETTERED, (EEConfig.CLINGING_FETTERED_DURATION_BASE.get() * (amplifier + 1))));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onMobSpawnEvent(FinalizeSpawnEvent event) {
        Mob mob = event.getEntity();
        MobSpawnType type = event.getSpawnType();
        if (mob instanceof Enemy) {
            if (type == MobSpawnType.NATURAL || type == MobSpawnType.PATROL || type == MobSpawnType.REINFORCEMENT || type == MobSpawnType.JOCKEY) {
                Player player = event.getLevel().getNearestPlayer(event.getX(), event.getY(), event.getZ(), 64, true);
                if (player != null && player.hasEffect(EEEffects.FAITHFUL)) {
                    MobEffectInstance instance = player.getEffect(EEEffects.FAITHFUL);
                    double distance = mob.distanceTo(player);
                    if (instance != null && distance <= (24 + (EEConfig.FAITHFUL_DISABLE_MOB_SPAWNING_RANGE.get() * (instance.getAmplifier() + 1)))) {
                        event.setSpawnCancelled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity().hasEffect(EEEffects.EXCITED)) {
            MobEffectInstance instance = event.getEntity().getEffect(EEEffects.EXCITED);
            if (instance != null && event.getOriginalAmount() >= 0) {
                event.setAmount(event.getOriginalAmount() + (event.getOriginalAmount() * (instance.getAmplifier() + 1)));
            }
        }
    }

    @SubscribeEvent
    static void onEntityTickPre(EntityTickEvent.Pre event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity livingEntity)
            if (livingEntity.hasEffect(EEEffects.FETTERED)) {
                Vec3 deltaMovement = entity.getDeltaMovement();
                entity.setDeltaMovement(deltaMovement.x * 0, deltaMovement.y * 0, deltaMovement.z * 0);
            }
    }

    @SubscribeEvent
    static void onMobKilled(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        Entity source = event.getSource().getDirectEntity();
        if (source instanceof Player player) {
            if (player.getItemInHand(InteractionHand.MAIN_HAND).is(EEItems.ALL_ROUNDER)) {
                Inventory inventory = player.getInventory();
                for (int i = 0; i < inventory.getContainerSize(); i++) {
                    ItemStack itemStack = inventory.getItem(i);
                    if (itemStack.is(Registry.GOBLET.get().asItem()) && !itemStack.has(DataComponents.BLOCK_ENTITY_DATA) && !(entity instanceof Monster)) {
                        ItemStack filledGoblet = createFilledGoblet(entity);
                        player.addItem(filledGoblet);
                        if (!player.hasInfiniteMaterials()) {
                            itemStack.shrink(1);
                        }
                    }
                }
            }
        }
    }
    public static ItemStack createFilledGoblet(Entity entity) {
        ItemStack filledGoblet = new ItemStack(Registry.GOBLET.get().asItem());
        CompoundTag blockEntityData = new CompoundTag();
        blockEntityData.putString("type", EntityType.getKey(entity.getType()).toString());
        BlockItem.setBlockEntityData(filledGoblet, Registry.GOBLET_TILE_ENTITY.get(), blockEntityData);
        return filledGoblet;
    }

    @SubscribeEvent
    public static void onDrop(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        if (event.getSource().getEntity() instanceof Player && !entity.level().isClientSide()) {
            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            if ((month == 10 && day >= 28 || month == 11 && day <= 2) && entity.level().random.nextInt(10) == 0) {
                if (entity instanceof WraithEntity) {
                    event.getDrops().add(new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), new ItemStack(EEItems.CHERRY_CANDY.get())));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onCodexPostInitEvent(CodexEvents.PostInit event) {
        CodexChapters.RITUALS_INDEX.addPage(new TitledIndexPage(
                "eidolon_edoni.codex.page.rituals",
                new IndexPage.IndexEntry(
                        new CodexBuilder()
                                .title("eidolon_edoni.codex.chapter.butchery_ritual")
                                .titledRitualPage("eidolon_edoni.codex.page.butchery_ritual", ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID, "ritual/butchery"))
                                .textPage("eidolon_edoni.codex.page.butchery_ritual")
                                .build(),
                        new ItemStack(ModItems.IRON_KNIFE.get()))
                )
        );
        CodexChapters.SPELLS_INDEX.addPage(new TitledIndexPage(
                "eidolon_edoni.codex.page.spells",
                new IndexPage.IndexEntry(
                        new CodexBuilder()
                                .title("eidolon_edoni.codex.chapter.devour")
                                .chantPage("eidolon_edoni.codex.page.devour", EidolonEdoni.DEVOUR_SPELL)
                                .build(),
                        new ItemStack(ModItems.SMOKED_HAM.get()))
                )
        );
        CodexChapters.ARTIFICE_INDEX.addPage(new TitledIndexPage(
                "eidolon_edoni.codex.page.artifice",
                new IndexPage.IndexEntry(
                        new CodexBuilder()
                                .title("eidolon_edoni.codex.chapter.all_rounder")
                                .titlePage("eidolon_edoni.codex.page.all_rounder")
                                .worktablePage(EEItems.ALL_ROUNDER.get())
                                .titlePage("eidolon_edoni.codex.page.all_rounder.1")
                                .build(),
                        new ItemStack(EEItems.ALL_ROUNDER.get()))
                )
        );
        CodexChapters.CENSER.addPage(new TitlePage("eidolon_edoni.codex.page.censer.incense.0"));
        CodexChapters.CENSER.addPage(new CraftingPage(EEItems.CHORUS_INCENSE.get()));
        CodexChapters.CENSER.addPage(new CraftingPage(new ItemStack(EEItems.CHORUS_INCENSE.get(), 4), ResourceLocation.fromNamespaceAndPath(EidolonEdoni.MODID, "chorus_incense_from_calibrated_sculk_sensor")));
        CodexChapters.CENSER.addPage(new TextPage("eidolon_edoni.codex.page.censer.incense.1"));
        CodexChapters.CENSER.addPage(new CraftingPage(EEItems.GLUTTONY_INCENSE.get()));
        CodexChapters.CENSER.addPage(new CraftingPage(EEItems.RELAXING_INCENSE.get()));
        CodexChapters.CENSER.addPage(new TextPage("eidolon_edoni.codex.page.censer.incense.2"));
        CodexChapters.CENSER.addPage(new CruciblePage(EEItems.STIMULATING_INCENSE.get()));
        CodexChapters.PLANTS.addPage(new TitlePage("eidolon_edoni.codex.page.rich_soil_planter"));
        CodexChapters.PLANTS.addPage(new WorktablePage(EEItems.RICH_SOIL_PLANTER.get()));
    }
}