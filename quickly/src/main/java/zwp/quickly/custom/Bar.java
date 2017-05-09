package zwp.quickly.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import zwp.quickly.R;
import zwp.quickly.utils.SoftKeyBoardUtils;
import zwp.quickly.utils.StringUtils;
import zwp.quickly.utils.ViewUtils;
import zwp.quickly.utils.toolsUtils.GlideUtils;

/**
 * 自定义bar
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class Bar extends RelativeLayout {

    private RelativeLayout barLayout;
    private ImageView bar_return;
    private TextView bar_text_center;
    private TextView bar_text_right;
    private ImageView bar_right_img;

    private Activity mActivity;

    public Bar(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.bar_child_layout, this);
        barLayout = (RelativeLayout) findViewById(R.id.bar_layout);
        bar_return = (ImageView) findViewById(R.id.bar_return);
        bar_text_center = (TextView) findViewById(R.id.bar_text_center);
        bar_text_right = (TextView) findViewById(R.id.bar_text_right);
        bar_right_img = (ImageView) findViewById(R.id.bar_right_img);

        ViewUtils.setWidgetSelectorAlpha(bar_return);
        ViewUtils.setWidgetSelectorAlpha(bar_text_right);
        ViewUtils.setWidgetSelectorAlpha(bar_right_img);
    }

    /**
     * 初始化 自定义bar
     *
     * @param activity
     * @param title         不可以为空
     * @param leftIconId    不需要返回键填写-1 / 图片资源ID
     * @param rightContent  为null/任意string/camera/
     * @param leftObserver  如果未finish功能则填写null，需要指定observer则手动指定listener
     * @param rightObserver 为null/指定listener
     * @param rightIconId   右侧图标ID
     * @param cleanBg       是否清楚背景
     */
    public void initBar(@Nullable Activity activity, @Nullable String title, int leftIconId, String rightContent, int rightIconId, Boolean cleanBg, OnClickListener leftObserver, OnClickListener rightObserver) {
        this.mActivity = activity;
        bar_text_center.setText(title);
        if (cleanBg) {
            barLayout.setBackgroundColor(Color.TRANSPARENT);
        }
        if (leftIconId != -1) {
            GlideUtils.glideCenterCropNoErrorPic(activity,leftIconId, bar_return, true);
            if (leftObserver == null) bar_return.setOnClickListener(finishObserver);
            else bar_return.setOnClickListener(leftObserver);
        }
        if (!StringUtils.isEmpty(rightContent)) {
            bar_text_right.setText(rightContent);
            bar_text_right.setOnClickListener(rightObserver);
        }
        if (rightIconId != -1) {
            bar_right_img.setVisibility(VISIBLE);
            GlideUtils.glideCenterCropNoErrorPic(activity,rightIconId, bar_right_img, true);
            bar_right_img.setOnClickListener(rightObserver);
        }
    }

    /**
     * 左侧默认图标，右侧不设置
     *
     * @param activity
     * @param title
     */
    public void initBar(Activity activity, String title) {
        this.mActivity = activity;
        bar_text_center.setText(title);
        GlideUtils.glideCenterCropNoErrorPic(activity,R.mipmap.bar_return_icon1, bar_return, true);
        bar_return.setOnClickListener(finishObserver);
    }

    /**
     * 左侧默认图标，右侧不设置，清空背景
     *
     * @param activity
     * @param title
     * @param cleanBg
     */
    public void initBar(Activity activity, String title, Boolean cleanBg) {
        this.mActivity = activity;
        bar_text_center.setText(title);
        if (cleanBg) {
            barLayout.setBackgroundColor(Color.TRANSPARENT);
        }
        GlideUtils.glideCenterCropNoErrorPic(activity,R.mipmap.bar_return_icon1, bar_return, true);
        bar_return.setOnClickListener(finishObserver);
    }

    /**
     * 左侧默认图标，右侧设置文字
     *
     * @param activity
     * @param title
     * @param rightContent
     * @param rightObserver
     */
    public void initBar(Activity activity, String title, String rightContent, OnClickListener rightObserver) {
        this.mActivity = activity;
        bar_text_center.setText(title);
        GlideUtils.glideCenterCropNoErrorPic(activity,R.mipmap.bar_return_icon1, bar_return, true);
        bar_return.setOnClickListener(finishObserver);
        if (!StringUtils.isEmpty(rightContent)) {
            bar_text_right.setText(rightContent);
            bar_text_right.setOnClickListener(rightObserver);
        }
    }

    /**
     * 左侧默认图标，右侧设置图片
     *
     * @param activity
     * @param title
     * @param rightIconId
     * @param rightObserver
     */
    public void initBar(Activity activity, String title, int rightIconId, OnClickListener rightObserver) {
        this.mActivity = activity;
        bar_text_center.setText(title);
        GlideUtils.glideCenterCropNoErrorPic(activity,R.mipmap.bar_return_icon1, bar_return, true);
        bar_return.setOnClickListener(finishObserver);
        if (rightIconId != -1) {
            GlideUtils.glideCenterCropNoErrorPic(activity,rightIconId, bar_right_img, true);
            bar_right_img.setOnClickListener(rightObserver);
        }
    }


    OnClickListener finishObserver = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mActivity != null) {
                SoftKeyBoardUtils.hideKeyBoard(view);
                mActivity.finish();
            }
        }
    };
}
