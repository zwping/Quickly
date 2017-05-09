package zwp.quickly.custom;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import zwp.quickly.R;
import zwp.quickly.base.BasePopupWindow;
import zwp.quickly.utils.ActivityResultUtils;
import zwp.quickly.utils.StringUtils;
import zwp.quickly.utils.toolsUtils.ImagePickerUtils;

/**
 * 拍照 - 相册选择
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */
public class PhotoPopup extends BasePopupWindow {

    /**
     *
     * @param activity
     * @param multiSelect 是否多选
     * @param multiSelectNum  最多选择多少张图片
     * @param showCamera  是否显示拍照按钮
     */
    public PhotoPopup(final Activity activity,String title, Boolean multiSelect, int multiSelectNum, Boolean showCamera) {
        super(activity);
        if (mPopupView != null) {
            if (!StringUtils.isEmpty(title))
                ((TextView)findViewById(R.id.title)).setText(title); //设置title
            ImagePickerUtils.init(showCamera, multiSelect, multiSelectNum, false, true);
            mPopupView.findViewById(R.id.photo_select).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                    ImagePickerUtils.openImagePicker(activity, ActivityResultUtils.GALLERY);
                }
            });
            mPopupView.findViewById(R.id.photograph).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //拍照
                    dismiss();
                    ImagePickerUtils.openCamera(activity, ActivityResultUtils.GALLERY);
                    //photoPopupInterface.photograph(IntentUtils.takeCamera(activity, new File(folder), ActivityResultUtils.PHOTOGRAPH_TWO));
                }
            });
            mPopupView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
        showPopupWindow();
    }

    @Override
    protected int bindLayout() {
        return R.layout.popup_photo_layout;
    }

    @Override
    protected View clickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    protected Animation showAnimation() {
        return getTranslateAnimation(250 * 2, 0, 300);
    }

    @Override
    protected Animation exitAnimation() {
        return getTranslateAnimation(0, 250 * 2, 100);
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }


    public interface PhotoPopupInterface {
        /** intent返回Uri */
        void photograph(Uri photoUri);
    }

}
