package net.zhaiji.elainabroom.init;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;
import net.zhaiji.elainabroom.entity.ElainaBroomModel;
import net.zhaiji.elainabroom.entity.ElainaBroomRender;

@Mod.EventBusSubscriber(modid = ElainaBroom.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
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
