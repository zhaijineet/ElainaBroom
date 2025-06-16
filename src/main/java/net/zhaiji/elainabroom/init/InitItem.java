package net.zhaiji.elainabroom.init;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;

public class InitItem {
    public static final DeferredRegister.Items ITEM = DeferredRegister.createItems(ElainaBroom.MOD_ID);

    public static final DeferredItem<Item> ELAINA_BROOM = ITEM.register(
            "elaina_broom",
            () -> new Item(new Item.Properties().stacksTo(1)) {
                @Override
                public InteractionResult useOn(UseOnContext context) {
                    if (context.getClickedFace() != Direction.DOWN) {
                        Level level = context.getLevel();
                        BlockPos clickPos = new BlockPlaceContext(context).getClickedPos();
                        AABB aabb = ElainaBroomEntity.TYPE.getDimensions().makeBoundingBox(Vec3.atBottomCenterOf(clickPos));
                        if (level.noCollision(aabb) && level.getEntities(null, aabb).isEmpty()) {
                            ItemStack stack = context.getItemInHand();
                            if (level instanceof ServerLevel serverLevel) {
                                ElainaBroomEntity broom = ElainaBroomEntity.summonBroom(serverLevel, stack, context.getClickedPos(), null);
                                if (broom == null) {
                                    return InteractionResult.FAIL;
                                }
                            }
                            stack.shrink(1);
                            return InteractionResult.sidedSuccess(level.isClientSide());
                        }
                    }
                    return InteractionResult.FAIL;
                }
            });
}
