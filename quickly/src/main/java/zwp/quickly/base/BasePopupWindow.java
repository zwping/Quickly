package zwp.quickly.base;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;

import zwp.quickly.R;
import zwp.quickly.utils.ScreenUtils;
import zwp.quickly.utils.ViewUtils;

/**
 * basePopupWindow二次封装，通用popup_create
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public abstract class BasePopupWindow extends razerdp.basepopup.BasePopupWindow {

    protected View mPopupView;

    protected abstract int bindLayout();

    /**
     * popupWindow退出选择
     */
    protected abstract View clickToDismissView();

    /**
     * popupWindow显示动画
     */
    protected abstract Animation showAnimation();

    /**
     * popupWindow退出动画
     */
    protected abstract Animation exitAnimation();


    public BasePopupWindow(Activity context) {
        super(context);
        setPopupWindowFullScreen(true);
    }

    @Override
    protected Animation initShowAnimation() {
        return showAnimation();
    }

    @Override
    protected Animation initExitAnimation() {
        return exitAnimation();
    }

    @Override
    public View getClickToDismissView() {
        return clickToDismissView();
    }

    @Override
    public View onCreatePopupView() {
        mPopupView = ViewUtils.layoutInflater(getContext(), bindLayout());
        return mPopupView;
    }

    @Override
    public View initAnimaView() {
        return mPopupView.findViewById(R.id.popup_create);
    }

    public interface OnClickListenerInterface {
        /**
         * 关闭当前popup，继承clickListener，实现方法传递
         */
        void clickDismissPopup();
    }

    public interface OnClickListenerInterface1 {
        /**
         * 关闭当前popup，继承clickListener，实现方法传递
         */
        void clickDismissPopup(String str);
    }

    /**
     * 往下方滑动消失
     */
    public static AnimatorSet getDefaultSlideFromTopAnimationSet(View mAnimaView) {
        AnimatorSet set = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            set = new AnimatorSet();
            if (mAnimaView != null) {
                set.playTogether(
                        ObjectAnimator.ofFloat(mAnimaView, "translationY", 0, 250).setDuration(300),
                        ObjectAnimator.ofFloat(mAnimaView, "alpha", 1f, 0.0f).setDuration(160 * 3 / 2)
                );
            }
        }
        return set;
    }

    /**
     * 上往下，带有回弹效果的动画
     * 带有插值器的animation，动画执行过程中更改变化率(越来越快/突然变快了)
     */
    public static Animation getInterpolatorFromTopAnimation(Context context) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, -ScreenUtils.dip2px(context, 350f), 0);
        translateAnimation.setDuration(450);
        translateAnimation.setInterpolator(new OvershootInterpolator(1));
        return translateAnimation;
    }
    /**
     * 下往上，带有回弹效果的动画
     * 带有插值器的animation，动画执行过程中更改变化率(越来越快/突然变快了)
     */
    public static Animation getInterpolatorFromBottomAnimation(Context context) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, 0, -ScreenUtils.dip2px(context, 350f));
        translateAnimation.setDuration(450);
        translateAnimation.setInterpolator(new OvershootInterpolator(-4));
        return translateAnimation;
    }

    /** 常用已封装动画*/
    //底往上滑动进场(位移动画) getTranslateAnimation(250 * 2, 0, 300);
    //往底部滑动退出           getTranslateAnimation(0, 250 * 2, 100)
    //上往下，带有回弹效果的动画 getInterpolatorFromTopAnimation(getContext());
    //下往上，带有回弹效果的动画 getInterpolatorFromBottomAnimation(getContext());
    //从下方滑动上来(animator动画) getDefaultSlideFromBottomAnimationSet()； (需要实现initShowAnimator,showAnimation返回null)
    //往下方滑动消失 getDefaultSlideFromTopAnimationSet(mAnimaView); (需要实现initExitAnimator,exitAnimation返回null)
    /*相对位置滑动入场(缩放动画)  getScaleAnimation(0.0f, 动画起始时 X坐标上的伸缩尺寸
                                        1.0f, 动画结束时 X坐标上的伸缩尺寸
                                        1.0f, Y
                                        1.0f, Y
                                        Animation.RELATIVE_TO_SELF, 动画在X轴相对于物件位置类型
                                        1.0f, 动画相对于物件的X坐标的开始位置
                                        Animation.RELATIVE_TO_SELF, Y
                                        0.0f); Y
     **相对坐标均以view的左上角为起点；0.0f(起始) -> 1.0(结束)为展现动画，反之为隐藏动画；X或Y有动画，它们对应的起始位置才有效，起始位置为0.0->1.0的相对位置；
     */

    /** 相对View显示popupWindow
     * demo: https://github.com/razerdp/BasePopup/blob/master/app/src/main/java/razerdp/demo/popup/CommentPopup.java
     * 在构造函数中，super(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
     * 注意使用showPopupWindow(View) {@link razerdp.basepopup.BasePopupWindow#showPopupWindow(View)}
     * 在showPopupWindow的super();前面设置setOffsetX{@link BasePopupWindow#setOffsetX(int)} 及 setOffsetY{@link razerdp.basepopup.BasePopupWindow#setOffsetY(int)}
     * 在这说下setOffsetX Y，默认showPopupWindow(View)会以view的左上角作为锚点，setOffsetX Y将以popupView的左上角作为对称点，设置popupView的X Y的偏移量
     * */


}
