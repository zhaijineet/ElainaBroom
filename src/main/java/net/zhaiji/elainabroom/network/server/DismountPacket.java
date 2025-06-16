package net.zhaiji.elainabroom.network.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;

import java.util.function.Supplier;

public class DismountPacket {
    public static DismountPacket decode(FriendlyByteBuf buf) {
        return new DismountPacket();
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null && player.getVehicle() instanceof ElainaBroomEntity) {
                player.stopRiding();
            }
        });
        context.setPacketHandled(true);
    }
}
