package zwp.quickly.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import zwp.quickly.Quickly;
import zwp.quickly.base.BaseApplication;
import zwp.quickly.utils.toolsUtils.LogUtils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * 定位工具类，有封装百度定位，每次更改key即可
 * <p>    note：状态改变有三种情况{@link MyLocationListener#onStatusChanged(String, int, Bundle)}
 * <p>
 * author：ping on 2016/11/29 15:37 mail：1101558280@qq.com web: www.zcyke.com
 * <table>
 *     <tr>
 *         <th>注册 {@link #register(long, long, OnLocationChangeListener)}</th>
 *     </tr>
 *     <tr>
 *         <th>注销 {@link #unregister()}</th>
 *     </tr>
 *     <tr>
 *         <th>根据经纬度获取地理位置 {@link #getAddress(double, double)}</th>
 *     </tr>
 *     <tr>
 *         <th>根据经纬度获取所在国家 {@link #getCountryName(double, double)}</th>
 *     </tr>
 *     <tr>
 *         <th>根据经纬度获取所在地 {@link #getLocality(double, double)}</th>
 *     </tr>
 *     <tr>
 *         <th>根据经纬度获取所在街道 {@link #getStreet(double, double)}</th>
 *     </tr>
 *     <tr>
 *         <th>百度地图定位关键点 {@底部}</th>
 *     </tr>
 * </table>
 */

public class LocationUtils {

    private static OnLocationChangeListener mListener;
    private static MyLocationListener       myLocationListener;
    private static LocationManager          mLocationManager;

    private LocationUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 注册
     * <p>使用完记得调用{@link #unregister()}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>}</p>
     * <p>如果{@code minDistance}为0，则通过{@code minTime}来定时更新；</p>
     * <p>{@code minDistance}不为0，则以{@code minDistance}为准；</p>
     * <p>两者都为0，则随时刷新。</p>
     *
     * @param minTime     位置信息更新周期（单位：毫秒）
     * @param minDistance 位置变化最小距离：当位置距离变化超过此值时，将更新位置信息（单位：米）
     * @param listener    位置刷新的回调接口
     * @return {@code true}: 初始化成功<br>{@code false}: 初始化失败
     */
    public static boolean register(long minTime, long minDistance, OnLocationChangeListener listener) {
        if (listener == null) return false;
        mLocationManager = (LocationManager) Quickly.getContext().getSystemService(Context.LOCATION_SERVICE);
        mListener = listener;
        if (!IntentUtils.isLocationEnabled()) {
            ToastUtils.showShortSafe("无法定位，请打开定位服务");
            return false;
        }
        String provider = mLocationManager.getBestProvider(getCriteria(), true);
        Location location = mLocationManager.getLastKnownLocation(provider);
        if (location != null) listener.getLastKnownLocation(location);
        if (myLocationListener == null) myLocationListener = new MyLocationListener();
        mLocationManager.requestLocationUpdates(provider, minTime, minDistance, myLocationListener);
        return true;
    }


    /**
     * 注销
     */
    public static void unregister() {
        if (mLocationManager != null) {
            if (myLocationListener != null) {
                mLocationManager.removeUpdates(myLocationListener);
                myLocationListener = null;
            }
            mLocationManager = null;
        }
    }

    /**
     * 设置定位参数
     *
     * @return {@link Criteria}
     */
    private static Criteria getCriteria() {
        Criteria criteria = new Criteria();
        //设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        //设置是否需要方位信息
        criteria.setBearingRequired(false);
        //设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    /**
     * 根据经纬度获取地理位置
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return {@link Address}
     */
    public static Address getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(Quickly.getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) return addresses.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据经纬度获取所在国家
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return 所在国家
     */
    public static String getCountryName(double latitude, double longitude) {
        Address address = getAddress(latitude, longitude);
        return address == null ? "unknown" : address.getCountryName();
    }

    /**
     * 根据经纬度获取所在地
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return 所在地
     */
    public static String getLocality(double latitude, double longitude) {
        Address address = getAddress(latitude, longitude);
        return address == null ? "unknown" : address.getLocality();
    }

    /**
     * 根据经纬度获取所在街道
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return 所在街道
     */
    public static String getStreet(double latitude, double longitude) {
        Address address = getAddress(latitude, longitude);
        return address == null ? "unknown" : address.getAddressLine(0);
    }

    private static class MyLocationListener
            implements LocationListener {
        /**
         * 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
         *
         * @param location 坐标
         */
        @Override
        public void onLocationChanged(Location location) {
            if (mListener != null) {
                mListener.onLocationChanged(location);
            }
        }

        /**
         * provider的在可用、暂时不可用和无服务三个状态直接切换时触发此函数
         *
         * @param provider 提供者
         * @param status   状态
         * @param extras   provider可选包
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (mListener != null) {
                mListener.onStatusChanged(provider, status, extras);
            }
            switch (status) {
                case LocationProvider.AVAILABLE:
                    LogUtils.i("当前GPS状态为可见状态");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    LogUtils.i("当前GPS状态为服务区外状态");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    LogUtils.i("当前GPS状态为暂停服务状态");
                    break;
            }
        }

        /**
         * provider被enable时触发此函数，比如GPS被打开
         */
        @Override
        public void onProviderEnabled(String provider) {
        }

        /**
         * provider被disable时触发此函数，比如GPS被关闭
         */
        @Override
        public void onProviderDisabled(String provider) {
        }
    }

    public interface OnLocationChangeListener {

        /**
         * 获取最后一次保留的坐标
         *
         * @param location 坐标
         */
        void getLastKnownLocation(Location location);

        /**
         * 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
         *
         * @param location 坐标
         */
        void onLocationChanged(Location location);

        /**
         * provider的在可用、暂时不可用和无服务三个状态直接切换时触发此函数
         *
         * @param provider 提供者
         * @param status   状态
         * @param extras   provider可选包
         */
        void onStatusChanged(String provider, int status, Bundle extras);//位置状态发生改变
    }


}
// service注册
//                    if (locType == 61 || locType == 65 || locType == 66 || locType == 68 || locType == 161) { //具体数值见该类底部
/*百度定位返回值*/ //http://lbsyun.baidu.com/index.php?title=android-locsdk/guide/getloc
//        61 ： GPS定位结果，GPS定位成功。
//        62 ： 无法获取有效定位依据，定位失败，请检查运营商网络或者WiFi网络是否正常开启，尝试重新请求定位。
//        63 ： 网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位。
//        65 ： 定位缓存的结果。
//        66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果。
//        67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果。
//        68 ： 网络连接失败时，查找本地离线定位时对应的返回结果。
//        161： 网络定位结果，网络定位成功。
//        162： 请求串密文解析失败，一般是由于客户端SO文件加载失败造成，请严格参照开发指南或demo开发，放入对应SO文件。
//        167： 服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。
//        502： AK参数错误，请按照说明文档重新申请AK。
//        505：AK不存在或者非法，请按照说明文档重新申请AK。
//        601： AK服务被开发者自己禁用，请按照说明文档重新申请AK。
//        602： key mcode不匹配，您的AK配置过程中安全码设置有问题，请确保：SHA1正确，“;”分号是英文状态；且包名是您当前运行应用的包名，请按照说明文档重新申请AK。
//        501～700：AK验证失败，请按照说明文档重新申请AK。