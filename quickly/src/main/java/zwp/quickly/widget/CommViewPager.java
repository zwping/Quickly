package zwp.quickly.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * <p>describe：高度自适应
 * <p>version：V1.0
 * <p>author：zwp on 2017/3/18 mail：1101558280@qq.com web: www.zcyke.com</p>
 */

public class CommViewPager extends ViewPager {

    public CommViewPager(Context context) {
        super(context);
    }

    public CommViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        //下面遍历所有child的高度
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) //采用最大的view的高度。
                height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
