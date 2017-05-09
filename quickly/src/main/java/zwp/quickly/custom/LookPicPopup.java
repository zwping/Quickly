package zwp.quickly.custom;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bm.library.PhotoView;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;

import java.util.ArrayList;
import java.util.List;

import zwp.quickly.R;
import zwp.quickly.base.BasePopupWindow;
import zwp.quickly.utils.BarUtils;
import zwp.quickly.utils.HandlerUtils;
import zwp.quickly.utils.TimeUtils;
import zwp.quickly.utils.ViewUtils;
import zwp.quickly.utils.toolsUtils.GlideUtils;
import zwp.quickly.utils.toolsUtils.LogUtils;

/**
 * 图片查看popup
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class LookPicPopup extends BasePopupWindow {

    private View statusBar = null;
    private ImageView exitImg = null;
    private FixedIndicatorView indicator = null;
    private ViewPager viewpager = null;
    private RelativeLayout multiplePicLayout = null;
    private PhotoView singlePic = null;

    private Activity mActivity = null;
    private String mImgServiceUrl = null;

    private List<String> mData = null;
    private IndicatorViewPager mIndicatorViewPager = null;


    /**
     * 多张图片查看器
     *
     * @param imgServiceUrl  图片服务器地址
     * @param imgPathList  图片地址
     * @param itemPosition 当前图片的位置
     */
    public LookPicPopup(Activity activity, String imgServiceUrl, final List<String> imgPathList, int itemPosition) {
        super(activity);
        if (mPopupView != null) {
            initView();
            mActivity = activity;
            singlePic.setVisibility(View.GONE);
            mImgServiceUrl = imgServiceUrl;

            BarUtils.replaceStatusBarHeight(activity, statusBar);

            mData = new ArrayList<>();
            mData.add(imgPathList.get(0));

            exitImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            mIndicatorViewPager = new IndicatorViewPager(indicator, viewpager);
            mIndicatorViewPager.setAdapter(adapter);
            mIndicatorViewPager.setCurrentItem(itemPosition, true);

            HandlerUtils.runOnUiThreadDelay(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < imgPathList.size(); i++) {
                        if (i > 0) {
                            mData.add(imgPathList.get(i));
                        }
                    }
                    mIndicatorViewPager.notifyDataSetChanged();
                }
            },500); //注意这个时间，一定更要比popup展现动画的时间要长，不然会造成展现卡顿
        }
        showPopupWindow();
    }

    /**
     * 单张图片查看
     *
     * @param activity
     * @param imgPath
     */
    public LookPicPopup(Activity activity, String imgPath) {
        super(activity);
        if (mPopupView != null) {
            initView();
            mActivity = activity;
            multiplePicLayout.setVisibility(View.GONE);

            // 启用图片缩放功能
            singlePic.enable();
            // 禁用图片缩放功能 (默认为禁用，会跟普通的ImageView一样，缩放功能需手动调用enable()启用)
            //photoView.disenable();
            // 获取/设置 最大缩放倍数
            singlePic.setMaxScale(1.5f);
            GlideUtils.glide(activity, imgPath, singlePic, true);
            singlePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        showPopupWindow();
    }

    /*初始化控件*/
    private void initView() {
        if (mPopupView != null) {
            statusBar = ViewUtils.findViewByid(mPopupView, R.id.status_bar);
            indicator = ViewUtils.findViewByid(mPopupView, R.id.indicator);
            viewpager = ViewUtils.findViewByid(mPopupView, R.id.viewpager);
            multiplePicLayout = ViewUtils.findViewByid(mPopupView, R.id.multiple_pic_layout);
            singlePic = ViewUtils.findViewByid(mPopupView, R.id.single_pic);
            exitImg = ViewUtils.findViewByid(mPopupView, R.id.exit_icon);
        }
    }

    @Override
    protected int bindLayout() {
        return R.layout.popup_look_pic_layout;
    }

    @Override
    protected View clickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    protected Animation showAnimation() {
        return getDefaultScaleAnimation();
    }

    @Override
    protected Animation exitAnimation() {
        return null;
    }


    /*viewpager adapter*/
    private IndicatorViewPager.IndicatorViewPagerAdapter adapter = new IndicatorViewPager.IndicatorViewPagerAdapter() {
        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = ViewUtils.layoutInflater(mActivity, R.layout.item_look_pic_tab);
            }
            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = ViewUtils.layoutInflater(mActivity, R.layout.item_look_pic_viewpager);
                PhotoView photoView = ViewUtils.findViewByid(convertView, R.id.full_popup_img);
                // 启用图片缩放功能
                photoView.enable();
                // 禁用图片缩放功能 (默认为禁用，会跟普通的ImageView一样，缩放功能需手动调用enable()启用)
                //photoView.disenable();
                // 获取/设置 最大缩放倍数
                photoView.setMaxScale(1.5f);
                GlideUtils.glide(mActivity, mImgServiceUrl + mData.get(position), photoView, true);
            }
            return convertView;
        }
    };

}
