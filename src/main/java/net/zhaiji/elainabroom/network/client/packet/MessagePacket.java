package net.zhaiji.elainabroom.network.client.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.network.client.ClientPacketHandler;

public record MessagePacket(int level) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessagePacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ElainaBroom.MOD_ID, "message_packet"));

    public static final StreamCodec<ByteBuf, MessagePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            MessagePacket::level,
            MessagePacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handler(MessagePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> ClientPacketHandler.handlerMessagePacket(packet));
    }
}
