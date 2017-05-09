package zwp.quickly.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.reflect.Method;

/**
 * <p>describe：状态栏 导航栏 <s>标题栏</s> 相关操作
 * <p>    note：沉浸式指的是布局展示在状态栏 或 导航栏，默认情况不适用沉浸式，如需要使用5.0沉浸式建议使用{@link BarUtils#setTranslucentBar(Activity, Boolean, Boolean)} <br />
 * 沉浸式4.4以上版本才有，5.0与4.4有差异，应当正确使用 <br />
 * <p>author：zwp on 2017/2/17 15:12 mail：1101558280@qq.com web: http://www.zwping.win</p>
 * <table>
 * <tr>
 * <th>设置替代状态栏排挤布局的view{@link BarUtils#replaceStatusBarHeight(Activity, View)}</th>
 * </tr>
 * <tr>
 * <th>替换导航栏排挤布局的View{@link BarUtils#replaceNavigationBarHeight(Context, View)}</th>
 * </tr>
 * <tr>
 * <th>设置状态栏沉浸{@link BarUtils#setTranslucentStatusBar(Activity)}</th>
 * </tr>
 * <tr>
 * <th>设置状态栏+ 导航栏沉浸{@link BarUtils#setTranslucentStatusBar2NavigationBar(Activity)}</th>
 * </tr>
 * <tr>
 * <th>设置状态栏颜色{@link BarUtils#setStatusBarColor(Activity, int)}</th>
 * </tr>
 * <tr>
 * <th>设置状态栏颜色，及颜色透明度{@link BarUtils#setStatusBarColor(Activity, int, int)}</th>
 * </tr>
 * <tr>
 * <th>获取状态栏高度{@link BarUtils#getStatusBarHeight(Activity)}</th>
 * </tr>
 * <tr>
 * <th>设置状态栏纯色,不加半透明效果{@link BarUtils#setColorNoTranslucent(Activity, int)}</th>
 * </tr>
 * <tr>
 * <th>设置状态栏全透明{@link BarUtils#setStatusBarTransparent(Activity)}</th>
 * </tr>
 * <tr>
 * <th>隐藏状态栏{@link BarUtils#hideStatusBar(Activity)}</th>
 * </tr>
 * <tr>
 * <th>判断状态栏是否存在{@link BarUtils#isStatusBarExists(Activity)}</th>
 * </tr>
 * <tr>
 * <th>获取是否存在NavigationBar{@link BarUtils#isHaveNavigationBar(Context)}</th>
 * </tr>
 * <tr>
 * <th>获取导航栏高度{@link BarUtils#getNavigationBarHeigh(Context)}</th>
 * </tr>
 * <tr>
 * <th>获取ActionBar高度{@link BarUtils#getActionBarHeight(Activity)}</th>
 * </tr>
 * <tr>
 * <th>显示通知栏(需要权限){@link BarUtils#showNotificationBar(Context, boolean)}</th>
 * </tr>
 * <tr>
 * <th>隐藏通知栏(需要权限){@link BarUtils#hideNotificationBar(Context)}</th>
 * </tr>
 * </table>
 */
public class BarUtils {

    public BarUtils() {
        throw new UnsupportedOperationException("u can't instantiate me..."); //不支持操作的异常
    }

    public static class StatusBarView extends View {
        public StatusBarView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public StatusBarView(Context context) {
            super(context);
        }
    }

    public static final int DEFAULT_STATUS_BAR_ALPHA = 112;


    /**
     * 设置替代状态栏排挤布局的view
     * <P> 目前的思路为在顶部设置一个与系统状态栏等同高度的View，如果sdk>=21则让其顶替statusBar的高度，排挤下面的布局
     *
     * @param view 替代statusBar的view
     */
    public static void replaceStatusBarHeight(Activity activity, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setVisibility(View.VISIBLE);
            ViewUtils.setWidgetSize(view, 0, getStatusBarHeight(activity));
        } else {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 替换导航栏排挤布局的View
     *
     * @param view
     */
    public static void replaceNavigationBarHeight(Context context, View view) {
        if (isHaveNavigationBar(context)) {
            view.setVisibility(View.VISIBLE);
            ViewUtils.setWidgetSize(view, 0, getNavigationBarHeigh(context));
        } else view.setVisibility(View.GONE);
    }


    /**
     * 设置“沉浸式”包含状态栏和导航栏 沉浸
     * <P>sdk>=19才有“沉浸式”功能，在这为了兼容性更简单明了，建议使用该方法（>=21）
     *
     * @param activity
     * @param statusBar     设置状态栏 沉浸
     * @param navigationBar 设置导航栏+ 状态栏 沉浸
     */
    private static void setTranslucentBar(Activity activity, Boolean statusBar, Boolean navigationBar) {
        if (Build.VERSION.SDK_INT >= 21) {
            if (!statusBar && !navigationBar) return;
            View decorView = activity.getWindow().getDecorView();
            int option = 0;
            if (statusBar) //设置状态栏 沉浸
                option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            if (navigationBar) { //设置导航栏+状态栏 沉浸
                option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
            }
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            decorView.setSystemUiVisibility(option);
        }
    }

    /**
     * 设置状态栏沉浸
     */
    public static void setTranslucentStatusBar(Activity activity) {
        setTranslucentBar(activity, true, false);
    }

    /**
     * 设置状态栏+ 导航栏沉浸
     */
    public static void setTranslucentStatusBar2NavigationBar(Activity activity) {
        setTranslucentBar(activity, true, true);
    }

//    /**
//     * 设置沉浸式状态栏
//     *
//     * @param activity
//     * @param on
//     * @deprecated 处理兼容性BUG过于复杂，主要问题（如果只需要实现状态栏沉浸，导航栏不沉浸怎么实现？？？）
//     */
//    @Deprecated
//    @TargetApi(19)
//    public static void setTranslucentStatusBar(Activity activity, boolean on) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window win = activity.getWindow();
//            WindowManager.LayoutParams winParams = win.getAttributes();
//            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//            if (on) {
//                winParams.flags |= bits;
//            } else {
//                winParams.flags &= ~bits;
//            }
//            win.setAttributes(winParams);
//        }
//    }

//    /**
//     * 设置根布局参数
//     * 处理兼容性BUG过于复杂，主要问题（如果只需要实现状态栏沉浸，导航栏不沉浸怎么实现？？？）
//     */
//    @Deprecated
//    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    public static void setRootView(Activity activity, Boolean isFitsSystemWindows) {
//        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
//        rootView.setFitsSystemWindows(isFitsSystemWindows);
//        rootView.setClipToPadding(isFitsSystemWindows);
//    }


    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     */
    public static void setStatusBarColor(Activity activity, int color) {
        setStatusBarColor(activity, color, DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     * 设置状态栏颜色，其颜色透明度
     *
     * @param activity       需要设置的activity
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */

    public static void setStatusBarColor(Activity activity, int color, int statusBarAlpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(calculateStatusColor(color, statusBarAlpha));
        }
//        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //关键是这部分导致放弃维护19的状态栏颜色
//            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
//            int count = decorView.getChildCount();
//            if (count > 0 && decorView.getChildAt(count - 1) instanceof StatusBarView) {
//                decorView.getChildAt(count - 1).setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
//            } else {
//                StatusBarView statusView = createStatusBarView(activity, color, statusBarAlpha);
//                decorView.addView(statusView);
//            }
//        }
    }

    /**
     * 通过窗口对象获取状态栏高度（有局限性）
     *
     * @param activity
     * @return
     */
    @Deprecated
    public static int getStatusBarHeight2(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    /**
     * 通过资源ID获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = -1;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 通过反射获取状态栏高度
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        int statusBarHeight = getStatusBarHeight2(activity);
        if (0 == statusBarHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int id = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusBarHeight = activity.getResources().getDimensionPixelSize(id);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | IllegalArgumentException | SecurityException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }


    /**
     * 设置状态栏纯色,不加半透明效果
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     */
    public static void setColorNoTranslucent(Activity activity, int color) {
        setStatusBarColor(activity, color, 0);
    }


    /**
     * 使状态栏透明
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setStatusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /**
     * 生成一个和状态栏大小相同的半透明矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @param alpha    透明值
     * @return 状态栏矩形条
     */
    private static StatusBarView createStatusBarView(Activity activity, int color, int alpha) {
        // 绘制一个和状态栏一样高的矩形
        StatusBarView statusBarView = new StatusBarView(activity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(calculateStatusColor(color, alpha));
        return statusBarView;
    }

    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private static int calculateStatusColor(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }


    /**
     * 隐藏状态栏
     * <p>也就是设置全屏，一定要在setContentView之前调用，否则报错</p>
     * <p>此方法Activity可以继承AppCompatActivity</p>
     * <p>启动的时候状态栏会显示一下再隐藏，比如QQ的欢迎界面</p>
     * <p>在配置文件中Activity加属性android:theme="@android:style/Theme.NoTitleBar.Fullscreen"</p>
     * <p>如加了以上配置Activity不能继承AppCompatActivity，会报错</p>
     *
     * @param activity activity
     */
    public static void hideStatusBar(Activity activity) {
        //activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 判断状态栏是否存在
     *
     * @param activity activity
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isStatusBarExists(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        return (params.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    /**
     * 获取是否存在NavigationBar（虚拟按键）
     *
     * @param context
     * @return
     */
    public static boolean isHaveNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;
    }

    /**
     * 获取导航栏高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeigh(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    /**
     * 获取ActionBar高度
     *
     * @param activity activity
     * @return ActionBar高度
     */
    public static int getActionBarHeight(Activity activity) {
        TypedValue tv = new TypedValue();
        if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
        }
        return 0;
    }

    /**
     * 显示通知栏
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>}</p>
     *
     * @param context        上下文
     * @param isSettingPanel {@code true}: 打开设置<br>{@code false}: 打开通知
     */
    public static void showNotificationBar(Context context, boolean isSettingPanel) {
        String methodName = (Build.VERSION.SDK_INT <= 16) ? "expand"
                : (isSettingPanel ? "expandSettingsPanel" : "expandNotificationsPanel");
        invokePanels(context, methodName);
    }

    /**
     * 隐藏通知栏
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>}</p>
     *
     * @param context 上下文
     */
    public static void hideNotificationBar(Context context) {
        String methodName = (Build.VERSION.SDK_INT <= 16) ? "collapse" : "collapsePanels";
        invokePanels(context, methodName);
    }

    /**
     * 反射唤醒通知栏
     *
     * @param context    上下文
     * @param methodName 方法名
     */
    private static void invokePanels(Context context, String methodName) {
        try {
            Object service = context.getSystemService("statusbar");
            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
            Method expand = statusBarManager.getMethod(methodName);
            expand.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
