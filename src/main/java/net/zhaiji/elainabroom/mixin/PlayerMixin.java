package net.zhaiji.elainabroom.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    public PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
        method = "updatePlayerPose",
        at = @At("HEAD"),
        cancellable = true
    )
    public void elainaBroom$updatePlayerPose(CallbackInfo ci) {
        if (this.getVehicle() instanceof ElainaBroomEntity) {
            this.setPose(Pose.STANDING);
            ci.cancel();
        }
    }

    @Inject(
        method = "wantsToStopRiding",
        at = @At("HEAD"),
        cancellable = true
    )
    public void elainaBroom$wantsToStopRiding(CallbackInfoReturnable<Boolean> cir) {
        if (this.getVehicle() instanceof ElainaBroomEntity) {
            cir.setReturnValue(false);
        }
    }
}
