package net.zhaiji.elainabroom.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.zhaiji.elainabroom.network.client.packet.MessagePacket;
import net.zhaiji.elainabroom.network.server.packet.DismountPacket;
import net.zhaiji.elainabroom.network.server.packet.SummonOrRecallBroomPacket;

public class PacketManager {
    public static final String VERSION = "1.0";

    /**
     * 注册所有网络包
     */
    public static void handlerRegisterPayloadHandlersEvent(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(VERSION);
        registrar.playToServer(
                SummonOrRecallBroomPacket.TYPE,
                SummonOrRecallBroomPacket.STREAM_CODEC,
                SummonOrRecallBroomPacket::handler
        );

        registrar.playToServer(
                DismountPacket.TYPE,
                DismountPacket.STREAM_CODEC,
                DismountPacket::handler
        );

        registrar.playToClient(
                MessagePacket.TYPE,
                MessagePacket.STREAM_CODEC,
                MessagePacket::handler
        );
    }
}
