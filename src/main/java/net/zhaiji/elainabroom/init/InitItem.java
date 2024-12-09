package net.zhaiji.elainabroom.init;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;

public class InitItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ElainaBroom.MOD_ID);

    public static final RegistryObject<Item> ELAINA_BROOM = ITEMS.register("elaina_broom", () -> new Item(new Item.Properties().stacksTo(1)) {
        @Override
        public InteractionResult useOn(UseOnContext context) {
            if (context.getClickedFace() != Direction.DOWN) {
                Level world = context.getLevel();
                BlockPos clickedPos = new BlockPlaceContext(context).getClickedPos();
                AABB boundingBox = ElainaBroomEntity.TYPE.getDimensions().makeBoundingBox(Vec3.atBottomCenterOf(clickedPos));
                if (world.noCollision(boundingBox) && world.getEntities(null, boundingBox).isEmpty()) {
                    ItemStack stack = context.getItemInHand();
                    if (world instanceof ServerLevel serverWorld) {
                        ElainaBroomEntity broom = ElainaBroomEntity.TYPE.create(serverWorld, stack.getTag(), (entity) -> {
                            if (stack.hasCustomHoverName()) {
                                entity.setCustomName(stack.getDisplayName());
                            }
                        }, context.getClickedPos(), MobSpawnType.SPAWN_EGG, true, true);
                        if (broom == null) {
                            return InteractionResult.FAIL;
                        }
                        world.addFreshEntity(broom);
                        world.playSound(null, broom.getX(), broom.getY(), broom.getZ(), SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 0.75F, 0.8F);
                    }
                    stack.shrink(1);
                    return InteractionResult.sidedSuccess(world.isClientSide());
                }
            }
            return InteractionResult.FAIL;
        }
    });
}
