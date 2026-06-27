package net.zhaiji.elainabroom.client.event;

import net.neoforged.bus.api.IEventBus;

public class ClientEventManager {
    public static void init(IEventBus modBus, IEventBus gameBus) {
        ClientEventManager.modBusListener(modBus);
        ClientEventManager.gameBusListener(gameBus);
    }

    public static void modBusListener(IEventBus modBus) {
        modBus.addListener(ClientEventHandler::handlerRegisterKeyMappingsEvent);
        modBus.addListener(ClientEventHandler::handlerEntityRenderersEvent$RegisterRenderers);
        modBus.addListener(ClientEventHandler::handlerEntityRenderersEvent$RegisterLayerDefinitions);
    }

    public static void gameBusListener(IEventBus gameBus) {
        gameBus.addListener(ClientEventHandler::handlerInputEvent$MouseButton$Pre);
        gameBus.addListener(ClientEventHandler::handlerInputEvent$Key);
        gameBus.addListener(ClientEventHandler::handlerEntityMountEvent);
        gameBus.addListener(ClientEventHandler::handlerClientTickEvent$Pre);
        gameBus.addListener(ClientEventHandler::handlerItemTooltipEvent);
    }
}
