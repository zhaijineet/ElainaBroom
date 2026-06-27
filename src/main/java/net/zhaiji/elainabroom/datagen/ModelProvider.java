package net.zhaiji.elainabroom.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.init.InitItem;

public class ModelProvider extends net.minecraft.client.data.models.ModelProvider {

    public ModelProvider(PackOutput output) {
        super(output, ElainaBroom.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(InitItem.ELAINA_BROOM.get(), ModelTemplates.FLAT_ITEM);
    }
}
