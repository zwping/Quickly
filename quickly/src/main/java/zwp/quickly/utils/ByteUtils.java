package zwp.quickly.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>describe：
 * <p>    note：
 * <p>  author：zwp on 2017/4/22 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */

public class ByteUtils {


    private ByteUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 比较两个bytes是否相等
     *
     * @param b1
     * @param b2
     * @return
     */
    public static boolean compereByteArray(byte[] b1, byte[] b2) {
        if (b1.length == 0 || b2.length == 0) {
            return false;
        }
        if (b1.length != b2.length) {
            return false;
        }
        boolean isEqual = true;
        for (int i = 0; i < b1.length && i < b2.length; i++) {
            if (b1[i] != b2[i]) {
                isEqual = false;
                break;
            }
        }
        return isEqual;
    }

    /**
     * 数字转换为固定长度的byte[]
     *
     * @param number
     * @param length
     * @return
     */
    public static byte[] intToBytes(int number, int length) {
        return String.format("%0" + length + "d", number).getBytes();
    }

    /**
     * 读取固定长度的bytes
     *
     * @param bytes
     * @param bytesLength
     * @param startPosition
     * @return
     */
    public static byte[] readGivenLengthBytes(byte[] bytes, int bytesLength, int startPosition) {
        byte[] bytes1 = new byte[bytesLength];
        int endPosition = startPosition + bytesLength;
        int count = 0;
        for (int i = 0; i < bytes.length; i++) {
            if (startPosition <= i && i < endPosition) {
                bytes1[count] = bytes[i];
                count++;
            }
            if (i >= endPosition) break;
        }
//        return ConvertUtils.inputStream2String(ConvertUtils.bytes2InputStream(bytes1), "utf-8");
        return bytes1;
    }

    /**
     * bytes按固定长度切割
     *
     * @param bytes
     * @param splitLength
     * @return
     */
    public static List<byte[]> splitBytes(byte[] bytes, int splitLength) {
        int bytesLength = bytes.length;
        List<byte[]> list = new ArrayList<>();
        if (bytesLength < splitLength) {
            list.add(bytes);
            return list;
        }
        int forTime = bytesLength / splitLength + (bytesLength % splitLength == 0 ? 0 : 1);
        for (int i = 0; i < forTime; i++) {
            list.add(readGivenLengthBytes(bytes, splitLength, i * splitLength));
        }
        return list;
    }

    /**
     * 按顺序拼接两个bytes
     *
     * @param firstBytes
     * @param secondBytes
     * @return
     */
    public static byte[] blendBytes(byte[] firstBytes, byte[] secondBytes) {
        int firstBytesLength = firstBytes.length;
        int secondBytesLength = secondBytes.length;
        if (firstBytesLength == 0 || secondBytesLength == 0)
            return null;
        byte[] bytes = new byte[firstBytesLength + secondBytesLength];
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            if (i < firstBytesLength)
                bytes[i] = firstBytes[i];
            else
                bytes[i] = secondBytes[i - firstBytesLength];
        }
        return bytes;
    }

    /**
     * 将bytes填充至固定长度
     * @param bytes
     * @param length
     * @return
     */
    public static byte[] bytesFillFixLength(byte[] bytes, int length) {
        byte[] bytes1 = new byte[length];
        int bytesLength = bytes.length;
        for (int i = 0; i < length; i++) {
            if (i < bytesLength)
                bytes1[i] = bytes[i];
        }
        return bytes1;
    }
}
