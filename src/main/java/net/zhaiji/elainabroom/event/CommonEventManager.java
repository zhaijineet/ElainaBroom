package net.zhaiji.elainabroom.event;

import net.neoforged.bus.api.IEventBus;
import net.zhaiji.elainabroom.datagen.DataGenHandler;
import net.zhaiji.elainabroom.network.PacketManager;

public class CommonEventManager {
    public static void init(IEventBus modBus, IEventBus gameBus) {
        CommonEventManager.modBusListener(modBus);
        CommonEventManager.gameBusListener(gameBus);
    }

    public static void modBusListener(IEventBus modBus) {
        modBus.addListener(PacketManager::handlerRegisterPayloadHandlersEvent);
        modBus.addListener(DataGenHandler::handlerGatherDataEvent);
    }

    public static void gameBusListener(IEventBus gameBus) {
    }
}
