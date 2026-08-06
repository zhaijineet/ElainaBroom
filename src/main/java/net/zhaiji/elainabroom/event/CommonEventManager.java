package net.zhaiji.elainabroom.event;

import net.minecraftforge.eventbus.api.IEventBus;
import net.zhaiji.elainabroom.datagen.DataGenHandler;

public class CommonEventManager {
    public static void init(IEventBus modBus, IEventBus gameBus) {
        CommonEventManager.modBusListener(modBus);
        CommonEventManager.gameBusListener(gameBus);
    }

    public static void modBusListener(IEventBus modBus) {
        modBus.addListener(DataGenHandler::handlerGatherDataEvent);
    }

    public static void gameBusListener(IEventBus gameBus) {
    }
}
