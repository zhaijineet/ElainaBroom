package net.zhaiji.elainabroom.compat;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.ElainaBroomConfig;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ClothConfigCompat {
    public static void registerConfigScreen() {
        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, parent) -> createConfigScreen(parent)));
    }

    private static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable(ElainaBroom.MOD_ID + ".configuration.title"))
                .setSavingRunnable(ClothConfigCompat::saveAllLoadedConfigs);
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        Minecraft minecraft = Minecraft.getInstance();

        for (ModConfig modConfig : getModConfigs()) {
            ConfigCategory category = builder.getOrCreateCategory(getCategoryTitle(modConfig));
            Component lockReason = getLockReason(modConfig, minecraft);
            if (lockReason != null) {
                // 锁定分支绝不读/写 spec 值（未加载时 get() 抛异常、set() 与 save() 会 NPE），只展示原因
                category.addEntry(entryBuilder.startTextDescription(lockReason).build());
                continue;
            }
            // 本 mod 的配置项全部定义在 SERVER spec 中，仅该类型配置需要生成条目
            if (modConfig.getType() == ModConfig.Type.SERVER) {
                buildEntries(category, entryBuilder);
            }
        }
        return builder.build();
    }

    private static List<ModConfig> getModConfigs() {
        return ConfigTracker.INSTANCE.configSets().values().stream()
                .flatMap(Set::stream)
                .filter(config -> config.getModId().equals(ElainaBroom.MOD_ID))
                .sorted(Comparator.comparingInt(config -> config.getType().ordinal()))
                .toList();
    }

    private static Component getCategoryTitle(ModConfig modConfig) {
        String typeName = modConfig.getType().name().toLowerCase(Locale.ROOT);
        String translationKey = ElainaBroom.MOD_ID + ".configuration.section." + typeName;
        return I18n.exists(translationKey) ? Component.translatable(translationKey) : Component.literal(typeName);
    }

    private static Component getLockReason(ModConfig modConfig, Minecraft minecraft) {
        if (!((ForgeConfigSpec) modConfig.getSpec()).isLoaded()) {
            return Component.translatable(ElainaBroom.MOD_ID + ".configuration.server_config_not_loaded");
        }
        if (modConfig.getType() == ModConfig.Type.SERVER) {
            if (minecraft.getCurrentServer() != null && !minecraft.isSingleplayer()) {
                return Component.translatable(ElainaBroom.MOD_ID + ".configuration.server_config_while_online");
            }
            if (minecraft.hasSingleplayerServer() && minecraft.getSingleplayerServer().isPublished()) {
                return Component.translatable(ElainaBroom.MOD_ID + ".configuration.server_config_while_lan");
            }
        }
        return null;
    }

    // 关闭界面时把所有已加载配置写盘，未加载时 save() 会 NPE
    private static void saveAllLoadedConfigs() {
        for (ModConfig modConfig : getModConfigs()) {
            ForgeConfigSpec spec = (ForgeConfigSpec) modConfig.getSpec();
            if (spec.isLoaded()) {
                spec.save();
            }
        }
    }

    private static void buildEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
        addGeneralEntries(entryBuilder, category);
        addFlightEntries(entryBuilder, category);
    }

    private static void addGeneralEntries(ConfigEntryBuilder entryBuilder, ConfigCategory category) {
        category.addEntry(entryBuilder.startIntField(
                        Component.translatable(ElainaBroom.MOD_ID + ".configuration.recallDistance"),
                        ElainaBroomConfig.RECALL_DISTANCE.get())
                .setDefaultValue(ElainaBroomConfig.RECALL_DISTANCE.getDefault())
                .setMin(5)
                .setMax(20)
                .setTooltip(Component.translatable(ElainaBroom.MOD_ID + ".configuration.recallDistance.tooltip"))
                .setSaveConsumer(ElainaBroomConfig.RECALL_DISTANCE::set)
                .build());

        category.addEntry(entryBuilder.startIntField(
                        Component.translatable(ElainaBroom.MOD_ID + ".configuration.needLevel"),
                        ElainaBroomConfig.NEED_LEVEL.get())
                .setDefaultValue(ElainaBroomConfig.NEED_LEVEL.getDefault())
                .setMin(0)
                .setMax(Integer.MAX_VALUE)
                .setTooltip(Component.translatable(ElainaBroom.MOD_ID + ".configuration.needLevel.tooltip"))
                .setSaveConsumer(ElainaBroomConfig.NEED_LEVEL::set)
                .build());

        category.addEntry(entryBuilder.startIntField(
                        Component.translatable(ElainaBroom.MOD_ID + ".configuration.maxLevel"),
                        ElainaBroomConfig.MAX_LEVEL.get())
                .setDefaultValue(ElainaBroomConfig.MAX_LEVEL.getDefault())
                .setMin(0)
                .setMax(Integer.MAX_VALUE)
                .setTooltip(Component.translatable(ElainaBroom.MOD_ID + ".configuration.maxLevel.tooltip"))
                .setSaveConsumer(ElainaBroomConfig.MAX_LEVEL::set)
                .build());

        category.addEntry(entryBuilder.startBooleanToggle(
                        Component.translatable(ElainaBroom.MOD_ID + ".configuration.unlimitedSpeed"),
                        ElainaBroomConfig.UNLIMITED_SPEED.get())
                .setDefaultValue(ElainaBroomConfig.UNLIMITED_SPEED.getDefault())
                .setTooltip(Component.translatable(ElainaBroom.MOD_ID + ".configuration.unlimitedSpeed.tooltip"))
                .setSaveConsumer(ElainaBroomConfig.UNLIMITED_SPEED::set)
                .build());
    }

    private static void addFlightEntries(ConfigEntryBuilder entryBuilder, ConfigCategory category) {
        category.addEntry(entryBuilder.startDoubleField(
                        Component.translatable(ElainaBroom.MOD_ID + ".configuration.speed"),
                        ElainaBroomConfig.SPEED.get())
                .setDefaultValue(ElainaBroomConfig.SPEED.getDefault())
                .setMin(0.0)
                .setMax(10.0)
                .setTooltip(Component.translatable(ElainaBroom.MOD_ID + ".configuration.speed.tooltip"))
                .setSaveConsumer(ElainaBroomConfig.SPEED::set)
                .build());

        category.addEntry(entryBuilder.startDoubleField(
                        Component.translatable(ElainaBroom.MOD_ID + ".configuration.friction"),
                        ElainaBroomConfig.FRICTION.get())
                .setDefaultValue(ElainaBroomConfig.FRICTION.getDefault())
                .setMin(0.01)
                .setMax(0.99)
                .setTooltip(Component.translatable(ElainaBroom.MOD_ID + ".configuration.friction.tooltip"))
                .setSaveConsumer(ElainaBroomConfig.FRICTION::set)
                .build());

        category.addEntry(entryBuilder.startDoubleField(
                        Component.translatable(ElainaBroom.MOD_ID + ".configuration.forwardSpeed"),
                        ElainaBroomConfig.FORWARD_SPEED.get())
                .setDefaultValue(ElainaBroomConfig.FORWARD_SPEED.getDefault())
                .setMin(0.1)
                .setMax(5.0)
                .setTooltip(Component.translatable(ElainaBroom.MOD_ID + ".configuration.forwardSpeed.tooltip"))
                .setSaveConsumer(ElainaBroomConfig.FORWARD_SPEED::set)
                .build());

        category.addEntry(entryBuilder.startDoubleField(
                        Component.translatable(ElainaBroom.MOD_ID + ".configuration.backSpeed"),
                        ElainaBroomConfig.BACK_SPEED.get())
                .setDefaultValue(ElainaBroomConfig.BACK_SPEED.getDefault())
                .setMin(0.1)
                .setMax(5.0)
                .setTooltip(Component.translatable(ElainaBroom.MOD_ID + ".configuration.backSpeed.tooltip"))
                .setSaveConsumer(ElainaBroomConfig.BACK_SPEED::set)
                .build());

        category.addEntry(entryBuilder.startDoubleField(
                        Component.translatable(ElainaBroom.MOD_ID + ".configuration.lateralSpeed"),
                        ElainaBroomConfig.LATERAL_SPEED.get())
                .setDefaultValue(ElainaBroomConfig.LATERAL_SPEED.getDefault())
                .setMin(0.1)
                .setMax(5.0)
                .setTooltip(Component.translatable(ElainaBroom.MOD_ID + ".configuration.lateralSpeed.tooltip"))
                .setSaveConsumer(ElainaBroomConfig.LATERAL_SPEED::set)
                .build());

        category.addEntry(entryBuilder.startDoubleField(
                        Component.translatable(ElainaBroom.MOD_ID + ".configuration.verticalSpeed"),
                        ElainaBroomConfig.VERTICAL_SPEED.get())
                .setDefaultValue(ElainaBroomConfig.VERTICAL_SPEED.getDefault())
                .setMin(0.1)
                .setMax(5.0)
                .setTooltip(Component.translatable(ElainaBroom.MOD_ID + ".configuration.verticalSpeed.tooltip"))
                .setSaveConsumer(ElainaBroomConfig.VERTICAL_SPEED::set)
                .build());
    }
}
