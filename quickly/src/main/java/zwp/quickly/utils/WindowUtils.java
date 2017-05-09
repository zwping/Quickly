package zwp.quickly.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * <p>describe：Window操作
 * <p>    note：
 * <p>  author：zwp on 2017/5/2 mail：1101558280@qq.com web: http://www.zwping.win </p>
 *
 * @deprecated 等待填坑
 */

@Deprecated
public class WindowUtils {

    private static WindowManager mWindowManager;

    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    private static WindowManager.LayoutParams createDefaultLayoutParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();

        int type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            type = WindowManager.LayoutParams.TYPE_TOAST;
        }
        params.type = type;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.format = PixelFormat.TRANSLUCENT;

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        params.gravity = Gravity.CENTER;
        return params;
    }

    /**
     * 使用指定的布局参数添加子视图。
     *
     * @param context
     * @param view
     */
    public static void addView(Context context, View view) {
        addView(context, view, createDefaultLayoutParams());
    }

    /**
     * 使用指定的布局参数添加子视图
     *
     * @param context
     * @param view
     * @param params
     */
    public static void addView(Context context, View view, WindowManager.LayoutParams params) {
        if (context == null || view == null) {
            throw new IllegalArgumentException("argument cannot be null.");
        }
        mWindowManager = getWindowManager(context);
        mWindowManager.addView(view, params);
    }

    /**
     * 在布局期间移除视图
     *
     * @param context
     * @param view
     */
    public static void removeView(Context context, View view) {
        if (context == null || view == null) {
            throw new IllegalArgumentException("argument cannot be null.");
        }
        mWindowManager = getWindowManager(context);
        mWindowManager.removeView(view);
    }

    /**
     * 通过参数更新视图
     *
     * @param context
     * @param view
     * @param params
     */
    public static void updateView(Context context, View view, WindowManager.LayoutParams params) {
        if (context == null || view == null || params == null) {
            throw new IllegalArgumentException("argument cannot be null.");
        }
        mWindowManager = getWindowManager(context);
        mWindowManager.updateViewLayout(view, params);
    }
}
