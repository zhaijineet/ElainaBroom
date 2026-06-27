package net.zhaiji.elainabroom.datagen;

import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DataGenHandler {

    /**
     * 注册数据生成器
     */
    public static void handlerGatherDataEvent(GatherDataEvent.Client event) {
        event.createProvider(ModelProvider::new);
        event.createProvider(RecipeProvider.Runner::new);
        event.createProvider(output -> new LanguageProvider(output, LanguageProvider.EN_US));
        event.createProvider(output -> new LanguageProvider(output, LanguageProvider.ZH_CN));
        event.createProvider(output -> new LanguageProvider(output, LanguageProvider.JA_JP));
    }
}
