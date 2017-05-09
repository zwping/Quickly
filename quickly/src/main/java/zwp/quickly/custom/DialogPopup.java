package zwp.quickly.custom;

import android.animation.Animator;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import zwp.quickly.R;
import zwp.quickly.base.BasePopupWindow;
import zwp.quickly.utils.SoftKeyBoardUtils;
import zwp.quickly.utils.StringUtils;
import zwp.quickly.utils.ToastUtils;
import zwp.quickly.utils.ViewUtils;

/**
 * 仿写系统dialog，样式自定义更简洁
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class DialogPopup extends BasePopupWindow {

    private RelativeLayout mPopupDismiss = null;
    private TextView mTitle = null;
    private TextView mContent = null;
    private EditText mEditText = null;
    private Button mCancel = null;
    private Button mComplete = null;

    /**
     * bar
     * 提示dialog
     * 标题默认为 提示 cancel默认dismiss
     *
     * @param activity
     * @param content
     * @param cancel
     * @param complete
     * @param completeDismissObserver
     */
    public DialogPopup(Activity activity, @NonNull String content, String cancel, String complete, final OnClickListenerInterface cancelDismissObserver, final OnClickListenerInterface completeDismissObserver) {
        super(activity);

        initView();
        if (mPopupView != null) {
            mContent.setText(content);
            if (cancel == null) mCancel.setText(activity.getString(R.string.no));
            else mCancel.setText(cancel);
            if (complete == null) mComplete.setText(activity.getString(R.string.yes));
            else mComplete.setText(complete);
            mComplete.setVisibility(View.VISIBLE);

            mEditText.setVisibility(View.GONE);

            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                    if (cancelDismissObserver != null)
                        cancelDismissObserver.clickDismissPopup();
                }
            });

            mComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                    if (completeDismissObserver != null)
                        completeDismissObserver.clickDismissPopup();
                }
            });

        }
        if (!isShowing()) {
            showPopupWindow();
        }
    }

    /**
     * 显示输入的dialog
     *
     * @param activity
     * @param editText                输入框的问题
     * @param editTextHint            输入框的hint
     * @param cancel
     * @param complete
     * @param completeDismissObserver
     */
    public DialogPopup(Activity activity, String editText, String editTextHint, String title, String cancel, String complete, final OnClickListenerInterface1 completeDismissObserver) {
        super(activity);
        initView();
        if (mPopupView != null) {
            mContent.setVisibility(View.GONE);
            if (!StringUtils.isEmpty(editText))
                mEditText.setText(editText);
            if (!StringUtils.isEmpty(editTextHint))
                mEditText.setHint(editTextHint);
            if (!StringUtils.isEmpty(title))
                mTitle.setText(title);
            mCancel.setText(cancel);
            mComplete.setText(complete);
            mComplete.setVisibility(View.VISIBLE);

            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            mComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                    if (completeDismissObserver != null)
                        completeDismissObserver.clickDismissPopup(mEditText.getText().toString().trim());
                }
            });
        }
        if (!isShowing()) {
            showPopupWindow();
        }
    }

    private void initView() {
        if (mPopupView != null) {
            mPopupDismiss = ViewUtils.findViewByid(mPopupView, R.id.popup_dismiss);
            mTitle = (TextView) mPopupView.findViewById(R.id.dialog_popup_title);
            mContent = (TextView) mPopupView.findViewById(R.id.dialog_popup_text1);
            mEditText = ViewUtils.findViewByid(mPopupView, R.id.edit_text);
            mCancel = (Button) mPopupView.findViewById(R.id.dialog_popup_cancel);
            mComplete = (Button) mPopupView.findViewById(R.id.dialog_popup_Compelete);

            ViewUtils.setWidgetSelectorAlpha(mCancel);
            ViewUtils.setWidgetSelectorAlpha(mComplete);
            SoftKeyBoardUtils.clearAllEditTextFocusTouchListener(mPopupDismiss);
        }

    }

    @Override
    protected int bindLayout() {
        return R.layout.popup_dialog_layout;
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
