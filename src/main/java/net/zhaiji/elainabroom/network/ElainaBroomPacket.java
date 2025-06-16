package net.zhaiji.elainabroom.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.zhaiji.elainabroom.network.client.MessagePacket;
import net.zhaiji.elainabroom.network.server.DismountPacket;
import net.zhaiji.elainabroom.network.server.SummonOrRecallBroomPacket;

public class ElainaBroomPacket {
    public static final String VERSION = "1.0";

    public static void register(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar registrar = event.registrar(VERSION);
        registrar.playToServer(
                SummonOrRecallBroomPacket.TYPE,
                SummonOrRecallBroomPacket.STREAM_CODEC,
                SummonOrRecallBroomPacket::handle
        );

        registrar.playToServer(
                DismountPacket.TYPE,
                DismountPacket.STREAM_CODEC,
                DismountPacket::handle
        );

        registrar.playToClient(
                MessagePacket.TYPE,
                MessagePacket.STREAM_CODEC,
                MessagePacket::handle
        );
    }
}
