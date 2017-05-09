package zwp.quickly.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.shizhefei.fragment.LazyFragment;

import zwp.quickly.Quickly;
import zwp.quickly.custom.NetLoadingPopup;
import zwp.quickly.utils.StartActivityUtils;
import zwp.quickly.utils.ToastUtils;
import zwp.quickly.utils.toolsUtils.GlideUtils;
import zwp.quickly.utils.toolsUtils.LogUtils;

/**
 * <p>describe：fragment基类，应当注意support.v4
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public abstract class BaseFragment extends LazyFragment {

    private Context context = null;

    /**
     * 主线程UI更改的标识，根activity生命周期绑定
     */
    protected Boolean isChangeUi = false; //所有更新UI的方法最好依据@param isCanChangeUi二次封装

    /**
     * 封装Logger，发布正式版本需要更改注释掉logger{@link LogUtils#i(String)}
     */
    protected void logger(String msg) {
        LogUtils.i(msg);
    }

    /**
     * 封装Toast
     */
    protected void showToast(final String msg) {
        if (isChangeUi)
            ToastUtils.showShortSafe(msg);
    }

    protected void showLongToast(final String msg) {
        if (isChangeUi)
            ToastUtils.showLongSafe(msg, Toast.LENGTH_LONG);
    }


    /**
     * 当前Activity渲染的视图View
     */
    protected View mContextView = null;

    /**
     * [绑定布局]
     */
    public abstract int bindLayout();

    /**
     * [业务操作] ，使用butterknife的话，请在该方法首句添加ButterKnife.bind(this, mContextView);
     */
    public abstract void doBusiness(Context mContext);

    /**
     * [防止快速点击]，如果使用了butterknife，请在onclick方法首句添加if (!fastClick()) return; //防止快速点击
     */
    private long lastClick = 0;

    /**
     * @return 小于1秒返回false
     */
    protected boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    /**
     * 获取上下文，为空获取applicationContext
     */
    public Context getContext() {
        if (this.context == null) {
            return Quickly.getContext();
        }
        return this.context;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        isChangeUi = true;
        mContextView = LayoutInflater.from(getContext()).inflate(bindLayout(), null);
        setContentView(mContextView);
        doBusiness(getContext());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        isChangeUi = true;
    }

    @Override
    protected void onPauseLazy() {
        super.onPauseLazy();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        isChangeUi = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    // -----------------Activity跳转及结束-----------在getActivity()中操作，保持总线操作--------------------------------------------

    /**
     * 网络加载等待popupWindow 快捷  在getActivity()中操作，保持总线操作
     */

    /**
     * 图片加载 快捷 默认url/path加载 如果需要泛型加载，请直接使用glideUtils
     */

    protected <T> void glide(T object, ImageView intoImageView) {
        GlideUtils.glide(getContext(), object, intoImageView, true);
    }

    /**
     * 没有加载中与加载失败的图片 ，一般加载resource使用
     */
    protected <T> void glideNoErrorPic(T object, ImageView intoImageView) {
        GlideUtils.glideNoErrorPic(getContext(), object, intoImageView, true);
    }

    /**
     * 封装常用取本地资源的方法
     */
    protected String getStr(@StringRes int strId) {
        return getContext().getResources().getString(strId);
    }

    protected int getCol(@ColorRes int colorId) {
        return ContextCompat.getColor(getContext(), colorId);
    }

}
