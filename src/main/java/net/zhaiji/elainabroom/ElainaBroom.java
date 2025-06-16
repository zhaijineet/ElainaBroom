package net.zhaiji.elainabroom;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.zhaiji.elainabroom.init.InitCreativeModeTab;
import net.zhaiji.elainabroom.init.InitEntityType;
import net.zhaiji.elainabroom.init.InitItem;
import net.zhaiji.elainabroom.network.ElainaBroomPacket;

@Mod(ElainaBroom.MOD_ID)
public class ElainaBroom {
    public static final String MOD_ID = "elainabroom";

    public ElainaBroom(IEventBus modEventBus, ModContainer modContainer) {
        InitItem.ITEM.register(modEventBus);
        InitCreativeModeTab.CREATIVE_MODE_TAB.register(modEventBus);
        InitEntityType.ENTITY_TYPES.register(modEventBus);

        modEventBus.addListener(ElainaBroomPacket::register);

        modContainer.registerConfig(ModConfig.Type.COMMON, ElainaBroomConfig.SPEC);
    }
}
