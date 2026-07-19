package net.zhaiji.elainabroom.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.zhaiji.elainabroom.ElainaBroomConfig;
import net.zhaiji.elainabroom.compat.CompatManager;
import net.zhaiji.elainabroom.compat.SophisticatedBackpacksCompat;
import net.zhaiji.elainabroom.init.InitEntityType;
import net.zhaiji.elainabroom.init.InitItem;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ElainaBroomEntity extends Entity {
    private static final EntityDataAccessor<Integer> DATA_NEED_LEVEL = SynchedEntityData.defineId(ElainaBroomEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_MAX_LEVEL = SynchedEntityData.defineId(ElainaBroomEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DATA_SPEED = SynchedEntityData.defineId(ElainaBroomEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_FRICTION = SynchedEntityData.defineId(ElainaBroomEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_FORWARD_SPEED = SynchedEntityData.defineId(ElainaBroomEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_BACK_SPEED = SynchedEntityData.defineId(ElainaBroomEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_LATERAL_SPEED = SynchedEntityData.defineId(ElainaBroomEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_VERTICAL_SPEED = SynchedEntityData.defineId(ElainaBroomEntity.class, EntityDataSerializers.FLOAT);

    public boolean dismountMarker = false;

    /**
     * 召唤此扫帚的来源精妙背包存储 UUID，来自玩家物品栏或创造模式时为 null
     */
    @Nullable
    private UUID sourceStorageUuid;

    /**
     * 召唤此扫帚的原始物品堆叠，用于召回时完整归还
     */
    private ItemStack broomStack = ItemStack.EMPTY;

    private boolean inputForward;
    private boolean inputBack;
    private boolean inputLeft;
    private boolean inputRight;
    private boolean inputUp;
    private boolean inputDown;

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

    /**
     * 在服务端生成扫帚实体并播放出现音效
     */
    public static ElainaBroomEntity summonBroom(ServerLevel serverLevel, ItemStack stack, BlockPos pos, @Nullable Player player) {
        ElainaBroomEntity broom = InitEntityType.ELAINA_BROOM.get().create(
            serverLevel,
            stack.getTag(),
            entity -> {
                if (stack.hasCustomHoverName()) {
                    entity.setCustomName(stack.getHoverName());
                }
                entity.broomStack = stack.copyWithCount(1);
                if (player != null) {
                    entity.setYRot(player.getYRot());
                    entity.yRotO = player.getYRot();
                }
            },
            pos, MobSpawnType.SPAWN_EGG, true, true
        );
        if (broom != null) {
            serverLevel.addFreshEntity(broom);
            serverLevel.playSound(broom, broom.getOnPos(), SoundEvents.WOOL_PLACE, SoundSource.PLAYERS, 0.75F, 0.8F);
        }
        return broom;
    }

    /**
     * 按优先级归还扫帚物品：原来的精妙背包 → 其他精妙背包 → 玩家物品栏 → 掉落
     */
    public static void returnBroomStack(Player player, ItemStack broomStack, @Nullable UUID sourceStorageUuid) {
        if (sourceStorageUuid != null && CompatManager.SOPHISTICATED_BACKPACKS_LOADED) {
            if (SophisticatedBackpacksCompat.returnBroomToBackpack(player, broomStack, sourceStorageUuid)) {
                return;
            }
        }
        if (!addBroomPreferMainInventory(player, broomStack)) {
            player.spawnAtLocation(broomStack);
        }
    }

    /**
     * 将扫帚优先放入主背包，主背包已满时退回快捷栏，返回是否成功放入
     */
    private static boolean addBroomPreferMainInventory(Player player, ItemStack broomStack) {
        Inventory inventory = player.getInventory();
        int slot = -1;
        for (int i = 9; i < 36; i++) {
            if (inventory.getItem(i).isEmpty()) {
                slot = i;
                break;
            }
        }
        if (slot == -1) {
            for (int i = 0; i < 9; i++) {
                if (inventory.getItem(i).isEmpty()) {
                    slot = i;
                    break;
                }
            }
        }
        if (slot == -1) {
            return false;
        }
        inventory.setItem(slot, broomStack.copy());
        return true;
    }

    /**
     * 获取召唤此扫帚的来源精妙背包存储 UUID
     */
    @Nullable
    public UUID getSourceStorageUuid() {
        return this.sourceStorageUuid;
    }

    /**
     * 设置召唤此扫帚的来源精妙背包存储 UUID
     */
    public void setSourceStorageUuid(@Nullable UUID sourceStorageUuid) {
        this.sourceStorageUuid = sourceStorageUuid;
    }

    /**
     * 获取原始物品堆叠，旧存档实体无存储数据时回退到默认实例并保留自定义名称
     */
    public ItemStack getBroomStackOrFallback() {
        if (this.broomStack.isEmpty()) {
            ItemStack fallback = InitItem.ELAINA_BROOM.get().getDefaultInstance();
            if (this.hasCustomName()) {
                fallback.setHoverName(this.getCustomName());
            }
            return fallback;
        }
        return this.broomStack;
    }

    /**
     * 由客户端调用，设置扫帚的移动输入状态
     */
    public void setInput(boolean forward, boolean back, boolean left, boolean right, boolean up, boolean down) {
        this.inputForward = forward;
        this.inputBack = back;
        this.inputLeft = left;
        this.inputRight = right;
        this.inputUp = up;
        this.inputDown = down;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_NEED_LEVEL, 10);
        this.entityData.define(DATA_MAX_LEVEL, 100);
        this.entityData.define(DATA_SPEED, 1.0F);
        this.entityData.define(DATA_FRICTION, 0.93F);
        this.entityData.define(DATA_FORWARD_SPEED, 3.0F);
        this.entityData.define(DATA_BACK_SPEED, 1.5F);
        this.entityData.define(DATA_LATERAL_SPEED, 1.5F);
        this.entityData.define(DATA_VERTICAL_SPEED, 1.8F);
    }

    @Override
    public void tick() {
        super.tick();
        this.updateConfig();
        this.tickLerp();
        this.controlBroom();
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
        this.resetFallDistance();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.level().isClientSide || this.isRemoved()) {
            return true;
        } else if (this.isInvulnerableTo(source)) {
            return false;
        } else if (!this.isVehicle() && source.getEntity() instanceof Player player && player.isShiftKeyDown()) {
            returnBroomStack(player, this.getBroomStackOrFallback(), this.sourceStorageUuid);
            this.discard();
            return true;
        }
        return false;
    }

    @Override
    public boolean isPickable() {
        if (this.getControllingPassenger() instanceof Player) {
            return false;
        }
        return !this.isRemoved();
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.hasUUID("SourceStorageUuid")) {
            this.sourceStorageUuid = compoundTag.getUUID("SourceStorageUuid");
        }
        if (compoundTag.contains("BroomStack")) {
            this.broomStack = ItemStack.of(compoundTag.getCompound("BroomStack"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        if (this.sourceStorageUuid != null) {
            compoundTag.putUUID("SourceStorageUuid", this.sourceStorageUuid);
        }
        if (!this.broomStack.isEmpty()) {
            compoundTag.put("BroomStack", this.broomStack.save(new CompoundTag()));
        }
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (!player.isShiftKeyDown() && this.canAddPassenger(player)) {
            if (player.experienceLevel < this.getNeedLevel()) {
                if (this.level().isClientSide()) {
                    player.displayClientMessage(Component.translatable("tips.elainabroom.need_level", this.getNeedLevel()), true);
                }
                return super.interact(player, hand);
            }
            this.setDeltaMovement(Vec3.ZERO);
            player.setYRot(this.getYRot());
            player.yRotO = this.getYRot();
            if (!this.level().isClientSide()) {
                player.startRiding(this);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }
        return super.interact(player, hand);
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction callback) {
        if (this.hasPassenger(passenger)) {
            double d0 = this.getY() + this.getPassengersRidingOffset() + passenger.getMyRidingOffset();
            callback.accept(passenger, this.getX(), d0 - 0.2, this.getZ());
        }
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        this.dismountMarker = true;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().isEmpty();
    }

    @Override
    public void lerpTo(double x, double y, double z, float yRot, float xRot, int steps, boolean teleport) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYRot = yRot;
        this.lerpXRot = xRot;
        if (this.dismountMarker) {
            this.lerpSteps = 0;
            this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
            this.dismountMarker = false;
        } else {
            this.lerpSteps = 10;
        }
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        if (this.getFirstPassenger() instanceof Player player) {
            return player;
        }
        return super.getControllingPassenger();
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return InitItem.ELAINA_BROOM.get().getDefaultInstance();
    }

    public int getNeedLevel() {
        return this.entityData.get(DATA_NEED_LEVEL);
    }

    public int getMaxLevel() {
        return this.entityData.get(DATA_MAX_LEVEL);
    }

    public float getSpeed() {
        return this.entityData.get(DATA_SPEED);
    }

    public float getFriction() {
        return this.entityData.get(DATA_FRICTION);
    }

    public float getForwardSpeed() {
        return this.entityData.get(DATA_FORWARD_SPEED);
    }

    public float getBackSpeed() {
        return this.entityData.get(DATA_BACK_SPEED);
    }

    public float getLateralSpeed() {
        return this.entityData.get(DATA_LATERAL_SPEED);
    }

    public float getVerticalSpeed() {
        return this.entityData.get(DATA_VERTICAL_SPEED);
    }

    /**
     * 定期从配置同步参数到同步数据
     */
    public void updateConfig() {
        if (this.tickCount % 40 != 0) return;
        this.entityData.set(DATA_NEED_LEVEL, ElainaBroomConfig.needLevel);
        this.entityData.set(DATA_MAX_LEVEL, ElainaBroomConfig.maxLevel);
        this.entityData.set(DATA_SPEED, (float) ElainaBroomConfig.speed);
        this.entityData.set(DATA_FRICTION, (float) ElainaBroomConfig.friction);
        this.entityData.set(DATA_FORWARD_SPEED, (float) ElainaBroomConfig.forwardSpeed);
        this.entityData.set(DATA_BACK_SPEED, (float) ElainaBroomConfig.backSpeed);
        this.entityData.set(DATA_LATERAL_SPEED, (float) ElainaBroomConfig.lateralSpeed);
        this.entityData.set(DATA_VERTICAL_SPEED, (float) ElainaBroomConfig.verticalSpeed);
    }

    /**
     * 根据玩家等级计算速度倍率，创造模式保底 1.0，开启 unlimitedSpeed 时按真实等级继续突破
     */
    public static double calculateSpeedScale(int playerLevel, boolean instabuild) {
        double ratio = (double) (playerLevel + 5) / (ElainaBroomConfig.maxLevel + 5);
        double speedScale;
        if (ElainaBroomConfig.unlimitedSpeed && ratio > 1.0) {
            speedScale = 1.0 + Math.sqrt(ratio - 1.0);
        } else {
            speedScale = Math.min(1.0, ratio);
        }
        if (instabuild) {
            if (ElainaBroomConfig.unlimitedSpeed) {
                speedScale = Math.max(1.0, speedScale);
            } else {
                speedScale = 1.0;
            }
        }
        return speedScale;
    }

    public double getFloatSpeed() {
        return 0.0015 * Math.sin(this.tickCount * Math.PI / 20);
    }

    /**
     * 根据当前输入状态控制扫帚的移动
     */
    public void controlBroom() {
        if (this.isVehicle() && this.getControllingPassenger() instanceof Player player) {
            if (!this.isControlledByLocalInstance()) return;
            this.setYRot(player.getYRot());

            double moveX = this.getDeltaMovement().x;
            double moveY = this.getDeltaMovement().y;
            double moveZ = this.getDeltaMovement().z;

            double speedScale = calculateSpeedScale(player.experienceLevel, player.getAbilities().instabuild);

            double forwardSpeed = 0.03 * this.getForwardSpeed() * this.getSpeed() * speedScale;
            double backSpeed = 0.03 * this.getBackSpeed() * this.getSpeed() * speedScale;
            double lateralSpeed = 0.03 * this.getLateralSpeed() * this.getSpeed() * speedScale;
            double verticalSpeed = 0.03 * this.getVerticalSpeed() * this.getSpeed() * speedScale;
            double friction = this.getFriction();
            double yawRadians = Math.toRadians(this.getYRot());

            if (this.inputForward) {
                moveX += Math.cos(yawRadians + Math.PI / 2) * forwardSpeed;
                moveZ += Math.sin(yawRadians + Math.PI / 2) * forwardSpeed;
            }
            if (this.inputBack) {
                moveX -= Math.cos(yawRadians + Math.PI / 2) * backSpeed;
                moveZ -= Math.sin(yawRadians + Math.PI / 2) * backSpeed;
            }
            if (this.inputLeft) {
                moveX += Math.cos(yawRadians) * lateralSpeed;
                moveZ += Math.sin(yawRadians) * lateralSpeed;
            }
            if (this.inputRight) {
                moveX -= Math.cos(yawRadians) * lateralSpeed;
                moveZ -= Math.sin(yawRadians) * lateralSpeed;
            }
            if (this.inputUp) {
                moveY += verticalSpeed;
            }
            if (this.inputDown) {
                moveY -= verticalSpeed;
            }

            moveX *= friction;
            moveY *= friction;
            moveZ *= friction;

            moveY += this.getFloatSpeed();

            this.setDeltaMovement(moveX, moveY, moveZ);
            this.move(MoverType.SELF, this.getDeltaMovement());
        } else {
            if (this.isEffectiveAi()) {
                if ((this.getBlockStateOn().isAir() || !this.getBlockStateOn().isSolid()) && this.getBlockStateOn()
                    .getFluidState()
                    .isEmpty()) {
                    this.setDeltaMovement(0, -0.3f, 0);
                    this.move(MoverType.SELF, this.getDeltaMovement());
                } else if (this.isUnderWater()) {
                    this.setDeltaMovement(0, -0.1f, 0);
                    this.move(MoverType.SELF, this.getDeltaMovement());
                } else {
                    this.setDeltaMovement(Vec3.ZERO);
                }
            }
        }
    }

    public void tickLerp() {
        if (this.isControlledByLocalInstance()) {
            this.lerpSteps = 0;
            this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
        }
        if (this.lerpSteps > 0) {
            double d0 = this.getX() + (this.lerpX - this.getX()) / (double) this.lerpSteps;
            double d1 = this.getY() + (this.lerpY - this.getY()) / (double) this.lerpSteps;
            double d2 = this.getZ() + (this.lerpZ - this.getZ()) / (double) this.lerpSteps;
            double d3 = Mth.wrapDegrees(this.lerpYRot - (double) this.getYRot());
            this.setYRot(this.getYRot() + (float) d3 / (float) this.lerpSteps);
            this.setXRot(this.getXRot() + (float) (this.lerpXRot - (double) this.getXRot()) / (float) this.lerpSteps);
            --this.lerpSteps;
            this.setPos(d0, d1, d2);
            this.setRot(this.getYRot(), this.getXRot());
        }
    }
}
