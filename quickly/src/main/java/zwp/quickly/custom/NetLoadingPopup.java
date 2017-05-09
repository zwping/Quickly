package zwp.quickly.custom;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;

import com.ldoublem.loadingviewlib.view.LVCircularRing;

import zwp.quickly.R;
import zwp.quickly.base.BasePopupWindow;

/**
 * 网络加载等待popup
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class NetLoadingPopup extends BasePopupWindow {

    public NetLoadingPopup(Activity context) {
        super(context);
        if (mPopupView != null) {
            ((LVCircularRing)mPopupView.findViewById(R.id.loading)).startAnim();
        }
    }

    @Override
    protected int bindLayout() {
        return R.layout.popup_net_loading_layout;
    }

    @Override
    protected View clickToDismissView() {
        return null;
    }

    @Override
    protected Animation showAnimation() {
        return getDefaultAlphaAnimation();
    }

    @Override
    protected Animation exitAnimation() {
        return null;
    }


}
