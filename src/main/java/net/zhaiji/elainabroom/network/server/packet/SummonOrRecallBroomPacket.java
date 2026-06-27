package net.zhaiji.elainabroom.network.server.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.network.server.ServerPacketHandler;

public record SummonOrRecallBroomPacket() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SummonOrRecallBroomPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ElainaBroom.MOD_ID, "summon_or_recall_broom_packet"));

    public static final StreamCodec<ByteBuf, SummonOrRecallBroomPacket> STREAM_CODEC = StreamCodec.unit(new SummonOrRecallBroomPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handler(SummonOrRecallBroomPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> ServerPacketHandler.handlerSummonOrRecallBroomPacket(context.player(), packet));
    }
}
