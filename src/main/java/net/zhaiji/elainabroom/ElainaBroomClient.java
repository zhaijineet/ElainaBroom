package net.zhaiji.elainabroom;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.zhaiji.elainabroom.client.event.ClientEventManager;
import net.zhaiji.elainabroom.compat.ClothConfigCompat;
import net.zhaiji.elainabroom.compat.CompatManager;

@OnlyIn(Dist.CLIENT)
public class ElainaBroomClient {
    public static void init(IEventBus modEventBus, IEventBus forgeEventBus) {
        ClientEventManager.init(modEventBus, forgeEventBus);
        if (CompatManager.CLOTH_CONFIG_LOADED) {
            ClothConfigCompat.registerConfigScreen();
        }
    }
}
