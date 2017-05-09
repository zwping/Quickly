package zwp.quickly.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * <p>describe：数字格式化
 * <p>    note：
 * <p>  author：zwp on 2017/5/2 mail：1101558280@qq.com web: http://www.zwping.win </p>
 * <table>
 *     <tr>
 *         <th>
 *             保留一位小数 {@link #formatOneDecimal(float)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             保留两位小数 {@link #formatTwoDecimal(float)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             保留两位小数百分比 {@link #formatTwoDecimalPercent(float)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             四舍五入 {@link #roundingNumber(float)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             String 2 int(Integer) {@link #convertToint(String, int)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             string 2 long(Long) {@link #convertTolong(String, long)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             string 2 float(Float) {@link #convertTofloat(String, float)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             string 2 double(Double) {@link #convertToDouble(String)}
 *         </th>
 *     </tr>
 * </table>
 */

public class NumberUtils {
    /**
     * 保留一位小数
     *
     * @param number
     * @return
     */
    public static String formatOneDecimal(float number) {
        DecimalFormat oneDec = new DecimalFormat("##0.0");
        return oneDec.format(number);
    }

    /**
     * 保留两位小数
     *
     * @param number
     * @return
     */
    public static String formatTwoDecimal(float number) {
        DecimalFormat twoDec = new DecimalFormat("##0.00");
        return twoDec.format(number);
    }

    /**
     * 保留两位小数百分比
     *
     * @param number
     * @return
     */
    public static String formatTwoDecimalPercent(float number) {
        return formatTwoDecimal(number) + "%";
    }


    /**
     * 四舍五入
     *
     * @param number
     * @return
     */
    public static double roundingNumber(float number) {
        return roundingNumber(number, 0, RoundingMode.HALF_UP);
    }
    /**
     * 四舍五入
     *
     * @param number
     * @param scale  scale of the result returned.
     * @return
     */
    public static double roundingNumber(float number, int scale) {
        return roundingNumber(number, scale, RoundingMode.HALF_UP);
    }

    /**
     * 四舍五入
     *
     * @param number
     * @param scale        scale of the result returned.
     * @param roundingMode rounding mode to be used to round the result.
     * @return
     */
    public static double roundingNumber(float number, int scale, RoundingMode roundingMode) {
        BigDecimal b = new BigDecimal(number);
        return b.setScale(scale, roundingMode).doubleValue();
    }

    public static int convertToint(String intStr, int defValue) {
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return defValue;
    }

    public static long convertTolong(String longStr, long defValue) {
        try {
            return Long.parseLong(longStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return defValue;
    }

    public static float convertTofloat(String fStr, float defValue) {
        try {
            return Float.parseFloat(fStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return defValue;
    }

    public static double convertTodouble(String dStr, double defValue) {
        try {
            return Double.parseDouble(dStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return defValue;
    }


    public static Integer convertToInteger(String intStr) {
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public static Long convertToLong(String longStr) {
        try {
            return Long.parseLong(longStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public static Float convertToFloat(String fStr) {
        try {
            return Float.parseFloat(fStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public static Double convertToDouble(String dStr) {
        try {
            return Double.parseDouble(dStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return null;
    }


}
