package net.zhaiji.elainabroom.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.item.ElainaBroomItem;

public class InitItem {
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, ElainaBroom.MOD_ID);

    public static final RegistryObject<Item> ELAINA_BROOM = ITEM.register(
            "elaina_broom",
            () -> new ElainaBroomItem(new Item.Properties().stacksTo(1))
    );
}
