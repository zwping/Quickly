package zwp.quickly.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.NoEncryption;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.logging.Level;

import zwp.quickly.Quickly;
import zwp.quickly.utils.AppUtils;
import zwp.quickly.utils.CrashUtils;
import zwp.quickly.utils.ToastUtils;

/**
 * <p>describe：Application基类，logger/okGo初始化，同时获取屏幕宽/高
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Quickly.init(this);

//        if (!AppUtils.isAppDebug(this)) { //错误日志保存（非DEBUG模式才收集）
//            CrashUtils.getInstance().init();
//        }
        //注册大鹅bugly  (上架时需要关闭log)
//        CrashReport.initCrashReport(this, "c00172efe4", true);
        Hawk.init(this).build(); //初始化hawk-替代sharedpreferences
        initOkGo(); //初始化OkGo网络请求框架(上架时需要关闭log)
        initLogger(); //初始化logger (上架时需要关闭log)
    }

    //当终止应用程序对象时调用，不保证一定被调用，当程序是被内核终止以便为其他
    // 应用程序释放资源，那么将不会提醒，并且不调用应用程序的对象的onTerminate方法而直接终止进程
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    //当后台程序已经终止资源还匮乏时会调用这个方法。好的应用程序一般会在这个方法里面释放一些
    // 不必要的资源来应付当后台程序已经终止，前台应用程序内存还不够时的情况。
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {   //配置改变时触发这个方法
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 初始化OkGo网络请求框架
     */
    private void initOkGo() {
        OkGo.init(this);
        OkGo.getInstance()

                // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                .debug("quickly", Level.INFO, true)


                //如果使用默认的 60秒,以下三行也不需要传
                .setConnectTimeout(20000)  //全局的连接超时时间
                .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间

                //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                .setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)

                //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE);
    }

    /**
     * logger 初始化
     */
    private void initLogger() {
        // logger配置信息
        Settings settings = Logger.init("quickly"); // 配置tag
        settings.methodCount(3); // 配置Log中调用堆栈的函数行数
        //settings.hideThreadInfo(); // 隐藏Log中的线程信息
        settings.methodOffset(0); // 设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息
        settings.logLevel(LogLevel.FULL); // 设置Log的是否输出，LogLevel.NONE即无Log输出
    }

    /**
     * 初始化hawk
     */
    private void initHawk() {
        Hawk.init(this)
                .setEncryption(new NoEncryption())  //没有加密
                .build();
    }
}
