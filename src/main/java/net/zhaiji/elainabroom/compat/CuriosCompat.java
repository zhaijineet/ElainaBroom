package net.zhaiji.elainabroom.compat;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import top.theillusivec4.curios.api.CuriosCapability;

import javax.annotation.Nullable;

/**
 * 与 Curios 饰品栏交互的封装，直接引用 Curios API 获取饰品栏能力
 */
public final class CuriosCompat {
    /**
     * 获取实体身上的饰品栏物品处理器，封装了所有饰品槽的读写
     */
    @Nullable
    public static ResourceHandler<ItemResource> getCuriosItemHandler(LivingEntity livingEntity) {
        return livingEntity.getCapability(CuriosCapability.ITEM_HANDLER);
    }
}
