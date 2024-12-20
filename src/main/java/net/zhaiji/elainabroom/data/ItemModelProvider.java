package net.zhaiji.elainabroom.data;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.init.InitItem;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {

    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ElainaBroom.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(InitItem.ELAINA_BROOM.get());
    }
}
