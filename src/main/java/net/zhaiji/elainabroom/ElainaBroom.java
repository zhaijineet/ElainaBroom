package net.zhaiji.elainabroom;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.zhaiji.elainabroom.init.InitCreativeModeTab;
import net.zhaiji.elainabroom.init.InitEntityType;
import net.zhaiji.elainabroom.init.InitItem;
import net.zhaiji.elainabroom.network.ElainaBroomPacket;

@Mod(ElainaBroom.MOD_ID)
public class ElainaBroom {
    public static final String MOD_ID = "elainabroom";

    public ElainaBroom() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ElainaBroomConfig.SPEC);

        InitItem.ITEM.register(modEventBus);
        InitCreativeModeTab.CREATIVE_MODE_TAB.register(modEventBus);
        InitEntityType.ENTITY_TYPE.register(modEventBus);

        ElainaBroomPacket.register();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            ElainaBroomClient.init(modEventBus, forgeEventBus);
        });
    }
}
