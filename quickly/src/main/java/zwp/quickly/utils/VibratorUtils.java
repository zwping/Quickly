package zwp.quickly.utils;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/**
 * <p>describe：震动
 * <p>    note：<uses-permission android:name="android.permission.VIBRATE" />
 * <p>  author：zwp on 2017/5/2 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */

public class VibratorUtils {

    private static final int VIBRATE_TIME = 1000;
    private static final long[] PATTERN = new long[]{10, 1000, 10, 1000};

    private static Vibrator vibrator = null;

    private static Vibrator getInstance(Context context) {
        if (vibrator == null) {
            vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        }
        return vibrator;
    }

    /**
     * 震动1s
     *
     * @param context
     */
    public static void vibrate(Context context) {
        vibrate(context, VIBRATE_TIME);
    }

    /**
     * 震动指定时间
     *
     * @param context
     * @param milliseconds
     */
    public static void vibrate(Context context, long milliseconds) {
        Vibrator vibrator = getInstance(context);
        vibrator.vibrate(milliseconds);
    }

    /**
     * 指定模式震动
     *
     * @param context
     * @param pattern 模式
     * @param repeat 重复
     */
    public static void vibrate(Context context, long[] pattern, int repeat) {
        Vibrator vibrator = getInstance(context);
        vibrator.vibrate(pattern, repeat);
    }

    /**
     * 取消震动
     *
     * @param context
     */
    public static void cancel(Context context) {
        Vibrator vibrator = getInstance(context);
        vibrator.cancel();
    }

}
