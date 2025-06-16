package net.zhaiji.elainabroom.client.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.zhaiji.elainabroom.client.keymapping.KeyMappings;
import net.zhaiji.elainabroom.client.model.ElainaBroomModel;
import net.zhaiji.elainabroom.client.render.ElainaBroomRender;
import net.zhaiji.elainabroom.client.sound.BroomFlightSound;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;
import net.zhaiji.elainabroom.network.ElainaBroomPacket;
import net.zhaiji.elainabroom.network.server.DismountPacket;
import net.zhaiji.elainabroom.network.server.SummonOrRecallBroomPacket;

@OnlyIn(Dist.CLIENT)
public class EventHandle {
    public static void addListener(IEventBus modEventBus, IEventBus forgeEventBus) {
        EventHandle.addModBusListener(modEventBus);
        EventHandle.addForgeBusListener(forgeEventBus);
    }

    public static void addModBusListener(IEventBus modEventBus) {
        modEventBus.addListener(EventHandle::RegisterKeyMappingsEvent);
        modEventBus.addListener(EventHandle::RegisterRenderers);
        modEventBus.addListener(EventHandle::RegisterLayerDefinitions);
    }

    public static void addForgeBusListener(IEventBus forgeEventBus) {
        forgeEventBus.addListener(EventHandle::InputEvent);
        forgeEventBus.addListener(EventHandle::EntityMountEvent);
    }

    public static void RegisterKeyMappingsEvent(RegisterKeyMappingsEvent event) {
        event.register(KeyMappings.BroomSummonKey);
        event.register(KeyMappings.BroomDismountKey);
        event.register(KeyMappings.BroomUpKey);
        event.register(KeyMappings.BroomDownKey);
    }

    public static void InputEvent(InputEvent.Key event) {
        if (KeyMappings.BroomSummonKey.getKey().getValue() == event.getKey() && KeyMappings.BroomSummonKey.consumeClick() && event.getAction() == InputConstants.PRESS) {
            ElainaBroomPacket.sendToServer(new SummonOrRecallBroomPacket());
        }
        if (KeyMappings.BroomDismountKey.getKey().getValue() == event.getKey() && KeyMappings.BroomDismountKey.consumeClick() && event.getAction() == InputConstants.PRESS) {
            ElainaBroomPacket.sendToServer(new DismountPacket());
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
