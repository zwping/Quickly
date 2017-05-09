package zwp.quickly.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * <p>describe：转换相关工具类
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 * <table>
 * <tr>
 * <th>以unit为单位的内存大小转字节数{@link ConvertUtils#memorySize2Byte(long, FileUtils.MemoryUnit)}</th>
 * </tr>
 * <tr>
 * <th>字节数转以unit为单位的内存大小{@link ConvertUtils#byte2MemorySize(long, FileUtils.MemoryUnit)}</th>
 * </tr>
 * <tr>
 * <th>字节数转合适内存大小{@link ConvertUtils#byte2FitMemorySize(long)}</th>
 * </tr>
 * <tr>
 * <th>以unit为单位的时间长度转毫秒时间戳{@link ConvertUtils#timeSpan2Millis(long, TimeUtils.TimeUnit)}</th>
 * </tr>
 * <tr>
 * <th>毫秒时间戳转以unit为单位的时间长度{@link ConvertUtils#millis2TimeSpan(long, TimeUtils.TimeUnit)}</th>
 * </tr>
 * <tr>
 * <th>毫秒时间戳转合适时间长度{@link ConvertUtils#millis2FitTimeSpan(long, int)}</th>
 * </tr>
 * <tr>
 * <th>Byte 2 bit{@link ConvertUtils#bytes2Bits(byte[])}</th>
 * </tr>
 * <tr>
 * <th>bit 2 Byte{@link ConvertUtils#bits2Bytes(String)}</th>
 * </tr>
 * <tr>
 * <th>inputStream转outputStream{@link ConvertUtils#input2OutputStream(InputStream)}</th>
 * </tr>
 * <tr>
 * <th>outputStream转inputStream{@link ConvertUtils#output2InputStream(OutputStream)}</th>
 * </tr>
 * <tr>
 * <th>inputStream转byteArr{@link ConvertUtils#inputStream2Bytes(InputStream)}</th>
 * </tr>
 * <tr>
 * <th>byteArr转inputStream{@link ConvertUtils#bytes2InputStream(byte[])}</th>
 * </tr>
 * <tr>
 * <th>outputStream转byteArr{@link ConvertUtils#outputStream2Bytes(OutputStream)}</th>
 * </tr>
 * <tr>
 * <th>byteArr转outputStream{@link ConvertUtils#bytes2OutputStream(byte[])}</th>
 * </tr>
 * <tr>
 * <th>inputStream转string（按编码）{@link ConvertUtils#inputStream2String(InputStream, String)}</th>
 * </tr>
 * <tr>
 * <th>string转inputStream（按编码）{@link ConvertUtils#string2InputStream(String, String)}</th>
 * </tr>
 * <tr>
 * <th>outputStream转string（按编码）{@link ConvertUtils#outputStream2String(OutputStream, String)}</th>
 * </tr>
 * <tr>
 * <th>string转outputStream（按编码）{@link ConvertUtils#string2OutputStream(String, String)}</th>
 * </tr>
 * <tr>
 * <th>bitmap转byteArr{@link ConvertUtils#bitmap2Bytes(Bitmap, Bitmap.CompressFormat)}</th>
 * </tr>
 * <tr>
 * <th>byteArr转bitmap{@link ConvertUtils#bytes2Bitmap(byte[])}</th>
 * </tr>
 * <tr>
 * <th>drawable转bitmap{@link ConvertUtils#drawable2Bitmap(Drawable)}</th>
 * </tr>
 * <tr>
 * <th>bitmap转drawable{@link ConvertUtils#bitmap2Drawable(Resources, Bitmap)}</th>
 * </tr>
 * <tr>
 * <th>drawable转byteArr{@link ConvertUtils#drawable2Bytes(Drawable, Bitmap.CompressFormat)}</th>
 * </tr>
 * <tr>
 * <th>byteArr转drawable{@link ConvertUtils#bytes2Drawable(Resources, byte[])}</th>
 * </tr>
 * <tr>
 * <th>view转Bitmap{@link ConvertUtils#view2Bitmap(View)}</th>
 * </tr>
 * <tr>
 * <th>dp2px px2dp dp2sp sp2dp px2sp sp2px{@link ConvertUtils#sp2px(Context, float)}</th>
 * </tr>
 * <tr>
 * <th>字节数组转换为16进制大写字符串{@link ConvertUtils#bytes2HexString(byte[])}</th>
 * </tr>
 * <tr>
 * <th>十六进制字符串转换为字节数组{@link ConvertUtils#hexString2Bytes(String)}</th>
 * </tr>
 * <tr>
 * <th>charArr转byteArr{@link ConvertUtils#chars2Bytes(char[])}</th>
 * </tr>
 * <tr>
 * <th>byteArr转charArr{@link ConvertUtils#bytes2Chars(byte[])}</th>
 * </tr>
 * </table>
 */

public class ConvertUtils {

    private ConvertUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

      /**
     * 以unit为单位的内存大小转字节数
     *
     * @param memorySize 大小
     * @param unit       单位类型
     *                   <ul>
     *                   <li>{@link FileUtils.MemoryUnit#BYTE}: 字节</li>
     *                   <li>{@link FileUtils.MemoryUnit#KB}  : 千字节</li>
     *                   <li>{@link FileUtils.MemoryUnit#MB}  : 兆</li>
     *                   <li>{@link FileUtils.MemoryUnit#GB}  : GB</li>
     *                   </ul>
     * @return 字节数
     */
    public static long memorySize2Byte(long memorySize, FileUtils.MemoryUnit unit) {
        if (memorySize < 0) return -1;
        switch (unit) {
            default:
            case BYTE:
                return memorySize;
            case KB:
                return memorySize * FileUtils.KB;
            case MB:
                return memorySize * FileUtils.MB;
            case GB:
                return memorySize * FileUtils.GB;
        }
    }

    /**
     * 字节数转以unit为单位的内存大小
     *
     * @param byteNum 字节数
     * @param unit    单位类型
     *                <ul>
     *                <li>{@link FileUtils.MemoryUnit#BYTE}: 字节</li>
     *                <li>{@link FileUtils.MemoryUnit#KB}  : 千字节</li>
     *                <li>{@link FileUtils.MemoryUnit#MB}  : 兆</li>
     *                <li>{@link FileUtils.MemoryUnit#GB}  : GB</li>
     *                </ul>
     * @return 以unit为单位的size
     */
    public static double byte2MemorySize(long byteNum, FileUtils.MemoryUnit unit) {
        if (byteNum < 0) return -1;
        switch (unit) {
            default:
            case BYTE:
                return (double) byteNum;
            case KB:
                return (double) byteNum / FileUtils.KB;
            case MB:
                return (double) byteNum / FileUtils.MB;
            case GB:
                return (double) byteNum / FileUtils.GB;
        }
    }

    /**
     * 字节数转合适内存大小
     * <p>保留2位小数</p>
     *
     * @param byteNum 字节数
     * @return 合适内存大小
     */
    @SuppressLint("DefaultLocale")
    public static String byte2FitMemorySize(long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < FileUtils.KB) {
            return String.format("%.2fB", (float) byteNum + 0.0005);
        } else if (byteNum < FileUtils.MB) {
            return String.format("%.2fKB", (float) byteNum / FileUtils.KB + 0.0005);
        } else if (byteNum < FileUtils.GB) {
            return String.format("%.2fMB", (float) byteNum / FileUtils.MB + 0.0005);
        } else {
            return String.format("%.2fGB", (float) byteNum / FileUtils.GB + 0.0005);
        }
    }

    /**
     * 以unit为单位的时间长度转毫秒时间戳
     *
     * @param timeSpan 毫秒时间戳
     * @param unit     单位类型
     *                 <ul>
     *                 <li>{@link TimeUtils.TimeUnit#MSEC}: 毫秒</li>
     *                 <li>{@link TimeUtils.TimeUnit#SEC }: 秒</li>
     *                 <li>{@link TimeUtils.TimeUnit#MIN }: 分</li>
     *                 <li>{@link TimeUtils.TimeUnit#HOUR}: 小时</li>
     *                 <li>{@link TimeUtils.TimeUnit#DAY }: 天</li>
     *                 </ul>
     * @return 毫秒时间戳
     */
    public static long timeSpan2Millis(long timeSpan, TimeUtils.TimeUnit unit) {
        switch (unit) {
            default:
            case MSEC:
                return timeSpan;
            case SEC:
                return timeSpan * TimeUtils.SEC;
            case MIN:
                return timeSpan * TimeUtils.MIN;
            case HOUR:
                return timeSpan * TimeUtils.HOUR;
            case DAY:
                return timeSpan * TimeUtils.DAY;
        }
    }

    /**
     * 毫秒时间戳转以unit为单位的时间长度
     *
     * @param millis 毫秒时间戳
     * @param unit   单位类型
     *               <ul>
     *               <li>{@link TimeUtils.TimeUnit#MSEC}: 毫秒</li>
     *               <li>{@link TimeUtils.TimeUnit#SEC }: 秒</li>
     *               <li>{@link TimeUtils.TimeUnit#MIN }: 分</li>
     *               <li>{@link TimeUtils.TimeUnit#HOUR}: 小时</li>
     *               <li>{@link TimeUtils.TimeUnit#DAY }: 天</li>
     *               </ul>
     * @return 以unit为单位的时间长度
     */
    public static long millis2TimeSpan(long millis, TimeUtils.TimeUnit unit) {
        switch (unit) {
            default:
            case MSEC:
                return millis;
            case SEC:
                return millis / TimeUtils.SEC;
            case MIN:
                return millis / TimeUtils.MIN;
            case HOUR:
                return millis / TimeUtils.HOUR;
            case DAY:
                return millis / TimeUtils.DAY;
        }
    }

    /**
     * 毫秒时间戳转合适时间长度
     *
     * @param millis    毫秒时间戳
     *                  <p>小于等于0，返回null</p>
     * @param precision 精度
     *                  <ul>
     *                  <li>precision = 0，返回null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return 合适时间长度
     */
    @SuppressLint("DefaultLocale")
    public static String millis2FitTimeSpan(long millis, int precision) {
        if (millis <= 0 || precision <= 0) return null;
        StringBuilder sb = new StringBuilder();
        String[] units = {"天", "小时", "分钟", "秒", "毫秒"};
        int[] unitLen = {86400000, 3600000, 60000, 1000, 1};
        precision = Math.min(precision, 5);
        for (int i = 0; i < precision; i++) {
            if (millis >= unitLen[i]) {
                long mode = millis / unitLen[i];
                millis -= mode * unitLen[i];
                sb.append(mode).append(units[i]);
            }
        }
        return sb.toString();
    }

    /**
     * bytes转bits
     * Byte：字节，计算机文件大小的基本运算单位(B)
     * bit：比特，计算机运算基础，二进制范畴（b）
     * 1B = 8b
     *
     * @param bytes 字节数组
     * @return bits
     */
    public static String bytes2Bits(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            for (int j = 7; j >= 0; --j) {
                sb.append(((aByte >> j) & 0x01) == 0 ? '0' : '1');
            }
        }
        return sb.toString();
    }

    /**
     * bits转bytes
     * Byte：字节，计算机文件大小的基本运算单位(B)
     * bit：比特，计算机运算基础，二进制范畴（b）
     * 1B = 8b
     *
     * @param bits 二进制
     * @return bytes
     */
    public static byte[] bits2Bytes(String bits) {
        int lenMod = bits.length() % 8;
        int byteLen = bits.length() / 8;
        // 不是8的倍数前面补0
        if (lenMod != 0) {
            for (int i = lenMod; i < 8; i++) {
                bits = "0" + bits;
            }
            byteLen++;
        }
        byte[] bytes = new byte[byteLen];
        for (int i = 0; i < byteLen; ++i) {
            for (int j = 0; j < 8; ++j) {
                bytes[i] <<= 1;
                bytes[i] |= bits.charAt(i * 8 + j) - '0';
            }
        }
        return bytes;
    }

    /**
     * inputStream转outputStream
     *
     * @param is 输入流
     * @return outputStream子类
     */
    public static ByteArrayOutputStream input2OutputStream(InputStream is) {
        if (is == null) return null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[FileUtils.KB];
            int len;
            while ((len = is.read(b, 0, FileUtils.KB)) != -1) {
                os.write(b, 0, len);
            }
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.close(is);
        }
    }

    /**
     * outputStream转inputStream
     *
     * @param out 输出流
     * @return inputStream子类
     */
    public ByteArrayInputStream output2InputStream(OutputStream out) {
        if (out == null) return null;
        return new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
    }

    /**
     * inputStream转byteArr
     *
     * @param is 输入流
     * @return 字节数组
     */
    public static byte[] inputStream2Bytes(InputStream is) {
        if (is == null) return null;
        return input2OutputStream(is).toByteArray();
    }

    /**
     * byteArr转inputStream
     *
     * @param bytes 字节数组
     * @return 输入流
     */
    public static InputStream bytes2InputStream(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) return null;
        return new ByteArrayInputStream(bytes);
    }

    /**
     * outputStream转byteArr
     *
     * @param out 输出流
     * @return 字节数组
     */
    public static byte[] outputStream2Bytes(OutputStream out) {
        if (out == null) return null;
        return ((ByteArrayOutputStream) out).toByteArray();
    }

    /**
     * byteArr转outputStream
     *
     * @param bytes 字节数组
     * @return 字节数组
     */
    public static OutputStream bytes2OutputStream(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) return null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            os.write(bytes);
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.close(os);
        }
    }

    /**
     * inputStream转string（按编码）
     *
     * @param is          输入流
     * @param charsetName 编码格式
     * @return 字符串
     */
    public static String inputStream2String(InputStream is, String charsetName) {
        if (is == null || StringUtils.isEmpty(charsetName)) return null;
        try {
            return new String(inputStream2Bytes(is), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * string转inputStream（按编码）
     *
     * @param string      字符串
     * @param charsetName 编码格式
     * @return 输入流
     */
    public static InputStream string2InputStream(String string, String charsetName) {
        if (string == null || StringUtils.isEmpty(charsetName)) return null;
        try {
            return new ByteArrayInputStream(string.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * outputStream转string（按编码）
     *
     * @param out         输出流
     * @param charsetName 编码格式
     * @return 字符串
     */
    public static String outputStream2String(OutputStream out, String charsetName) {
        if (out == null || StringUtils.isEmpty(charsetName)) return null;
        try {
            return new String(outputStream2Bytes(out), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * string转outputStream（按编码）
     *
     * @param string      字符串
     * @param charsetName 编码格式
     * @return 输入流
     */
    public static OutputStream string2OutputStream(String string, String charsetName) {
        if (string == null || StringUtils.isEmpty(charsetName)) return null;
        try {
            return bytes2OutputStream(string.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * bitmap转byteArr
     *
     * @param bitmap bitmap对象
     * @param format 格式
     * @return 字节数组
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, Bitmap.CompressFormat format) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return baos.toByteArray();
    }

    /**
     * byteArr转bitmap
     *
     * @param bytes 字节数组
     * @return bitmap
     */
    public static Bitmap bytes2Bitmap(byte[] bytes) {
        return (bytes == null || bytes.length == 0) ? null : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * drawable转bitmap
     *
     * @param drawable drawable对象
     * @return bitmap
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        return drawable == null ? null : ((BitmapDrawable) drawable).getBitmap();
    }

    /**
     * bitmap转drawable
     *
     * @param res    resources对象
     * @param bitmap bitmap对象
     * @return drawable
     */
    public static Drawable bitmap2Drawable(Resources res, Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(res, bitmap);
    }

    /**
     * drawable转byteArr
     *
     * @param drawable drawable对象
     * @param format   格式
     * @return 字节数组
     */
    public static byte[] drawable2Bytes(Drawable drawable, Bitmap.CompressFormat format) {
        return drawable == null ? null : bitmap2Bytes(drawable2Bitmap(drawable), format);
    }

    /**
     * byteArr转drawable
     *
     * @param res   resources对象
     * @param bytes 字节数组
     * @return drawable
     */
    public static Drawable bytes2Drawable(Resources res, byte[] bytes) {
        return res == null ? null : bitmap2Drawable(res, bytes2Bitmap(bytes));
    }

    /**
     * view转Bitmap
     *
     * @param view 视图
     * @return bitmap
     */
    public static Bitmap view2Bitmap(View view) {
        if (view == null) return null;
        Bitmap ret = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return ret;
    }

    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        return ScreenUtils.dip2px(context, dpValue);
    }

    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(Context context, float pxValue) {
        return ScreenUtils.px2dip(context, pxValue);
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(Context context, float spValue) {
        return ScreenUtils.sp2px(context, spValue);
    }

    /**
     * px转sp
     *
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(Context context, float pxValue) {
        return ScreenUtils.px2sp(context, pxValue);
    }

    /**
     * ============================= 以下是16进制 / 2进制专场 ==========================================
     */
    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /*
    等待填坑：
    hex：16进制
    字节数组转换为16进制大写字符串
    二进制移位运算符概念：
    左移运算符 x<<y eg：2<<2 --> 0000 0010 --> 0000 1000 --> 2<<2 = 8
    右移运算符 x>>y eg：4>>2 --> 0000 0100 --> 0000 0001 --> 4>>2 = 1
    >>> 无符号右移，忽略符号位，空位都以0补齐 （只是对32位和64位的值有意义）
    0x0f 0x开始的值为16进制数值
     */

    /**
     * byteArr转hexString
     * <p>例如：</p>
     * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
     *
     * @param bytes 字节数组
     * @return 16进制大写字符串
     */
    public static String bytes2HexString(byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * hexString转byteArr
     * <p>例如：</p>
     * hexString2Bytes("00A8") returns { 0, (byte) 0xA8 }
     *
     * @param hexString 十六进制字符串
     * @return 字节数组
     */
    public static byte[] hexString2Bytes(String hexString) {
        if (StringUtils.isEmpty(hexString)) return null;
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    /**
     * hexChar转int
     *
     * @param hexChar hex单个字节
     * @return 0..15
     */
    private static int hex2Dec(char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * charArr转byteArr
     *
     * @param chars 字符数组
     * @return 字节数组
     */
    public static byte[] chars2Bytes(char[] chars) {
        if (chars == null || chars.length <= 0) return null;
        int len = chars.length;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) (chars[i]);
        }
        return bytes;
    }

    /**
     * byteArr转charArr
     *
     * @param bytes 字节数组
     * @return 字符数组
     */
    public static char[] bytes2Chars(byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = (char) (bytes[i] & 0xff);
        }
        return chars;
    }
}
