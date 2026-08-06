package net.zhaiji.elainabroom.compat;

import net.minecraftforge.fml.ModList;

/**
 * 管理可选模组兼容状态，在类加载时一次性检测各模组是否安装
 */
public final class CompatManager {
    public static final boolean SOPHISTICATED_BACKPACKS_LOADED = ModList.get().isLoaded("sophisticatedbackpacks");

    public static final boolean CURIOS_LOADED = ModList.get().isLoaded("curios");

    public static final boolean CLOTH_CONFIG_LOADED = ModList.get().isLoaded("cloth_config");
}
