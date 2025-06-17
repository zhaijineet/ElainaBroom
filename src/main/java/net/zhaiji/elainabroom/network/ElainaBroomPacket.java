package net.zhaiji.elainabroom.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.network.client.packet.MessagePacket;
import net.zhaiji.elainabroom.network.server.DismountPacket;
import net.zhaiji.elainabroom.network.server.SummonOrRecallBroomPacket;

public class ElainaBroomPacket {
    public static final String VERSION = "1.0";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ElainaBroom.MOD_ID, "main"),
            () -> VERSION,
            VERSION::equals,
            VERSION::equals
    );

    public static void register() {
        int id = 0;
        INSTANCE.messageBuilder(SummonOrRecallBroomPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(SummonOrRecallBroomPacket::decode)
                .encoder(SummonOrRecallBroomPacket::encode)
                .consumerMainThread(SummonOrRecallBroomPacket::handle)
                .add();

        INSTANCE.messageBuilder(DismountPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(DismountPacket::decode)
                .encoder(DismountPacket::encode)
                .consumerMainThread(DismountPacket::handle)
                .add();

        INSTANCE.messageBuilder(MessagePacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MessagePacket::decode)
                .encoder(MessagePacket::encode)
                .consumerMainThread(MessagePacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToClient(ServerPlayer serverPlayer, MSG message) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), message);
    }
}
