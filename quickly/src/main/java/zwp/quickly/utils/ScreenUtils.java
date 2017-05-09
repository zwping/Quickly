package zwp.quickly.utils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.Window;

import zwp.quickly.Quickly;
import zwp.quickly.utils.toolsUtils.LogUtils;


/**
 * <p>describe：屏幕相关工具
 * <p>    note：dp sp px 相关简介查看 http://blog.csdn.net/ZuoMoHuaEr/article/details/39522171 <br />
 * 屏幕中Bar的相关操作{@link BarUtils}
 * <p>  author：zwp on 2017/5/2 mail：1101558280@qq.com web: http://www.zwping.win </p>
 * <table>
 * <tr>
 * <th>TextureView视频截图{@link ScreenUtils#getTextureViewScreenshot(Activity, TextureView)}</th>
 * </tr>
 * <tr>
 * <th>获取当前屏幕截图(包含状态栏){@link ScreenUtils#screenshotsHasStatusBar(Activity)}</th>
 * </tr>
 * <tr>
 * <th>获取当前屏幕截图(不包含状态栏){@link ScreenUtils#screenshotsNoStatusBar(Activity)}</th>
 * </tr>
 * <tr>
 * <th>
 * 设置屏幕为横屏 {@link #setLandscape(Activity)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 设置屏幕为竖屏 {@link #setPortrait(Activity)}
 * </th>
 * </tr>
 * <tr>
 * <th>判断当前屏幕是否横屏{@link ScreenUtils#isLandscape(Context)}</th>
 * </tr>
 * <tr>
 * <th>判断当前屏幕是否竖屏{@link ScreenUtils#isPortrait(Context)}</th>
 * </tr>
 * <tr>
 * <th>
 * 获取屏幕旋转角度 {@link #getScreenRotation(Activity)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 判断是否锁屏 {@link #isScreenLock()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 设置进入休眠时长{@link #setSleepDuration(int)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 获取进入休眠时长{@link #getSleepDuration()}
 * </th>
 * </tr>
 * <tr>
 * <th>获取屏幕宽度{@link ScreenUtils#getScreenWidth(Context)}</th>
 * </tr>
 * <tr>
 * <th>获取屏幕高度{@link ScreenUtils#getScreenHeight(Context)}</th>
 * </tr>
 * <tr>
 * <th>获取屏幕密度{@link ScreenUtils#getScreenDensity(Context)} </th>
 * </tr>
 * <tr>
 * <th>获取屏幕像素密度{@link ScreenUtils#getScreenDensityDPI(Context)} </th>
 * </tr>
 * <tr>
 * <th>dip 转换为 px(return Integer){@link ScreenUtils#dip2px(Context, float)} </th>
 * </tr>
 * <tr>
 * <th>dip 转换为 px(return Float){@link ScreenUtils#dipToPx(Context, float)} </th>
 * </tr>
 * <tr>
 * <th>px 转换为 dip(return Integer){@link ScreenUtils#px2dip(Context, float)} </th>
 * </tr>
 * <tr>
 * <th>px 转换为 dip(return Float){@link ScreenUtils#pxToDip(Context, float)} </th>
 * </tr>
 * <tr>
 * <th>px 转换为 sp(return Integer){@link ScreenUtils#px2sp(Context, float)} </th>
 * </tr>
 * <tr>
 * <th>px 转换为 sp(return Float){@link ScreenUtils#pxToSp(Context, float)} </th>
 * </tr>
 * <tr>
 * <th>sp 转换为 px(return Integer){@link ScreenUtils#sp2px(Context, float)} </th>
 * </tr>
 * <tr>
 * <th>sp 转换为 px(return Float){@link ScreenUtils#spToPx(Context, float)} </th>
 * </tr>
 * </table>
 */

public class ScreenUtils {


    public ScreenUtils() {
        throw new UnsupportedOperationException("u can't instantiate me..."); //不支持操作的异常
    }

    /**
     * TextureView视频截图(包含视频上方控件)
     *
     * @param textureView 当前播放的textureView
     */
    public static Bitmap getTextureViewScreenshot(Activity activity, TextureView textureView) {
        Bitmap content = textureView.getBitmap();   //获取textureView的截图
        Bitmap layout = screenshotsHasStatusBar(activity); //截屏
        Bitmap screenshot = Bitmap.createBitmap(layout.getWidth(), layout.getHeight(), Bitmap.Config.ARGB_4444);
        // 把两部分拼起来，先把视频截图绘制到上下左右居中的位置，再把播放器的布局元素绘制上去。
        //Canvas canvas = new Canvas(screenshot);
        //canvas.drawBitmap(content, (layout.getWidth() - content.getWidth()) / 2, (layout.getHeight() - content.getHeight()) / 2, new Paint());
        //canvas.drawBitmap(layout, 0, 0, new Paint());
        //canvas.save();
        //canvas.restore();
        return ImageUtils.mergeBitmap(content, layout);
        //return screenshot;
    }

    /**
     * 获取当前屏幕截图(包含状态栏)
     *
     * @return Bitmap
     */
    public static Bitmap screenshotsHasStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        view.destroyDrawingCache();
        return ret;
    }

    /**
     * 获取当前屏幕截图(不包含状态栏)
     *
     * @return Bitmap
     */
    public static Bitmap screenshotsNoStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int statusBarHeight = BarUtils.getStatusBarHeight(activity);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret = Bitmap.createBitmap(bmp, 0, statusBarHeight, dm.widthPixels, dm.heightPixels - statusBarHeight);
        view.destroyDrawingCache();
        return ret;
    }

    /**
     * 设置屏幕为横屏
     * <p>还有一种就是在Activity中加属性android:screenOrientation="landscape"</p>
     * <p>不设置Activity的android:configChanges时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次</p>
     * <p>设置Activity的android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次</p>
     * <p>设置Activity的android:configChanges="orientation|keyboardHidden|screenSize"（4.0以上必须带最后一个参数）时
     * 切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法</p>
     *
     * @param activity activity
     * @deprecated 建议直接修改配置清单
     */
    @Deprecated
    public static void setLandscape(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 设置屏幕为竖屏
     *
     * @param activity activity
     * @deprecated 建议直接修改配置清单
     */
    @Deprecated
    public static void setPortrait(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 判断当前屏幕是否横屏
     *
     * @return boolean
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断当前屏幕是否竖屏
     *
     * @return boolean
     */
    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 获取屏幕旋转角度
     *
     * @param activity activity
     * @return 屏幕旋转角度
     */
    public static int getScreenRotation(Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            default:
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
    }

    /**
     * 判断是否锁屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isScreenLock() {
        KeyguardManager km = (KeyguardManager) Quickly.getContext().getSystemService(Context.KEYGUARD_SERVICE);
        return km.inKeyguardRestrictedInputMode();
    }

    /**
     * 设置进入休眠时长
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     *
     * @param duration 时长
     */
    public static void setSleepDuration(int duration) {
        Settings.System.putInt(Quickly.getContext().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, duration);
    }

    /**
     * 获取进入休眠时长
     *
     * @return 进入休眠时长，报错返回-123
     */
    public static int getSleepDuration() {
        try {
            return Settings.System.getInt(Quickly.getContext().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -123;
        }
    }

    /**
     * Get screen width, in pixels
     *
     * @return widthPixels
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * Get screen height, in pixels
     *
     * @return heightPixels
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * Get screen density（密度）, the logical density of the display
     * <P> note:density值表示每英寸有多少个显示点，与分辨率是两个概念。
     *
     * @return density
     */
    public static float getScreenDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.density;
    }

    /**
     * Get screen density dpi（像素密度）, the screen density expressed as dots-per-inch
     * <P> note:屏幕像素密度 一般计算公式为，由屏幕长宽的分辨率，根据勾股定律求出对角线的像素数，再除以屏幕的尺寸，即可得densityDpi.
     *
     * @return densityDpi
     */
    public static int getScreenDensityDPI(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.densityDpi;
    }

    /**
     * Convert dp to px by the density of phone
     * <p> translation：通过手机密度将dip 转换 为px
     *
     * @param dp
     * @return Integer
     */
    public static int dip2px(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return (int) (dipToPx(context, dp) + 0.5f);
    }

    /**
     * Convert dp to px
     *
     * @param dp
     * @return Float
     */
    private static float dipToPx(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale;
    }

    /**
     * Convert px to dp by the density of phone
     *
     * @param px
     * @return Integer
     */
    public static int px2dip(Context context, float px) {
        if (context == null) {
            return -1;
        }
        return (int) (pxToDip(context, px) + 0.5f);
    }

    /**
     * Convert px to dp
     *
     * @param px
     * @return Float
     */
    private static float pxToDip(Context context, float px) {
        if (context == null) {
            return -1;
        }
        float scale = context.getResources().getDisplayMetrics().density;
        return px / scale;
    }

    /**
     * Convert px to sp
     *
     * @param px
     * @return Integer
     */
    public static int px2sp(Context context, float px) {
        return (int) (pxToSp(context, px) + 0.5f);
    }

    /**
     * Convert px to sp
     *
     * @param px
     * @return Float
     */
    private static float pxToSp(Context context, float px) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return px / fontScale;
    }

    /**
     * Convert sp to px
     *
     * @param sp
     * @return Integer
     */
    public static int sp2px(Context context, float sp) {
        return (int) (spToPx(context, sp) + 0.5f);
    }

    /**
     * Convert sp to px
     *
     * @param sp
     * @return Float
     */
    private static float spToPx(Context context, float sp) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * fontScale;
    }

    /**
     * 打印屏幕显示信息
     */
    public static DisplayMetrics printDisplayInfo(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        StringBuilder sb = new StringBuilder();
        sb.append("_______  显示信息:  ");
        sb.append("\ndensity         :").append(dm.density); //密度
        sb.append("\ndensityDpi      :").append(dm.densityDpi); //像素密度
        sb.append("\nheightPixels    :").append(dm.heightPixels); //屏幕高
        sb.append("\nwidthPixels     :").append(dm.widthPixels); //屏幕宽
        sb.append("\nscaledDensity   :").append(dm.scaledDensity); //缩放密度
        sb.append("\nxdpi            :").append(dm.xdpi); //分辨率 宽
        sb.append("\nydpi            :").append(dm.ydpi); //分辨率 高
        LogUtils.i(sb.toString());
        return dm;
    }

}
