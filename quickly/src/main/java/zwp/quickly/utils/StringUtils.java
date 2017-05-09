package zwp.quickly.utils;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Locale;

import zwp.quickly.Quickly;
import zwp.quickly.base.BaseApplication;

/**
 * <p>describe：字符串操作
 * <p>    note：
 * <p>  author：zwp on 2017/5/2 mail：1101558280@qq.com web: http://www.zwping.win </p>
 * <table>
 * <tr>
 * <th>
 * 根据某字符分割某字符串(注意key转译\\) {@link #split(String, String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 取某字符之前的字符串(注意key转译\\) {@link #splitFront(String, String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 取某字符之后的字符串(注意key转译\\) {@link #splitBack(String, String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 隐藏11的手机号码中间4位 {@link #hidePhone(String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 身份证号，中间10位星号替换 {@link #hideId(String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 只显示@前面的首位和末位 {@link #hideEmail(String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 银行卡号，保留最后4位，其他星号替换 {@link #hideCardId(String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 隐藏名字关键词，只保留最后一位 {@link #hideName(String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 判断字符串为""或null {@link #isEmpty(String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 判断字符串是否为null或全为空格 {@link #isTrimEmpty(String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 判断字符串是否为null或全为空白字符 {@link #isSpace(String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 判断两字符串是否相等 {@link #equals(CharSequence, CharSequence)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 判断两字符串忽略大小写是否相等 {@link #equalsIgnoreCase(String, String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 判断字符串是否为数字 {@link #isNumeric(String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 对字符串进行编码(UTF-8) {@link #encodeString(String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 对字符串进行解码(UTF-8) {@link #decodeString(String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 返回字符串长度 {@link #length(CharSequence)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 首字母大写 {@link #upperFirstLetter(String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 首字母小写 {@link #lowerFirstLetter(String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 反转字符串 {@link #reverse(String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 转化为半角字符 {@link #toDBC(String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 转化为全角字符 {@link #toSBC(String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 全部转换为小写 {@link #toLowerCase(String)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 全部转换为大写 {@link #toUpperCase(String)}
 * </th>
 * </tr>
 * </table>
 */

public class StringUtils {

    //半角：\u0020
    public static final String HALF_SPACE = "\u0020";
    //全角：\u3000
    public static final String SPACE = "\u3000";

    /**
     * 根据某字符分割某字符串(注意key转译\\)
     */
    public static String[] split(String str, String key) {
        String[] data = null;
        if (str.contains(key))
            data = str.split(key);
        return data;
    }

    /**
     * 取某字符之前的字符串(注意key转译\\)
     */
    public static String splitFront(String str, String key) {
        String data = null;
        if (str.contains(key))
            data = str.substring(0, str.indexOf(key));
        return data;
    }

    /**
     * 取某字符之后的字符串(注意key转译\\)
     */
    public static String splitBack(String str, String key) {
        String data = null;
        if (str.contains(key))
            data = str.split(key)[str.split(key).length - 1];
        return data;
    }

    /**
     * 隐藏11的手机号码中间4位
     */
    public static String hidePhone(String phoneNum) {
        if (StringUtils.isEmpty(phoneNum)) return null;
        // 括号表示组，被替换的部分$n表示第n组的内容
        // 正则表达式中，替换字符串，括号的意思是分组，在replace()方法中，
        // 参数二中可以使用$n(n为数字)来依次引用模式串中用括号定义的字串。
        // "(\d{3})\d{4}(\d{4})", "$1****$2"的这个意思就是用括号，
        // 分为(前3个数字)中间4个数字(最后4个数字)替换为(第一组数值，保持不变$1)(中间为*)(第二组数值，保持不变$2)
        return phoneNum.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"); //$1，$2分别匹配第一个括号和第二个括号中的内容
    }

    /**
     * 身份证号，中间10位星号替换
     *
     * @param id 身份证号
     * @return 星号替换的身份证号
     */
    public static String hideId(String id) {
        return id.replaceAll("(\\d{4})\\d{10}(\\d{4})", "$1** **** ****$2");
    }

    /**
     * 只显示@前面的首位和末位
     */
    public static String hideEmail(String email) {
        if (StringUtils.isEmpty(email)) return null;
        return email.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4");
    }

    /**
     * 银行卡号，保留最后4位，其他星号替换
     *
     * @param cardId 卡号
     * @return 星号替换的银行卡号
     */
    public static String hideCardId(String cardId) {
        return cardId.replaceAll("\\d{15}(\\d{3})", "**** **** **** **** $1");
    }

    /**
     * 隐藏名字关键词，只保留最后一位
     */
    public static String hideName(@NonNull String name) {
        if (StringUtils.isEmpty(name)) return null;
        return "**" + name.substring((name.length() - 1));
    }

    /**
     * 判断字符串为""或null
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0 || str.equalsIgnoreCase("null")) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isTrimEmpty(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断字符串是否为null或全为空白字符
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空白字符<br> {@code false}: 不为null且不全空白字符
     */
    public static boolean isSpace(String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        return a == null ? b == null : a.equalsIgnoreCase(b);
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 对字符串进行编码(UTF-8)
     *
     * @param str
     * @return
     */
    public static String encodeString(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }

    /**
     * 对字符串进行解码(UTF-8)
     *
     * @param str
     * @return
     */
    public static String decodeString(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(String s) {
        int len = length(s);
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 全部转换为小写
     *
     * @param s
     * @return
     */
    public static String toLowerCase(String s) {
        return s.toLowerCase(Locale.getDefault());
    }

    /**
     * 全部转换为大写
     *
     * @param s
     * @return
     */
    public static String toUpperCase(String s) {
        return s.toUpperCase(Locale.getDefault());
    }

}
