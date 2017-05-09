package zwp.quickly.utils.toolsUtils;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.io.File;

import zwp.quickly.Quickly;
import zwp.quickly.R;
import zwp.quickly.base.BaseApplication;

/**
 * <p>describe：imagePicker框架 工具
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 * https://github.com/jeasonlzy/ImagePicker
 */

public class ImagePickerUtils {

    private ImagePickerUtils() {
        throw new UnsupportedOperationException("u can't instantiate me..."); //不支持操作的异常
    }

    /** 直接打开相机 */
    public static void openCamera(Activity activity,int requestCode) {
        Intent intent = new Intent(activity, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
        activity.startActivityForResult(intent, requestCode);
    }

    /** 打开相册 */
    public static void openImagePicker(Activity activity,int requestCode) {
        Intent intent = new Intent(activity, ImageGridActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 初始化imagePickerUtils框架
     *
     * @param showCamera       显示拍照按钮
     * @param multiSelect      是否多选
     * @param multiSelectNum   多选选中数量限制
     * @param isCut            允许裁剪（单选有效）
     * @param cutSaveRectangle 裁剪后是否按矩形保存
     */
    public static void init(Boolean showCamera, Boolean multiSelect, int multiSelectNum, Boolean isCut, Boolean cutSaveRectangle) {
        // 设置自定义相册
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(showCamera);  //显示拍照按钮
        imagePicker.setCrop(isCut);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(cutSaveRectangle); //是否按矩形区域保存
        imagePicker.setMultiMode(multiSelect);   //单选false or多选true
        imagePicker.setSelectLimit(multiSelectNum);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //*****裁剪框的形状CIRCLE圆/RECTANGLE矩形
        imagePicker.setFocusWidth(Quickly.screenWidth);   //*****裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(Quickly.screenHeight);  //*****裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(Quickly.screenWidth);//*****保存文件的宽度。单位像素 裁剪过后保存的宽度
        imagePicker.setOutPutY(Quickly.screenHeight);//*****保存文件的高度。单位像素 裁剪过后保存的高度
    }


    private static class PicassoImageLoader implements ImageLoader {
        @Override
        public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
            Glide.with(activity)//
                    .load(new File(path))//
                    .placeholder(R.mipmap.default_image)//
                    .error(R.mipmap.default_image)//
                    .into(imageView);
        }

        @Override
        public void clearMemoryCache() {
            //这里是清除缓存的方法,根据需要自己实现
        }
    }
}
