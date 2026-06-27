package net.zhaiji.elainabroom.client.keymapping;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;
import net.zhaiji.elainabroom.network.server.packet.DismountPacket;
import net.zhaiji.elainabroom.network.server.packet.SummonOrRecallBroomPacket;

public class KeyMappings {
    public static final KeyMapping.Category BROOM_CATEGORY = new KeyMapping.Category(Identifier.fromNamespaceAndPath(ElainaBroom.MOD_ID, "categories"));

    public static final String BROOM_SUMMON_TRANSLATABLE = "key.elainabroom.broom_summon_recall";
    public static final String BROOM_DISMOUNT_TRANSLATABLE = "key.elainabroom.broom_dismount";
    public static final String BROOM_UP_TRANSLATABLE = "key.elainabroom.broom_up";
    public static final String BROOM_DOWN_TRANSLATABLE = "key.elainabroom.broom_down";

    public static final KeyMapping BROOM_SUMMON = new KeyMapping(
        BROOM_SUMMON_TRANSLATABLE,
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_B,
        BROOM_CATEGORY
    );

    public static final KeyMapping BROOM_DISMOUNT = new KeyMapping(
        BROOM_DISMOUNT_TRANSLATABLE,
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_LSHIFT,
        BROOM_CATEGORY
    );

    public static final KeyMapping BROOM_UP = new KeyMapping(
        BROOM_UP_TRANSLATABLE,
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_SPACE,
        BROOM_CATEGORY
    );

    public static final KeyMapping BROOM_DOWN = new KeyMapping(
        BROOM_DOWN_TRANSLATABLE,
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_LCONTROL,
        BROOM_CATEGORY
    );

    /**
     * 根据按键触发对应网络包
     */
    public static void customKeyTrigger(InputConstants.Key key) {
        if (BROOM_SUMMON.isActiveAndMatches(key)) {
            ClientPacketDistributor.sendToServer(new SummonOrRecallBroomPacket());
        }
        if (BROOM_DISMOUNT.isActiveAndMatches(key) && Minecraft.getInstance().player.getVehicle() instanceof ElainaBroomEntity) {
            ClientPacketDistributor.sendToServer(new DismountPacket());
        }
    }
}
