package zwp.quickly.utils;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <p>describe：SD卡相关工具类
 * <p>    note：
 * <p>  author：zwp on 2017/5/2 mail：1101558280@qq.com web: http://www.zwping.win </p>
 * <table>
 * <tr>
 * <th>判断SD卡是否可用 {@link #isSDCardEnable()}</th>
 * </tr>
 * <tr>
 * <th>获取SD卡路径 {@link #getSDCardPath()}</th>
 * </tr>
 * <tr>
 * <th>获取SD卡data路径 {@link #getDataPath()}</th>
 * </tr>
 * <tr>
 * <th>获取SD卡剩余空间 {@link #getFreeSpace()}</th>
 * </tr>
 * <tr>
 * <th>获取SD卡信息 {@link #getSDCardInfo()}</th>
 * </tr>
 * <tr>
 * <th>判断外部内存是否已满 {@link #isExternalMemoryFull()}</th>
 * </tr>
 * <tr>
 * <th>获取可用内存大小 {@link #getAvailableInternalMemorySize()}</th>
 * </tr>
 * <tr>
 * <th>获取可用的外部内存大小 {@link #getAvailableExternalMemorySize()}</th>
 * </tr>
 * <tr>
 * <th>获取内存大小 {@link #getTotalInternalMemorySize()}</th>
 * </tr>
 * <tr>
 * <th>获取外部内存大小 {@link #getTotalExternalMemorySize()}</th>
 * </tr>
 * <tr>
 * <th>得到的总内存 {@link #getTotalMemory(Context)}</th>
 * </tr>
 * <tr>
 * <th>得到的可用临时内存 {@link #getAvailableMemory(Context)}</th>
 * </tr>
 * <tr>
 * <th>获取临时内存大小 {@link #getAllMemory(Context)}</th>
 * </tr>
 * <tr>
 * <th>获取内存所有信息 {@link #getMemoryInfo(Context)}</th>
 * </tr>
 * </table>
 */

public class SDCardUtils {

    private SDCardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取SD卡路径
     * <p>先用shell，shell失败再普通方法获取，一般是/storage/emulated/0/</p>
     *
     * @return SD卡路径
     */
    public static String getSDCardPath() {
        if (!isSDCardEnable()) return null;
        String cmd = "cat/proc/mounts";
        Runtime run = Runtime.getRuntime();
        BufferedReader bufferedReader = null;
        try {
            Process p = run.exec(cmd);
            bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(p.getInputStream())));
            String lineStr;
            while ((lineStr = bufferedReader.readLine()) != null) {
                if (lineStr.contains("sdcard") && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray.length >= 5) {
                        return strArray[1].replace("/.android_secure", "") + File.separator;
                    }
                }
                if (p.waitFor() != 0 && p.exitValue() == 1) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(bufferedReader);
        }
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    /**
     * 获取SD卡data路径
     *
     * @return SD卡data路径
     */
    public static String getDataPath() {
        if (!isSDCardEnable()) return null;
        return Environment.getExternalStorageDirectory().getPath() + File.separator + "data" + File.separator;
    }

    /**
     * 获取SD卡剩余空间
     *
     * @return SD卡剩余空间
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getFreeSpace() {
        if (!isSDCardEnable()) return null;
        StatFs stat = new StatFs(getSDCardPath());
        long blockSize, availableBlocks;
        availableBlocks = stat.getAvailableBlocksLong();
        blockSize = stat.getBlockSizeLong();
        return ConvertUtils.byte2FitMemorySize(availableBlocks * blockSize);
    }

    /**
     * 获取SD卡信息
     *
     * @return SDCardInfo
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getSDCardInfo() {
        if (!isSDCardEnable()) return null;
        SDCardInfo sd = new SDCardInfo();
        sd.isExist = true;
        StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
        sd.totalBlocks = sf.getBlockCountLong();
        sd.blockByteSize = sf.getBlockSizeLong();
        sd.availableBlocks = sf.getAvailableBlocksLong();
        sd.availableBytes = sf.getAvailableBytes();
        sd.freeBlocks = sf.getFreeBlocksLong();
        sd.freeBytes = sf.getFreeBytes();
        sd.totalBytes = sf.getTotalBytes();
        return sd.toString();
    }

    public static class SDCardInfo {
        boolean isExist;
        long totalBlocks;
        long freeBlocks;
        long availableBlocks;
        long blockByteSize;
        long totalBytes;
        long freeBytes;
        long availableBytes;

        @Override
        public String toString() {
            return "isExist=" + isExist +
                    "\ntotalBlocks=" + totalBlocks +
                    "\nfreeBlocks=" + freeBlocks +
                    "\navailableBlocks=" + availableBlocks +
                    "\nblockByteSize=" + blockByteSize +
                    "\ntotalBytes=" + totalBytes +
                    "\nfreeBytes=" + freeBytes +
                    "\navailableBytes=" + availableBytes;
        }
    }

    /**
     * 判断外部内存是否已满
     *
     * @return
     */
    public static boolean isExternalMemoryFull() {
        return getAvailableExternalMemorySize() - (50 * 1024 * 1024) < 0;  /*50 * 1024 * 1024 = 50MB*/
    }

    /**
     * 获取可用内存大小
     *
     * @return
     */
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 获取可用的外部内存大小
     *
     * @return
     */
    public static long getAvailableExternalMemorySize() {
        if (isSDCardEnable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return -1;
        }
    }

    /**
     * 获取内存大小
     *
     * @return
     */
    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    /**
     * 获取外部内存大小
     *
     * @return
     */
    public static long getTotalExternalMemorySize() {
        if (isSDCardEnable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return -1;
        }
    }


    /**
     * 得到的总内存
     *
     * @param context
     * @return
     * @deprecated 取到的值为负数
     */
    @Deprecated
    public static String getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;
            localBufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }

    /**
     * 得到的可用临时内存
     *
     * @param context
     * @return
     */
    public static String getAvailableMemory(Context context) {// 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        // mi.availMem; 当前系统的可用内存
        return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
    }

    /**
     * 获取内存临时大小
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static String getAllMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return Formatter.formatFileSize(context, mi.totalMem);
    }

    /**
     * 获取内存所有信息
     *
     * @param context
     */
    public static void getMemoryInfo(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("判断外部内存是否已满：" + isExternalMemoryFull() + "\n");
        stringBuilder.append("获取可用内存大小：" + ConvertUtils.byte2FitMemorySize(getAvailableInternalMemorySize()) + "\n");
        stringBuilder.append("获取内存大小：" + ConvertUtils.byte2FitMemorySize(getTotalInternalMemorySize()) + "\n");
        stringBuilder.append("获取可用的外部内存大小：" + ConvertUtils.byte2FitMemorySize(getAvailableExternalMemorySize()) + "\n");
        stringBuilder.append("获取外部内存大小：" + ConvertUtils.byte2FitMemorySize(getTotalExternalMemorySize()) + "\n");
        stringBuilder.append("得到的总内存：" + getTotalMemory(context) + "\n");
        stringBuilder.append("得到的可用内存：" + getAvailableMemory(context) + "\n");
        stringBuilder.append("获取内存大小：" + getAllMemory(context) + "\n");
        zwp.quickly.utils.toolsUtils.LogUtils.i(stringBuilder);
    }
}
