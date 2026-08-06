package net.zhaiji.elainabroom.datagen;

import net.minecraft.data.PackOutput;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.init.InitItem;

import javax.annotation.Nullable;

public class LanguageProvider extends net.minecraftforge.common.data.LanguageProvider {
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

        addConfig("title", "Elaina's Broom Config");
        addConfig("section.server", "Server Settings");
        addConfig("server_config_not_loaded", "Settings in here are only available while a world is loaded.");
        addConfig("server_config_while_online", "Settings in here are determined by the server and cannot be changed while online.");
        addConfig("server_config_while_lan", "Settings in here cannot be edited while your game is open to LAN. Please return to the main menu and load the world again.");
        addConfig("recallDistance", "Recall Distance", "The maximum distance a player can recall the broom.");
        addConfig("needLevel", "Required Level", "The minimum experience level required to ride the broom.");
        addConfig("maxLevel", "Max Speed Level", "The player level at which max speed is reached.");
        addConfig("unlimitedSpeed", "Unlimited Speed", "When enabled, speed continues to grow beyond maxLevel.");
        addConfig("speed", "Base Flight Speed", "The base flight speed multiplier of the broom.");
        addConfig("friction", "Friction", "The flight deceleration factor. Lower values slow down faster.");
        addConfig("forwardSpeed", "Forward Speed", "The speed when flying forward.");
        addConfig("backSpeed", "Backward Speed", "The speed when flying backward.");
        addConfig("lateralSpeed", "Lateral Speed", "The speed when moving sideways.");
        addConfig("verticalSpeed", "Vertical Speed", "The speed when ascending or descending.");
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

        addConfig("title", "伊蕾娜的扫帚配置");
        addConfig("section.server", "服务器设置");
        addConfig("server_config_not_loaded", "此处的配置仅在加载世界后可用。");
        addConfig("server_config_while_online", "此处的配置由服务器决定，联机时无法更改。");
        addConfig("server_config_while_lan", "游戏开放局域网时无法编辑此处的配置。请返回主菜单并重新加载世界。");
        addConfig("recallDistance", "召回距离", "玩家召回扫帚的最大距离。");
        addConfig("needLevel", "所需等级", "乘坐扫帚所需的最低经验等级。");
        addConfig("maxLevel", "满速等级", "达到最大速度所需的玩家等级。");
        addConfig("unlimitedSpeed", "无限加速", "启用后，速度将持续增长至超过满速等级。");
        addConfig("speed", "基础飞行速度", "扫帚的基础飞行速度倍率。");
        addConfig("friction", "减速系数", "飞行减速系数，数值越低减速越快。");
        addConfig("forwardSpeed", "前进速度", "向前飞行时的速度。");
        addConfig("backSpeed", "后退速度", "向后飞行时的速度。");
        addConfig("lateralSpeed", "侧移速度", "横向移动时的速度。");
        addConfig("verticalSpeed", "垂直速度", "上升或下降时的速度。");
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

        addConfig("title", "イレイナのほうきの設定");
        addConfig("section.server", "サーバー設定");
        addConfig("server_config_not_loaded", "この設定はワールドを読み込んでいる間のみ利用できます。");
        addConfig("server_config_while_online", "この設定はサーバーによって決定されるため、オンライン中は変更できません。");
        addConfig("server_config_while_lan", "ゲームをLANに公開している間は、この設定を編集できません。メインメニューに戻ってワールドをもう一度読み込んでください。");
        addConfig("recallDistance", "回収距離", "プレイヤーがほうきを回収できる最大距離。");
        addConfig("needLevel", "必要レベル", "ほうきに乗るために必要な最低経験レベル。");
        addConfig("maxLevel", "最高速レベル", "最高速度に到達するプレイヤーレベル。");
        addConfig("unlimitedSpeed", "速度無制限", "有効にすると、最高速レベルを超えて速度が上昇し続けます。");
        addConfig("speed", "基礎飛行速度", "ほうきの基礎飛行速度倍率。");
        addConfig("friction", "減速係数", "飛行中の減速係数。値が低いほど速く減速します。");
        addConfig("forwardSpeed", "前進速度", "前方に飛行するときの速度。");
        addConfig("backSpeed", "後退速度", "後方に飛行するときの速度。");
        addConfig("lateralSpeed", "横移動速度", "横に移動するときの速度。");
        addConfig("verticalSpeed", "垂直速度", "上昇または下降するときの速度。");
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

    @Override
    protected void addTranslations() {
        switch (locale) {
            case EN_US -> English();
            case ZH_CN -> Chinese();
            case JA_JP -> Japanese();
        }
    }
}
