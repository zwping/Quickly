package zwp.quickly.utils.toolsUtils;

import android.content.Context;

import com.orhanobut.logger.Logger;

import zwp.quickly.Quickly;
import zwp.quickly.base.BaseApplication;
import zwp.quickly.utils.AppUtils;

/**
 * <p>describe：logger日志打印，使用第三方logger：https://github.com/orhanobut/logger，第三方打印注意初始化{@link BaseApplication }
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class LogUtils {
    public LogUtils() {
        throw new UnsupportedOperationException("u can't instantiate me..."); //不支持操作的异常
    }

    public static void i(Object msg) {
        if (AppUtils.isAppDebug(Quickly.getContext())) //debug模式才输出log
            Logger.e(msg + "");
    }

}
