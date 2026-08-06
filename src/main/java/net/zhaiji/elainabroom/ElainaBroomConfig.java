package net.zhaiji.elainabroom;

import net.minecraftforge.common.ForgeConfigSpec;

public class ElainaBroomConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.IntValue RECALL_DISTANCE = BUILDER
            .push("General")
            .comment("The maximum distance a player can recall the broom.")
            .defineInRange("recallDistance", 16, 5, 20);

    public static final ForgeConfigSpec.IntValue NEED_LEVEL = BUILDER
            .comment("The minimum experience level required to ride the broom.")
            .defineInRange("needLevel", 10, 0, Integer.MAX_VALUE);

    public static final ForgeConfigSpec.IntValue MAX_LEVEL = BUILDER
            .comment("The player level at which max speed is reached.")
            .comment("Even when unlimitedSpeed is enabled, this value remains the baseline for speed calculation.")
            .defineInRange("maxLevel", 100, 0, Integer.MAX_VALUE);

    public static final ForgeConfigSpec.BooleanValue UNLIMITED_SPEED = BUILDER
            .comment("When enabled, speed continues to grow beyond maxLevel.")
            .comment("Formula beyond cap: speedScale = 1.0 + sqrt((level + 5) / (maxLevel + 5) - 1.0)")
            .comment("Growth follows a square root curve, gradually slowing down.")
            .define("unlimitedSpeed", false);

    public static final ForgeConfigSpec.DoubleValue SPEED = BUILDER
            .pop()
            .push("Flight")
            .comment("The base flight speed multiplier of the broom.")
            .defineInRange("speed", 1.0, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue FRICTION = BUILDER
            .comment("The flight deceleration factor. Lower values slow down faster.")
            .defineInRange("friction", 0.93, 0.01, 0.99);

    public static final ForgeConfigSpec.DoubleValue FORWARD_SPEED = BUILDER
            .comment("The speed when flying forward.")
            .defineInRange("forwardSpeed", 3.0, 0.1, 5.0);

    public static final ForgeConfigSpec.DoubleValue BACK_SPEED = BUILDER
            .comment("The speed when flying backward.")
            .defineInRange("backSpeed", 1.5, 0.1, 5.0);

    public static final ForgeConfigSpec.DoubleValue LATERAL_SPEED = BUILDER
            .comment("The speed when moving sideways.")
            .defineInRange("lateralSpeed", 1.5, 0.1, 5.0);

    public static final ForgeConfigSpec.DoubleValue VERTICAL_SPEED = BUILDER
            .comment("The speed when ascending or descending.")
            .defineInRange("verticalSpeed", 1.8, 0.1, 5.0);

    public static final ForgeConfigSpec SPEC = BUILDER.pop().build();
}
