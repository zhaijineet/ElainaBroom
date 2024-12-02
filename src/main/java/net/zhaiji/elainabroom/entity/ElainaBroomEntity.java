package net.zhaiji.elainabroom.entity;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.zhaiji.elainabroom.ElainaBroomConfig;
import net.zhaiji.elainabroom.init.InitItem;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class ElainaBroomEntity extends Entity {
    public static final EntityType<ElainaBroomEntity> TYPE = EntityType.Builder.<ElainaBroomEntity>of(ElainaBroomEntity::new, MobCategory.MISC)
            .fireImmune()
            .clientTrackingRange(10)
            .sized(1.3f, 0.5f)
            .build("elaina_broom");

    public static final Logger LOGGER = LogUtils.getLogger();

    private boolean keyForward = false;
    private boolean keyBack = false;
    private boolean keyLeft = false;
    private boolean keyRight = false;
    private boolean keyUp = false;
    private boolean keyDown = false;

    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYRot;
    private double lerpXRot;

    public ElainaBroomEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.blocksBuilding = true;
    }

    public ElainaBroomEntity(Level level) {
        super(TYPE, level);
    }

    @OnlyIn(Dist.CLIENT)
    private static boolean keyForward() {
        return Minecraft.getInstance().options.keyUp.isDown();
    }

    @OnlyIn(Dist.CLIENT)
    private static boolean keyBack() {
        return Minecraft.getInstance().options.keyDown.isDown();
    }

    @OnlyIn(Dist.CLIENT)
    private static boolean keyLeft() {
        return Minecraft.getInstance().options.keyLeft.isDown();
    }

    @OnlyIn(Dist.CLIENT)
    private static boolean keyRight() {
        return Minecraft.getInstance().options.keyRight.isDown();
    }

    @OnlyIn(Dist.CLIENT)
    private static boolean keyUp() {
        return Minecraft.getInstance().options.keyJump.isDown();
    }

    @OnlyIn(Dist.CLIENT)
    private static boolean keyDown() {
        return Minecraft.getInstance().options.keySprint.isDown();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {

    }

    /**
     * lerp是从boat类里直接拿的
     */
    @Override
    public void lerpTo(double x, double y, double z, float yRot, float xRot, int steps) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYRot = yRot;
        this.lerpXRot = xRot;
        this.lerpSteps = 10;
    }

    /**
     * 这里有bug 但我不知道怎么改
     */
    private void tickLerp() {
        if(!this.isVehicle()) return;
        if (this.isControlledByLocalInstance()) {
            this.lerpSteps = 0;
            this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
        }
        if (this.lerpSteps > 0) {
            this.lerpPositionAndRotationStep(this.lerpSteps, this.lerpX, this.lerpY, this.lerpZ, this.lerpYRot, this.lerpXRot);
            this.lerpSteps--;
        }
    }

    @Override
    public double lerpTargetX() {
        return this.lerpSteps > 0 ? this.lerpX : this.getX();
    }

    @Override
    public double lerpTargetY() {
        return this.lerpSteps > 0 ? this.lerpY : this.getY();
    }

    @Override
    public double lerpTargetZ() {
        return this.lerpSteps > 0 ? this.lerpZ : this.getZ();
    }

    @Override
    public float lerpTargetXRot() {
        return this.lerpSteps > 0 ? (float) this.lerpXRot : this.getXRot();
    }

    @Override
    public float lerpTargetYRot() {
        return this.lerpSteps > 0 ? (float) this.lerpYRot : this.getYRot();
    }

    // 获取实体掉落物品
    public Item getDropItem() {
        return InitItem.ELAINA_BROOM.get();
    }

    // 获取控制乘客
    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        if (this.getFirstPassenger() instanceof Player player) {
            return player;
        }
        return null;
    }

    // 是否可以添加乘客
    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().isEmpty();
    }

    // 是否可以交互
    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    // 与扫帚交互
    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (!player.isDiscrete() && this.canAddPassenger(this)) {
            this.setDeltaMovement(Vec3.ZERO);
            if (!this.level().isClientSide) {
                player.setYRot(this.getYRot());
                player.startRiding(this);
            }
            player.setYRot(this.getYRot());
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.interact(player, hand);
    }

    /**
     * 攻击与掉落物品
     * 当扫帚没有乘客 并且玩家按下shift键时
     * 删除实体并给予玩家对应掉落物
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.level().isClientSide || this.isRemoved()) {
            return true;
        } else if (this.isInvulnerableTo(source)) {
            return false;
        } else if (source.getEntity() instanceof Player player && player.isDiscrete()) {
            ItemStack itemStack = new ItemStack(this.getDropItem());
            if (this.hasCustomName()) {
                itemStack.set(DataComponents.CUSTOM_NAME, this.getCustomName());
            }
            player.spawnAtLocation(itemStack);
            this.discard();
            return true;
        }
        return false;
    }

    // 无摔落伤害
    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
        this.resetFallDistance();
    }

    public double getFloatSpeed() {
        return 0.005 * Math.sin(this.tickCount * Math.PI / 18);
    }

    // 扫帚控制
    // 正是因为如此 有BUG啊有BUG!!!
    public void controlBroom() {
        if (this.isVehicle()) {
            if (!this.isControlledByLocalInstance()) return;
            keyForward = keyForward();
            keyBack = keyBack();
            keyLeft = keyLeft();
            keyRight = keyRight();
            keyUp = keyUp();
            keyDown = keyDown();

            // 同步玩家视角方向到扫帚
            this.setYRot(this.getControllingPassenger().getYRot());

            // 获取扫帚方向
            double yawRadians = Math.toRadians(this.getControllingPassenger().getYRot());

            // 当前速度
            double moveX = this.getDeltaMovement().x;
            double moveY = this.getDeltaMovement().y;
            double moveZ = this.getDeltaMovement().z;

            //应用配置文件的等级速度关联
            int playerLevel = ((Player) this.getControllingPassenger()).experienceLevel;
            double d = Math.min(1.0, (double) (playerLevel + 5) / (ElainaBroomConfig.max_level + 5));

            // 推进力增量（加速度）
            double forwardSpeed = 0.1 * ElainaBroomConfig.Speed * d; // 前进速度 2倍
            double backSpeed = 0.05 * ElainaBroomConfig.Speed * d;    // 后退速度
            double lateralSpeed = 0.05 * ElainaBroomConfig.Speed * d; // 左右移动速度
            double verticalSpeed = 0.05 * ElainaBroomConfig.Speed * d; // 上下移动速度
            double friction = ElainaBroomConfig.friction; // 惯性摩擦系数 接近近1.0 惯性越大

            // 计算移动方向
            if (keyForward) {
                moveX += Math.cos(yawRadians + Math.PI / 2) * forwardSpeed;
                moveZ += Math.sin(yawRadians + Math.PI / 2) * forwardSpeed;
            }
            if (keyBack) {
                moveX -= Math.cos(yawRadians + Math.PI / 2) * backSpeed;
                moveZ -= Math.sin(yawRadians + Math.PI / 2) * backSpeed;
            }
            if (keyLeft) {
                moveX += Math.cos(yawRadians) * lateralSpeed;
                moveZ += Math.sin(yawRadians) * lateralSpeed;
            }
            if (keyRight) {
                moveX -= Math.cos(yawRadians) * lateralSpeed;
                moveZ -= Math.sin(yawRadians) * lateralSpeed;
            }
            if (keyUp) {
                moveY += verticalSpeed;
            }
            if (keyDown) {
                moveY -= verticalSpeed;
            }

            // 应用摩擦力（让速度逐渐衰减以模拟惯性）
            moveX *= friction;
            moveY *= friction;
            moveZ *= friction;

            // 加入上下漂浮效果
            moveY += this.getFloatSpeed();

            // 设置新的移动速度 执行移动
            this.setDeltaMovement(moveX, moveY, moveZ);
            this.move(MoverType.SELF, this.getDeltaMovement());

        } else {
            // 下方是空气或者是非实心方块 并且不在流体中
            if (this.getBlockStateOn().isAir()|| !this.getBlockStateOn().isSolid() && !this.isInFluidType()) {
                double friction = ElainaBroomConfig.friction;
                this.setDeltaMovement(this.getDeltaMovement().x * friction, -0.30f, this.getDeltaMovement().z * friction);
                this.move(MoverType.SELF, this.getDeltaMovement());
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.tickLerp();
        this.checkInsideBlocks();
        this.controlBroom();
    }
}