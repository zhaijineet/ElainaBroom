package net.zhaiji.elainabroom.network.server;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.zhaiji.elainabroom.ElainaBroomConfig;
import net.zhaiji.elainabroom.compat.CompatManager;
import net.zhaiji.elainabroom.compat.FoundBroom;
import net.zhaiji.elainabroom.compat.SophisticatedBackpacksCompat;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;
import net.zhaiji.elainabroom.init.InitItem;
import net.zhaiji.elainabroom.network.client.packet.MessagePacket;
import net.zhaiji.elainabroom.network.server.packet.DismountPacket;
import net.zhaiji.elainabroom.network.server.packet.SummonOrRecallBroomPacket;

public class ServerPacketHandler {
    public static void handlerSummonOrRecallBroomPacket(Player player, SummonOrRecallBroomPacket packet) {
        if (player.isPassenger() && player.getVehicle() instanceof ElainaBroomEntity broom && broom.getControllingPassenger() == player) {
            recallBroom(player, broom);
        } else if (!player.isPassenger()) {
            boolean flag = false;
            boolean messageFlag = false;
            ServerLevel serverLevel = (ServerLevel) player.level();
            if (!player.getAbilities().instabuild) {
                for (ItemStack stack : player.getInventory().getNonEquipmentItems()) {
                    if (stack.is(InitItem.ELAINA_BROOM.get())) {
                        if (player.experienceLevel < ElainaBroomConfig.needLevel) {
                            messageFlag = true;
                        } else {
                            ElainaBroomEntity broom = ElainaBroomEntity.summonBroom(serverLevel, stack, player.getOnPos(), player);
                            if (broom != null) {
                                stack.shrink(1);
                                rideBroom(player, broom);
                                flag = true;
                            }
                        }
                        break;
                    }
                }
                if (!flag && !messageFlag && CompatManager.SOPHISTICATED_BACKPACKS_LOADED) {
                    if (player.experienceLevel < ElainaBroomConfig.needLevel) {
                        if (SophisticatedBackpacksCompat.hasBroomInBackpacks(player)) {
                            messageFlag = true;
                        }
                    } else {
                        FoundBroom foundBroom = SophisticatedBackpacksCompat.findBroomInBackpacks(player);
                        if (foundBroom != null) {
                            ElainaBroomEntity broom = ElainaBroomEntity.summonBroom(
                                serverLevel,
                                foundBroom.broomStack(),
                                player.getOnPos(),
                                player
                            );
                            if (broom != null) {
                                broom.setSourceStorageUuid(foundBroom.sourceStorageUuid());
                                rideBroom(player, broom);
                                flag = true;
                            } else {
                                ElainaBroomEntity.returnBroomStack(player, foundBroom.broomStack(), foundBroom.sourceStorageUuid());
                            }
                        }
                    }
                }
            } else {
                ElainaBroomEntity broom = ElainaBroomEntity.summonBroom(
                    serverLevel,
                    InitItem.ELAINA_BROOM.get().getDefaultInstance(),
                    player.getOnPos(),
                    player
                );
                if (broom != null) {
                    rideBroom(player, broom);
                }
            }
            if (!flag) {
                for (Entity entity : player.level()
                    .getEntities(player, player.getBoundingBox().inflate(ElainaBroomConfig.recallDistance))) {
                    if (entity instanceof ElainaBroomEntity broom && !broom.isVehicle()) {
                        recallBroom(player, broom);
                        messageFlag = false;
                        break;
                    }
                }
            }
            if (messageFlag) {
                PacketDistributor.sendToPlayer((ServerPlayer) player, new MessagePacket(ElainaBroomConfig.needLevel));
            }
        }
    }

    public static void handlerDismountPacket(Player player, DismountPacket packet) {
        if (player.getVehicle() instanceof ElainaBroomEntity) {
            player.stopRiding();
        }
    }

    private static void recallBroom(Player player, ElainaBroomEntity broom) {
        if (!player.getAbilities().instabuild || broom.hasCustomName()) {
            ElainaBroomEntity.returnBroomStack(player, broom.getBroomStackOrFallback(), broom.getSourceStorageUuid());
        }
        broom.discard();
    }

    private static void rideBroom(Player player, ElainaBroomEntity broom) {
        broom.setYRot(player.getYRot());
        broom.yRotO = player.getYRot();
        player.startRiding(broom);
    }
}
