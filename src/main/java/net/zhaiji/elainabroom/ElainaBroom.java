package net.zhaiji.elainabroom;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.zhaiji.elainabroom.event.CommonEventManager;
import net.zhaiji.elainabroom.init.InitCreativeModeTab;
import net.zhaiji.elainabroom.init.InitEntityType;
import net.zhaiji.elainabroom.init.InitItem;
import net.zhaiji.elainabroom.network.PacketManager;

@Mod(ElainaBroom.MOD_ID)
public class ElainaBroom {
    public static final String MOD_ID = "elainabroom";

    public ElainaBroom() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ElainaBroomConfig.SPEC);

        InitItem.ITEM.register(modEventBus);
        InitCreativeModeTab.CREATIVE_MODE_TAB.register(modEventBus);
        InitEntityType.ENTITY_TYPES.register(modEventBus);

        PacketManager.register();

        CommonEventManager.init(modEventBus, forgeEventBus);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ElainaBroomClient.init(modEventBus, forgeEventBus));
    }

    /**
     * 以模组命名空间和指定路径构建资源标识符
     */
    public static ResourceLocation of(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
