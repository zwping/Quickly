package zwp.quickly.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.media.ExifInterface;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import zwp.quickly.Quickly;


/**
 * <p>describe：Image操作常用工具类
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 * <table>
 * <tr>
 * <th>bitmap转byteArr {@link #bitmap2Bytes(Bitmap, CompressFormat)}</th>
 * </tr>
 * <tr>
 * <th>byteArr转bitmap {@link #bytes2Bitmap(byte[])}</th>
 * </tr>
 * <tr>
 * <th>drawable转bitmap {@link #drawable2Bitmap(Drawable)}</th>
 * </tr>
 * <tr>
 * <th>bitmap转drawable {@link #bitmap2Drawable(Resources, Bitmap)}</th>
 * </tr>
 * <tr>
 * <th>drawable转byteArr {@link #drawable2Bytes(Drawable, CompressFormat)}</th>
 * </tr>
 * <tr>
 * <th>byteArr转drawable {@link #bytes2Drawable(Resources, byte[])}</th>
 * </tr>
 * <tr>
 * <th>view转Bitmap {@link #view2Bitmap(View)}</th>
 * </tr>
 * <tr>
 * <th>计算采样大小 {@link #calculateInSampleSize(BitmapFactory.Options, int, int)}</th>
 * </tr>
 * <tr>
 * <th>通过file/filepath/inputStream/bytes/resources/fileDescriptor获取图片 {@link #getBitmap(byte[], int)}</th>
 * </tr>
 * <tr>
 * <th>缩放图片(新高度/倍数) {@link #scale(Bitmap, float, float)}</th>
 * </tr>
 * <tr>
 * <th>裁剪图片 {@link #clip(Bitmap, int, int, int, int)}</th>
 * </tr>
 * <tr>
 * <th>倾斜图片 {@link #skew(Bitmap, float, float)}</th>
 * </tr>
 * <tr>
 * <th>旋转图片 {@link #rotate(Bitmap, int, float, float)}</th>
 * </tr>
 * <tr>
 * <th>获取图片旋转角度 {@link #getRotateDegree(String)}</th>
 * </tr>
 * <tr>
 * <th>转为圆形图片 {@link #toRound(Bitmap)}</th>
 * </tr>
 * <tr>
 * <th>转为圆角图片 {@link #toRoundCorner(Bitmap, float)}</th>
 * </tr>
 * <tr>
 * <th>快速模糊(先缩小原图，对小图进行模糊，再放大回原先尺寸) {@link #fastBlur(Bitmap, float, float)}</th>
 * </tr>
 * <tr>
 * <th>renderScript模糊图片(sdk>19) {@link #renderScriptBlur(Context, Bitmap, float)}</th>
 * </tr>
 * <tr>
 * <th>stack模糊图片 {@link #stackBlur(Bitmap, int, boolean)}</th>
 * </tr>
 * <tr>
 * <th>添加颜色边框 {@link #addFrame(Bitmap, int, int)}</th>
 * </tr>
 * <tr>
 * <th>添加倒影 {@link #addReflection(Bitmap, int)}</th>
 * </tr>
 * <tr>
 * <th>添加文字水印 {@link #addTextWatermark(Bitmap, String, float, int, float, float, boolean)}</th>
 * </tr>
 * <tr>
 * <th>添加图片水印 {@link #addImageWatermark(Bitmap, Bitmap, int, int, int)}</th>
 * </tr>
 * <tr>
 * <th>图片合并(底图为基准) {@link #mergeBitmap(Bitmap, Bitmap)}</th>
 * </tr>
 * <tr>
 * <th>图片左右合并 {@link #mergeBitmap_LR(Bitmap, Bitmap, boolean)}</th>
 * </tr>
 * <tr>
 * <th>图片上下合并 {@link #mergeBitmap_TB(Bitmap, Bitmap, boolean)}</th>
 * </tr>
 * <tr>
 * <th>转为alpha位图 {@link #toAlpha(Bitmap)}</th>
 * </tr>
 * <tr>
 * <th>转为灰度图片 {@link #toGray(Bitmap)}</th>
 * </tr>
 * <tr>
 * <th>浮雕效果 {@link #emboss(Bitmap)}</th>
 * </tr>
 * <tr>
 * <th>锐化效果 {@link #sharpen(Bitmap)}</th>
 * </tr>
 * <tr>
 * <th>底片效果 {@link #film(Bitmap)}</th>
 * </tr>
 * <tr>
 * <th>光照效果 {@link #sunshine(Bitmap, int, int)}</th>
 * </tr>
 * <tr>
 * <th>柔化效果 {@link #soften(Bitmap)}</th>
 * </tr>
 * <tr>
 * <th>怀旧效果 {@link #nostalgic(Bitmap)}</th>
 * </tr>
 * <tr>
 * <th>亮度处理 {@link #lum(Bitmap, int)}</th>
 * </tr>
 * <tr>
 * <th>色相处理 {@link #hue(Bitmap, int)}</th>
 * </tr>
 * <tr>
 * <th>饱和度处理 {@link #saturation(Bitmap, int)}</th>
 * </tr>
 * <tr>
 * <th>亮度、色相、饱和度处理 {@link #lumAndHueAndSaturation(Bitmap, int, int, int)}</th>
 * </tr>
 * <tr>
 * <th>保存图片 {@link #save(Bitmap, File, CompressFormat)}</th>
 * </tr>
 * <tr>
 * <th>根据文件名判断文件是否为图片 {@link #isImage(File)}</th>
 * </tr>
 * <tr>
 * <th>获取图片类型 {@link #getImageType(byte[])}</th>
 * </tr>
 * <tr>
 * <th>判断bitmap对象是否为空 {@link #isEmptyBitmap(Bitmap)}</th>
 * </tr>
 * <tr>
 * <th>按缩放压缩(新高度/倍数) {@link #compressByScale(Bitmap, float, float)}</th>
 * </tr>
 * <tr>
 * <th>按质量压缩((0-100)/最大字节数) {@link #compressByQuality(Bitmap, int)}</th>
 * </tr>
 * <tr>
 * <th>按采样大小压缩 {@link #compressBySampleSize(Bitmap, int)}</th>
 * </tr>
 * </table>
 */

public class ImageUtils {

    private ImageUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * bitmap转byteArr
     *
     * @param bitmap bitmap对象
     * @param format 格式
     * @return 字节数组
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, CompressFormat format) {
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
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap.createBitmap(
                    drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
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
    public static byte[] drawable2Bytes(Drawable drawable, CompressFormat format) {
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
     * 计算采样大小
     *
     * @param options   选项
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 采样大小
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int maxWidth, int maxHeight) {
        if (maxWidth == 0 || maxHeight == 0) return 1;
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        while ((height >>= 1) >= maxHeight && (width >>= 1) >= maxWidth) {
            inSampleSize <<= 1;
        }
        return inSampleSize;
    }

    /**
     * 获取bitmap
     *
     * @param file 文件
     * @return bitmap
     */
    public static Bitmap getBitmap(File file) {
        if (file == null) return null;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            return BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.close(is);
        }
    }

    /**
     * 获取bitmap
     *
     * @param file      文件
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(File file, int maxWidth, int maxHeight) {
        if (file == null) return null;
        InputStream is = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            is = new BufferedInputStream(new FileInputStream(file));
            BitmapFactory.decodeStream(is, null, options);
            options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(is, null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.close(is);
        }
    }

    /**
     * 获取bitmap
     *
     * @param filePath 文件路径
     * @return bitmap
     */
    public static Bitmap getBitmap(String filePath) {
        if (isSpace(filePath)) return null;
        return BitmapFactory.decodeFile(filePath);
    }

    /**
     * 获取bitmap
     *
     * @param filePath  文件路径
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(String filePath, int maxWidth, int maxHeight) {
        if (isSpace(filePath)) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 获取bitmap
     *
     * @param is 输入流
     * @return bitmap
     */
    public static Bitmap getBitmap(InputStream is) {
        if (is == null) return null;
        return BitmapFactory.decodeStream(is);
    }

    /**
     * 获取bitmap
     *
     * @param is        输入流
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(InputStream is, int maxWidth, int maxHeight) {
        if (is == null) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is, null, options);
    }

    /**
     * 获取bitmap
     *
     * @param data   数据
     * @param offset 偏移量
     * @return bitmap
     */
    public static Bitmap getBitmap(byte[] data, int offset) {
        if (data.length == 0) return null;
        return BitmapFactory.decodeByteArray(data, offset, data.length);
    }

    /**
     * 获取bitmap
     *
     * @param data      数据
     * @param offset    偏移量
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(byte[] data, int offset, int maxWidth, int maxHeight) {
        if (data.length == 0) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, offset, data.length, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, offset, data.length, options);
    }

    /**
     * 获取bitmap
     *
     * @param res 资源对象
     * @param id  资源id
     * @return bitmap
     */
    public static Bitmap getBitmap(Resources res, int id) {
        if (res == null) return null;
        return BitmapFactory.decodeResource(res, id);
    }

    /**
     * 获取bitmap
     *
     * @param res       资源对象
     * @param id        资源id
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(Resources res, int id, int maxWidth, int maxHeight) {
        if (res == null) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, id, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, id, options);
    }

    /**
     * 获取bitmap
     *
     * @param fd 文件描述
     * @return bitmap
     */
    public static Bitmap getBitmap(FileDescriptor fd) {
        if (fd == null) return null;
        return BitmapFactory.decodeFileDescriptor(fd);
    }

    /**
     * 获取bitmap
     *
     * @param fd        文件描述
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(FileDescriptor fd, int maxWidth, int maxHeight) {
        if (fd == null) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    /**
     * 缩放图片
     *
     * @param src       源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @return 缩放后的图片
     */
    public static Bitmap scale(Bitmap src, int newWidth, int newHeight) {
        return scale(src, newWidth, newHeight, false);
    }

    /**
     * 缩放图片
     *
     * @param src       源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @param recycle   是否回收
     * @return 缩放后的图片
     */
    public static Bitmap scale(Bitmap src, int newWidth, int newHeight, boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        Bitmap ret = Bitmap.createScaledBitmap(src, newWidth, newHeight, true);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 缩放图片
     *
     * @param src         源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @return 缩放后的图片
     */
    public static Bitmap scale(Bitmap src, float scaleWidth, float scaleHeight) {
        return scale(src, scaleWidth, scaleHeight, false);
    }

    /**
     * 缩放图片
     *
     * @param src         源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @param recycle     是否回收
     * @return 缩放后的图片
     */
    public static Bitmap scale(Bitmap src, float scaleWidth, float scaleHeight, boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        Matrix matrix = new Matrix();
        matrix.setScale(scaleWidth, scaleHeight);
        Bitmap ret = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 裁剪图片
     *
     * @param src    源图片
     * @param x      开始坐标x
     * @param y      开始坐标y
     * @param width  裁剪宽度
     * @param height 裁剪高度
     * @return 裁剪后的图片
     */
    public static Bitmap clip(Bitmap src, int x, int y, int width, int height) {
        return clip(src, x, y, width, height, false);
    }

    /**
     * 裁剪图片
     *
     * @param src     源图片
     * @param x       开始坐标x
     * @param y       开始坐标y
     * @param width   裁剪宽度
     * @param height  裁剪高度
     * @param recycle 是否回收
     * @return 裁剪后的图片
     */
    public static Bitmap clip(Bitmap src, int x, int y, int width, int height, boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        Bitmap ret = Bitmap.createBitmap(src, x, y, width, height);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 倾斜图片
     *
     * @param src 源图片
     * @param kx  倾斜因子x
     * @param ky  倾斜因子y
     * @return 倾斜后的图片
     */
    public static Bitmap skew(Bitmap src, float kx, float ky) {
        return skew(src, kx, ky, 0, 0, false);
    }

    /**
     * 倾斜图片
     *
     * @param src     源图片
     * @param kx      倾斜因子x
     * @param ky      倾斜因子y
     * @param recycle 是否回收
     * @return 倾斜后的图片
     */
    public static Bitmap skew(Bitmap src, float kx, float ky, boolean recycle) {
        return skew(src, kx, ky, 0, 0, recycle);
    }

    /**
     * 倾斜图片
     *
     * @param src 源图片
     * @param kx  倾斜因子x
     * @param ky  倾斜因子y
     * @param px  平移因子x
     * @param py  平移因子y
     * @return 倾斜后的图片
     */
    public static Bitmap skew(Bitmap src, float kx, float ky, float px, float py) {
        return skew(src, kx, ky, px, py, false);
    }

    /**
     * 倾斜图片
     *
     * @param src     源图片
     * @param kx      倾斜因子x
     * @param ky      倾斜因子y
     * @param px      平移因子x
     * @param py      平移因子y
     * @param recycle 是否回收
     * @return 倾斜后的图片
     */
    public static Bitmap skew(Bitmap src, float kx, float ky, float px, float py, boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        Matrix matrix = new Matrix();
        matrix.setSkew(kx, ky, px, py);
        Bitmap ret = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 旋转图片
     *
     * @param src     源图片
     * @param degrees 旋转角度
     * @param px      旋转点横坐标
     * @param py      旋转点纵坐标
     * @return 旋转后的图片
     */
    public static Bitmap rotate(Bitmap src, int degrees, float px, float py) {
        return rotate(src, degrees, px, py, false);
    }

    /**
     * 旋转图片
     *
     * @param src     源图片
     * @param degrees 旋转角度
     * @param px      旋转点横坐标
     * @param py      旋转点纵坐标
     * @param recycle 是否回收
     * @return 旋转后的图片
     */
    public static Bitmap rotate(Bitmap src, int degrees, float px, float py, boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        if (degrees == 0) return src;
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, px, py);
        Bitmap ret = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 获取图片旋转角度
     *
     * @param filePath 文件路径
     * @return 旋转角度
     */
    public static int getRotateDegree(String filePath) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                default:
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 转为圆形图片
     *
     * @param src 源图片
     * @return 圆形图片
     */
    public static Bitmap toRound(Bitmap src) {
        return toRound(src, false);
    }

    /**
     * 转为圆形图片
     *
     * @param src     源图片
     * @param recycle 是否回收
     * @return 圆形图片
     */
    public static Bitmap toRound(Bitmap src, boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        int width = src.getWidth();
        int height = src.getHeight();
        int radius = Math.min(width, height) >> 1;
        Bitmap ret = Bitmap.createBitmap(width, height, src.getConfig());
        Paint paint = new Paint();
        Canvas canvas = new Canvas(ret);
        Rect rect = new Rect(0, 0, width, height);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(width >> 1, height >> 1, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(src, rect, rect, paint);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 转为圆角图片
     *
     * @param src    源图片
     * @param radius 圆角的度数
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Bitmap src, float radius) {
        return toRoundCorner(src, radius, false);
    }

    /**
     * 转为圆角图片
     *
     * @param src     源图片
     * @param radius  圆角的度数
     * @param recycle 是否回收
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Bitmap src, float radius, boolean recycle) {
        if (null == src) return null;
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap ret = Bitmap.createBitmap(width, height, src.getConfig());
        Paint paint = new Paint();
        Canvas canvas = new Canvas(ret);
        Rect rect = new Rect(0, 0, width, height);
        paint.setAntiAlias(true);
        canvas.drawRoundRect(new RectF(rect), radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(src, rect, rect, paint);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 快速模糊
     * <p>先缩小原图，对小图进行模糊，再放大回原先尺寸</p>
     *
     * @param src    源图片
     * @param scale  缩放比例(0...1)
     * @param radius 模糊半径
     * @return 模糊后的图片
     */
    public static Bitmap fastBlur(Bitmap src,
                                  @FloatRange(from = 0, to = 1, fromInclusive = false) float scale,
                                  @FloatRange(from = 0, to = 25, fromInclusive = false) float radius) {
        return fastBlur(src, scale, radius, false);
    }

    /**
     * 快速模糊图片
     * <p>先缩小原图，对小图进行模糊，再放大回原先尺寸</p>
     *
     * @param src     源图片
     * @param scale   缩放比例(0...1)
     * @param radius  模糊半径(0...25)
     * @param recycle 是否回收
     * @return 模糊后的图片
     */
    public static Bitmap fastBlur(Bitmap src,
                                  @FloatRange(from = 0, to = 1, fromInclusive = false) float scale,
                                  @FloatRange(from = 0, to = 25, fromInclusive = false) float radius,
                                  boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        int width = src.getWidth();
        int height = src.getHeight();
        int scaleWidth = (int) (width * scale + 0.5f);
        int scaleHeight = (int) (height * scale + 0.5f);
        if (scaleWidth == 0 || scaleHeight == 0) return null;
        Bitmap scaleBitmap = Bitmap.createScaledBitmap(src, scaleWidth, scaleHeight, true);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas();
        PorterDuffColorFilter filter = new PorterDuffColorFilter(
                Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
        paint.setColorFilter(filter);
        canvas.scale(scale, scale);
        canvas.drawBitmap(scaleBitmap, 0, 0, paint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            scaleBitmap = renderScriptBlur(Quickly.getContext(), scaleBitmap, radius);
        } else {
            scaleBitmap = stackBlur(scaleBitmap, (int) radius, recycle);
        }
        if (scale == 1) return scaleBitmap;
        Bitmap ret = Bitmap.createScaledBitmap(scaleBitmap, width, height, true);
        if (scaleBitmap != null && !scaleBitmap.isRecycled()) scaleBitmap.recycle();
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * renderScript模糊图片
     * <p>API大于17</p>
     *
     * @param context 上下文
     * @param src     源图片
     * @param radius  模糊半径(0...25)
     * @return 模糊后的图片
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap renderScriptBlur(Context context, Bitmap src, @FloatRange(from = 0, to = 25, fromInclusive = false) float radius) {
        if (isEmptyBitmap(src)) return null;
        RenderScript rs = null;
        try {
            rs = RenderScript.create(context);
            rs.setMessageHandler(new RenderScript.RSMessageHandler());
            Allocation input = Allocation.createFromBitmap(rs, src, Allocation.MipmapControl.MIPMAP_NONE, Allocation
                    .USAGE_SCRIPT);
            Allocation output = Allocation.createTyped(rs, input.getType());
            ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            blurScript.setInput(input);
            blurScript.setRadius(radius);
            blurScript.forEach(output);
            output.copyTo(src);
        } finally {
            if (rs != null) {
                rs.destroy();
            }
        }
        return src;
    }

    /**
     * stack模糊图片
     *
     * @param src     源图片
     * @param radius  模糊半径
     * @param recycle 是否回收
     * @return stack模糊后的图片
     */
    public static Bitmap stackBlur(Bitmap src, int radius, boolean recycle) {
        Bitmap ret;
        if (recycle) {
            ret = src;
        } else {
            ret = src.copy(src.getConfig(), true);
        }

        if (radius < 1) {
            return null;
        }

        int w = ret.getWidth();
        int h = ret.getHeight();

        int[] pix = new int[w * h];
        ret.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }
        ret.setPixels(pix, 0, w, 0, 0, w, h);
        return ret;
    }

    /**
     * 添加颜色边框
     *
     * @param src         源图片
     * @param borderWidth 边框宽度
     * @param color       边框的颜色值
     * @return 带颜色边框图
     */
    public static Bitmap addFrame(Bitmap src, int borderWidth, int color) {
        return addFrame(src, borderWidth, color, false);
    }

    /**
     * 添加颜色边框
     *
     * @param src         源图片
     * @param borderWidth 边框宽度
     * @param color       边框的颜色值
     * @param recycle     是否回收
     * @return 带颜色边框图
     */
    public static Bitmap addFrame(Bitmap src, int borderWidth, int color, boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        int doubleBorder = borderWidth << 1;
        int newWidth = src.getWidth() + doubleBorder;
        int newHeight = src.getHeight() + doubleBorder;
        Bitmap ret = Bitmap.createBitmap(newWidth, newHeight, src.getConfig());
        Canvas canvas = new Canvas(ret);
        Rect rect = new Rect(0, 0, newWidth, newHeight);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        // setStrokeWidth是居中画的，所以要两倍的宽度才能画，否则有一半的宽度是空的
        paint.setStrokeWidth(doubleBorder);
        canvas.drawRect(rect, paint);
        //noinspection SuspiciousNameCombination
        canvas.drawBitmap(src, borderWidth, borderWidth, null);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 添加倒影
     *
     * @param src              源图片的
     * @param reflectionHeight 倒影高度
     * @return 带倒影图片
     */
    public static Bitmap addReflection(Bitmap src, int reflectionHeight) {
        return addReflection(src, reflectionHeight, false);
    }

    /**
     * 添加倒影
     *
     * @param src              源图片的
     * @param reflectionHeight 倒影高度
     * @param recycle          是否回收
     * @return 带倒影图片
     */
    public static Bitmap addReflection(Bitmap src, int reflectionHeight, boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        // 原图与倒影之间的间距
        final int REFLECTION_GAP = 0;
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        Bitmap reflectionBitmap = Bitmap.createBitmap(src, 0, srcHeight - reflectionHeight,
                srcWidth, reflectionHeight, matrix, false);
        Bitmap ret = Bitmap.createBitmap(srcWidth, srcHeight + reflectionHeight, src.getConfig());
        Canvas canvas = new Canvas(ret);
        canvas.drawBitmap(src, 0, 0, null);
        canvas.drawBitmap(reflectionBitmap, 0, srcHeight + REFLECTION_GAP, null);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        LinearGradient shader = new LinearGradient(0, srcHeight,
                0, ret.getHeight() + REFLECTION_GAP,
                0x70FFFFFF, 0x00FFFFFF, Shader.TileMode.MIRROR);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, srcHeight + REFLECTION_GAP,
                srcWidth, ret.getHeight(), paint);
        if (!reflectionBitmap.isRecycled()) reflectionBitmap.recycle();
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 添加文字水印
     *
     * @param src      源图片
     * @param content  水印文本
     * @param textSize 水印字体大小
     * @param color    水印字体颜色
     * @param x        起始坐标x
     * @param y        起始坐标y
     * @return 带有文字水印的图片
     */
    public static Bitmap addTextWatermark(Bitmap src, String content, int textSize, int color, float x,
                                          float y) {
        return addTextWatermark(src, content, textSize, color, x, y, false);
    }

    /**
     * 添加文字水印
     *
     * @param src      源图片
     * @param content  水印文本
     * @param textSize 水印字体大小
     * @param color    水印字体颜色
     * @param x        起始坐标x
     * @param y        起始坐标y
     * @param recycle  是否回收
     * @return 带有文字水印的图片
     */
    public static Bitmap addTextWatermark(Bitmap src, String content, float textSize, int color, float x,
                                          float y, boolean recycle) {
        if (isEmptyBitmap(src) || content == null) return null;
        Bitmap ret = src.copy(src.getConfig(), true);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas(ret);
        paint.setColor(color);
        paint.setTextSize(textSize);
        Rect bounds = new Rect();
        paint.getTextBounds(content, 0, content.length(), bounds);
        canvas.drawText(content, x, y + textSize, paint);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 添加图片水印
     *
     * @param src       源图片
     * @param watermark 图片水印
     * @param x         起始坐标x
     * @param y         起始坐标y
     * @param alpha     透明度
     * @return 带有图片水印的图片
     */
    public static Bitmap addImageWatermark(Bitmap src, Bitmap watermark, int x, int y, int alpha) {
        return addImageWatermark(src, watermark, x, y, alpha, false);
    }

    /**
     * 添加图片水印
     *
     * @param src       源图片
     * @param watermark 图片水印
     * @param x         起始坐标x
     * @param y         起始坐标y
     * @param alpha     透明度
     * @param recycle   是否回收
     * @return 带有图片水印的图片
     */
    public static Bitmap addImageWatermark(Bitmap src, Bitmap watermark, int x, int y, int alpha, boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        Bitmap ret = src.copy(src.getConfig(), true);
        if (!isEmptyBitmap(watermark)) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            Canvas canvas = new Canvas(ret);
            paint.setAlpha(alpha);
            canvas.drawBitmap(watermark, x, y, paint);
        }
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * Combine bitmaps
     *
     * @param bgBitmap 背景Bitmap
     * @param fgBitmap 前景Bitmap
     * @return 合成后的Bitmap
     */
    public static Bitmap combineBitmap(Bitmap bgBitmap, Bitmap fgBitmap) {
        Bitmap bmp;

        int width = bgBitmap.getWidth() > fgBitmap.getWidth() ? bgBitmap.getWidth() : fgBitmap
                .getWidth();
        int height = bgBitmap.getHeight() > fgBitmap.getHeight() ? bgBitmap.getHeight() : fgBitmap
                .getHeight();

        bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(bgBitmap, 0, 0, null);
        canvas.drawBitmap(fgBitmap, 0, 0, paint);

        return bmp;
    }

    /**
     * Combine bitmaps
     *
     * @param bgd 后景Bitmap
     * @param fg  前景Bitmap
     * @return 合成后Bitmap
     */
    public static Bitmap combineBitmapInSameSize(Bitmap bgd, Bitmap fg) {
        Bitmap bmp;

        int width = bgd.getWidth() < fg.getWidth() ? bgd.getWidth() : fg
                .getWidth();
        int height = bgd.getHeight() < fg.getHeight() ? bgd.getHeight() : fg
                .getHeight();

        if (fg.getWidth() != width && fg.getHeight() != height) {
            fg = compressByScale(fg, width, height);
        }
        if (bgd.getWidth() != width && bgd.getHeight() != height) {
            bgd = compressByScale(bgd, width, height);
        }

        bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(bgd, 0, 0, null);
        canvas.drawBitmap(fg, 0, 0, paint);

        return bmp;
    }
    /**
     * 把两个位图覆盖合成为一个位图，以底层位图的长宽为基准
     * @param backBitmap 在底部的位图
     * @param frontBitmap 盖在上面的位图
     * @return
     */
    public static Bitmap mergeBitmap(Bitmap backBitmap, Bitmap frontBitmap) {

        if (backBitmap == null || backBitmap.isRecycled()
                || frontBitmap == null || frontBitmap.isRecycled()) {
            return null;
        }
        Bitmap bitmap = backBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Rect baseRect  = new Rect(0, 0, backBitmap.getWidth(), backBitmap.getHeight());
        Rect frontRect = new Rect(0, 0, frontBitmap.getWidth(), frontBitmap.getHeight());
        canvas.drawBitmap(frontBitmap, frontRect, baseRect, null);
        return bitmap;
    }

    /**
     * 把两个位图覆盖合成为一个位图，左右拼接
     * @param leftBitmap
     * @param rightBitmap
     * @param isBaseMax 是否以宽度大的位图为准，true则小图等比拉伸，false则大图等比压缩
     * @return
     */
    public static Bitmap mergeBitmap_LR(Bitmap leftBitmap, Bitmap rightBitmap, boolean isBaseMax) {

        if (leftBitmap == null || leftBitmap.isRecycled()
                || rightBitmap == null || rightBitmap.isRecycled()) {
//            JDLog.logError(TAG, "leftBitmap=" + leftBitmap + ";rightBitmap=" + rightBitmap);
            return null;
        }
        int height = 0; // 拼接后的高度，按照参数取大或取小
        if (isBaseMax) {
            height = leftBitmap.getHeight() > rightBitmap.getHeight() ? leftBitmap.getHeight() : rightBitmap.getHeight();
        } else {
            height = leftBitmap.getHeight() < rightBitmap.getHeight() ? leftBitmap.getHeight() : rightBitmap.getHeight();
        }

        // 缩放之后的bitmap
        Bitmap tempBitmapL = leftBitmap;
        Bitmap tempBitmapR = rightBitmap;

        if (leftBitmap.getHeight() != height) {
            tempBitmapL = Bitmap.createScaledBitmap(leftBitmap, (int)(leftBitmap.getWidth()*1f/leftBitmap.getHeight()*height), height, false);
        } else if (rightBitmap.getHeight() != height) {
            tempBitmapR = Bitmap.createScaledBitmap(rightBitmap, (int)(rightBitmap.getWidth()*1f/rightBitmap.getHeight()*height), height, false);
        }

        // 拼接后的宽度
        int width = tempBitmapL.getWidth() + tempBitmapR.getWidth();

        // 定义输出的bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // 缩放后两个bitmap需要绘制的参数
        Rect leftRect = new Rect(0, 0, tempBitmapL.getWidth(), tempBitmapL.getHeight());
        Rect rightRect  = new Rect(0, 0, tempBitmapR.getWidth(), tempBitmapR.getHeight());

        // 右边图需要绘制的位置，往右边偏移左边图的宽度，高度是相同的
        Rect rightRectT  = new Rect(tempBitmapL.getWidth(), 0, width, height);

        canvas.drawBitmap(tempBitmapL, leftRect, leftRect, null);
        canvas.drawBitmap(tempBitmapR, rightRect, rightRectT, null);
        return bitmap;
    }


    /**
     * 把两个位图覆盖合成为一个位图，上下拼接
     * @param topBitmap
     * @param bottomBitmap
     * @param isBaseMax 是否以高度大的位图为准，true则小图等比拉伸，false则大图等比压缩
     * @return
     */
    public static Bitmap mergeBitmap_TB(Bitmap topBitmap, Bitmap bottomBitmap, boolean isBaseMax) {

        if (topBitmap == null || topBitmap.isRecycled()
                || bottomBitmap == null || bottomBitmap.isRecycled()) {
//            JDLog.logError(TAG, "topBitmap=" + topBitmap + ";bottomBitmap=" + bottomBitmap);
            return null;
        }
        int width = 0;
        if (isBaseMax) {
            width = topBitmap.getWidth() > bottomBitmap.getWidth() ? topBitmap.getWidth() : bottomBitmap.getWidth();
        } else {
            width = topBitmap.getWidth() < bottomBitmap.getWidth() ? topBitmap.getWidth() : bottomBitmap.getWidth();
        }
        Bitmap tempBitmapT = topBitmap;
        Bitmap tempBitmapB = bottomBitmap;

        if (topBitmap.getWidth() != width) {
            tempBitmapT = Bitmap.createScaledBitmap(topBitmap, width, (int)(topBitmap.getHeight()*1f/topBitmap.getWidth()*width), false);
        } else if (bottomBitmap.getWidth() != width) {
            tempBitmapB = Bitmap.createScaledBitmap(bottomBitmap, width, (int)(bottomBitmap.getHeight()*1f/bottomBitmap.getWidth()*width), false);
        }

        int height = tempBitmapT.getHeight() + tempBitmapB.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Rect topRect = new Rect(0, 0, tempBitmapT.getWidth(), tempBitmapT.getHeight());
        Rect bottomRect  = new Rect(0, 0, tempBitmapB.getWidth(), tempBitmapB.getHeight());

        Rect bottomRectT  = new Rect(0, tempBitmapT.getHeight(), width, height);

        canvas.drawBitmap(tempBitmapT, topRect, topRect, null);
        canvas.drawBitmap(tempBitmapB, bottomRect, bottomRectT, null);
        return bitmap;
    }

    /**
     * 转为alpha位图
     *
     * @param src 源图片
     * @return alpha位图
     */
    public static Bitmap toAlpha(Bitmap src) {
        return toAlpha(src, false);
    }

    /**
     * 转为alpha位图
     *
     * @param src     源图片
     * @param recycle 是否回收
     * @return alpha位图
     */
    public static Bitmap toAlpha(Bitmap src, Boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        Bitmap ret = src.extractAlpha();
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 转为灰度图片
     *
     * @param src 源图片
     * @return 灰度图
     */
    public static Bitmap toGray(Bitmap src) {
        return toGray(src, false);
    }

    /**
     * 转为灰度图片
     *
     * @param src     源图片
     * @param recycle 是否回收
     * @return 灰度图
     */
    public static Bitmap toGray(Bitmap src, boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        Bitmap grayBitmap = Bitmap.createBitmap(src.getWidth(),
                src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(grayBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorMatrixColorFilter);
        canvas.drawBitmap(src, 0, 0, paint);
        if (recycle && !src.isRecycled()) src.recycle();
        return grayBitmap;
    }

    /**
     * 保存图片
     *
     * @param src      源图片
     * @param filePath 要保存到的文件路径
     * @param format   格式
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean save(Bitmap src, String filePath, CompressFormat format) {
        return save(src, FileUtils.getFileByPath(filePath), format, false);
    }

    /**
     * 保存图片
     *
     * @param src    源图片
     * @param file   要保存到的文件
     * @param format 格式
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean save(Bitmap src, File file, CompressFormat format) {
        return save(src, file, format, false);
    }

    /**
     * 保存图片
     *
     * @param src      源图片
     * @param filePath 要保存到的文件路径
     * @param format   格式
     * @param recycle  是否回收
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean save(Bitmap src, String filePath, CompressFormat format, boolean recycle) {
        return save(src, FileUtils.getFileByPath(filePath), format, recycle);
    }

    /**
     * 保存图片
     *
     * @param src     源图片
     * @param file    要保存到的文件
     * @param format  格式
     * @param recycle 是否回收
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean save(Bitmap src, File file, CompressFormat format, boolean recycle) {
        if (isEmptyBitmap(src) || !FileUtils.createOrExistsFile(file)) return false;
        System.out.println(src.getWidth() + ", " + src.getHeight());
        OutputStream os = null;
        boolean ret = false;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            ret = src.compress(format, 100, os);
            if (recycle && !src.isRecycled()) src.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(os);
        }
        return ret;
    }

    /**
     * 根据文件名判断文件是否为图片
     *
     * @param file 　文件
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isImage(File file) {
        return file != null && isImage(file.getPath());
    }

    /**
     * 根据文件名判断文件是否为图片
     *
     * @param filePath 　文件路径
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isImage(String filePath) {
        String path = filePath.toUpperCase();
        return path.endsWith(".PNG") || path.endsWith(".JPG")
                || path.endsWith(".JPEG") || path.endsWith(".BMP")
                || path.endsWith(".GIF");
    }

    /**
     * 获取图片类型
     *
     * @param filePath 文件路径
     * @return 图片类型
     */
    public static String getImageType(String filePath) {
        return getImageType(FileUtils.getFileByPath(filePath));
    }

    /**
     * 获取图片类型
     *
     * @param file 文件
     * @return 图片类型
     */
    public static String getImageType(File file) {
        if (file == null) return null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            return getImageType(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.close(is);
        }
    }

    /**
     * 流获取图片类型
     *
     * @param is 图片输入流
     * @return 图片类型
     */
    public static String getImageType(InputStream is) {
        if (is == null) return null;
        try {
            byte[] bytes = new byte[8];
            return is.read(bytes, 0, 8) != -1 ? getImageType(bytes) : null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取图片类型
     *
     * @param bytes bitmap的前8字节
     * @return 图片类型
     */
    public static String getImageType(byte[] bytes) {
        if (isJPEG(bytes)) return "JPEG";
        if (isGIF(bytes)) return "GIF";
        if (isPNG(bytes)) return "PNG";
        if (isBMP(bytes)) return "BMP";
        return null;
    }

    private static boolean isJPEG(byte[] b) {
        return b.length >= 2
                && (b[0] == (byte) 0xFF) && (b[1] == (byte) 0xD8);
    }

    private static boolean isGIF(byte[] b) {
        return b.length >= 6
                && b[0] == 'G' && b[1] == 'I'
                && b[2] == 'F' && b[3] == '8'
                && (b[4] == '7' || b[4] == '9') && b[5] == 'a';
    }

    private static boolean isPNG(byte[] b) {
        return b.length >= 8
                && (b[0] == (byte) 137 && b[1] == (byte) 80
                && b[2] == (byte) 78 && b[3] == (byte) 71
                && b[4] == (byte) 13 && b[5] == (byte) 10
                && b[6] == (byte) 26 && b[7] == (byte) 10);
    }

    private static boolean isBMP(byte[] b) {
        return b.length >= 2
                && (b[0] == 0x42) && (b[1] == 0x4d);
    }

    /**
     * 判断bitmap对象是否为空
     *
     * @param src 源图片
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private static boolean isEmptyBitmap(Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }

    /**
     * 饱和度处理
     *
     * @param bitmap          原图
     * @param saturationValue 新的饱和度值
     * @return 改变了饱和度值之后的图片
     */
    public static Bitmap saturation(Bitmap bitmap, int saturationValue) {
        // 计算出符合要求的饱和度值
        float newSaturationValue = saturationValue * 1.0F / 127;
        // 创建一个颜色矩阵
        ColorMatrix saturationColorMatrix = new ColorMatrix();
        // 设置饱和度值
        saturationColorMatrix.setSaturation(newSaturationValue);
        // 创建一个画笔并设置其颜色过滤器
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(saturationColorMatrix));
        // 创建一个新的图片并创建画布
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        // 将原图使用给定的画笔画到画布上
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return newBitmap;
    }

    /**
     * 亮度处理
     *
     * @param bitmap   原图
     * @param lumValue 新的亮度值
     * @return 改变了亮度值之后的图片
     */
    public static Bitmap lum(Bitmap bitmap, int lumValue) {
        // 计算出符合要求的亮度值
        float newlumValue = lumValue * 1.0F / 127;
        // 创建一个颜色矩阵
        ColorMatrix lumColorMatrix = new ColorMatrix();
        // 设置亮度值
        lumColorMatrix.setScale(newlumValue, newlumValue, newlumValue, 1);
        // 创建一个画笔并设置其颜色过滤器
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(lumColorMatrix));
        // 创建一个新的图片并创建画布
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        // 将原图使用给定的画笔画到画布上
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return newBitmap;
    }

    /**
     * 色相处理
     *
     * @param bitmap   原图
     * @param hueValue 新的色相值
     * @return 改变了色相值之后的图片
     */
    public static Bitmap hue(Bitmap bitmap, int hueValue) {
        // 计算出符合要求的色相值
        float newHueValue = (hueValue - 127) * 1.0F / 127 * 180;
        // 创建一个颜色矩阵
        ColorMatrix hueColorMatrix = new ColorMatrix();
        // 控制让红色区在色轮上旋转的角度
        hueColorMatrix.setRotate(0, newHueValue);
        // 控制让绿红色区在色轮上旋转的角度
        hueColorMatrix.setRotate(1, newHueValue);
        // 控制让蓝色区在色轮上旋转的角度
        hueColorMatrix.setRotate(2, newHueValue);
        // 创建一个画笔并设置其颜色过滤器
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(hueColorMatrix));
        // 创建一个新的图片并创建画布
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        // 将原图使用给定的画笔画到画布上
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return newBitmap;
    }

    /**
     * 亮度、色相、饱和度处理
     *
     * @param bitmap          原图
     * @param lumValue        亮度值
     * @param hueValue        色相值
     * @param saturationValue 饱和度值
     * @return 亮度、色相、饱和度处理后的图片
     */
    public static Bitmap lumAndHueAndSaturation(Bitmap bitmap, int lumValue,
                                                int hueValue, int saturationValue) {
        // 计算出符合要求的饱和度值
        float newSaturationValue = saturationValue * 1.0F / 127;
        // 计算出符合要求的亮度值
        float newlumValue = lumValue * 1.0F / 127;
        // 计算出符合要求的色相值
        float newHueValue = (hueValue - 127) * 1.0F / 127 * 180;

        // 创建一个颜色矩阵并设置其饱和度
        ColorMatrix colorMatrix = new ColorMatrix();

        // 设置饱和度值
        colorMatrix.setSaturation(newSaturationValue);
        // 设置亮度值
        colorMatrix.setScale(newlumValue, newlumValue, newlumValue, 1);
        // 控制让红色区在色轮上旋转的角度
        colorMatrix.setRotate(0, newHueValue);
        // 控制让绿红色区在色轮上旋转的角度
        colorMatrix.setRotate(1, newHueValue);
        // 控制让蓝色区在色轮上旋转的角度
        colorMatrix.setRotate(2, newHueValue);

        // 创建一个画笔并设置其颜色过滤器
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        // 创建一个新的图片并创建画布
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        // 将原图使用给定的画笔画到画布上
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return newBitmap;
    }

    /**
     * 怀旧效果
     *
     * @param bitmap
     * @return
     */
    public static Bitmap nostalgic(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);
        int pixColor = 0;
        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int newR = 0;
        int newG = 0;
        int newB = 0;
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < height; i++) {
            for (int k = 0; k < width; k++) {
                pixColor = pixels[width * i + k];
                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);
                newR = (int) (0.393 * pixR + 0.769 * pixG + 0.189 * pixB);
                newG = (int) (0.349 * pixR + 0.686 * pixG + 0.168 * pixB);
                newB = (int) (0.272 * pixR + 0.534 * pixG + 0.131 * pixB);
                int newColor = Color.argb(255, newR > 255 ? 255 : newR,
                        newG > 255 ? 255 : newG, newB > 255 ? 255 : newB);
                pixels[width * i + k] = newColor;
            }
        }
        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    /**
     * 柔化效果
     *
     * @param bitmap
     * @return
     */
    public static Bitmap soften(Bitmap bitmap) {
        // 高斯矩阵
        int[] gauss = new int[]{1, 2, 1, 2, 4, 2, 1, 2, 1};

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int delta = 16; // 值越小图片会越亮，越大则越暗

        int idx = 0;
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + m) * width + k + n];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);

                        newR = newR + (int) (pixR * gauss[idx]);
                        newG = newG + (int) (pixG * gauss[idx]);
                        newB = newB + (int) (pixB * gauss[idx]);
                        idx++;
                    }
                }

                newR /= delta;
                newG /= delta;
                newB /= delta;

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[i * width + k] = Color.argb(255, newR, newG, newB);

                newR = 0;
                newG = 0;
                newB = 0;
            }
        }

        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    /**
     * 光照效果
     *
     * @param bitmap
     * @param centerX 光源在X轴的位置
     * @param centerY 光源在Y轴的位置
     * @return
     */
    public static Bitmap sunshine(Bitmap bitmap, int centerX, int centerY) {
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;
        int radius = Math.min(centerX, centerY);

        final float strength = 150F; // 光照强度 100~150
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        int pos = 0;
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                pos = i * width + k;
                pixColor = pixels[pos];

                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);

                newR = pixR;
                newG = pixG;
                newB = pixB;

                // 计算当前点到光照中心的距离，平面座标系中求两点之间的距离
                int distance = (int) (Math.pow((centerY - i), 2) + Math.pow(
                        centerX - k, 2));
                if (distance < radius * radius) {
                    // 按照距离大小计算增加的光照值
                    int result = (int) (strength * (1.0 - Math.sqrt(distance)
                            / radius));
                    newR = pixR + result;
                    newG = pixG + result;
                    newB = pixB + result;
                }

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[pos] = Color.argb(255, newR, newG, newB);
            }
        }

        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    /**
     * 底片效果
     *
     * @param bitmap
     * @return
     */
    public static Bitmap film(Bitmap bitmap) {
        // RGBA的最大值
        final int MAX_VALUE = 255;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        int pos = 0;
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                pos = i * width + k;
                pixColor = pixels[pos];

                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);

                newR = MAX_VALUE - pixR;
                newG = MAX_VALUE - pixG;
                newB = MAX_VALUE - pixB;

                newR = Math.min(MAX_VALUE, Math.max(0, newR));
                newG = Math.min(MAX_VALUE, Math.max(0, newG));
                newB = Math.min(MAX_VALUE, Math.max(0, newB));

                pixels[pos] = Color.argb(MAX_VALUE, newR, newG, newB);
            }
        }

        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    /**
     * 锐化效果
     *
     * @param bitmap
     * @return
     */
    public static Bitmap sharpen(Bitmap bitmap) {
        // 拉普拉斯矩阵
        int[] laplacian = new int[]{-1, -1, -1, -1, 9, -1, -1, -1, -1};

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int idx = 0;
        float alpha = 0.3F;
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + n) * width + k + m];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);

                        newR = newR + (int) (pixR * laplacian[idx] * alpha);
                        newG = newG + (int) (pixG * laplacian[idx] * alpha);
                        newB = newB + (int) (pixB * laplacian[idx] * alpha);
                        idx++;
                    }
                }

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[i * width + k] = Color.argb(255, newR, newG, newB);
                newR = 0;
                newG = 0;
                newB = 0;
            }
        }

        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    /**
     * 浮雕效果
     *
     * @param bitmap
     * @return
     */
    public static Bitmap emboss(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        int pos = 0;
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                pos = i * width + k;
                pixColor = pixels[pos];

                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);

                pixColor = pixels[pos + 1];
                newR = Color.red(pixColor) - pixR + 127;
                newG = Color.green(pixColor) - pixG + 127;
                newB = Color.blue(pixColor) - pixB + 127;

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[pos] = Color.argb(255, newR, newG, newB);
            }
        }

        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 下方和压缩有关
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 按缩放压缩
     *
     * @param src       源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @return 缩放压缩后的图片
     */
    public static Bitmap compressByScale(Bitmap src, int newWidth, int newHeight) {
        return scale(src, newWidth, newHeight, false);
    }

    /**
     * 按缩放压缩
     *
     * @param src       源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @param recycle   是否回收
     * @return 缩放压缩后的图片
     */
    public static Bitmap compressByScale(Bitmap src, int newWidth, int newHeight, boolean recycle) {
        return scale(src, newWidth, newHeight, recycle);
    }

    /**
     * 按缩放压缩
     *
     * @param src         源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @return 缩放压缩后的图片
     */
    public static Bitmap compressByScale(Bitmap src, float scaleWidth, float scaleHeight) {
        return scale(src, scaleWidth, scaleHeight, false);
    }

    /**
     * 按缩放压缩
     *
     * @param src         源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @param recycle     是否回收
     * @return 缩放压缩后的图片
     */
    public static Bitmap compressByScale(Bitmap src, float scaleWidth, float scaleHeight, boolean recycle) {
        return scale(src, scaleWidth, scaleHeight, recycle);
    }

    /**
     * 按质量压缩
     *
     * @param src     源图片
     * @param quality 质量
     * @return 质量压缩后的图片
     */
    public static Bitmap compressByQuality(Bitmap src, @IntRange(from = 0, to = 100) int quality) {
        return compressByQuality(src, quality, false);
    }

    /**
     * 按质量压缩
     *
     * @param src     源图片
     * @param quality 质量
     * @param recycle 是否回收
     * @return 质量压缩后的图片
     */
    public static Bitmap compressByQuality(Bitmap src, @IntRange(from = 0, to = 100) int quality, boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        if (recycle && !src.isRecycled()) src.recycle();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 按质量压缩
     *
     * @param src         源图片
     * @param maxByteSize 允许最大值字节数
     * @return 质量压缩压缩过的图片
     */
    public static Bitmap compressByQuality(Bitmap src, long maxByteSize) {
        return compressByQuality(src, maxByteSize, false);
    }

    /**
     * 按质量压缩
     *
     * @param src         源图片
     * @param maxByteSize 允许最大值字节数
     * @param recycle     是否回收
     * @return 质量压缩压缩过的图片
     */
    public static Bitmap compressByQuality(Bitmap src, long maxByteSize, boolean recycle) {
        if (isEmptyBitmap(src) || maxByteSize <= 0) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        src.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        while (baos.toByteArray().length > maxByteSize && quality > 0) {
            baos.reset();
            src.compress(Bitmap.CompressFormat.JPEG, quality -= 5, baos);
        }
        if (quality < 0) return null;
        byte[] bytes = baos.toByteArray();
        if (recycle && !src.isRecycled()) src.recycle();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 按采样大小压缩
     *
     * @param src        源图片
     * @param sampleSize 采样率大小
     * @return 按采样率压缩后的图片
     */
    public static Bitmap compressBySampleSize(Bitmap src, int sampleSize) {
        return compressBySampleSize(src, sampleSize, false);
    }

    /**
     * 按采样大小压缩
     *
     * @param src        源图片
     * @param sampleSize 采样率大小
     * @param recycle    是否回收
     * @return 按采样率压缩后的图片
     */
    public static Bitmap compressBySampleSize(Bitmap src, int sampleSize, boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        if (recycle && !src.isRecycled()) src.recycle();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    private static boolean isSpace(String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
