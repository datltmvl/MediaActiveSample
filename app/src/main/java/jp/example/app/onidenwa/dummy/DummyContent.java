package jp.example.app.onidenwa.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.example.app.onidenwa.R;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        if(position%3 == 0) {
            return new DummyItem(String.valueOf(position), "寝ないとき", "02x00-7595-7257",
                    R.raw.jp_namahage_red_iukoto_20170523, R.drawable.namahage_red_iukoto_20170523,
                    R.drawable.bg_btn_large_namahage_201701_20170525);
        }
        return new DummyItem(String.valueOf(position), "言うことを書かない時", "02x00-7595-7257",
                R.raw.oni_iukoto_jp_old_talking_20170524, R.drawable.oni_iukoto_calling_20170523,
                R.drawable.bg_btn_large_oni_20170525);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final int content;
        public final String tel;
        public final String title;
        public final int avatar;
        public final int background;

        public DummyItem(String id, String title, String tel, int contentResid, int avatarResid,
                         int background) {
            this.id = id;
            this.content = contentResid;
            this.title = title;
            this.avatar = avatarResid;
            this.tel = tel;
            this.background = background;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
