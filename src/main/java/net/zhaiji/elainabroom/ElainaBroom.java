package net.zhaiji.elainabroom;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.zhaiji.elainabroom.init.InitCreativeModeTab;
import net.zhaiji.elainabroom.init.InitEntity;
import net.zhaiji.elainabroom.init.InitItem;

@Mod(ElainaBroom.MOD_ID)
public class ElainaBroom {
    public static final String MOD_ID = "elainabroom";

    public ElainaBroom(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, ElainaBroomConfig.SPEC);
        InitItem.ITEMS.register(modEventBus);
        InitCreativeModeTab.CREATIVE_MODE_TAB.register(modEventBus);
        InitEntity.ENTITY_TYPES.register(modEventBus);
    }
}
