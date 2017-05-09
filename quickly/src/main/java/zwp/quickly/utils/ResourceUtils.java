package zwp.quickly.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <p>describe：资源操作
 * <p>    note：
 * <p>  author：zwp on 2017/5/2 mail：1101558280@qq.com web: http://www.zwping.win </p>
 * <table>
 *     <tr>
 *         <th>
 *             通过资源名称获取资源ID {@link #getResourceByName(Context, String, String)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             获取res/raw的文件 {@link #getRaw(Context, int)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             获取res/raw的文件描述符? {@link #getRawFd(Context, int)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             读取rawText文件 {@link #getRawText(Context, int)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             获取xml原始文件 {@link #getXml(Context, int)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             获取drawable {@link #getDrawable(Context, int)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             Get string {@link #getString(Context, int)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             Get string array {@link #getStringArray(Context, int)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             Get color {@link #getColor(Context, int)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             Get color state list {@link #getColorStateList(Context, int)}
 *         </th>
 *     </tr>
 *     <tr>
 *         <th>
 *             Get dimension {@link #getDimension(Context, int)}
 *         </th>
 *     </tr>
 * </table>
 */

public class ResourceUtils {

    /**
     * 通过资源名称获取资源ID
     *
     * @param context
     * @param drawableName 包名 + : + 资源文件夹名 + / +资源名(eg:win.zwping.quickly:drawable/icon)
     * @param defType      格式
     * @return
     */
    public static int getResourceByName(Context context, String drawableName, String defType) {
        return context.getResources().getIdentifier(drawableName, defType, AppUtils.getAppPackageName(context));
    }

    /**
     * 获取res/raw的文件
     * <P>res/raw：下不允许存在目录结构 / 会生成资源ID / 会编译成二进制? </P>
     * <P>assets：目录允许下面有多级子目录 / 不会生成资源ID / 编译时不对其文件做任何处理 </P>
     *
     * @param context
     * @param id
     * @return
     */
    public static InputStream getRaw(Context context, int id) {
        return context.getResources().openRawResource(id);
    }

    /**
     * 获取res/raw的文件描述符?
     *
     * Get raw file descriptor, ui/raw/file. This function only works for resources that are stored in the package as
     * uncompressed data, which typically includes things like mp3 files and png images.
     *
     * @param context
     * @param id
     * @return
     */
    public static AssetFileDescriptor getRawFd(Context context, int id) {
        return context.getResources().openRawResourceFd(id);
    }

    /**
     * 读取rawText文件
     *
     * @param context
     * @param id
     * @return
     */
    public String getRawText(Context context, int id) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getRaw(context, id));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            StringBuilder result = new StringBuilder();
            while ((line = bufReader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取xml原始文件
     *
     * @param context
     * @param id
     * @return
     */
    public static XmlResourceParser getXml(Context context, int id) {
        return context.getResources().getXml(id);
    }

    /**
     * 获取drawable
     *
     * @param context
     * @param id
     * @return
     */
    public static Drawable getDrawable(Context context, int id) {
        return ContextCompat.getDrawable(context, id);
    }

    /**
     * Get string, ui/values/strings.xml
     *
     * @param context
     * @param id
     * @return
     */
    public static String getString(Context context, int id) {
        return context.getResources().getString(id);
    }

    /**
     * Get string array, ui/values/strings.xml
     *
     * @param context
     * @param id
     * @return
     */
    public static String[] getStringArray(Context context, int id) {
        return context.getResources().getStringArray(id);
    }

    /**
     * Get color, ui/values/colors.xml
     *
     * @param context
     * @param id
     * @return
     */
    public static int getColor(Context context, int id) {
        return ContextCompat.getColor(context, id);
    }

    /**
     * Get color state list, ui/values/colors.xml
     *
     * @param context
     * @param id
     * @return
     */
    public static ColorStateList getColorStateList(Context context, int id) {
        return context.getResources().getColorStateList(id);
    }

    /**
     * Get dimension, ui/values/dimens.xml
     *
     * @param context
     * @param id
     * @return View dimension value multiplied by the appropriate metric.
     */
    public static float getDimension(Context context, int id) {
        return context.getResources().getDimension(id);
    }

    /**
     * Get dimension, ui/values/dimens.xml
     *
     * @param context
     * @param id
     * @return View dimension value multiplied by the appropriate metric and truncated to integer pixels.
     */
    public static int getDimensionPixelOffset(Context context, int id) {
        return context.getResources().getDimensionPixelOffset(id);
    }

    /**
     * Get dimension, ui/values/dimens.xml
     *
     * @param context
     * @param id
     * @return View dimension value multiplied by the appropriate metric and truncated to integer pixels.
     */
    public static int getDimensionPixelSize(Context context, int id) {
        return context.getResources().getDimensionPixelSize(id);
    }
}
