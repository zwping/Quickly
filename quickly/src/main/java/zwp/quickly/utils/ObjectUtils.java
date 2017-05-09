package zwp.quickly.utils;

/**
 * <p>describe：对象操作
 * <p>    note：
 * <p>  author：zwp on 2017/5/2 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */

public class ObjectUtils {

    /**
     * Returns true if a and b are equal.
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(Object a, Object b) {
        return a == b || (a == null ? b == null : a.equals(b));
    }

}
