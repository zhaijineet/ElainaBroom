package net.zhaiji.elainabroom.client.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.event.entity.EntityMountEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.zhaiji.elainabroom.client.keymapping.KeyMappings;
import net.zhaiji.elainabroom.client.model.ElainaBroomModel;
import net.zhaiji.elainabroom.client.render.ElainaBroomRender;
import net.zhaiji.elainabroom.client.sound.BroomFlightSound;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;
import net.zhaiji.elainabroom.network.server.DismountPacket;
import net.zhaiji.elainabroom.network.server.SummonOrRecallBroomPacket;

@OnlyIn(Dist.CLIENT)
public class EventHandle {
    public static void addListener(IEventBus modEventBus, IEventBus gameEventBus) {
        EventHandle.addModBusListener(modEventBus);
        EventHandle.addGameBusListener(gameEventBus);
    }

    public static void addModBusListener(IEventBus modEventBus) {
        modEventBus.addListener(EventHandle::RegisterKeyMappingsEvent);
        modEventBus.addListener(EventHandle::RegisterRenderers);
        modEventBus.addListener(EventHandle::RegisterLayerDefinitions);
    }

    public static void addGameBusListener(IEventBus gameEventBus) {
        gameEventBus.addListener(EventHandle::InputEvent);
        gameEventBus.addListener(EventHandle::EntityMountEvent);
    }

    public static void RegisterKeyMappingsEvent(RegisterKeyMappingsEvent event) {
        event.register(KeyMappings.BroomSummonKey);
        event.register(KeyMappings.BroomDismountKey);
        event.register(KeyMappings.BroomUpKey);
        event.register(KeyMappings.BroomDownKey);
    }

    public static void InputEvent(InputEvent.Key event) {
        if (KeyMappings.BroomSummonKey.getKey().getValue() == event.getKey() && KeyMappings.BroomSummonKey.consumeClick() && event.getAction() == InputConstants.PRESS) {
            PacketDistributor.sendToServer(new SummonOrRecallBroomPacket());
        }
        if (KeyMappings.BroomDismountKey.getKey().getValue() == event.getKey() && KeyMappings.BroomDismountKey.consumeClick() && event.getAction() == InputConstants.PRESS) {
            PacketDistributor.sendToServer(new DismountPacket());
        }
    }

    public static void RegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        EntityRenderers.register(ElainaBroomEntity.TYPE, ElainaBroomRender::new);
    }

    public static void RegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ElainaBroomModel.LAYER, ElainaBroomModel::createBodyLayer);
    }

    public static void EntityMountEvent(EntityMountEvent event) {
        if (event.getLevel().isClientSide() && event.getEntityBeingMounted() instanceof ElainaBroomEntity broom) {
            if (event.isMounting()) {
                Minecraft.getInstance().getSoundManager().play(new BroomFlightSound(broom));
            }
        }
    }
}
