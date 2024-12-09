package net.zhaiji.elainabroom.sound;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;

@Mod.EventBusSubscriber(modid = ElainaBroom.MOD_ID, value = Dist.CLIENT)
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
