package zwp.quickly.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * <p>describe：Handler
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class HandlerUtils {

    private HandlerUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**所有的handler操作结果均放置主线程中处理(Looper.getMainLooper())*/
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    /**
     * Run on ui thread
     *
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        HANDLER.post(runnable);
    }

    /**
     * Run on ui thread delay
     *
     * @param runnable
     * @param delayMillis
     */
    public static void runOnUiThreadDelay(Runnable runnable, long delayMillis) {
        HANDLER.postDelayed(runnable, delayMillis);
    }

    /**
     * Remove runnable
     *
     * @param runnable
     */
    public static void removeRunable(Runnable runnable) {
        HANDLER.removeCallbacks(runnable);
    }

}
