package net.zhaiji.elainabroom.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.zhaiji.elainabroom.ElainaBroom;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ElainaBroom.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
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
        generator.addProvider(event.includeServer(), new RecipeProvider(packOutput, lookupProvider));
    }
}
