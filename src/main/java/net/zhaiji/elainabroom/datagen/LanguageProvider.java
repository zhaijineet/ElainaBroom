package net.zhaiji.elainabroom.datagen;

import net.minecraft.data.PackOutput;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.init.InitItem;
import org.jetbrains.annotations.Nullable;

public class LanguageProvider extends net.neoforged.neoforge.common.data.LanguageProvider {
    public static final String EN_US = "en_us";
    public static final String ZH_CN = "zh_cn";
    public static final String JA_JP = "ja_jp";

    public static final String KEY_CATEGORY = "key.elainabroom.categories";
    public static final String KEY_SUMMON = "key.elainabroom.broom_summon_recall";
    public static final String KEY_DISMOUNT = "key.elainabroom.broom_dismount";
    public static final String KEY_UP = "key.elainabroom.broom_up";
    public static final String KEY_DOWN = "key.elainabroom.broom_down";

    public final String locale;

    public LanguageProvider(PackOutput output, String locale) {
        super(output, ElainaBroom.MOD_ID, locale);
        this.locale = locale;
    }

    private void addConfig(String key, String name, @Nullable String tooltip) {
        add(ElainaBroom.MOD_ID + ".configuration." + key, name);
        if (tooltip != null) {
            add(ElainaBroom.MOD_ID + ".configuration." + key + ".tooltip", tooltip);
        }
    }

    private void addConfig(String key, String name) {
        addConfig(key, name, null);
    }

    public void English() {
        add("itemGroup.elainabroom.elaina_broom", "Elaina's Broom");
        addItem(InitItem.ELAINA_BROOM, "Elaina's Broom");
        add("entity.elainabroom.elaina_broom", "Elaina's Broom");

        add("tips.elainabroom.need_level", "You need at least %d level to ride the broom");
        add("tips.elainabroom.dismount_by_recall", "Use Summon/Recall to dismount");
        add("tips.elainabroom.cannot_dismount", "Unable to dismount");

        add(KEY_CATEGORY, "Elaina's Broom");
        add(KEY_SUMMON, "Summon / Recall");
        add(KEY_DISMOUNT, "Dismount");
        add(KEY_UP, "Up");
        add(KEY_DOWN, "Down");

        add("tooltip.elainabroom.broom.ride_and_max_level", "Requires at least level %s to ride, max speed at level %s");
        add("tooltip.elainabroom.broom.need_level", "Requires at least level %s to ride");
        add("tooltip.elainabroom.broom.max_level", "Reaches max speed at level %s");
        add("tooltip.elainabroom.broom.speed", "Current speed ~%s blocks/s");
        add("tooltip.elainabroom.broom.summon_and_recall", "Press %s to summon or recall, recall range %s blocks");

        add(ElainaBroom.MOD_ID + ".configuration.title", "Elaina's Broom Config");
        addConfig("General", "General", "General settings");
        addConfig("recallDistance", "Recall Distance", "Maximum distance a player can recall the broom");
        addConfig("needLevel", "Required Level", "Minimum experience level required to ride the broom");
        addConfig("maxLevel", "Max Speed Level", "Player level at which maximum flight speed is reached");
        addConfig("unlimitedSpeed", "Unlimited Speed", "When enabled, speed continues to grow beyond max level following a square root curve");
        addConfig("Flight", "Flight", "Flight speed settings");
        addConfig("speed", "Base Speed", "Base flight speed multiplier of the broom");
        addConfig("friction", "Friction", "Flight deceleration factor, lower values slow down faster");
        addConfig("forwardSpeed", "Forward Speed", "Speed when flying forward");
        addConfig("backSpeed", "Backward Speed", "Speed when flying backward");
        addConfig("lateralSpeed", "Lateral Speed", "Speed when moving sideways");
        addConfig("verticalSpeed", "Vertical Speed", "Speed when ascending or descending");
    }

    public void Chinese() {
        add("itemGroup.elainabroom.elaina_broom", "伊蕾娜的扫帚");
        addItem(InitItem.ELAINA_BROOM, "伊蕾娜的扫帚");
        add("entity.elainabroom.elaina_broom", "伊蕾娜的扫帚");

        add("tips.elainabroom.need_level", "你需要至少%d级才能乘坐扫帚");
        add("tips.elainabroom.dismount_by_recall", "召唤/召回可以取消乘坐");
        add("tips.elainabroom.cannot_dismount", "目前无法取消乘坐");

        add(KEY_CATEGORY, "伊蕾娜的扫帚");
        add(KEY_SUMMON, "召唤 / 召回");
        add(KEY_DISMOUNT, "取消乘坐");
        add(KEY_UP, "上升");
        add(KEY_DOWN, "下降");

        add("tooltip.elainabroom.broom.ride_and_max_level", "需要至少%s级才能乘坐，%s级达到满速");
        add("tooltip.elainabroom.broom.need_level", "需要至少%s级才能乘坐");
        add("tooltip.elainabroom.broom.max_level", "%s级达到满速");
        add("tooltip.elainabroom.broom.speed", "当前速度约%s格/秒");
        add("tooltip.elainabroom.broom.summon_and_recall", "按%s召唤或回收扫帚，回收范围%s格");

        add(ElainaBroom.MOD_ID + ".configuration.title", "伊蕾娜的扫帚配置");
        addConfig("General", "通用", "通用设置");
        addConfig("recallDistance", "召回距离", "玩家可召回扫帚的最大距离");
        addConfig("needLevel", "乘坐所需等级", "乘坐扫帚所需的最低经验等级");
        addConfig("maxLevel", "满速等级", "达到最高飞行速度所需的等级");
        addConfig("unlimitedSpeed", "无限速度", "开启后速度在满速等级之后继续增长，遵循平方根曲线逐渐放缓");
        addConfig("Flight", "飞行", "飞行速度设置");
        addConfig("speed", "基础速度", "扫帚的基础飞行速度倍率");
        addConfig("friction", "摩擦力", "飞行减速系数，值越小减速越快");
        addConfig("forwardSpeed", "前进速度", "向前飞行的速度");
        addConfig("backSpeed", "后退速度", "向后飞行的速度");
        addConfig("lateralSpeed", "横向速度", "左右移动的速度");
        addConfig("verticalSpeed", "垂直速度", "升降速度");
    }

    public void Japanese() {
        add("itemGroup.elainabroom.elaina_broom", "イレイナのほうき");
        addItem(InitItem.ELAINA_BROOM, "イレイナのほうき");
        add("entity.elainabroom.elaina_broom", "イレイナのほうき");

        add("tips.elainabroom.need_level", "ほうきを使うには、最低%dレベルが必要です");
        add("tips.elainabroom.dismount_by_recall", "召喚/召回で降りられる");
        add("tips.elainabroom.cannot_dismount", "現在降りることができません");

        add(KEY_CATEGORY, "イレイナのほうき");
        add(KEY_SUMMON, "召喚 / 召回");
        add(KEY_DISMOUNT, "降りる");
        add(KEY_UP, "上昇");
        add(KEY_DOWN, "下降");

        add("tooltip.elainabroom.broom.ride_and_max_level", "乗車には最低%sレベルが必要、%sレベルで最高速に到達");
        add("tooltip.elainabroom.broom.need_level", "乗車には最低%sレベルが必要");
        add("tooltip.elainabroom.broom.max_level", "%sレベルで最高速に到達");
        add("tooltip.elainabroom.broom.speed", "現在の速度約%sブロック/秒");
        add("tooltip.elainabroom.broom.summon_and_recall", "%sキーでほうきを召喚・回収、回収範囲%sブロック");

        add(ElainaBroom.MOD_ID + ".configuration.title", "イレイナのほうき設定");
        addConfig("General", "一般", "一般設定");
        addConfig("recallDistance", "回収距離", "プレイヤーがほうきを回収できる最大距離");
        addConfig("needLevel", "乗車必要レベル", "ほうきに乗るために必要な最低経験レベル");
        addConfig("maxLevel", "最高速レベル", "最高飛行速度に到達するプレイヤーレベル");
        addConfig("unlimitedSpeed", "無制限速度", "有効にすると最高速レベルを超えて速度が成長し、平方根曲線に従い徐々に緩やかになる");
        addConfig("Flight", "飛行", "飛行速度設定");
        addConfig("speed", "基本速度", "ほうきの基本飛行速度倍率");
        addConfig("friction", "摩擦", "飛行減速係数、値が小さいほど早く減速する");
        addConfig("forwardSpeed", "前進速度", "前方に飛行する時の速度");
        addConfig("backSpeed", "後退速度", "後方に飛行する時の速度");
        addConfig("lateralSpeed", "横方向速度", "左右に移動する時の速度");
        addConfig("verticalSpeed", "垂直速度", "上昇・下降する時の速度");
    }

    @Override
    protected void addTranslations() {
        switch (locale) {
            case EN_US -> English();
            case ZH_CN -> Chinese();
            case JA_JP -> Japanese();
        }
    }
}
