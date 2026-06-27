package net.zhaiji.elainabroom.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * 拦截原版碰撞检测流程，为骑乘状态的扫帚放大碰撞箱，防止玩家卡入墙体
 */
@Mixin(Entity.class)
public class EntityMixin {
    /**
     * 在 collide() 内部获取碰撞箱时，若扫帚载有乘客则将碰撞箱向上扩展1.25格，
     * 使碰撞检测范围覆盖玩家身高，原版碰撞系统会自然阻止扫帚把玩家推入墙体
     */
    @WrapOperation(
            method = "collide",
            at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/world/entity/Entity;getBoundingBox()Lnet/minecraft/world/phys/AABB;"
            )
    )
    public AABB elainaBroom$collide(Entity entity, Operation<AABB> original) {
        AABB boundingBox = original.call(entity);
        if (entity instanceof ElainaBroomEntity broom && broom.isVehicle()) {
            return boundingBox.expandTowards(0, 1.25, 0);
        }
        return boundingBox;
    }
}
