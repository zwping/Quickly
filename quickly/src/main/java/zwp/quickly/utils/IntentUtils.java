package zwp.quickly.utils;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.IOException;
import java.util.List;

import zwp.quickly.Quickly;
import zwp.quickly.custom.CommonScene;

/**
 * <p>describe：Intent操作
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 * <table>
 *     <tr>
 *         <th>打开"更多网络设置"界面{@link #openWirelessSettings(Context)}</th>
 *     </tr>
 * <tr>
 * <th>跳转应用市场 {@link #goToMarket(Context, String)}</th>
 * </tr>
 * <tr>
 * <th>判断Gps是否可用 {@link #isGpsEnabled()}</th>
 * </tr>
 * <tr>
 * <th>判断定位是否可用 {@link #isLocationEnabled()}</th>
 * </tr>
 * <tr>
 * <th>打开Gps设置界面 {@link #openGpsSettings(Context)}</th>
 * </tr>
 * <tr>
 * <th>在浏览器中搜索文字 {@link #search(Context, String)}</th>
 * </tr>
 * <tr>
 * <th>在浏览器中打开URL {@link #openUrl(Context, String)}</th>
 * </tr>
 * <tr>
 * <th>拨号(permission) {@link #openDial(Context, String)}</th>
 * </tr>
 * <tr>
 * <th>发送短信 {@link #sendMessage(Context, String, String)}</th>
 * </tr>
 * <tr>
 * <th>打开联系人 {@link #openContacts(Context)}</th>
 * </tr>
 * <tr>
 * <th>打开系统设置 {@link #openSettings(Context)}</th>
 * </tr>
 * <tr>
 * <th>打开相机(返回大图/返回小图) {@link #openCamera(Activity, File, int)} </th>
 * </tr>
 * <tr>
 * <th>打开相册(精准打开/打开相册) {@link #openGallery(Activity, int)} </th>
 * </tr>
 * <tr>
 * <th>跳转到手机应用管理页 {@link #openPhoneDetail(Context)}</th>
 * </tr>
 * <tr>
 * <th>跳转至应用详情 {@link #openAppDetail(Context)}</th>
 * </tr>
 * <tr>
 * <th>调用系统分享 {@link #shareToOtherApp(Context, String, String, String)}</th>
 * </tr>
 * <tr>
 * <th>打开APP {@link #openApp(Context, String)}</th>
 * </tr>
 * <tr>
 * <th>获取安装App（支持6.0）的意图 {@link #getInstallAppIntent(Context, File)}</th>
 * </tr>
 * <tr>
 * <th>获取卸载App的意图 {@link #getUninstallAppIntent(String)}</th>
 * </tr>
 * <tr>
 *     <th>获取关机的意图 {@link #getShutdownIntent()}</th>
 * </tr>
 * <tr>
 *     <th>获取其他应用组件的意图 {@link #getComponentIntent(String, String)}</th>
 * </tr>
 * </table>
 */

public class IntentUtils {

    private IntentUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 打开"更多网络设置"界面
     * <p>3.0以下打开设置界面</p>
     */
    public static void openWirelessSettings(Context context) {
        if (android.os.Build.VERSION.SDK_INT > 10) {
            context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else {
            context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    /**
     * 跳转应用市场
     *
     * @param context
     */
    public static void goToMarket(Context context) {
        goToMarket(context, AppUtils.getAppPackageName(context));
    }

    /**
     * 跳转应用市场
     */
    public static void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断Gps是否可用
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isGpsEnabled() {
        LocationManager lm = (LocationManager) Quickly.getContext().getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 判断定位是否可用
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isLocationEnabled() {
        LocationManager lm = (LocationManager) Quickly.getContext().getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 打开Gps设置界面
     */
    public static void openGpsSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 在浏览器中搜索文字
     *
     * @param context
     * @param string
     */
    public static void search(Context context, String string) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, string);
        context.startActivity(intent);
    }

    /**
     * 在浏览器中打开URL
     *
     * @param context
     * @param url
     */
    public static void openUrl(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    /**
     * Open dial
     *
     * @param context
     */
    public static void openDial(Context context) {
        Intent intent = new Intent(Intent.ACTION_CALL_BUTTON);
        context.startActivity(intent);
    }

    /**
     * Call up, requires Permission "android.permission.CALL_PHONE"
     *
     * @param context
     * @param number
     */
    public static void openDial(Context context, String number) {
        Uri uri = Uri.parse("tel:" + number);
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        context.startActivity(intent);
    }

    /**
     * Send message
     *
     * @param context
     * @param sendNo
     * @param sendContent
     */
    public static void sendMessage(Context context, String sendNo, String sendContent) {
        Uri uri = Uri.parse("smsto:" + sendNo);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", sendContent);
        context.startActivity(intent);
    }

    /**
     * Open contact person 打开联系人
     *
     * @param context
     */
    public static void openContacts(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
        context.startActivity(intent);
    }

    /**
     * Open system settings
     *
     * @param context
     */
    public static void openSettings(Context context) {
        openSettings(context, Settings.ACTION_SETTINGS);
    }

    /**
     * Open system settings
     *
     * @param context
     * @param action  The action contains global system-level device preferences.
     */
    public static void openSettings(Context context, String action) {
        if (!StringUtils.isEmpty(action)) {
            Intent intent = new Intent(action);
            context.startActivity(intent);
        } else {
            openSettings(context);
        }
    }

    /**
     * Take camera, this photo data will be returned in onActivityResult()
     * <P>相机权限不足弹出dialog
     *
     * @param activity
     * @param requestCode
     * @return 返回bitmap*小图*
     * @deprecated 使用imagePicker框架可以直接调用拍照功能，框架解决返回值问题
     */
    @Deprecated
    public static void openCamera(Activity activity, int requestCode) {
        if (!CommonScene.cameraProhibitPopup(activity)) return;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * 开启拍照，返回本地uri
     *
     * @param activity 当前拍照activity
     * @param folder   拍照存取的根目录(包含sd路径)
     * @return 如果返回null 说明拍照失败，拍照获取的是原图（大图）
     */
    // TODO: 2017/2/21 需要设置全局变量获取返回值Uri
    public static Uri openCamera(Activity activity, File folder, int requestCode) {
        if (!CommonScene.cameraProhibitPopup(activity)) return null;
        //指定拍照intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = null;
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {  //activity是否能解析intent
            String photoName = "photograph_" + TimeUtils.getNowTimeString() + ".png";
            File outputImage = null;
            try {
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                outputImage = new File(folder, photoName);
                if (outputImage.exists()) {
                    outputImage.delete();
                }
                outputImage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (outputImage != null) {
                imageUri = Uri.fromFile(outputImage);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                activity.startActivityForResult(takePictureIntent, requestCode);
            }
        }
        return imageUri;
    }

    /**
     * Choose photo, this photo data will be returned in onActivityResult()
     *
     * @param activity
     * @param requestCode
     */
    public static void openPhoto(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 打开包含image目录的工具（文件管理/图库...）
     */
    public static void openGallery(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 精准打开图库
     */
    public static void openGallery2(Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转到手机应用管理页
     */
    public static void openPhoneDetail(Context context) {
        context.startActivity(new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS));  //跳转到手机应用管理页
    }

    /**
     * Open App Detail page 跳转至应用详情
     *
     * @param context
     */
    public static void openAppDetail(Context context) {
        openAppDetail(context.getPackageName(), context);
    }

    /**
     * Open App Detail page  跳转至应用详情
     *
     * @param packageName
     * @param context
     */
    public static void openAppDetail(String packageName, Context context) {
        Intent intent = new Intent();
        final int apiLevel = Build.VERSION.SDK_INT;
        if (apiLevel >= 9) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", packageName, null);
            intent.setData(uri);
        } else {
            final String appPkgName = (apiLevel == 8 ? "pkg" : "com.android.settings.ApplicationPkgName");
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra(appPkgName, packageName);
        }
        context.startActivity(intent);
    }

    /**
     * 调用系统分享
     */
    public static void shareToOtherApp(Context context, String title, String content, String dialogTitle) {
        Intent intentItem = new Intent(Intent.ACTION_SEND);
        intentItem.setType("text/plain");
        intentItem.putExtra(Intent.EXTRA_SUBJECT, title);
        intentItem.putExtra(Intent.EXTRA_TEXT, content);
        intentItem.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intentItem, dialogTitle));
    }

    /**
     * 获取其他应用组件的意图
     *
     * @param packageName 包名
     * @param className   全类名
     * @return intent
     */
    public static Intent getComponentIntent(String packageName, String className) {
        return getComponentIntent(packageName, className, null);
    }

    /**
     * 获取其他应用组件的意图
     *
     * @param packageName 包名
     * @param className   全类名
     * @param bundle      bundle
     * @return intent
     */
    public static Intent getComponentIntent(String packageName, String className, Bundle bundle) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (bundle != null) intent.putExtras(bundle);
        ComponentName cn = new ComponentName(packageName, className);
        intent.setComponent(cn);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取关机的意图
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.SHUTDOWN"/>}</p>
     *
     * @return intent
     */
    public static Intent getShutdownIntent() {
        Intent intent = new Intent(Intent.ACTION_SHUTDOWN);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 打开APP
     * @param context
     * @param packageName
     */
    public static void openApp(Context context, String packageName) {
        PackageInfo packageinfo = null;
        try {
            packageinfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        List<ResolveInfo> resolveinfoList = context.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            String pkgName = resolveinfo.activityInfo.packageName;
            String className = resolveinfo.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            ComponentName cn = new ComponentName(pkgName, className);
            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }

    /**
     * 获取打开App的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getLaunchAppIntent(Context context, String packageName) {
        return context.getPackageManager().getLaunchIntentForPackage(packageName);
    }

    /**
     * 获取安装App（支持6.0）的意图
     *
     * @param filePath 文件路径
     * @return intent
     */
    public static Intent getInstallAppIntent(Context context, String filePath) {
        return getInstallAppIntent(context, FileUtils.getFileByPath(filePath));
    }

    /**
     * 获取安装App(支持6.0)的意图
     *
     * @param file 文件
     * @return intent
     */
    public static Intent getInstallAppIntent(Context context, File file) {
        if (file == null) return null;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String type;

        if (Build.VERSION.SDK_INT < 23) {
            type = "application/vnd.android.package-archive";
        } else {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(FileUtils.getFileExtension(file));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.your.package.fileProvider", file);
            intent.setDataAndType(contentUri, type);
        }
        intent.setDataAndType(Uri.fromFile(file), type);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取卸载App的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getUninstallAppIntent(String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

}
