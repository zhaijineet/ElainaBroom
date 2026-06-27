package net.zhaiji.elainabroom.init;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.item.ElainaBroomItem;

public class InitItem {
    public static final DeferredRegister.Items ITEM = DeferredRegister.createItems(ElainaBroom.MOD_ID);

    public static final DeferredItem<Item> ELAINA_BROOM = ITEM.registerItem(
            "elaina_broom",
            ElainaBroomItem::new,
            properties -> properties.stacksTo(1)
    );
}
