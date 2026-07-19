package net.zhaiji.elainabroom.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.zhaiji.elainabroom.client.keymapping.KeyMappings;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Shadow
    private ClientLevel level;

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(
        method = "handleSetEntityPassengersPacket",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/Gui;setOverlayMessage(Lnet/minecraft/network/chat/Component;Z)V"
        ),
        cancellable = true
    )
    public void elainaBroom$handleSetEntityPassengersPacket(ClientboundSetPassengersPacket packet, CallbackInfo ci) {
        if (this.level.getEntity(packet.getVehicle()) instanceof ElainaBroomEntity) {
            Component component;
            if (!KeyMappings.BROOM_DISMOUNT.isUnbound()) {
                component = Component.translatable("mount.onboard", KeyMappings.BROOM_DISMOUNT.getTranslatedKeyMessage());
            } else if (!KeyMappings.BROOM_SUMMON.isUnbound()) {
                component = Component.translatable("tips.elainabroom.dismount_by_recall");
            } else {
                component = Component.translatable("tips.elainabroom.cannot_dismount");
            }
            this.minecraft.gui.setOverlayMessage(component, false);
            this.minecraft.getNarrator().sayNow(component);
            ci.cancel();
        }
    }
}
