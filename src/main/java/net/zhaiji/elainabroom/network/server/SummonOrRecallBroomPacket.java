package net.zhaiji.elainabroom.network.server;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.ElainaBroomConfig;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;
import net.zhaiji.elainabroom.init.InitItem;
import net.zhaiji.elainabroom.network.client.MessagePacket;

public record SummonOrRecallBroomPacket() implements CustomPacketPayload {
    public static final Type<SummonOrRecallBroomPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ElainaBroom.MOD_ID, "summon_or_recall_broom_packet"));

    public static final StreamCodec<ByteBuf, SummonOrRecallBroomPacket> STREAM_CODEC = StreamCodec.unit(new SummonOrRecallBroomPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void recallBroom(Player player, ElainaBroomEntity broom) {
        if (!player.getAbilities().instabuild || broom.hasCustomName()) {
            ItemStack stack = InitItem.ELAINA_BROOM.get().getDefaultInstance();
            if (broom.hasCustomName()) {
                stack.set(DataComponents.CUSTOM_NAME, broom.getCustomName());
            }
            if (!player.getInventory().add(stack)) {
                player.spawnAtLocation(stack);
            }
        }
        broom.discard();
    }

    public static void rideBroom(Player player, ElainaBroomEntity broom) {
        broom.setYRot(player.getYRot());
        player.startRiding(broom);
    }

    public static void handle(final SummonOrRecallBroomPacket packet, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player.isPassenger() && player.getVehicle() instanceof ElainaBroomEntity broom && broom.getControllingPassenger() == player) {
                SummonOrRecallBroomPacket.recallBroom(player, broom);
            } else if (!player.isPassenger()) {
                boolean flag = false;
                boolean messageFlag = false;
                ServerLevel serverLevel = (ServerLevel) player.level();
                if (!player.getAbilities().instabuild) {
                    if (player.experienceLevel < ElainaBroomConfig.need_level) {
                        messageFlag = true;
                    } else {
                        for (ItemStack stack : player.getInventory().items) {
                            if (stack.is(InitItem.ELAINA_BROOM.get())) {
                                ElainaBroomEntity broom = ElainaBroomEntity.summonBroom(serverLevel, stack, player.getOnPos(), player);
                                if (broom != null) {
                                    stack.shrink(1);
                                    SummonOrRecallBroomPacket.rideBroom(player, broom);
                                    flag = true;
                                }
                                break;
                            }
                        }
                    }
                } else {
                    ElainaBroomEntity broom = ElainaBroomEntity.summonBroom(serverLevel, InitItem.ELAINA_BROOM.get().getDefaultInstance(), player.getOnPos(), player);
                    if (broom != null) {
                        SummonOrRecallBroomPacket.rideBroom(player, broom);
                    }
                }
                if (!flag) {
                    for (Entity entity : player.level().getEntities(player, player.getBoundingBox().inflate(ElainaBroomConfig.recall_distance))) {
                        if (entity instanceof ElainaBroomEntity broom && !broom.isVehicle()) {
                            SummonOrRecallBroomPacket.recallBroom(player, broom);
                            messageFlag = false;
                            break;
                        }
                    }
                }
                if (messageFlag) {
                    PacketDistributor.sendToPlayer((ServerPlayer) player, new MessagePacket(ElainaBroomConfig.need_level));
                }
            }
        });
    }
}
