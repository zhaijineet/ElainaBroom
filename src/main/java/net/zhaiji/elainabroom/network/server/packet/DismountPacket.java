package net.zhaiji.elainabroom.network.server.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.network.server.ServerPacketHandler;

public record DismountPacket() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<DismountPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ElainaBroom.MOD_ID, "dismount_packet"));

    public static final StreamCodec<ByteBuf, DismountPacket> STREAM_CODEC = StreamCodec.unit(new DismountPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handler(DismountPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> ServerPacketHandler.handlerDismountPacket(context.player(), packet));
    }
}
