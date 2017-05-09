package win.zwping.quickly;

import zwp.quickly.Quickly;
import zwp.quickly.base.BaseApplication;
import zwp.quickly.utils.CrashUtils;

/**
 * <p>describe：
 * <p>    note：
 * <p>  author：zwp on 2017/4/8 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        CrashUtils.getInstance().init(this);
    }
}
