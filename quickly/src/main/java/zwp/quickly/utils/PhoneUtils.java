package zwp.quickly.utils;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Xml;

import zwp.quickly.Quickly;
import zwp.quickly.base.BaseApplication;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>describe：手机相关工具类
 * <p>    note：
 * <p>  author：zwp on 2017/5/2 mail：1101558280@qq.com web: http://www.zwping.win </p>
 * <table>
 * <tr>
 * <th>
 * 判断设备是否是手机 {@link #isPhone()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 获取IMEI码 {@link #getIMEI()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 获取IMSI码 {@link #getIMSI()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 获取移动终端类型 {@link #getPhoneType()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 判断sim卡是否准备好 {@link #isSimCardReady()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 获取Sim卡运营商名称 {@link #getSimOperatorByMnc()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 获取Sim卡运营商名称 {@link #getSimOperatorName()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 获取手机状态信息 {@link #getPhoneStatus()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 获取手机联系人 {@link #getAllContactInfo()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 打开手机联系人界面点击联系人后便获取该号码 {@link #getContactNum()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 获取手机短信并保存到xml中 {@link #getAllSMS()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 获取设备的制造商 {@link #getDeviceManufacture()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 获取设备名称 {@link #getDeviceName()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 获取系统版本号 {@link #getSystemVersion()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * isART  {@link #isART()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * isDALVIK {@link #isDalvik()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 获取主板名称 {@link #getBoard()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 系统(固件)版本号 {@link #getID()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 设备的唯一标识。由设备的多个信息拼接合成 {@link #getFingerPrint()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 得到产品整体名称 {@link #getProduct()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 获取设备驱动名称 {@link #getDevice()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 获取设备指令集名称（CPU的类型） {@link #getCpuAbi()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 获取第二个指令集名称 {@link #getCpuAbi2()}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 获取设备显示的版本包（在系统设置中显示为版本号）和ID一样 {@link #getDisplay()}
 * </th>
 * </tr>
 * </table>
 */

public class PhoneUtils {

    private PhoneUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断设备是否是手机
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isPhone() {
        TelephonyManager tm = (TelephonyManager) Quickly.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 获取IMEI码 (你的手机是绑定关系 用于区别移动终端设备)
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @return IMEI码 国际移动设备身份码的缩写，国际移动装备辨识码，是由15位数字组成的"电子串号"，它与每台手机一一对应，而且该码是全世界唯一的
     */
    @SuppressLint("HardwareIds")
    public static String getIMEI() {
        TelephonyManager tm = (TelephonyManager) Quickly.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getDeviceId() : null;
    }

    /**
     * 获取IMSI码 (你的手机卡是绑定关系 用于区别移动用户的有效信息)
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @return IMSI码 区别移动用户的标志，储存在SIM卡中，可用于区别移动用户的有效信息
     */
    @SuppressLint("HardwareIds")
    public static String getIMSI() {
        TelephonyManager tm = (TelephonyManager) Quickly.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSubscriberId() : null;
    }

    /**
     * 获取移动终端类型
     *
     * @return 手机制式
     * <ul>
     * <li>{@link TelephonyManager#PHONE_TYPE_NONE } : 0 手机制式未知</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_GSM  } : 1 手机制式为GSM，移动和联通</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_CDMA } : 2 手机制式为CDMA，电信</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_SIP  } : 3</li>
     * </ul>
     */
    public static int getPhoneType() {
        TelephonyManager tm = (TelephonyManager) Quickly.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getPhoneType() : -1;
    }

    /**
     * 判断sim卡是否准备好
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSimCardReady() {
        TelephonyManager tm = (TelephonyManager) Quickly.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }

    /**
     * 获取Sim卡运营商名称
     * <p>中国移动、如中国联通、中国电信</p>
     *
     * @return sim卡运营商名称
     */
    public static String getSimOperatorName() {
        TelephonyManager tm = (TelephonyManager) Quickly.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSimOperatorName() : null;
    }

    /**
     * 获取Sim卡运营商名称
     * <p>中国移动、如中国联通、中国电信</p>
     *
     * @return 移动网络运营商名称
     */
    public static String getSimOperatorByMnc() {
        TelephonyManager tm = (TelephonyManager) Quickly.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        String operator = tm != null ? tm.getSimOperator() : null;
        if (operator == null) return null;
        switch (operator) {
            case "46000":
            case "46002":
            case "46007":
                return "中国移动";
            case "46001":
                return "中国联通";
            case "46003":
                return "中国电信";
            default:
                return operator;
        }
    }

    /**
     * 获取手机状态信息
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @return DeviceId(IMEI) = 99000311726612<br>
     * DeviceSoftwareVersion = 00<br>
     * Line1Number =<br>
     * NetworkCountryIso = cn<br>
     * NetworkOperator = 46003<br>
     * NetworkOperatorName = 中国电信<br>
     * NetworkType = 6<br>
     * honeType = 2<br>
     * SimCountryIso = cn<br>
     * SimOperator = 46003<br>
     * SimOperatorName = 中国电信<br>
     * SimSerialNumber = 89860315045710604022<br>
     * SimState = 5<br>
     * SubscriberId(IMSI) = 460030419724900<br>
     * VoiceMailNumber = *86<br>
     */
    public static String getPhoneStatus() {
        TelephonyManager tm = (TelephonyManager) Quickly.getContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        String str = "";
        str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";
        str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";
        str += "Line1Number = " + tm.getLine1Number() + "\n";
        str += "NetworkCountryIso = " + tm.getNetworkCountryIso() + "\n";
        str += "NetworkOperator = " + tm.getNetworkOperator() + "\n";
        str += "NetworkOperatorName = " + tm.getNetworkOperatorName() + "\n";
        str += "NetworkType = " + tm.getNetworkType() + "\n";
        str += "PhoneType = " + tm.getPhoneType() + "\n";
        str += "SimCountryIso = " + tm.getSimCountryIso() + "\n";
        str += "SimOperator = " + tm.getSimOperator() + "\n";
        str += "SimOperatorName = " + tm.getSimOperatorName() + "\n";
        str += "SimSerialNumber = " + tm.getSimSerialNumber() + "\n";
        str += "SimState = " + tm.getSimState() + "\n";
        str += "SubscriberId(IMSI) = " + tm.getSubscriberId() + "\n";
        str += "VoiceMailNumber = " + tm.getVoiceMailNumber() + "\n";
        return str;
    }

    /**
     * 获取手机联系人
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_CONTACTS"/>}</p>
     *
     * @return 联系人链表
     */
    public static List<HashMap<String, String>> getAllContactInfo() {
        SystemClock.sleep(3000);
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        // 1.获取内容解析者
        ContentResolver resolver = Quickly.getContext().getContentResolver();
        // 2.获取内容提供者的地址:com.android.contacts
        // raw_contacts表的地址 :raw_contacts
        // view_data表的地址 : data
        // 3.生成查询地址
        Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri date_uri = Uri.parse("content://com.android.contacts/data");
        // 4.查询操作,先查询raw_contacts,查询contact_id
        // projection : 查询的字段
        Cursor cursor = resolver.query(raw_uri, new String[]{"contact_id"}, null, null, null);
        try {
            // 5.解析cursor
            while (cursor.moveToNext()) {
                // 6.获取查询的数据
                String contact_id = cursor.getString(0);
                // cursor.getString(cursor.getColumnIndex("contact_id"));//getColumnIndex
                // : 查询字段在cursor中索引值,一般都是用在查询字段比较多的时候
                // 判断contact_id是否为空
                if (!StringUtils.isEmpty(contact_id)) {//null   ""
                    // 7.根据contact_id查询view_data表中的数据
                    // selection : 查询条件
                    // selectionArgs :查询条件的参数
                    // sortOrder : 排序
                    // 空指针: 1.null.方法 2.参数为null
                    Cursor c = resolver.query(date_uri, new String[]{"data1",
                                    "mimetype"}, "raw_contact_id=?",
                            new String[]{contact_id}, null);
                    HashMap<String, String> map = new HashMap<String, String>();
                    // 8.解析c
                    while (c.moveToNext()) {
                        // 9.获取数据
                        String data1 = c.getString(0);
                        String mimetype = c.getString(1);
                        // 10.根据类型去判断获取的data1数据并保存
                        if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                            // 电话
                            map.put("phone", data1);
                        } else if (mimetype.equals("vnd.android.cursor.item/name")) {
                            // 姓名
                            map.put("name", data1);
                        }
                    }
                    // 11.添加到集合中数据
                    list.add(map);
                    // 12.关闭cursor
                    c.close();
                }
            }
        } finally {
            // 12.关闭cursor
            cursor.close();
        }
        return list;
    }

    /**
     * 打开手机联系人界面点击联系人后便获取该号码
     * <p>参照以下注释代码</p>
     */
    public static void getContactNum() {
        Log.d("tips", "U should copy the following code.");
        /*
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.setType("vnd.android.cursor.dir/phone_v2");
        startActivityForResult(intent, 0);

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (data != null) {
                Uri uri = data.getData();
                String num = null;
                // 创建内容解析者
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(uri,
                        null, null, null, null);
                while (cursor.moveToNext()) {
                    num = cursor.getString(cursor.getColumnIndex("data1"));
                }
                cursor.close();
                num = num.replaceAll("-", "");//替换的操作,555-6 -> 5556
            }
        }
        */
    }

    /**
     * 获取手机短信并保存到xml中
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_SMS"/>}</p>
     */
    public static void getAllSMS() {
        // 1.获取短信
        // 1.1获取内容解析者
        ContentResolver resolver = Quickly.getContext().getContentResolver();
        // 1.2获取内容提供者地址   sms,sms表的地址:null  不写
        // 1.3获取查询路径
        Uri uri = Uri.parse("content://sms");
        // 1.4.查询操作
        // projection : 查询的字段
        // selection : 查询的条件
        // selectionArgs : 查询条件的参数
        // sortOrder : 排序
        Cursor cursor = resolver.query(uri, new String[]{"address", "date", "type", "body"}, null, null, null);
        // 设置最大进度
        int count = cursor.getCount();//获取短信的个数
        // 2.备份短信
        // 2.1获取xml序列器
        XmlSerializer xmlSerializer = Xml.newSerializer();
        try {
            // 2.2设置xml文件保存的路径
            // os : 保存的位置
            // encoding : 编码格式
            xmlSerializer.setOutput(new FileOutputStream(new File("/mnt/sdcard/backupsms.xml")), "utf-8");
            // 2.3设置头信息
            // standalone : 是否独立保存
            xmlSerializer.startDocument("utf-8", true);
            // 2.4设置根标签
            xmlSerializer.startTag(null, "smss");
            // 1.5.解析cursor
            while (cursor.moveToNext()) {
                SystemClock.sleep(1000);
                // 2.5设置短信的标签
                xmlSerializer.startTag(null, "sms");
                // 2.6设置文本内容的标签
                xmlSerializer.startTag(null, "address");
                String address = cursor.getString(0);
                // 2.7设置文本内容
                xmlSerializer.text(address);
                xmlSerializer.endTag(null, "address");
                xmlSerializer.startTag(null, "date");
                String date = cursor.getString(1);
                xmlSerializer.text(date);
                xmlSerializer.endTag(null, "date");
                xmlSerializer.startTag(null, "type");
                String type = cursor.getString(2);
                xmlSerializer.text(type);
                xmlSerializer.endTag(null, "type");
                xmlSerializer.startTag(null, "body");
                String body = cursor.getString(3);
                xmlSerializer.text(body);
                xmlSerializer.endTag(null, "body");
                xmlSerializer.endTag(null, "sms");
                System.out.println("address:" + address + "   date:" + date + "  type:" + type + "  body:" + body);
            }
            xmlSerializer.endTag(null, "smss");
            xmlSerializer.endDocument();
            // 2.8将数据刷新到文件中
            xmlSerializer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**===================更多设备知识点：http://blog.csdn.net/zy987654zy/article/details/8637435 ==================**/

    /**
     * 获取设备的制造商
     *
     * @return 设备制造商 (Xiaomi)
     */
    public static String getDeviceManufacture() {
        return Build.MANUFACTURER; //Build.BRAND //品牌
    }

    /**
     * 获取设备名称
     *
     * @return 设备名称 （Redmi Pro）
     */
    public static String getDeviceName() {
        return Build.MODEL;
    }

    /**
     * 获取系统版本号
     *
     * @return 系统版本号 (6.0)
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * ART
     * ART：android runtime 前身是Dalvik。作用跟Windows RT一样，用来运行android app的
     *
     * @return
     */
    public static boolean isART() {
        final String vmVersion = System.getProperty("java.vm.version");
        return vmVersion != null && vmVersion.startsWith("2");
    }

    /**
     * DALVIK
     * DALVIK：Google公司自己设计用于Android平台的Java虚拟机
     *
     * @return
     */
    public static boolean isDalvik() {
        final String vmVersion = System.getProperty("java.vm.version");
        return vmVersion != null && vmVersion.startsWith("1");
    }

    /**
     * 获取主板名称, like "MSM8660_SURF".
     *
     * @return
     */
    public static String getBoard() {
        return Build.BOARD;
    }

    /**
     * 系统(固件)版本号, or a label like "JZO54K".
     *
     * @return
     */
    public static String getID() {
        return Build.ID;
    }

    /**
     * 设备的唯一标识。由设备的多个信息拼接合成
     *
     * @return
     */
    public static String getFingerPrint() {
        return Build.FINGERPRINT;
    }

    /**
     * 得到产品整体名称
     *
     * @return
     */
    public static String getProduct() {
        return Build.PRODUCT;
    }

    /**
     * 获取设备驱动名称
     *
     * @return
     */
    public static String getDevice() {
        return Build.DEVICE;
    }

    /**
     * 获取设备指令集名称（CPU的类型）, like "armeabi-v7a".
     *
     * @return
     */
    public static String getCpuAbi() {
        return Build.CPU_ABI;
    }

    /**
     * 获取第二个指令集名称 (CPU type + ABI convention) of native code, like "armeabi".
     *
     * @return
     */
    public static String getCpuAbi2() {
        return Build.CPU_ABI2;
    }

    /**
     * 获取设备显示的版本包（在系统设置中显示为版本号）和ID一样, like "JZO54K".
     *
     * @return
     */
    public static String getDisplay() {
        return Build.DISPLAY;
    }

    /**===================更多设备知识点：http://blog.csdn.net/zy987654zy/article/details/8637435 ==================**/
}
