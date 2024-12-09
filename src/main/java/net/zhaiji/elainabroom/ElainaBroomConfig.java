package net.zhaiji.elainabroom;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = ElainaBroom.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ElainaBroomConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder()
            .comment("config")
            .push("Config");

    private static final ModConfigSpec.DoubleValue SPEED_VALUE = BUILDER
            .comment("speed")
            .defineInRange(
                    "Speed",
                    1.0,
                    0.0,
                    10.0
            );

    private static final ModConfigSpec.DoubleValue FRICTION_VALUE = BUILDER
            .comment("friction")
            .defineInRange(
                    "friction",
                    0.95,
                    0.01,
                    0.99
            );

    private static final ModConfigSpec.IntValue MAX_LEVEL_VALUE = BUILDER
            .comment("player level correlates with speed")
            .comment("higher level, higher speed.")
            .defineInRange(
                    "max_level",
                    30,
                    0,
                    Integer.MAX_VALUE
            );

    private static final ModConfigSpec.IntValue NEED_LEVEL_VALUE = BUILDER
            .comment("player must be at least this level to ride.")
            .defineInRange(
                    "need_level",
                    10,
                    0,
                    Integer.MAX_VALUE
            );

    private static final ModConfigSpec.DoubleValue FORWARD_SPEED = BUILDER
            .comment("forwardSpeed")
            .defineInRange(
                    "forwardSpeed",
                    2.0,
                    0.1,
                    5.0
            );

    private static final ModConfigSpec.DoubleValue BACK_SPEED = BUILDER
            .comment("backSpeed")
            .defineInRange(
                    "backSpeed",
                    1.0,
                    0.1,
                    5.0
            );

    private static final ModConfigSpec.DoubleValue LATERAL_SPEED = BUILDER
            .comment("lateralSpeed")
            .defineInRange(
                    "lateralSpeed",
                    1.0,
                    0.1,
                    5.0
            );

    private static final ModConfigSpec.DoubleValue VERTICAL_SPEED = BUILDER
            .comment("verticalSpeed")
            .defineInRange(
                    "verticalSpeed",
                    1.2,
                    0.1,
                    5.0
            );

    public static final ModConfigSpec SPEC = BUILDER.pop().build();

    public static double Speed;
    public static double friction;
    public static int max_level;
    public static int need_level;
    public static double forwardSpeed;
    public static double backSpeed;
    public static double lateralSpeed;
    public static double verticalSpeed;

    @SubscribeEvent
    static void onLoad(ModConfigEvent event) {
        Speed = SPEED_VALUE.get();
        friction = FRICTION_VALUE.get();
        max_level = MAX_LEVEL_VALUE.get();
        need_level = NEED_LEVEL_VALUE.get();
        forwardSpeed = FORWARD_SPEED.get();
        backSpeed = BACK_SPEED.get();
        lateralSpeed = LATERAL_SPEED.get();
        verticalSpeed = VERTICAL_SPEED.get();
    }
}
