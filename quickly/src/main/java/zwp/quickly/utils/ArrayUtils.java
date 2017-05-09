package zwp.quickly.utils;

/**
 * <p>describe：数组工具类
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class ArrayUtils {

    private static final String DELIMITER = ",";

    /**
     * Judge whether a array is null.
     *
     * @param array
     * @return
     */
    public static <T> boolean isEmpty(T[] array) {
        return (array == null || array.length == 0);
    }

    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }

    /**
     * 遍历数组，输出{1,2,3,4,5...}
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> String traverseArray(T[] array) {
        if (!isEmpty(array)) {
            int len = array.length;
            StringBuilder builder = new StringBuilder(len);
            int i = 0;
            for (T t : array) {
                if (t == null) {
                    continue;
                }
                builder.append(t.toString());
                i++;
                if (i < len) {
                    builder.append(DELIMITER);
                }
            }
            return builder.toString();
        }
        return null;
    }

    /**
     * byte[]拼接
     * @param head
     * @param body
     * @return
     */
    public static byte[] byteMosaic(byte[] head, byte[] body) {
        if (head == null || head.length == 0 || body == null || body.length == 0)
            return null;

        byte[] bytes = new byte[head.length + body.length];
        for (int i = 0; i < bytes.length; i++) {
            if (i < head.length) {
                bytes[i] = head[i];
            } else {
                bytes[i] = body[i - head.length];
            }
        }
        return bytes;
    }

}
