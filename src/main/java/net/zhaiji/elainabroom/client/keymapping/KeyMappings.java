package net.zhaiji.elainabroom.client.keymapping;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;
import net.zhaiji.elainabroom.network.PacketManager;
import net.zhaiji.elainabroom.network.server.packet.DismountPacket;
import net.zhaiji.elainabroom.network.server.packet.SummonOrRecallBroomPacket;

@OnlyIn(Dist.CLIENT)
public class KeyMappings {
    public static final String KEY_CATEGORY_TRANSLATABLE = "key.elainabroom.categories";
    public static final String BROOM_SUMMON_TRANSLATABLE = "key.elainabroom.broom_summon_recall";
    public static final String BROOM_DISMOUNT_TRANSLATABLE = "key.elainabroom.broom_dismount";
    public static final String BROOM_UP_TRANSLATABLE = "key.elainabroom.broom_up";
    public static final String BROOM_DOWN_TRANSLATABLE = "key.elainabroom.broom_down";

    // 召唤或回收扫帚
    public static final KeyMapping BROOM_SUMMON = new KeyMapping(
            BROOM_SUMMON_TRANSLATABLE,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_B,
            KEY_CATEGORY_TRANSLATABLE
    );

    // 下扫帚
    public static final KeyMapping BROOM_DISMOUNT = new KeyMapping(
            BROOM_DISMOUNT_TRANSLATABLE,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_LSHIFT,
            KEY_CATEGORY_TRANSLATABLE
    );

    // 扫帚上升
    public static final KeyMapping BROOM_UP = new KeyMapping(
            BROOM_UP_TRANSLATABLE,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_SPACE,
            KEY_CATEGORY_TRANSLATABLE
    );

    // 扫帚下降
    public static final KeyMapping BROOM_DOWN = new KeyMapping(
            BROOM_DOWN_TRANSLATABLE,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_LCONTROL,
            KEY_CATEGORY_TRANSLATABLE
    );

    /**
     * 根据按键触发对应网络包
     */
    public static void customKeyTrigger(InputConstants.Key key) {
        if (BROOM_SUMMON.isActiveAndMatches(key)) {
            PacketManager.sendToServer(new SummonOrRecallBroomPacket());
        }
        if (BROOM_DISMOUNT.isActiveAndMatches(key) && Minecraft.getInstance().player.getVehicle() instanceof ElainaBroomEntity) {
            PacketManager.sendToServer(new DismountPacket());
        }
    }
}
