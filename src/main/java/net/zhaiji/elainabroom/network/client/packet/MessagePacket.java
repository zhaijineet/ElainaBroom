package net.zhaiji.elainabroom.network.client.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.zhaiji.elainabroom.network.client.ClientPacketHandler;

import java.util.function.Supplier;

public record MessagePacket(int level) {
    public static MessagePacket decode(FriendlyByteBuf buf) {
        return new MessagePacket(buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.level);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handlerMessagePacket(this)));
        context.setPacketHandled(true);
    }
}
