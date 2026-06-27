package net.zhaiji.elainabroom;

import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ElainaBroomConfig {
    public static int recallDistance;
    public static int needLevel;
    public static int maxLevel;
    public static double speed;
    public static double friction;
    public static double forwardSpeed;
    public static double backSpeed;
    public static double lateralSpeed;
    public static double verticalSpeed;
    public static boolean unlimitedSpeed;

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder()
            .comment("config")
            .push("Config");

    private static final ModConfigSpec.IntValue RECALL_DISTANCE = BUILDER
            .comment("player can recall broom distance")
            .defineInRange(
                    "recallDistance",
                    16,
                    5,
                    20
            );

    private static final ModConfigSpec.IntValue NEED_LEVEL_VALUE = BUILDER
            .comment("player must be at least this level to ride.")
            .defineInRange(
                    "needLevel",
                    10,
                    0,
                    Integer.MAX_VALUE
            );

    private static final ModConfigSpec.IntValue MAX_LEVEL_VALUE = BUILDER
            .comment("The player level at which max speed is reached.")
            .comment("Even when unlimitedSpeed is enabled, this value remains the baseline for speed calculation.")
            .defineInRange(
                    "maxLevel",
                    100,
                    0,
                    Integer.MAX_VALUE
            );

    private static final ModConfigSpec.DoubleValue SPEED_VALUE = BUILDER
            .comment("speed")
            .defineInRange(
                    "speed",
                    1.0,
                    0.0,
                    10.0
            );

    private static final ModConfigSpec.DoubleValue FRICTION_VALUE = BUILDER
            .comment("friction")
            .defineInRange(
                    "friction",
                    0.93,
                    0.01,
                    0.99
            );

    private static final ModConfigSpec.DoubleValue FORWARD_SPEED = BUILDER
            .comment("forwardSpeed")
            .defineInRange(
                    "forwardSpeed",
                    3.0,
                    0.1,
                    5.0
            );

    private static final ModConfigSpec.DoubleValue BACK_SPEED = BUILDER
            .comment("backSpeed")
            .defineInRange(
                    "backSpeed",
                    1.5,
                    0.1,
                    5.0
            );

    private static final ModConfigSpec.DoubleValue LATERAL_SPEED = BUILDER
            .comment("lateralSpeed")
            .defineInRange(
                    "lateralSpeed",
                    1.5,
                    0.1,
                    5.0
            );

    private static final ModConfigSpec.DoubleValue VERTICAL_SPEED = BUILDER
            .comment("verticalSpeed")
            .defineInRange(
                    "verticalSpeed",
                    1.8,
                    0.1,
                    5.0
            );

    private static final ModConfigSpec.BooleanValue UNLIMITED_SPEED = BUILDER
            .comment("When enabled, speed continues to grow beyond maxLevel.")
            .comment("Formula beyond cap: speedScale = 1.0 + sqrt((level + 5) / (maxLevel + 5) - 1.0)")
            .comment("Growth follows a square root curve, gradually slowing down.")
            .define("unlimitedSpeed", false);

    public static final ModConfigSpec SPEC = BUILDER.build();

    /**
     * 配置加载或重载时同步静态字段
     */
    public static void handlerModConfigEvent(ModConfigEvent event) {
        if (event.getConfig().getSpec() == SPEC) {
            recallDistance = RECALL_DISTANCE.get();
            maxLevel = MAX_LEVEL_VALUE.get();
            needLevel = NEED_LEVEL_VALUE.get();
            speed = SPEED_VALUE.get();
            friction = FRICTION_VALUE.get();
            forwardSpeed = FORWARD_SPEED.get();
            backSpeed = BACK_SPEED.get();
            lateralSpeed = LATERAL_SPEED.get();
            verticalSpeed = VERTICAL_SPEED.get();
            unlimitedSpeed = UNLIMITED_SPEED.get();
        }
    }
}
