package zwp.quickly;

import android.content.Context;

import zwp.quickly.utils.ScreenUtils;
import zwp.quickly.utils.ToastUtils;

/**
 * <p>describe：library 初始化
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class Quickly {
    private static Context mContext;
    public static int screenHeight;
    public static int screenWidth;

    public Quickly() {
    }

    public static void init(Context context) {
        Quickly.mContext = context;

        screenHeight = ScreenUtils.getScreenHeight(context);
        screenWidth = ScreenUtils.getScreenWidth(context);
    }

    public static Context getContext() {
        synchronized (Quickly.class) {
            if (Quickly.mContext == null)
                throw new NullPointerException("Call Quickly.init(context) within your Application onCreate() method." +
                        "Or extends BaseApplication");
            return Quickly.mContext.getApplicationContext();
        }
    }


}
