package zwp.quickly.custom;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import zwp.quickly.R;
import zwp.quickly.base.BasePopupWindow;
import zwp.quickly.utils.HandlerUtils;
import zwp.quickly.utils.StringUtils;
import zwp.quickly.utils.ToastUtils;
import zwp.quickly.utils.ViewUtils;
import zwp.quickly.widget.CommPdfView;

/**
 * 内容使用pdf展示的 协议popup
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class AgreementPopup extends BasePopupWindow {

    private Button cancel;
    private Button confirm;
    private TextView agreementTitle;
    private CommPdfView agreementPdfView;

    /**
     * 普通协议展示
     */
    public AgreementPopup(final Activity context, String title, final String contentPdfUrl) {
        super(context);
        setPopupWindowFullScreen(false);
        if (mPopupView != null) {
            initView();
            agreementTitle.setVisibility(View.GONE);  //小林提议隐藏2017年3月27日15:47:09
            agreementTitle.setText(title);
            HandlerUtils.runOnUiThreadDelay(new Runnable() {
                @Override
                public void run() {
                    if (!StringUtils.isEmpty(contentPdfUrl))
                        agreementPdfView.initView(contentPdfUrl);
                    else {
                        dismiss();
                        ToastUtils.showShortSafe(context.getString(R.string.network_error));
                    }
                }
            }, 400);
        }
        showPopupWindow();
    }

    /**
     * 展示双按钮的agreement popup
     * @param title
     * @param cancelStr
     * @param confirmStr
     * @param pdfUrl
     * @param confirmDismissObserver
     */
    public AgreementPopup(final Activity context, String title, String cancelStr, String confirmStr, final String pdfUrl, final OnClickListenerInterface confirmDismissObserver) {
        super(context);
        if (mPopupView != null) {
            initView();
            agreementTitle.setText(title);
            cancel.setText(cancelStr);
            confirm.setText(confirmStr);
            confirm.setVisibility(View.VISIBLE);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (confirmDismissObserver != null) confirmDismissObserver.clickDismissPopup();
                }
            });
            HandlerUtils.runOnUiThreadDelay(new Runnable() {
                @Override
                public void run() {
                    if (!StringUtils.isEmpty(pdfUrl))
                        agreementPdfView.initView(pdfUrl);
                    else {
                        dismiss();
                        ToastUtils.showShortSafe(context.getString(R.string.network_error));
                    }
                }
            }, 400);
        }
        showPopupWindow();
    }

    private void initView() {
        confirm = ViewUtils.findViewByid(mPopupView, R.id.confirm);
        cancel = ViewUtils.findViewByid(mPopupView, R.id.agreement_popup_return);
        agreementTitle = ViewUtils.findViewByid(mPopupView, R.id.agreement_popup_title);
        agreementPdfView = ViewUtils.findViewByid(mPopupView, R.id.agreement_popup_pdf_view);
    }

    @Override
    protected Animation initExitAnimation() {
        return null;
    }

    @Override
    protected int bindLayout() {
        return R.layout.popup_agreement_layout;
    }

    @Override
    protected View clickToDismissView() {
        return mPopupView.findViewById(R.id.agreement_popup_return);
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
