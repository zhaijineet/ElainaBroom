package net.zhaiji.elainabroom.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.InterpolationHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.zhaiji.elainabroom.ElainaBroomConfig;
import net.zhaiji.elainabroom.compat.CompatManager;
import net.zhaiji.elainabroom.compat.SophisticatedBackpacksCompat;
import net.zhaiji.elainabroom.init.InitEntityType;
import net.zhaiji.elainabroom.init.InitItem;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ElainaBroomEntity extends Entity {
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

    private final InterpolationHandler interpolation = new InterpolationHandler(this, 3);

    public ElainaBroomEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.blocksBuilding = true;
    }

    /**
     * 在服务端生成扫帚实体并播放出现音效
     */
    public static ElainaBroomEntity summonBroom(ServerLevel serverLevel, ItemStack stack, BlockPos pos, @Nullable Player player) {
        ElainaBroomEntity broom = InitEntityType.ELAINA_BROOM.get().create(
            serverLevel, entity -> {
                if (stack.has(DataComponents.CUSTOM_NAME)) {
                    entity.setCustomName(stack.getHoverName());
                }
                entity.broomStack = stack.copyWithCount(1);
                if (player != null) {
                    entity.setYRot(player.getYRot());
                    entity.yRotO = player.getYRot();
                }
            }, pos, EntitySpawnReason.SPAWN_ITEM_USE, true, true
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
            player.spawnAtLocation((ServerLevel) player.level(), broomStack);
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
                fallback.set(DataComponents.CUSTOM_NAME, this.getCustomName());
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
    public void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    public void tick() {
        super.tick();
        this.interpolation.interpolate();
        this.controlBroom();
    }

    @Override
    public void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
        this.resetFallDistance();
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource source, float amount) {
        if (this.isInvulnerableToBase(source)) {
            return false;
        }
        if (!this.isVehicle() && source.getEntity() instanceof Player player && player.isShiftKeyDown()) {
            returnBroomStack(player, this.getBroomStackOrFallback(), this.sourceStorageUuid);
            this.discard();
            return true;
        }
        return false;
    }

    @Override
    public boolean hurtClient(DamageSource source) {
        return !this.isRemoved();
    }

    @Override
    public boolean isPickable() {
        if (this.getControllingPassenger() instanceof Player) {
            return false;
        }
        return !this.isRemoved();
    }

    @Override
    public InterpolationHandler getInterpolation() {
        return this.interpolation;
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        this.sourceStorageUuid = input.read("SourceStorageUuid", UUIDUtil.CODEC).orElse(null);
        this.broomStack = input.read("BroomStack", ItemStack.CODEC).orElse(ItemStack.EMPTY);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        if (this.sourceStorageUuid != null) {
            output.store("SourceStorageUuid", UUIDUtil.CODEC, this.sourceStorageUuid);
        }
        if (!this.broomStack.isEmpty()) {
            output.store("BroomStack", ItemStack.CODEC, this.broomStack);
        }
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand, Vec3 location) {
        if (!player.isShiftKeyDown() && this.canAddPassenger(player)) {
            if (player.experienceLevel < ElainaBroomConfig.NEED_LEVEL.get()) {
                if (this.level().isClientSide()) {
                    player.sendOverlayMessage(Component.translatable("tips.elainabroom.need_level", ElainaBroomConfig.NEED_LEVEL.get()));
                }
                return super.interact(player, hand, location);
            }
            this.setDeltaMovement(Vec3.ZERO);
            player.setYRot(this.getYRot());
            player.yRotO = this.getYRot();
            if (!this.level().isClientSide()) {
                player.startRiding(this);
            }
            return this.level().isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
        }
        return super.interact(player, hand, location);
    }

    @Override
    public void positionRider(Entity passenger, MoveFunction callback) {
        Vec3 vec3 = this.getPassengerRidingPosition(passenger);
        Vec3 vec31 = passenger.getVehicleAttachmentPoint(this);
        callback.accept(passenger, vec3.x - vec31.x, vec3.y - vec31.y - 0.1, vec3.z - vec31.z);
    }

    @Override
    public void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        this.interpolation.cancel();
        this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
    }

    @Override
    public boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().isEmpty();
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

    /**
     * 根据玩家等级计算速度倍率，创造模式保底 1.0，开启 unlimitedSpeed 时按真实等级继续突破
     */
    public static double calculateSpeedScale(int playerLevel, boolean instabuild) {
        double ratio = (double) (playerLevel + 5) / (ElainaBroomConfig.MAX_LEVEL.get() + 5);
        double speedScale;
        if (ElainaBroomConfig.UNLIMITED_SPEED.get() && ratio > 1.0) {
            speedScale = 1.0 + Math.sqrt(ratio - 1.0);
        } else {
            speedScale = Math.min(1.0, ratio);
        }
        if (instabuild) {
            if (ElainaBroomConfig.UNLIMITED_SPEED.get()) {
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
            if (!this.isLocalInstanceAuthoritative()) return;
            this.setYRot(player.getYRot());

            double moveX = this.getDeltaMovement().x;
            double moveY = this.getDeltaMovement().y;
            double moveZ = this.getDeltaMovement().z;

            double speedScale = calculateSpeedScale(player.experienceLevel, player.getAbilities().instabuild);

            double speed = ElainaBroomConfig.SPEED.get();
            double friction = ElainaBroomConfig.FRICTION.get();
            double forwardSpeed = 0.03 * ElainaBroomConfig.FORWARD_SPEED.get() * speed * speedScale;
            double backSpeed = 0.03 * ElainaBroomConfig.BACK_SPEED.get() * speed * speedScale;
            double lateralSpeed = 0.03 * ElainaBroomConfig.LATERAL_SPEED.get() * speed * speedScale;
            double verticalSpeed = 0.03 * ElainaBroomConfig.VERTICAL_SPEED.get() * speed * speedScale;
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
                BlockState blockStateOn = this.getBlockStateOn();
                if ((blockStateOn.isAir() || blockStateOn.getCollisionShape(this.level(), this.getOnPos()).isEmpty()) && blockStateOn.getFluidState().isEmpty()) {
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
}
