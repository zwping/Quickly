package zwp.quickly.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.math.BigDecimal;

/**
 * 清除工具
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 * <table>
 * <tr>
 * <th>获取本应用内部缓存大小{@link CleanUtils#getInternalCacheSize(Context)}</th>
 * </tr>
 * <tr>
 * <th>清除本应用内部缓存{@link CleanUtils#cleanInternalCache(Context)}</th>
 * </tr>
 * <tr>
 * <th>清除本应用所有数据库{@link CleanUtils#cleanDatabases(Context)}</th>
 * </tr>
 * <tr>
 * <th>清除本应用SharedPreference{@link CleanUtils#cleanSharedPreference(Context)}</th>
 * </tr>
 * <tr>
 * <th>按名字清除本应用数据库{@link CleanUtils#cleanDatabaseByName(Context, String)}</th>
 * </tr>
 * <tr>
 * <th>清除/data/data/com.xxx.xxx/files下的内容{@link CleanUtils#cleanFiles(Context)}</th>
 * </tr>
 * <tr>
 * <th>清除外部cache下的内容{@link CleanUtils#cleanExternalCache(Context)}</th>
 * </tr>
 * <tr>
 * <th>清除自定义路径下的文件{@link CleanUtils#cleanCustomCache(String)}</th>
 * </tr>
 * <tr>
 * <th>清除本应用所有的数据{@link CleanUtils#cleanApplicationData(Context, String...)}</th>
 * </tr>
 * <tr>
 * <th>删除文件夹内的文件{@link CleanUtils#deleteFilesByDirectory(File)}</th>
 * </tr>
 * <tr>
 * <th>删除指定目录下文件及目录{@link CleanUtils#deleteFolderFile(String, boolean)}</th>
 * </tr>
 * <tr>
 * <th>获取文件大小(返回友好值){@link CleanUtils#getFolderFriendlySize(File)}</th>
 * </tr>
 * <tr>
 * <th>获取文件的大小{@link CleanUtils#getFolderSize(File)}</th>
 * </tr>
 * <tr>
 * <th>格式化单位{@link CleanUtils#getFormatSize(double)}</th>
 * </tr>
 * </table>
 */

public class CleanUtils {


    /**
     * 获取本应用内部缓存大小 (/data/data/com.xxx.xxx/cache) * *
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getInternalCacheSize(Context context) throws Exception {
        return getFolderFriendlySize(context.getCacheDir());
    }

    /**
     * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * *
     *
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * *
     *
     * @param context
     */
    @SuppressLint("SdCardPath")
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) *
     *
     * @param context
     */
    @SuppressLint("SdCardPath")
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * * 按名字清除本应用数据库 * *
     *
     * @param context
     * @param dbName
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * * 清除/data/data/com.xxx.xxx/files下的内容 * *
     *
     * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     *
     * @param context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * *
     *
     * @param filePath
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * * 清除本应用所有的数据 * *
     *
     * @param context
     * @param filepath
     */
    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        if (filepath == null) {
            return;
        }
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    /**
     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
     *
     * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath
     * @param filePath
     * @return
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 如果下面还有文件
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件大小(返回友好值)
     * getCacheDir (/data/data/com.xxx.xxx/cache)
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static String getFolderFriendlySize(File file) throws Exception {
        return ConvertUtils.byte2FitMemorySize(getFolderSize(file));
    }

    /**
     * 获取文件的大小(return 字节)
     * // Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/
     * // 目录，一般放一些长时间保存的数据
     * // Context.getExternalCacheDir() -->
     * // SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

}
