package net.zhaiji.elainabroom;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.zhaiji.elainabroom.init.InitCreativeModeTab;
import net.zhaiji.elainabroom.init.InitEntity;
import net.zhaiji.elainabroom.init.InitItem;

@Mod(ElainaBroom.MOD_ID)
public class ElainaBroom {
    public static final String MOD_ID = "elainabroom";

    public ElainaBroom() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ElainaBroomConfig.SPEC);
        InitItem.ITEMS.register(modEventBus);
        InitCreativeModeTab.CREATIVE_MODE_TAB.register(modEventBus);
        InitEntity.ENTITY_TYPES.register(modEventBus);
    }
}
