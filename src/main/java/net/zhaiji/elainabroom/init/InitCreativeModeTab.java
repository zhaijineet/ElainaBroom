package net.zhaiji.elainabroom.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.zhaiji.elainabroom.ElainaBroom;

import java.util.function.Supplier;

public class InitCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ElainaBroom.MOD_ID);

    public static final Supplier<CreativeModeTab> ELAINABROOM_TAB = CREATIVE_MODE_TAB.register(
            "elainabroom_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(InitItem.ELAINA_BROOM.get()))
                    .title(Component.translatable("creativetab.elainabroom.elainabroom_tab"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        InitItem.ITEMS.getEntries().forEach(itemDeferredHolder -> {
                            output.accept(itemDeferredHolder.get());
                        });
                    }))
                    .build()
    );
}
