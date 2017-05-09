package zwp.quickly.custom;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;

import zwp.quickly.R;
import zwp.quickly.base.BasePopupWindow;

/**
 * <p>describe：支付popup
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class PayPopup extends BasePopupWindow {

    public PayPopup(Activity context, final OnClickListenerInterface alipayClickListenerInterface,final OnClickListenerInterface wechatClickListenerInterface) {
        super(context);
        setPopupWindowFullScreen(true);
        mPopupView.findViewById(R.id.alipay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                alipayClickListenerInterface.clickDismissPopup();
            }
        });
        mPopupView.findViewById(R.id.wechatPay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                wechatClickListenerInterface.clickDismissPopup();
            }
        });
        mPopupView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        showPopupWindow();
    }

    @Override
    protected int bindLayout() {
        return R.layout.popup_pay_layout;
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
}
