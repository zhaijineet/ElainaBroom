package net.zhaiji.elainabroom.sound;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityMountEvent;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;

@EventBusSubscriber(modid = ElainaBroom.MOD_ID, value = Dist.CLIENT)
public class SoundHandle {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void EntityMountEvent(EntityMountEvent event) {
        if (event.getEntityBeingMounted() instanceof ElainaBroomEntity broom && event.getLevel().isClientSide()) {
            if (event.isMounting()) {
                Minecraft.getInstance().getSoundManager().play(new BroomSound(broom));
            }
        }
    }
}
