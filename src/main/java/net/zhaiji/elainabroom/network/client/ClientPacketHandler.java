package net.zhaiji.elainabroom.network.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.zhaiji.elainabroom.network.client.packet.MessagePacket;

public class ClientPacketHandler {
    public static void handlerMessagePacket(MessagePacket packet) {
        Minecraft.getInstance().player.sendOverlayMessage(Component.translatable("tips.elainabroom.need_level", packet.level()));
    }
}
