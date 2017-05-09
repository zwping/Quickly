package zwp.quickly.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.orhanobut.logger.Logger;

/**
 * 网络监听改变
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

//public class MainActivity extends AppCompatActivity implements ConnChangeReceiver.ConnChangeInterfaces{}
//private ConnChangeReceiver connChangeReceiver;
//
///**
// * 注册广播&&注册广播接口
// */
//private void registerReceiver() {
//        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        connChangeReceiver = new ConnChangeReceiver(this);
//        this.registerReceiver(connChangeReceiver, filter);
//        }
//
///**
// * 取消广播
// */
//private void unregisterReceiver() {
//        this.unregisterReceiver(connChangeReceiver);
//        }
//
///**
// * 网络改变接口回调
// */
//@Override
//public void connChangeInterface(Boolean isConnected) {
//        if (isConnected) showToast("网络可用");
//        else showToast("网络不可用");
//        }
public class ConnChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo  mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo  wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            connChangeInterfaces.connChangeInterface(false);
            //改变背景或者 处理网络的全局变量
        }else {
            connChangeInterfaces.connChangeInterface(true);
            //改变背景或者 处理网络的全局变量
        }
    }

    public ConnChangeReceiver(ConnChangeInterfaces connChangeInterfaces1) {
        this.connChangeInterfaces = connChangeInterfaces1;
    }

    private  ConnChangeInterfaces connChangeInterfaces;

    public interface ConnChangeInterfaces {
        void connChangeInterface(Boolean isConnected);
    }

}
