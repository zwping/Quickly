package zwp.quickly.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * <p>describe：相机操作
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class CameraUtils {

    /**
     * 判断相机能是否可用
     */
    public static boolean isCameraGranted() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            // setParameters 是针对魅族MX5 做的。MX5 通过Camera.open() 拿到的Camera
            // 对象不为null
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            canUse = false;
        }
        if (mCamera != null) {
            mCamera.release();
        }
        return canUse;
    }

    /**
     * 打开系统图库
     *
     * @param activity
     */
    public static void openPicture(Activity activity,int requestCode) {
        IntentUtils.openGallery2(activity,requestCode);
    }

    /**
     * 显示图片到相册
     *
     * @param context
     * @param photoFile 要保存的图片文件
     */
    public static void displayToGallery(Context context, File photoFile) {
        if (photoFile == null || !photoFile.exists()) {
            return;
        }
        String photoPath = photoFile.getAbsolutePath();
        String photoName = photoFile.getName();
        // 其次把文件插入到系统图库
        try {
            ContentResolver contentResolver = context.getContentResolver();
            MediaStore.Images.Media.insertImage(contentResolver, photoPath, photoName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + photoPath)));
    }

    // TODO: 2017/4/15 后续整理出拍照获取图片的两种方法+拍照后剪切+使用系统图库获取单张或多张图片
}
