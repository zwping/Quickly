package zwp.quickly.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * <p>describe：swipeRefreshLayout重写，添加四种颜色，避免每次调用
 * <p>version：V1.0
 * <p>author：zwp on 2017/2/13 21:21 mail：1101558280@qq.com web: www.zcyke.com</p>
 */

public class CommSwipeRefreshLayout extends SwipeRefreshLayout {

    public CommSwipeRefreshLayout(Context context) {
        super(context);
        init();
    }

    public CommSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }
}
