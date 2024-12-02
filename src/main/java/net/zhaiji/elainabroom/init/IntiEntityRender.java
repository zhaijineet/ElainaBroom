package net.zhaiji.elainabroom.init;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;
import net.zhaiji.elainabroom.entity.ElainaBroomModel;
import net.zhaiji.elainabroom.entity.ElainaBroomRender;

@EventBusSubscriber(modid = ElainaBroom.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class IntiEntityRender {
    @SubscribeEvent
    public static void onEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        EntityRenderers.register(ElainaBroomEntity.TYPE, ElainaBroomRender::new);
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ElainaBroomModel.LAYER, ElainaBroomModel::createBodyLayer);
    }
}
