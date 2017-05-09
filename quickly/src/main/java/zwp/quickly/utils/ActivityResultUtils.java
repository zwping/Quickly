package zwp.quickly.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;


/**
 * <p>describe：activity返回值常用操作的封装
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class ActivityResultUtils {

    private ActivityResultUtils() {
        throw new UnsupportedOperationException("u can't instantiate me..."); //不支持操作的异常
    }

    /**
     * 拍照返回码 返回bitmap（本地不可见，返回小图）
     */
    @Deprecated
    public static final int PHOTOGRAPH_ONE = 3000;
    /**
     * 拍照返回码 返回uri(本地可见，返回大图)
     */
    public static final int PHOTOGRAPH_TWO = 3001;

    /**
     * 打开图库返回码
     */
    public static final int GALLERY = 3002;

    /**
     * 封装onActivityResult返回值处理结果(系统拍照+imagePicker框架返回)
     *
     * @param bigPhotoUri 拍照获取大图的Uri，用于取消操作删除预建的流
     */
    @Deprecated
    public static void activityResult(Activity activity, int requestCode, int resultCode, Intent data, Uri bigPhotoUri, PhotoResultInterface photoResultInterface) {
        // TODO: 2017/2/21 判断取消操作resultCode == RESULT_CANCELED
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {   //使用ImagePicker框架 相册获取
            if (data != null && requestCode == GALLERY) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                photoResultInterface.resultImagePickerPhoto(images);
            } else {
                photoResultInterface.resultError();
            }
        }
        switch (requestCode) {
            case PHOTOGRAPH_ONE: //拍照获取小图
                if (resultCode == activity.RESULT_CANCELED)
                    photoResultInterface.resultOneSmallPhoto(null, true);
                Bitmap photoBitmap = null;
                //两种方式 获取拍好的图片
                if (data == null) return;
                if (data.getData() != null || data.getExtras() != null) { //防止没有返回结果
                    Uri uri = data.getData();
                    if (uri != null) {
                        photoBitmap = BitmapFactory.decodeFile(uri.getPath()); //拿到图片
                    }
                    if (photoBitmap == null) {
                        Bundle bundle = data.getExtras();
                        if (bundle != null) {
                            photoBitmap = (Bitmap) bundle.get("data");
                        } else {
                            photoResultInterface.resultError();
                        }
                    }
                    if (photoBitmap != null) {
                        photoResultInterface.resultOneSmallPhoto(photoBitmap, false);
                    } else {
                        photoResultInterface.resultError();
                    }
                }
            case PHOTOGRAPH_TWO: //拍照获取大图
                // TODO: 2017/2/21 需要获取intent返回的Uri 注意，取消拍照需要deleteUri
                if (resultCode == activity.RESULT_CANCELED) {
                    FileUtils.deleteFile(bigPhotoUri.getPath());
                    photoResultInterface.resultOneBigPhoto(true);
                } else photoResultInterface.resultOneBigPhoto(false);
            case GALLERY:
                // TODO: 2017/2/21 intent调用系统相册
        }
    }

    /**
     * 针对imagePicker框架activityResult返回值封装
     */
    public static void imagePickerResult(int requestCode, int resultCode, Intent data, ImagePickerActivityResultInterface imagePickerActivityResultInterface) {
        // TODO: 2017/2/21 判断取消操作resultCode == RESULT_CANCELED
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {   //使用ImagePicker框架 相册获取
            if (data != null && requestCode == GALLERY) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                imagePickerActivityResultInterface.resultImagePickerPhoto(images);
            } else {
                imagePickerActivityResultInterface.resultError();
            }
        }
    }

    public interface ImagePickerActivityResultInterface {
        /**
         * imagePicker框架 返回多张图片 / 一张图片
         */
        void resultImagePickerPhoto(ArrayList<ImageItem> images);

        /**
         * 选择照片失败
         */
        void resultError();
    }

    public interface PhotoResultInterface {
        /**
         * 返回一张图片(如果为拍照返回的结果则小图)
         */
        @Deprecated
        void resultOneSmallPhoto(Bitmap photoBitmap, Boolean isCancel);

        /**
         * 返回一张图片(如果为拍照返回的结果则大图*图片地址为intent返回的uri*)
         */
        void resultOneBigPhoto(Boolean isCancel);

        /**
         * imagePicker框架 返回多张图片 / 一张图片
         */
        void resultImagePickerPhoto(ArrayList<ImageItem> images);

        /**
         * 选择照片失败
         */
        void resultError();
    }

}
