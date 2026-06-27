package net.zhaiji.elainabroom;

import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.zhaiji.elainabroom.event.CommonEventManager;
import net.zhaiji.elainabroom.init.InitCreativeModeTab;
import net.zhaiji.elainabroom.init.InitEntityType;
import net.zhaiji.elainabroom.init.InitItem;

@Mod(ElainaBroom.MOD_ID)
public class ElainaBroom {
    public static final String MOD_ID = "elainabroom";

    public ElainaBroom(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, ElainaBroomConfig.SPEC);

        InitItem.ITEM.register(modEventBus);
        InitCreativeModeTab.CREATIVE_MODE_TAB.register(modEventBus);
        InitEntityType.ENTITY_TYPES.register(modEventBus);

        CommonEventManager.init(modEventBus, NeoForge.EVENT_BUS);
    }

    /**
     * 以模组命名空间和指定路径构建资源标识符
     */
    public static Identifier of(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }
}
