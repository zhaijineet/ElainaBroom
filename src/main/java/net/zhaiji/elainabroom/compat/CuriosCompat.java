package net.zhaiji.elainabroom.compat;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.items.IItemHandler;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import javax.annotation.Nullable;

/**
 * 与 Curios 饰品栏交互的封装，直接引用 Curios API 获取饰品栏能力
 */
public final class CuriosCompat {
    /**
     * 获取实体身上的饰品栏物品处理器，封装了所有饰品槽的读写
     */
    @Nullable
    public static IItemHandler getCuriosItemHandler(LivingEntity livingEntity) {
        ICuriosItemHandler handler = CuriosApi.getCuriosInventory(livingEntity).orElse(null);
        return handler != null ? handler.getEquippedCurios() : null;
    }
}
