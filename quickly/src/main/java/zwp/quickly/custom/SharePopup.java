package zwp.quickly.custom;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zwp.quickly.R;
import zwp.quickly.base.BasePopupWindow;
import zwp.quickly.utils.ToastUtils;
import zwp.quickly.utils.ViewUtils;
import zwp.quickly.utils.toolsUtils.GlideUtils;
import zwp.quickly.utils.toolsUtils.LogUtils;

/**
 * <p>describe：分享popup ， 包含viewPager + Recycler 经典结构
 * <p>    note：使用前修改{@link #showPopup(Activity, String, String, String, String)}  {@link #getShareList(Context)} <br />
 *              入口为{@link #showPopup(Activity, String, String, String, String)}
 * <p>  author：zwp on 2017/4/25 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */

public class SharePopup extends BasePopupWindow {

    private Activity mContext;

    /** 拼接viewPager和indicator的封装方法 */
    private IndicatorViewPager indicatorViewPager;
    private ViewPager mViewPager;
    private ScrollIndicatorView mIndicatorView;
    /** 当前页码 */
    private int currentPage = 0;
    private List<ShareItem_Info> mData = null;
    /** 每页recycler中item最大数量*/
    private int pageMax = 12;
    /** 通过data计算出来页的数量 */
    private int pageSize = 0;

    private RecyclerItemClickInterface recyclerItemClickInterface;

    public SharePopup(Activity context,RecyclerItemClickInterface recyclerItemClickInterface1) {
        super(context);
        mContext = context;
        recyclerItemClickInterface = recyclerItemClickInterface1;

        mData = getShareList(context);

        //计算Viewpager的页数
        pageSize = (mData.size() / pageMax); //求余等0
        if ((mData.size() % pageMax) != 0) pageSize = pageSize + 1;

        initView();
        showPopupWindow();
    }

    private void initView() {
        mPopupView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mViewPager = ViewUtils.findViewByid(mPopupView, R.id.popup_viewpager);
        mIndicatorView = ViewUtils.findViewByid(mPopupView, R.id.popup_indicator);
        indicatorViewPager = new IndicatorViewPager(mIndicatorView, mViewPager);
        indicatorViewPager.setAdapter(viewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int bindLayout() {
        return R.layout.popup_share_layout;
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


    /** 设置viewpager */
    private IndicatorViewPager.IndicatorViewPagerAdapter viewPagerAdapter = new IndicatorViewPager.IndicatorViewPagerAdapter() {
        @Override
        public int getCount() {
            return pageSize;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null){
                convertView = ViewUtils.layoutInflater(mContext, R.layout.tab_share_popup_indicator);
            }
            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {  //设置viewpagerItem中的recyclerView
                convertView = ViewUtils.layoutInflater(mContext, R.layout.comm_recycler_view);
                setAdapter(((RecyclerView)convertView),position);
            }
            return convertView;
        }
    };

    /**
     * 设置recyclerView
     * @param recyclerView
     * @param position 当前viewPager的显示页数，页数的数量已经通过mData计算出来
     */
    private void setAdapter(RecyclerView recyclerView,int position) {
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));   //添加item之间线条
//        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));

        //取得当前recycler的数据，viewpager中每页的数据最大值为12
        List<ShareItem_Info> itemInfo = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            if (i >= (pageMax * position) && i < (pageMax * (position + 1))) { //0 11,12 23,24 35,36 47
                itemInfo.add(mData.get(i));
            }
        }

        recyclerView.setAdapter(new Adapter(R.layout.item_share_popup_list,itemInfo));
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                dismiss();
                recyclerItemClickInterface.itemClickListener(mData.get((position + (pageMax * currentPage))).getPlatform());
            }
        });
    }
    /** recyclerAdapter*/
    private class Adapter extends BaseQuickAdapter<ShareItem_Info, BaseViewHolder> {

        public Adapter(int layoutResId, List<ShareItem_Info> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ShareItem_Info item) {
            helper.setText(R.id.item_share_name, item.getName());
            GlideUtils.glide(getContext(),item.getIconResId(), (ImageView) helper.getView(R.id.item_share_img), true);
        }
    }

    /**
     * 针对recyclerView的item点击编写的接口
     */
    public interface RecyclerItemClickInterface {
        /**
         *
         * @param platform  平台名称
         */
        void itemClickListener(String platform);
    }

    /*SharePopup.showPopup(activity(),"随行宝分享标题","随行宝随行包","http://www.zcyke.com","http://img2.niutuku.com/desk/1208/1947/ntk-1947-21023.jpg");*/
    /** 封装显示popup的方法 */
    public static void showPopup(final Activity activity, final String share_title, final String share_content, final String share_url, final String headPicUrl) {
        new SharePopup(activity, new SharePopup.RecyclerItemClickInterface() {
            @Override
            public void itemClickListener(String platform) {
//                MobUtils.showShare(activity, platform, share_title, share_content,
//                        headPicUrl,
//                        share_url,
//                        "【"+activity.getString(R.string.app_name)+"】",
//                        activity.getString(R.string.app_name),
//                        new PlatformActionListener() {
//                            @Override
//                            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                                LogUtils.i(platform.getName() + "分享完成");
//                                ToastUtils.showToast(activity.getString(R.string.share_success));
//                            }
//
//                            @Override
//                            public void onError(Platform platform, int i, Throwable throwable) {
//                                LogUtils.i(platform.getName() + "分享失败");
//                                ToastUtils.showToast(activity.getString(R.string.share_fail));
//                            }
//
//                            @Override
//                            public void onCancel(Platform platform, int i) {
//                                LogUtils.i(platform.getName() + "分享取消");
//                                ToastUtils.showToast(activity.getString(R.string.share_cancel));
//                            }
//                        });
            }
        });
    }


    /**
     * <p>describe：自定义分享的item数据实体类
     */
    public class ShareItem_Info implements Serializable {
        private int iconResId;
        private String name;
        private String platform;

        public int getIconResId() {
            return iconResId;
        }

        public void setIconResId(int iconResId) {
            this.iconResId = iconResId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public ShareItem_Info combination(int resId, String name, String platform) {
            ShareItem_Info info = new ShareItem_Info();
            info.setIconResId(resId);
            info.setName(name);
            info.setPlatform(platform);
            return info;
        }
    }

    /**
     * share集合
     */
    public static List<ShareItem_Info> getShareList(Context context) {
        List<ShareItem_Info> list = new ArrayList<>();
//        list.add(ShareItem_Info.combination(R.drawable.ssdk_oks_classic_sinaweibo,"新浪微博", ShareSDK.getPlatform(SinaWeibo.NAME).getName()));
//        list.add(ShareItem_Info.combination(R.drawable.ssdk_oks_classic_qq,"QQ", ShareSDK.getPlatform(QQ.NAME).getName()));
//        list.add(ShareItem_Info.combination(R.drawable.ssdk_oks_classic_qzone,"QQ空间", ShareSDK.getPlatform(QZone.NAME).getName()));
//
//        list.add(ShareItem_Info.combination(R.drawable.ssdk_oks_classic_wechat, "微信好友", ShareSDK.getPlatform(Wechat.NAME).getName()));
//        list.add(ShareItem_Info.combination(R.drawable.ssdk_oks_classic_wechatfavorite, "微信收藏", ShareSDK.getPlatform(WechatFavorite.NAME).getName()));
//        list.add(ShareItem_Info.combination(R.drawable.ssdk_oks_classic_wechatmoments, "微信朋友圈", ShareSDK.getPlatform(WechatMoments.NAME).getName()));

//        list.add(ShareItem_Info.combination(R.drawable.ssdk_oks_classic_instapaper,context.getString(R.string.Instapaper), ShareSDK.getPlatform(Instapaper.NAME).getName()));
//        list.add(ShareItem_Info.combination(R.drawable.ssdk_oks_classic_twitter,context.getString(R.string.twitter), ShareSDK.getPlatform(Twitter.NAME).getName()));
//        list.add(ShareItem_Info.combination(R.drawable.ssdk_oks_classic_line,context.getString(R.string.Line), ShareSDK.getPlatform(Line.NAME).getName()));
//        list.add(ShareItem_Info.combination(R.drawable.ssdk_oks_classic_instagram,context.getString(R.string.Instagram), ShareSDK.getPlatform(Instagram.NAME).getName()));
//        list.add(ShareItem_Info.combination(R.drawable.ssdk_oks_classic_googleplus,context.getString(R.string.google_plus), ShareSDK.getPlatform(GooglePlus.NAME).getName()));
//        list.add(ShareItem_Info.combination(R.drawable.ssdk_oks_classic_facebook,context.getString(R.string.facebook), ShareSDK.getPlatform(Facebook.NAME).getName()));
//        list.add(ShareItem_Info.combination(R.drawable.ssdk_oks_classic_shortmessage,context.getString(R.string.sms), ShareSDK.getPlatform(ShortMessage.NAME).getName()));
//        list.add(ShareItem_Info.combination(R.drawable.ssdk_oks_classic_email,context.getString(R.string.email), ShareSDK.getPlatform(Email.NAME).getName()));
        return list;
    }

}