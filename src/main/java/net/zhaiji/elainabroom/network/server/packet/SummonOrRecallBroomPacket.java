package net.zhaiji.elainabroom.network.server.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.zhaiji.elainabroom.network.server.ServerPacketHandler;

import java.util.function.Supplier;

public class SummonOrRecallBroomPacket {
    public static SummonOrRecallBroomPacket decode(FriendlyByteBuf buf) {
        return new SummonOrRecallBroomPacket();
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                ServerPacketHandler.handlerSummonOrRecallBroomPacket(player, this);
            }
        });
        context.setPacketHandled(true);
    }
}
