package zwp.quickly.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import zwp.quickly.R;
import zwp.quickly.custom.NetLoadingPopup;
import zwp.quickly.utils.APPManager;
import zwp.quickly.utils.AppUtils;
import zwp.quickly.utils.BarUtils;
import zwp.quickly.utils.StartActivityUtils;
import zwp.quickly.utils.ToastUtils;
import zwp.quickly.utils.toolsUtils.GlideUtils;
import zwp.quickly.utils.toolsUtils.LogUtils;

/**
 * <p>describe：activity基类，封装常用的方法，还添加了一个接口（在setContentView前面需要使用方法，百度地图就需要在setContentView前调用方法）
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public abstract class BaseAboveActivity extends AppCompatActivity {

    /**
     * 主线程UI更改的标识，根activity生命周期绑定
     */
    protected Boolean isChangeUi = false; //所有更新UI的方法最好依据@param isCanChangeUi二次封装

    protected Context context() {
        return this;
    }

    protected Activity activity() {
        return this;
    }

    /**
     * 封装Logger，只允许在Debug模式下打印
     */
    protected void logger(Object msg) {
            LogUtils.i(msg);
    }

    /**
     * 封装Toast
     */
    protected void showToast(Object msg) {
        ToastUtils.showShortSafe(msg);
    }

    protected void showLongToast(Object msg) {
        ToastUtils.showLongSafe(msg);
    }

    /**
     * 当前Activity渲染的视图View
     */
    private View mContextView = null;

    @Override
    public void finish() {
        if (isChangeUi)
            super.finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isChangeUi = true;
        APPManager.addActivity(this); //添加当前Activity到信息activity管理类
        initIntentParameter(getIntent().getExtras());
        aboveSetContentView();
        mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        setContentView(mContextView);
        setStatusBarColor();//设置状态栏颜色与项目主颜色同步
        doBusiness(this);
    }

    /**
     * 设置状态栏颜色（api 4.4以上才可以设置状态栏颜色）
     */
    public void setStatusBarColor() {
        BarUtils.setStatusBarColor(activity(), getCol(R.color.main_color),0);
    }

//    /**
//     * 隐藏状态栏
//     */
//    public void setfullScreen() {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//    }
//
//    /**
//     * 设置屏幕常亮
//     */
//    public void setScreenAlwaysBright() {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//    }
//
//    /**
//     * 设置屏幕方向
//     */
//    public void requestScreenOrientation(Boolean portraitOrLandscape) {
//        if (portraitOrLandscape) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
//        } else {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
//        }
//    }

    /**
     * [绑定布局]
     */
    public abstract int bindLayout();

    /**
     * [初始化传值过来的Bundle参数，如果木有传值，可不做处理]
     */
    public abstract void initIntentParameter(Bundle parameter);

    /**
     * 在setContentView方法前面使用的方法
     * */
    public abstract void aboveSetContentView();

    /**
     * [业务操作]，使用butterknife的话，请在该方法首句添加ButterKnife.bind(this, mContextView);
     */
    public abstract void doBusiness(Context mContext);

    /**
     * [防止快速点击]，如果使用了butterknife，请在onclick方法首句添加if (!fastClick()) return; //防止快速点击
     */
    private long lastClick = 0;

    protected boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isChangeUi = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isChangeUi = false;
        APPManager.removeActivity(this);
    }


    // -----------------Activity跳转及结束-------------------------------------------------------
    protected void startActivityAnim(Class<?> targetClass) {
        if (isChangeUi)
            StartActivityUtils.startActivityAnim(activity(), targetClass);
    }

    protected void startActivityAnim(Class<?> targetClass, Bundle bundle) {
        if (isChangeUi)
            StartActivityUtils.startActivityAnim(activity(), targetClass, bundle);
    }

    protected void startActivityAnimFinish(Class<?> targetClass, Bundle bundle) {
        if (isChangeUi)
            StartActivityUtils.startActivityAnimFinish(activity(), targetClass, bundle);
    }

    protected void startActivityAnimFinish(Class<?> targetClass) {
        if (isChangeUi)
            StartActivityUtils.startActivityAnimFinish(activity(), targetClass);
    }

    /**
     * 网络加载等待popupWindow 快捷
     */

    protected NetLoadingPopup netLoadingPopup;

    protected void showNetLoading() {
        if (netLoadingPopup == null) {
            netLoadingPopup = new NetLoadingPopup(this);
        }
        if (isChangeUi && !netLoadingPopup.isShowing()) {
            netLoadingPopup.showPopupWindow();
        }
    }

    protected void dismissNetLoading() {
        if (netLoadingPopup == null) {
            netLoadingPopup = new NetLoadingPopup(this);
        }
        if (isChangeUi && netLoadingPopup.isShowing()) {
            netLoadingPopup.dismiss();
        }
    }

    /**
     * 图片加载 快捷 默认url/path加载 如果需要泛型加载，请直接使用glideUtils
     */

    protected <T> void glide(T object, ImageView intoImageView) {
        GlideUtils.glide(context(),object, intoImageView, true);
    }

    /**
     * 没有加载中与加载失败的图片 ，一般加载resource使用
     */
    protected <T> void glideNoErrorPic(T object, ImageView intoImageView) {
        GlideUtils.glideNoErrorPic(context(),object, intoImageView, true);
    }

    /**
     * 封装常用取本地资源的方法
     */
    protected String getStr(@StringRes int strId) {
        return this.getResources().getString(strId);
    }

    protected int getCol(@ColorRes int colorId) {
        return ContextCompat.getColor(context(), colorId);
    }

}

//private long exitTime = 0;
//@Override
//public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//        //两秒之内按返回键就会退出
//        if ((System.currentTimeMillis() - exitTime) > 2000) {
//        showToast(getResources().getString(R.string.then_click_exit_soft));
//        exitTime = System.currentTimeMillis();
//        } else {
//        APPManager.finishAllActivity(activity());
//        }
//        return true;
//        }
//        return super.onKeyDown(keyCode, event);
//        }
//      /*set it to be no title*/
//requestWindowFeature(Window.FEATURE_NO_TITLE);
//       /*set it to be full screen*/
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
