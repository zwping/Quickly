package zwp.quickly.utils;

import java.util.Random;

/**
 * <p>describe：获取随机数
 * <p>    note：
 * <p>  author：zwp on 2017/5/2 mail：1101558280@qq.com web: http://www.zwping.win </p>
 * <table>
 * <tr>
 * <th>
 * 获取int的随机值(int 范围: -2147483648～2147483647 ) {@link #randomInt()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 获取0~n的取值范围 {@link #randomInt(int)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 获取指定范围的随机值 {@link #randomInt(int, int)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 返回0.0~1.0的随机float值 {@link #randomFloat()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 返回0.0~1.0的随机double值 {@link #randomDouble()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 返回随机long值(-9223372036854775808~9223372036854775807) {@link #randomLong()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 返回随机boolean值 {@link #randomBoolean()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 获取一个字符串中固定长度的随机值 {@link #randomString(char[], int)}
 * </th>
 * </tr>
 * </table>
 */

public class RandomUtils {

    /**
     * 获取int的随机值(int 范围: -2147483648～2147483647 )
     *
     * @return
     */
    public static int randomInt() {
        Random random = new Random();
        return random.nextInt();
    }

    /**
     * 获取0~n的取值范围
     *
     * @param n
     * @return
     */
    public static int randomInt(int n) {
        Random random = new Random();
        return random.nextInt(n);
    }

    /**
     * 获取指定范围的随机值
     *
     * @param min
     * @param max
     * @return
     */
    public static int randomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 返回0.0~1.0的随机float值
     *
     * @return
     */
    public static float randomFloat() {
        Random random = new Random();
        return random.nextFloat();
    }

    /**
     * 返回0.0~1.0的随机double值
     *
     * @return
     */
    public static double randomDouble() {
        Random random = new Random();
        return random.nextDouble();
    }

    /**
     * 返回随机long值(-9223372036854775808~9223372036854775807)
     *
     * @return
     */
    public static long randomLong() {
        Random random = new Random();
        return random.nextLong();
    }

    /**
     * 返回随机boolean值
     *
     * @return
     */
    public static boolean randomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }

    /**
     * 获取一个字符串中固定长度的随机值
     *
     * @param source
     * @param length
     * @return
     */
    public static String randomString(String source, int length) {
        return StringUtils.isEmpty(source) ? null : randomString(source.toCharArray(), length);
    }

    /**
     * 获取一个字符串中固定长度的随机值
     *
     * @param sourceChar
     * @param length
     * @return
     */
    public static String randomString(char[] sourceChar, int length) {
        if (sourceChar == null || sourceChar.length == 0 || length < 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(sourceChar[randomInt(sourceChar.length)]);
        }
        return builder.toString();
    }
}
