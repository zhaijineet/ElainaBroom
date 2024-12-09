package net.zhaiji.elainabroom.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zhaiji.elainabroom.ElainaBroom;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ElainaBroom.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Item Model
        generator.addProvider(event.includeClient(), new ItemModelProvider(packOutput, existingFileHelper));
        // Recipe
        generator.addProvider(event.includeServer(), new RecipeProvider(packOutput));
    }
}
