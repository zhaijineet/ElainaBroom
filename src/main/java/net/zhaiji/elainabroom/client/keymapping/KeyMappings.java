package net.zhaiji.elainabroom.client.keymapping;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.settings.KeyConflictContext;

@OnlyIn(Dist.CLIENT)
public class KeyMappings {
    public static final String BROOM_CATEGORY = "key.elainabroom.categories";
    public static final String BROOM_SUMMON = "key.elainabroom.broom_summon_recall";
    public static final String BROOM_DISMOUNT = "key.elainabroom.broom_dismount";
    public static final String BROOM_UP = "key.elainabroom.broom_up";
    public static final String BROOM_DOWN = "key.elainabroom.broom_down";

    public static KeyMapping BroomSummonKey = new KeyMapping(
            BROOM_SUMMON,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_B,
            BROOM_CATEGORY
    );

    public static KeyMapping BroomDismountKey = new KeyMapping(
            BROOM_DISMOUNT,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_LSHIFT,
            BROOM_CATEGORY
    );

    public static KeyMapping BroomUpKey = new KeyMapping(
            BROOM_UP,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_SPACE,
            BROOM_CATEGORY
    );

    public static KeyMapping BroomDownKey = new KeyMapping(
            BROOM_DOWN,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_LCONTROL,
            BROOM_CATEGORY
    );
}
