package zwp.quickly.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.annotation.StringRes;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;

import zwp.quickly.Quickly;
import zwp.quickly.R;
import zwp.quickly.utils.toolsUtils.LogUtils;

/**
 * <p>describe：View操作
 * <p>    note：
 * <p>  author：zwp on 2017/5/2 mail：1101558280@qq.com web: http://www.zwping.win </p>
 * <table>
 * <tr>
 * <th>
 * 获取string {@link #getStr(int)}
 * </th>
 * </tr>
 *     <tr>
 *         <th>
 *             给editText设置内容(textView) {@link #setText(EditText, Object)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             设置控件的宽高 **注意父控件的控制 {@link #setWidgetSize(View, int, int)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             设置父控件为LinearLayout的view margin {@link #setWidgetMargin(View, int, int, int, int)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             设置父控件为RelativeLayout的view margin {@link #setWidgetMargin2(View, int, int, int, int)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             仿iphone按下效果（按下颜色变淡，抬起颜色恢复） {@link #setWidgetSelectorAlpha(View)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             加载布局 {@link #layoutInflater(Context, int)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             findViewById 泛型 {@link #findViewByid(View, int)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             获取视图尺寸 {@link #}
 *         </th>
 *     </tr>
 * </table>
 */

public class ViewUtils {

    /**
     * 获取string
     *
     * @param resId
     * @return
     */
    public static String getStr(@StringRes int resId) {
        return Quickly.getContext().getString(resId);
    }

    public static String getStr(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static String getStr(Button button) {
        return button.getText().toString().trim();
    }

    public static String getStr(TextView textView) {
        return textView.getText().toString().trim();
    }

    /**
     * 给editText设置内容
     * @param editText
     * @param o
     */
    public static void setText(EditText editText, Object o) {
        editText.setText("" + o + "");
    }
    public static void setText(TextView editText, Object o) {
        editText.setText("" + o + "");
    }

    /**
     * 设置控件的宽高 **注意父控件的控制
     *
     * @param width  不需设置width填0
     * @param height 不需设置height填0
     */
    public static void setWidgetSize(@Nullable View view, int width, int height) {
        if (view == null) return;
        if (width > 0) view.getLayoutParams().width = width;
        if (height > 0) view.getLayoutParams().height = height;
        if (width > 0 || height > 0) view.setLayoutParams(view.getLayoutParams());
    }

    /**
     * 设置父控件为LinearLayout的view margin
     */
    public static void setWidgetMargin(View view, int top, int right, int bottom, int left) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(view.getLayoutParams());
        layoutParams.setMargins(left, top, right, bottom);
        view.setLayoutParams(layoutParams);
    }

    /**
     * 设置父控件为RelativeLayout的view margin
     * */
    public static void setWidgetMargin2(View view, @Px int top, int right, int bottom, int left) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(view.getLayoutParams());
        layoutParams.setMargins(left, top, right, bottom);
        view.setLayoutParams(layoutParams);
    }

    /**
     * 仿iphone按下效果（按下颜色变淡，抬起颜色恢复）
     */
    public static void setWidgetSelectorAlpha(View v) {
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (view.getVisibility() == View.VISIBLE)
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        view.setAlpha(0.6f);
                    else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                        view.setAlpha(1.0f);
                return false;
            }
        });
    }


    /**
     * 加载布局
     */
    public static View layoutInflater(Context context, int layoutID) {
        View view = null;
        view = LayoutInflater.from(context).inflate(layoutID, null);
        return view;
    }

    public static View layoutInflater(Context context, int layoutID, ViewGroup viewGroup) {
        View view = null;
        view = LayoutInflater.from(context).inflate(layoutID, viewGroup, false);
        return view;
    }


    /**
     * findViewById 泛型
     */
    @SuppressWarnings("unchecked")
    public static final <T extends View> T findViewByid(Activity context, int id) {
        try {
            return (T) context.findViewById(id);
        } catch (ClassCastException ex) {
            LogUtils.i("Could not cast View to concrete class." + ex);
            throw ex;
        }
    }

    @SuppressWarnings("unchecked")
    public static final <T extends View> T findViewByid(View context, int id) {
        try {
            return (T) context.findViewById(id);
        } catch (ClassCastException ex) {
            LogUtils.i("Could not cast View to concrete class." + ex);
            throw ex;
        }
    }

    /**
     * Measure view's height
     *
     * @param view
     * @return
     */
    public static int measureViewHeight(View view) {
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }

    /**
     * 在onCreate中获取视图的尺寸
     * <p>需回调onGetSizeListener接口，在onGetSize中获取view宽高</p>
     * <p>用法示例如下所示</p>
     * <pre>
     * SizeUtils.forceGetViewSize(view, new SizeUtils.onGetSizeListener() {
     *     Override
     *     public void onGetSize(View view) {
     *         view.getWidth();
     *     }
     * });
     * </pre>
     *
     * @param view     视图
     * @param listener 监听器
     */
    public static void forceGetViewSize(final View view, final onGetSizeListener listener) {
        view.post(new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onGetSize(view);
                }
            }
        });
    }

    /**
     * 获取到View尺寸的监听
     */
    public interface onGetSizeListener {
        void onGetSize(View view);
    }

    /**
     * 测量视图尺寸
     *
     * @param view 视图
     * @return arr[0]: 视图宽度, arr[1]: 视图高度
     */
    public static int[] measureView(View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
        int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
        int lpHeight = lp.height;
        int heightSpec;
        if (lpHeight > 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(widthSpec, heightSpec);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }

    /**
     * 获取测量视图宽度
     *
     * @param view 视图
     * @return 视图宽度
     */
    public static int getMeasuredWidth(View view) {
        return measureView(view)[0];
    }

    /**
     * 获取测量视图高度
     *
     * @param view 视图
     * @return 视图高度
     */
    public static int getMeasuredHeight(View view) {
        return measureView(view)[1];
    }

}
