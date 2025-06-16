package net.zhaiji.elainabroom.network.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.zhaiji.elainabroom.ElainaBroomConfig;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;
import net.zhaiji.elainabroom.init.InitItem;
import net.zhaiji.elainabroom.network.ElainaBroomPacket;
import net.zhaiji.elainabroom.network.client.MessagePacket;

import java.util.function.Supplier;

public class SummonOrRecallBroomPacket {
    public static SummonOrRecallBroomPacket decode(FriendlyByteBuf buf) {
        return new SummonOrRecallBroomPacket();
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void recallBroom(Player player, ElainaBroomEntity broom) {
        if (!player.getAbilities().instabuild || broom.hasCustomName()) {
            ItemStack stack = InitItem.ELAINA_BROOM.get().getDefaultInstance();
            if (broom.hasCustomName()) {
                stack.setHoverName(broom.getCustomName());
            }
            if (!player.getInventory().add(stack)) {
                player.spawnAtLocation(stack);
            }
        }
        broom.discard();
    }

    public void rideBroom(Player player, ElainaBroomEntity broom) {
        broom.setYRot(player.getYRot());
        player.startRiding(broom);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                if (player.isPassenger() && player.getVehicle() instanceof ElainaBroomEntity broom && broom.getControllingPassenger() == player) {
                    this.recallBroom(player, broom);
                } else if (!player.isPassenger()) {
                    boolean flag = false;
                    ServerLevel serverLevel = player.serverLevel();
                    boolean messageFlag = false;
                    if (!player.getAbilities().instabuild) {
                        if (player.experienceLevel < ElainaBroomConfig.need_level) {
                            messageFlag = true;
                        } else {
                            for (ItemStack stack : player.getInventory().items) {
                                if (stack.is(InitItem.ELAINA_BROOM.get())) {
                                    ElainaBroomEntity broom = ElainaBroomEntity.summonBroom(serverLevel, stack, player.getOnPos(), player);
                                    if (broom != null) {
                                        stack.shrink(1);
                                        this.rideBroom(player,broom);
                                        flag = true;
                                    }
                                    break;
                                }
                            }
                        }
                    } else {
                        ElainaBroomEntity broom = ElainaBroomEntity.summonBroom(serverLevel, InitItem.ELAINA_BROOM.get().getDefaultInstance(), player.getOnPos(), player);
                        if (broom != null) {
                            this.rideBroom(player,broom);
                        }
                    }
                    if (!flag) {
                        for (Entity entity : player.level().getEntities(player, player.getBoundingBox().inflate(ElainaBroomConfig.recall_distance))) {
                            if (entity instanceof ElainaBroomEntity broom && !broom.isVehicle()) {
                                this.recallBroom(player, broom);
                                messageFlag = false;
                                break;
                            }
                        }
                    }
                    if (messageFlag) {
                        ElainaBroomPacket.sendToClient(player, new MessagePacket(ElainaBroomConfig.need_level));
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}
