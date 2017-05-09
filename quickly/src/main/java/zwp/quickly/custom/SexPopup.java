package zwp.quickly.custom;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import zwp.quickly.R;
import zwp.quickly.base.BasePopupWindow;

/**
 * 性别选择 只包含男/女
 * <p>
 * 选择延时400 ms触发接口
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class SexPopup extends BasePopupWindow {

    private SexPopupCompleteInterface sexPopupCompleteInterface;
    private LinearLayout boy_layout = null;
    private LinearLayout girl_layout = null;
    private RadioButton boy = null;
    private RadioButton girl = null;

    /**
     * 性别选择
     *
     * @param context
     * @param defaultSelect 默认选择性别，可以为空
     * @param sexPopupCompleteInterface1
     */
    public SexPopup(final Activity context, Boolean defaultSelect, final SexPopupCompleteInterface sexPopupCompleteInterface1) {
        super(context);
        if (mPopupView != null) {
            initView();
            this.sexPopupCompleteInterface = sexPopupCompleteInterface1;
            if (defaultSelect != null) {
                if (defaultSelect) {
                    boy.setChecked(true);
                } else {
                    girl.setChecked(true);
                }
            }
            boy_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    operatingResult(context,true);
                }
            });
            girl_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    operatingResult(context,false);
                }
            });
            boy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    operatingResult(context,true);
                }
            });
            girl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    operatingResult(context,false);
                }
            });
        }
        showPopupWindow();
    }

    private String name = null;

    /**
     * 操作结果
     *
     * @param selectResult true -- boy / false -- girl
     */
    private void operatingResult(Activity activity, boolean selectResult) {
        name = activity.getResources().getString(R.string.girl);
        boolean result = false;
        if (selectResult) { //boy
            name = activity.getResources().getString(R.string.boy);
            result = true;
        }

        boy.setChecked(result);
        girl.setChecked(!result);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
                sexPopupCompleteInterface.sexPopupComplete(name);
            }
        }, 400);
    }

    private void initView() {
        boy_layout = (LinearLayout) mPopupView.findViewById(R.id.sex_boy);
        girl_layout = (LinearLayout) mPopupView.findViewById(R.id.sex_girl);
        boy = (RadioButton) mPopupView.findViewById(R.id.sex_boy_radio);
        girl = (RadioButton) mPopupView.findViewById(R.id.sex_girl_radio);
    }


    @Override
    protected int bindLayout() {
        return R.layout.popup_sex_layout;
    }

    @Override
    protected Animation showAnimation() {
        return getDefaultScaleAnimation();
    }

    @Override
    protected View clickToDismissView() {
        return null;
    }

    @Override
    protected Animation exitAnimation() {
        return null;
    }


    public interface SexPopupCompleteInterface {
        void sexPopupComplete(String sexStr);
    }


}
