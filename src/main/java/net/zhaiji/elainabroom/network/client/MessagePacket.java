package net.zhaiji.elainabroom.network.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.zhaiji.elainabroom.ElainaBroom;

public record MessagePacket(int level) implements CustomPacketPayload {
    public static final Type<MessagePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ElainaBroom.MOD_ID, "message_packet"));

    public static final StreamCodec<ByteBuf, MessagePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            MessagePacket::level,
            MessagePacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final MessagePacket packet, final IPayloadContext context) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            context.enqueueWork(() -> {
                context.player().displayClientMessage(Component.translatable("tips.elainabroom.need_level", packet.level), true);
            });
        }
    }
}
