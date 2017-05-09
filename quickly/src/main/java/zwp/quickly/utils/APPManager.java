package zwp.quickly.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import zwp.quickly.Quickly;
import zwp.quickly.utils.toolsUtils.LogUtils;

/**
 * App管理类
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class APPManager {

    private APPManager(){
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static List<Activity> activityList = new ArrayList<Activity>();
    private static APPManager instance;

    public static APPManager getInstance() {
        if (instance == null) {
            instance = new APPManager();
        }
        return instance;
    }

    /**
     * 添加 Activity 到列表
     *
     * @param activity activity
     */
    public static void addActivity(Activity activity) {
        if (activityList == null) {
            activityList = new ArrayList<Activity>();
        }
        activityList.add(activity);
    }

    /**
     * 获取界面数量
     *
     * @return activity size
     */
    public static int getActivitySize() {
        if (activityList != null) {
            return activityList.size();
        }
        return 0;
    }

    /**
     * 获取当前 Activity - 堆栈中最后一个压入的
     *
     * @return current Activity
     */
    public static Activity getCurrentActivity() {
        if (activityList != null && activityList.size() > 0) {
            Activity activity = activityList.get(activityList.size() - 1);
            return activity;
        }
        return null;
    }

    /**
     * 获取指定类名的 Activity
     *
     * @param cls 指定的类
     * @return Activity
     */
    public static Activity getActivity(Class<?> cls) {
        if (activityList == null) {
            return null;
        }
        for (Activity activity : activityList) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 结束指定的 Activity
     *
     * @param activity Activity
     */
    public static void removeActivity(Activity activity) {
        if (activity != null) {
            activityList.remove(activity);
        }
    }

    /**
     * 结束指定类名的 Activity
     *
     * @param cls 指定的类
     */
    public static void removeActivity(Class<?> cls) {
        if (activityList == null) {
            return;
        }
        for (Activity activity : activityList) {
            if (activity.getClass().equals(cls)) {
                activityList.remove(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     *
     *
     <!--允许程序调用killBackgroundProcesses(String).方法结束后台进程-->
     <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES"/>
     */
    public static void finishAllActivity() {
        //取消所有请求
        OkGo.getInstance().cancelAll();
        if (true) {
            ToastUtils.showShortSafe("您加系统权限了嘛");
            return;
        }
        if (activityList == null) {
            return;
        }
        int size = activityList.size();
        for (int i = 0; i < size; i++) {
            if (null != activityList.get(i)) {
                activityList.get(i).finish();
            }
        }
        activityList.clear();
        ActivityManager am = (ActivityManager) Quickly.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        am.killBackgroundProcesses(Quickly.getContext().getPackageName());
    }

    /**
     * 结束其他所有的Activity
     *
     * @param activity 不需要销毁的Activity
     */
    public static void finishOtherAllActivity(Activity activity) {
        if (activityList == null) {
            return;
        }
        for (int i = 0; i < activityList.size(); i++) {
            if (activity != activityList.get(i)) {
                activityList.get(i).finish();
                activityList.remove(i);
            }
        }
    }

}
