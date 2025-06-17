package net.zhaiji.elainabroom.network.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.zhaiji.elainabroom.network.client.packet.MessagePacket;

public class ClientPacketHandle {
    public static void handleMessagePacket(MessagePacket packet) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            player.displayClientMessage(Component.translatable("tips.elainabroom.need_level", packet.level()), true);
        }
    }
}
