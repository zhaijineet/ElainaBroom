package net.zhaiji.elainabroom.datagen;

import net.minecraft.data.PackOutput;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.init.InitItem;

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
    }

    public void Chinese() {
        add("itemGroup.elainabroom.elaina_broom", "伊蕾娜的扫帚");
        addItem(InitItem.ELAINA_BROOM, "伊蕾娜的扫帚");
        add("entity.elainabroom.elaina_broom", "伊蕾娜的扫帚");

        add("tips.elainabroom.need_level", "你需要至少%d级才能乘坐扫帚");

        add(KEY_CATEGORY, "伊蕾娜的扫帚");
        add(KEY_SUMMON, "召唤 / 召回");
        add(KEY_DISMOUNT, "下来");
        add(KEY_UP, "上升");
        add(KEY_DOWN, "下降");

        add("tooltip.elainabroom.broom.ride_and_max_level", "需要至少%s级才能乘坐，%s级达到满速");
        add("tooltip.elainabroom.broom.need_level", "需要至少%s级才能乘坐");
        add("tooltip.elainabroom.broom.max_level", "%s级达到满速");
        add("tooltip.elainabroom.broom.speed", "当前速度约%s格/秒");
        add("tooltip.elainabroom.broom.summon_and_recall", "按%s召唤或回收扫帚，回收范围%s格");
    }

    public void Japanese() {
        add("itemGroup.elainabroom.elaina_broom", "イレイナのほうき");
        addItem(InitItem.ELAINA_BROOM, "イレイナのほうき");
        add("entity.elainabroom.elaina_broom", "イレイナのほうき");

        add("tips.elainabroom.need_level", "ほうきを使うには、最低%dレベルが必要です");

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
