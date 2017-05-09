package zwp.quickly.custom;

import android.app.Activity;
import android.view.View;

import zwp.quickly.R;
import zwp.quickly.base.BasePopupWindow;
import zwp.quickly.utils.CameraUtils;
import zwp.quickly.utils.IntentUtils;
import zwp.quickly.utils.RecordingUtils;

/**
 * <p>describe：封装常用的场景
 * <p>version：V1.0
 * <p>author：zwp on 2017/2/21 10:32 mail：1101558280@qq.com web: www.zcyke.com</p>
 */

public class CommonScene {

    private CommonScene() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断是否存在相机权限，不存在显示dialog
     *
     * @return 有相机权限返回true
     */
    public static boolean cameraProhibitPopup(final Activity activity) {
        if (CameraUtils.isCameraGranted()) return true;
        new DialogPopup(activity, activity.getString(R.string.camera_not_available_is_to_open),
                activity.getString(R.string.no), activity.getString(R.string.yes),null, new BasePopupWindow.OnClickListenerInterface() {
            @Override
            public void clickDismissPopup() {
                IntentUtils.openAppDetail(activity);
            }
        });
        return false;
    }

    /**
     * 判断是否存在录音权限，不存在显示dialog
     * @return 有录音权限返回true
     */
    public static boolean micProhibitPopup(final Activity activity){
        if (RecordingUtils.isHasPermission(activity)) return true;
        new DialogPopup(activity, activity.getString(R.string.recording_not_available_go_to_open),
                activity.getString(R.string.no), activity.getString(R.string.yes), null,new BasePopupWindow.OnClickListenerInterface() {
            @Override
            public void clickDismissPopup() {
                IntentUtils.openAppDetail(activity);
            }
        });
        return false;
    }

}
