package zwp.quickly.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <p>describe：定时器管理
 * <p>    note：
 * <p>  author：zwp on 2017/5/2 mail：1101558280@qq.com web: http://www.zwping.win </p>
 *
 * @deprecated 定时器需要在主线程?中终止，不可封装
 */

@Deprecated
public class TimerUtils {

    private TimerUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 清理定时器
     *
     * @param timer 需要清理的定时器
     */
    public static void cancelTimer(Timer timer) {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

    /**
     * 清理定时任务
     *
     * @param timerTask 需要清理的定时任务
     */
    public static void cancelTimerTask(TimerTask timerTask) {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    /**
     * 清理定时器
     *
     * @param timer     需要清理的定时器
     * @param timerTask 需要清理的定时任务
     */
    public static void cancelTimer(Timer timer, TimerTask timerTask) {
        if (timer != null) {
            cancelTimer(timer);
        }
        if (timerTask != null) {
            cancelTimerTask(timerTask);
        }
    }

}
