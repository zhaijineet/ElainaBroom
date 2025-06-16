package net.zhaiji.elainabroom;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.zhaiji.elainabroom.client.event.EventHandle;

@Mod(value = ElainaBroom.MOD_ID, dist = Dist.CLIENT)
public class ElainaBroomClient {
    public ElainaBroomClient(IEventBus modEventBus, ModContainer modContainer) {
        IEventBus gameEventBus = NeoForge.EVENT_BUS;
        EventHandle.addListener(modEventBus, gameEventBus);
    }
}
