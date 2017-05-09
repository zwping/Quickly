package zwp.quickly.utils.toolsUtils;

import com.orhanobut.hawk.Hawk;

/**
 * <p>describe：hawk储存工具类
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class HawkUtils {
    public HawkUtils() {
        throw new UnsupportedOperationException("u can't instantiate me..."); //不支持操作的异常
    }

    public static <T> boolean put(String key, T value) {
        return Hawk.put(key, value);
    }

    public static String getString(String key) {
        return Hawk.get(key, null);
    }

    public static int getInt(String key) {
        return Hawk.get(key, null);
    }

    public static Boolean getBoolean(String key) {
        return Hawk.get(key, false);
    }

    public static <T> T get(String key) {
        return Hawk.get(key);
    }

    public static void delete(String key) {
        Hawk.delete(key);
    }

    public static void deleteAll() {
        Hawk.deleteAll();
    }

}
