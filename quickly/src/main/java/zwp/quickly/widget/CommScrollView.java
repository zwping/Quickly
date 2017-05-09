package zwp.quickly.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 滑动距离可以监听的scrollView
 * <p>
 * author：ping on 2016/12/13 09:53 mail：1101558280@qq.com web: www.zcyke.com
 */

public class CommScrollView extends ScrollView {

    private ScrollViewInterface scrollViewInterface = null;

    public CommScrollView(Context context) {
        super(context);
        init();
    }

    public CommScrollView(Context context, AttributeSet attrs,
                          int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CommScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setFillViewport(true);
        this.setFillViewport(true);
    }

    public void setScrollViewListener(ScrollViewInterface scrollViewListener) {
        this.scrollViewInterface = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewInterface != null) {
            scrollViewInterface.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public interface ScrollViewInterface {
        void onScrollChanged(CommScrollView scrollView, int x, int y, int oldx, int oldy);
    }

}
