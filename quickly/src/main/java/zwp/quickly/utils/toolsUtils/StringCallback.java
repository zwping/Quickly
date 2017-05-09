package zwp.quickly.utils.toolsUtils;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.lzy.okgo.request.BaseRequest;

import zwp.quickly.R;
import zwp.quickly.base.BaseBean;
import zwp.quickly.utils.ToastUtils;
import zwp.quickly.custom.NetLoadingPopup;

import okhttp3.Call;
import okhttp3.Response;

/**
 * <p>describe：主要实现3个功能，onBefore显示dialog,onAfter关闭dialog / 自动携带id和token / onError网络异常处理-->关闭当前页面
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 * <p>
 * 注意
 * - Id 和Token的key
 */

public abstract class StringCallback<T extends BaseBean> extends com.lzy.okgo.callback.StringCallback {

    /**
     * Key
     */
    public static final String USER_ID = "USER_ID";
    /**
     * Key
     */
    public static final String TOKEN = "TOKEN";

    private NetLoadingPopup netLoadingPopup = null;
    private SwipeRefreshLayout refreshLayout = null;
    /**
     * 是否自动添加id and token ，注意区分get post
     * - true 为加入params id and token
     * - post 为加入headers id and token
     * - null 为不添加id and token
     */
    private Boolean mAddParamsOrHeaders = null;
    /**
     * 传入activity即表示显示netLoading
     */
    private Activity mActivity = null;
    /**
     * error错误是否需要关闭当前页面 <p>
     */
    private ErrorFinishEnum mIsErrorFinish = ErrorFinishEnum.doNotChange;

    public enum ErrorFinishEnum {
        finish, doNotChange
    }

    /**
     * 泛型实体类
     */
    private Object mBean = null;

    /**
     * 判断传入的实体类是否是baseBean的子类
     *
     * @param t
     */
    private void judgeClass(Object t) {
        if (t instanceof BaseBean) this.mBean = t;
        else throw new IllegalStateException("实体类必须继承baseBean");
    }

    /**
     * 不显示loading / 不显示swipeRefreshLayout / 不处理error / 不自动携带id and token
     * <P>不建议使用，因为没有处理errorToast提示</P>
     */
    @Deprecated
    public StringCallback(Object t) {
        super();
        judgeClass(t);
    }


    /**
     * 处理onErrorToast问题
     */
    public StringCallback(Object t, Activity activity) {
        super();
        judgeClass(t);
        if (activity != null) mActivity = activity;
    }

    /**
     * 显示netLoading
     *
     * @param activity           传入activity getString loading和finish需要使用
     * @param isShowLoading      是否显示loading  **与SwipeRefresh不可兼得
     * @param swipeRefreshLayout 传入加以显示
     * @param isShowSwipeRefresh 是否显示SwipeRefresh刷新 **与loading不可兼得
     * @param addParamsOrHeaders 是否自动添加参数
     * @param isErrorFinish      error是否关闭当前页面
     */
    public StringCallback(Object t, @Nullable Activity activity, Boolean isShowLoading, SwipeRefreshLayout swipeRefreshLayout,
                          Boolean isShowSwipeRefresh, Boolean addParamsOrHeaders, ErrorFinishEnum isErrorFinish) {
        super();
        judgeClass(t);
        if (activity != null) mActivity = activity;
        if (activity != null && isShowLoading != null && isShowLoading) initLoading(activity);
        if (swipeRefreshLayout != null && isShowSwipeRefresh != null && isShowSwipeRefresh)
            refreshLayout = swipeRefreshLayout;
        if (addParamsOrHeaders != null) mAddParamsOrHeaders = addParamsOrHeaders;
        if (activity != null) mIsErrorFinish = isErrorFinish;
    }

    /**
     * 显示loading
     *
     * @param activity      传入activity loading和finish需要使用
     * @param isShowLoading 是否显示loading
     */
    public StringCallback(Object t, @Nullable Activity activity, Boolean isShowLoading) {
        super();
        judgeClass(t);
        if (activity != null) mActivity = activity;
        if (activity != null && isShowLoading != null && isShowLoading) initLoading(activity);
    }

    /**
     * 显示swipeRefresh控件
     *
     * @param swipeRefreshLayout 传入swipeRefreshLayout控件
     * @param isShowSwipeRefresh 是否显示
     */
    public StringCallback(Object t, Activity activity, SwipeRefreshLayout swipeRefreshLayout, Boolean isShowSwipeRefresh) {
        super();
        judgeClass(t);
        if (activity != null) mActivity = activity;
        if (swipeRefreshLayout != null && isShowSwipeRefresh != null && isShowSwipeRefresh)
            refreshLayout = swipeRefreshLayout;
    }

    /**
     * 添加id和token，区分get and post <P>
     * - true 为加入params id and token
     * - post 为加入headers id and token
     * - null 为不添加id and token
     */
//    @Deprecated
//    public StringCallback(Activity activity,Boolean addParamsOrHeaders) {
//        super();
//        if (activity != null) mActivity = activity;
//        if (addParamsOrHeaders != null) mAddParamsOrHeaders = addParamsOrHeaders;
//    }

    /**
     * error是否关闭页面
     *
     * @param activity
     * @param isErrorFinish error是否关闭当前页面
     */
    public StringCallback(Object t, @Nullable Activity activity, ErrorFinishEnum isErrorFinish) {
        super();
        judgeClass(t);
        if (activity != null) mActivity = activity;
        if (activity != null) mIsErrorFinish = isErrorFinish;
    }

    /**
     * 则自动显示loading  / error是否关闭页面 <P>
     *
     * @param activity      传入activity loading和finish需要使用
     * @param isShowLoading 是否显示loading
     * @param isErrorFinish error是否关闭当前页面
     */
    public StringCallback(Object t, @Nullable Activity activity, Boolean isShowLoading, ErrorFinishEnum isErrorFinish) {
        super();
        judgeClass(t);
        if (activity != null) mActivity = activity;
        if (activity != null && isShowLoading != null && isShowLoading) initLoading(activity);
        if (activity != null) mIsErrorFinish = isErrorFinish;
    }

    // TODO: 2017/2/27 显示loading and 添加param
    // TODO: 2017/2/27 添加param and error finish

    private void initLoading(Activity context) {
        netLoadingPopup = new NetLoadingPopup(context);
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        // 是否添加id and token 请求头
        if (mAddParamsOrHeaders != null) {
            if (mAddParamsOrHeaders) {
                request.headers("UserID", HawkUtils.getString(USER_ID))
                        .headers("TokenCode", HawkUtils.getString(TOKEN));
            } else {
                request.params("UserID", HawkUtils.getString(USER_ID))
                        .params("TokenCode", HawkUtils.getString(TOKEN));
            }
        }
        //网络请求前显示对话框
        // TODO: 2017/2/27 验证第一次使用show 第二次使用dismiss
        if (netLoadingPopup != null && !netLoadingPopup.isShowing()) {
            netLoadingPopup.showPopupWindow();
        }
        if (refreshLayout != null && !refreshLayout.isShown()) {
            refreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void onSuccess(String s, Call call, Response response) {
        /*
         * 目的：精简代码，少一层判断
         * 针对后台返回结果的实体类，创建了baseBean{@link BaseBean}
         * 先针对baseBean解析返回内容是否成功
         */
        mBean = JsonUtils.GsonToBean(s, mBean.getClass());
        String msg = ((BaseBean) mBean).getMsg();
        if (((BaseBean) mBean).isIsSuccessful()) { //后台数据库返回成功
            dbReturnSuccess(mBean, ((BaseBean) mBean).getMsg(), call, response);
        } else { //后台数据库返回失败
            if (msg.contains("用户令牌失效,请重新登陆")) {
                // TODO: 2017/4/14 令牌失效跳转
                ToastUtils.showShortSafe(msg + "---需要设置一下");
            } else
                dbReturnError(msg, call, response);
        }
    }

    /**
     * 请求后台接口内容返回成功
     */
    protected abstract void dbReturnSuccess(Object bean, String msg, Call call, Response response);

    /**
     * 请求后台接口内容返回失败
     */
    protected abstract void dbReturnError(String msg, Call call, Response response);


    @Override
    public void onAfter(@Nullable String s, @Nullable Exception e) {
        super.onAfter(s, e);
        //网络请求结束后关闭对话框
        if (netLoadingPopup != null && netLoadingPopup.isShowing()) {
            netLoadingPopup.dismiss();
        }
        if (refreshLayout != null && refreshLayout.isShown()) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
        if (mActivity != null)
            ToastUtils.showShortSafe(mActivity.getResources().getString(R.string.network_error));
        if (mActivity != null && mIsErrorFinish == ErrorFinishEnum.finish) mActivity.finish();
    }
}
