package net.zhaiji.elainabroom.network.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.zhaiji.elainabroom.network.client.packet.MessagePacket;

public class ClientPacketHandler {
    public static void handlerMessagePacket(MessagePacket packet) {
        Minecraft.getInstance().player.displayClientMessage(Component.translatable("tips.elainabroom.need_level", packet.level()), true);
    }
}
