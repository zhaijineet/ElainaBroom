package net.zhaiji.elainabroom.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.zhaiji.elainabroom.ElainaBroomConfig;
import net.zhaiji.elainabroom.client.keymapping.KeyMappings;
import net.zhaiji.elainabroom.init.InitItem;
import org.jetbrains.annotations.Nullable;

public class ElainaBroomEntity extends Entity {
    public static final EntityType<ElainaBroomEntity> TYPE = EntityType.Builder.of(ElainaBroomEntity::new, MobCategory.MISC)
            .fireImmune()
            .clientTrackingRange(10)
            .sized(1.3f, 0.6f)
            .build("elaina_broom");

    private static final EntityDataAccessor<Integer> DATA_NEED_LEVEL = SynchedEntityData.defineId(ElainaBroomEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_MAX_LEVEL = SynchedEntityData.defineId(ElainaBroomEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DATA_SPEED = SynchedEntityData.defineId(ElainaBroomEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_FRICTION = SynchedEntityData.defineId(ElainaBroomEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_FORWARD_SPEED = SynchedEntityData.defineId(ElainaBroomEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_BACK_SPEED = SynchedEntityData.defineId(ElainaBroomEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_LATERAL_SPEED = SynchedEntityData.defineId(ElainaBroomEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_VERTICAL_SPEED = SynchedEntityData.defineId(ElainaBroomEntity.class, EntityDataSerializers.FLOAT);

    public boolean dismountMarket = false;

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
        return KeyMappings.BroomUpKey.isDown();
    }

    @OnlyIn(Dist.CLIENT)
    private static boolean keyDown() {
        return KeyMappings.BroomDownKey.isDown();
    }

    public static ElainaBroomEntity summonBroom(ServerLevel serverLevel, ItemStack stack, BlockPos pos, @Nullable Player player) {
        ElainaBroomEntity broom = ElainaBroomEntity.TYPE.create(serverLevel, entity -> {
            if (stack.has(DataComponents.CUSTOM_NAME)) {
                entity.setCustomName(stack.getHoverName());
            }
            if (player != null) {
                entity.setYRot(player.getYRot());
            }
        }, pos, MobSpawnType.SPAWN_EGG, true, true);
        if (broom != null) {
            serverLevel.addFreshEntity(broom);
            serverLevel.playSound(broom, broom.getOnPos(), SoundEvents.WOOL_PLACE, SoundSource.PLAYERS, 0.75F, 0.8F);
        }
        return broom;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_NEED_LEVEL, 10);
        builder.define(DATA_MAX_LEVEL, 30);
        builder.define(DATA_SPEED, 1.0F);
        builder.define(DATA_FRICTION, 0.93F);
        builder.define(DATA_FORWARD_SPEED, 2.0F);
        builder.define(DATA_BACK_SPEED, 1.0F);
        builder.define(DATA_LATERAL_SPEED, 1.0F);
        builder.define(DATA_VERTICAL_SPEED, 1.2F);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

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

    public void updateConfig() {
        if (this.tickCount % 40 == 0) return;
        this.entityData.set(DATA_NEED_LEVEL, ElainaBroomConfig.need_level);
        this.entityData.set(DATA_MAX_LEVEL, ElainaBroomConfig.max_level);
        this.entityData.set(DATA_SPEED, (float) ElainaBroomConfig.speed);
        this.entityData.set(DATA_FRICTION, (float) ElainaBroomConfig.friction);
        this.entityData.set(DATA_FORWARD_SPEED, (float) ElainaBroomConfig.forwardSpeed);
        this.entityData.set(DATA_BACK_SPEED, (float) ElainaBroomConfig.backSpeed);
        this.entityData.set(DATA_LATERAL_SPEED, (float) ElainaBroomConfig.lateralSpeed);
        this.entityData.set(DATA_VERTICAL_SPEED, (float) ElainaBroomConfig.verticalSpeed);
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return InitItem.ELAINA_BROOM.get().getDefaultInstance();
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
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
            if (!this.level().isClientSide()) {
                player.startRiding(this);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }
        return super.interact(player, hand);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.level().isClientSide || this.isRemoved()) {
            return true;
        } else if (this.isInvulnerableTo(source)) {
            return false;
        } else if (source.getEntity() instanceof Player player && player.isShiftKeyDown()) {
            ItemStack stack = InitItem.ELAINA_BROOM.get().getDefaultInstance();
            if (this.hasCustomName()) {
                stack.set(DataComponents.CUSTOM_NAME, this.getCustomName());
            }
            if (!player.getInventory().add(stack)) {
                this.spawnAtLocation(stack);
            }
            this.discard();
            return true;
        }
        return false;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().isEmpty();
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        this.dismountMarket = true;
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction callback) {
        Vec3 vec3 = this.getPassengerRidingPosition(passenger);
        Vec3 vec31 = passenger.getVehicleAttachmentPoint(this);
        callback.accept(passenger, vec3.x - vec31.x, vec3.y - vec31.y - 0.1, vec3.z - vec31.z);
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        if (this.getFirstPassenger() instanceof Player player) {
            return player;
        }
        return super.getControllingPassenger();
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
        this.resetFallDistance();
    }

    public double getFloatSpeed() {
        return 0.0015 * Math.sin(this.tickCount * Math.PI / 20);
    }

    public void controlBroom() {
        if (this.isVehicle() && this.getControllingPassenger() instanceof Player player) {
            if (!this.isControlledByLocalInstance()) return;
            this.setYRot(player.getYRot());

            double moveX = this.getDeltaMovement().x;
            double moveY = this.getDeltaMovement().y;
            double moveZ = this.getDeltaMovement().z;

            double speedScale = Math.min(1.0, (double) (player.experienceLevel + 5) / (this.getMaxLevel() + 5));
            if (player.getAbilities().instabuild) {
                speedScale = 1.0;
            }

            double forwardSpeed = 0.03 * this.getForwardSpeed() * this.getSpeed() * speedScale;
            double backSpeed = 0.03 * this.getBackSpeed() * this.getSpeed() * speedScale;
            double lateralSpeed = 0.03 * this.getLateralSpeed() * this.getSpeed() * speedScale;
            double verticalSpeed = 0.03 * this.getVerticalSpeed() * this.getSpeed() * speedScale;
            double friction = this.getFriction();
            double yawRadians = Math.toRadians(this.getYRot());

            if (keyForward()) {
                moveX += Math.cos(yawRadians + Math.PI / 2) * forwardSpeed;
                moveZ += Math.sin(yawRadians + Math.PI / 2) * forwardSpeed;
            }
            if (keyBack()) {
                moveX -= Math.cos(yawRadians + Math.PI / 2) * backSpeed;
                moveZ -= Math.sin(yawRadians + Math.PI / 2) * backSpeed;
            }
            if (keyLeft()) {
                moveX += Math.cos(yawRadians) * lateralSpeed;
                moveZ += Math.sin(yawRadians) * lateralSpeed;
            }
            if (keyRight()) {
                moveX -= Math.cos(yawRadians) * lateralSpeed;
                moveZ -= Math.sin(yawRadians) * lateralSpeed;
            }
            if (keyUp()) {
                moveY += verticalSpeed;
            }
            if (keyDown()) {
                moveY -= verticalSpeed;
            }

            moveX *= friction;
            moveY *= friction;
            moveZ *= friction;

            moveY += this.getFloatSpeed();

            this.setDeltaMovement(moveX, moveY, moveZ);
            this.move(MoverType.SELF, this.getDeltaMovement());
        } else {
            if ((this.getBlockStateOn().isAir() || !this.getBlockStateOn().isSolid()) && this.getBlockStateOn().getFluidState().isEmpty()) {
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

    @Override
    public void lerpTo(double x, double y, double z, float yRot, float xRot, int steps) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYRot = yRot;
        this.lerpXRot = xRot;
        if (this.dismountMarket) {
            this.lerpSteps = 0;
            this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
            this.dismountMarket = false;
        } else {
            this.lerpSteps = 10;
        }
    }

    public void tickLerp() {
        if (this.isControlledByLocalInstance()) {
            this.lerpSteps = 0;
            this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
        }
        if (this.lerpSteps > 0) {
            this.lerpPositionAndRotationStep(this.lerpSteps, this.lerpX, this.lerpY, this.lerpZ, this.lerpYRot, this.lerpXRot);
            --this.lerpSteps;
        }
    }

    public void discordShiftDismount() {
        for (Entity entity : this.getPassengers()) {
            entity.setShiftKeyDown(false);
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.discordShiftDismount();
        this.updateConfig();
        this.tickLerp();
        this.controlBroom();
    }
}
