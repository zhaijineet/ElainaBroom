package net.zhaiji.elainabroom.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

public class DataGenHandler {
    /**
     * 注册数据生成器
     */
    public static void handlerGatherDataEvent(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        generator.addProvider(event.includeClient(), new ItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new LanguageProvider(packOutput, LanguageProvider.EN_US));
        generator.addProvider(event.includeClient(), new LanguageProvider(packOutput, LanguageProvider.ZH_CN));
        generator.addProvider(event.includeClient(), new LanguageProvider(packOutput, LanguageProvider.JA_JP));
        generator.addProvider(event.includeServer(), new RecipeProvider(packOutput));
    }
}
