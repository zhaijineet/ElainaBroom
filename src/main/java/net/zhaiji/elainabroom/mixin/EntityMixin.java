package net.zhaiji.elainabroom.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * 拦截原版碰撞检测流程，为骑乘状态的扫帚放大碰撞箱，防止玩家卡入墙体
 */
@Mixin(Entity.class)
public class EntityMixin {
    /**
     * 在 collide() 内部 getBoundingBox() 赋值后修改 aabb 局部变量，
     * 若扫帚载有乘客则将碰撞箱向上扩展 1.25 格，使碰撞检测范围覆盖玩家身高
     */
    @ModifyVariable(
            method = "collide(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;",
            at = @At(value = "STORE"),
            ordinal = 0
    )
    public AABB elainaBroom$collide(AABB aabb) {
        if (((Object) this) instanceof ElainaBroomEntity broom && broom.isVehicle()) {
            return aabb.expandTowards(0, 1.25, 0);
        }
        return aabb;
    }
}
