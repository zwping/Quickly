package zwp.quickly.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

import zwp.quickly.R;
import zwp.quickly.utils.HandlerUtils;
import zwp.quickly.utils.ViewUtils;


/**
 * <p>describe：自定义获取验证码控件，主要实现倒计时
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 * <p>
 * 注意
 * - timer需要和生命周期绑定
 */

public class CommGetVerify extends RelativeLayout {

    private Button mVerifyBtn;
    /**
     * 倒计时时间
     */
    private int mCountdown;
    /**
     * 保留倒计时时间
     */
    private int mCountdowns;
    /**
     * 倒计时提示语
     */
    private VerifyBtnHintType mBtnHintType;
    private String[] str = new String[1];
    /**
     * 提示语
     */
    private String mCommMarkedWords;

    private Timer timer;
    private TimerTask timerTask;
    /**如果timer在运行，则不能修改btn属性*/
    private boolean isTimerRun = false;

    public CommGetVerify(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.comm_button, this);
    }

    private void initView() {
        mVerifyBtn = ViewUtils.findViewByid(this, R.id.comm_button);
        mVerifyBtn.setBackgroundResource(R.drawable.comm_get_verify_button_bg);
        setBtnClickable(false);
        isTimerRun = false;
        mVerifyBtn.setTextColor(Color.WHITE);
    }

    /**
     * 初始化获取验证码控件，使用netLoading防止多点击，使用startTimer()方法开始倒计时，onDestroy绑定stopTimer()
     *
     * @param countdownTime 倒计时时间
     * @param btnHintType   倒计时提示语类型，可为 (重新获取60s) / (60s)
     */
    public void init(int countdownTime, VerifyBtnHintType btnHintType, OnClickListener onClickListener) {
        initView();
        mCountdowns = countdownTime;
        // 获取btn上面倒计时的提示语
        mBtnHintType = btnHintType;
        // 获取btn上面的提示语
        mCommMarkedWords = getResources().getString(R.string.get_verify);
        mVerifyBtn.setText(mCommMarkedWords);
        // 设置监听
        mVerifyBtn.setOnClickListener(onClickListener);
    }

    /**
     * 初始化获取验证码控件，使用netLoading防止多点击，使用startTimer()方法开始倒计时，onDestroy绑定stopTimer()
     *
     * @param countdownTime    倒计时时间
     * @param btnHintType      倒计时提示语类型，可为 (重新获取60s) / (60s)
     * @param isGetEmailVerify 是否是获取邮箱验证码，如果不是统一为获取btn.setText为“获取验证码”，反之则为“获取邮箱验证码”
     */
    public void init(int countdownTime, VerifyBtnHintType btnHintType, Boolean isGetEmailVerify, OnClickListener onClickListener) {
        initView();
        mCountdowns = countdownTime;
        // 获取btn上面倒计时的提示语
        mBtnHintType = btnHintType;
        // 获取btn上面的提示语
        mCommMarkedWords = getResources().getString(R.string.get_verify);
        if (isGetEmailVerify) mCommMarkedWords = getResources().getString(R.string.get_verify2);
        mVerifyBtn.setText(mCommMarkedWords);

        // 设置监听
        mVerifyBtn.setOnClickListener(onClickListener);
    }

    public enum VerifyBtnHintType {
        NUMBER, NUMBER2CHINESE
    }

    private void verifyUiChange() {
        switch (mBtnHintType) {
            case NUMBER:
                str[0] = String.valueOf(mCountdown) + "S";
                break;
            case NUMBER2CHINESE:
                str[0] = getResources().getString(R.string.re_get_verify) + "(" + String.valueOf(mCountdown) + "S)";
                break;
        }
        --mCountdown;
        HandlerUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mVerifyBtn.setText(str[0]);
            }
        });
    }


    /**
     * 设置BTN是否可用
     * @param clickable  true 可点击/未选中 false 不可点击/选中
     */
    public void setBtnClickable(Boolean clickable) {
        if (isTimerRun) return;
        mVerifyBtn.setEnabled(clickable);
        mVerifyBtn.setSelected(!clickable);
    }

    //需要实现的接口 能改变是否选中/请求成功倒计时开始/关闭计时器(绑定生命周期)/

    /**
     * 后台发送验证码成功则调用
     */
    public void startTimer() {
        if (mVerifyBtn == null)
            throw new IllegalStateException("Please invoke init method first.");
        mCountdown = mCountdowns;
        setBtnClickable(false);
        isTimerRun = true;
        if (timer == null)
            timer = new Timer();
        if (timerTask == null)
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (mCountdown == 0) {
                        stopTimer();
                    } else verifyUiChange();
                }
            };
        timer.schedule(timerTask, 0, 1000);
    }

    private void stopTimer() {
        if (mVerifyBtn == null)
            throw new IllegalStateException("Please invoke init method first.");
        cancelTimer();
        HandlerUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isTimerRun = false;
                setBtnClickable(true);
                mVerifyBtn.setText(mCommMarkedWords);
            }
        });
    }

    /**
     * 与当前activity destroy绑定
     */
    public void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }
}
