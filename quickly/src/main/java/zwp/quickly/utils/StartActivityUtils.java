package zwp.quickly.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import zwp.quickly.R;

/**
 * <p>describe：activity跳转工具
 * <p>    note：
 * <p>  author：zwp on 2017/5/2 mail：1101558280@qq.com web: http://www.zwping.win </p>
 * <p>
 * 底部有跳转动画案例
 */

public class StartActivityUtils {

    public static void startActivity(Activity activity, Class<?> targetClass) {
        Intent intent = new Intent(activity, targetClass);
        activity.startActivity(intent);
    }

    public static void startActivity(Activity activity, Class<?> targetClass, Bundle bundle) {
        Intent intent = new Intent(activity, targetClass);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }


    public static void startActivityFinish(Activity activity, Class<?> targetClass) {
        Intent intent = new Intent(activity, targetClass);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void startActivityFinish(Activity activity, Class<?> targetClass, Bundle bundle) {
        Intent intent = new Intent(activity, targetClass);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void startActivity(Activity activity, Class<?> targetClass, int inAnimID, int outAnimID) {
        Intent intent = new Intent(activity, targetClass);
        activity.startActivity(intent);
        activity.overridePendingTransition(inAnimID, outAnimID);
    }

    public static void startActivity(Activity activity, Class<?> targetClass, int inAnimID, int outAnimID, Bundle bundle) {
        Intent intent = new Intent(activity, targetClass);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(inAnimID, outAnimID);
    }

    public static void startActivityAnim(Activity activity, Class<?> targetClass) {
        Intent intent = new Intent(activity, targetClass);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void startActivityAnim(Activity activity, Class<?> targetClass, Bundle bundle) {
        Intent intent = new Intent(activity, targetClass);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void startActivityFinish(Activity activity, Class<?> targetClass, int inAnimID, int outAnimID) {
        Intent intent = new Intent(activity, targetClass);
        activity.startActivity(intent);
        activity.overridePendingTransition(inAnimID, outAnimID);
        activity.finish();
    }

    public static void startActivityAnimFinish(Activity activity, Class<?> targetClass) {
        Intent intent = new Intent(activity, targetClass);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        activity.finish();
    }

    public static void startActivityAnimFinish(Activity activity, Class<?> targetClass, Bundle bundle) {
        Intent intent = new Intent(activity, targetClass);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        activity.finish();
    }

}

/**
 * 切换Activity动画效果，切换动画效果需要清除缓存
 * overridePendingTransition(R.anim.fade, R.anim.hold);//淡入淡出
 * overridePendingTransition(R.anim.my_scale_action,R.anim.my_alpha_action);//放大淡出
 * overridePendingTransition(R.anim.scale_rotate,R.anim.my_alpha_action);//转动淡出1
 * overridePendingTransition(R.anim.scale_translate_rotate,R.anim.my_alpha_action);//转动淡出2
 * overridePendingTransition(R.anim.scale_translate,R.anim.my_alpha_action);//左上角展开淡出效果
 * overridePendingTransition(R.anim.hyperspace_in,R.anim.hyperspace_out);//压缩变小淡出效果
 * overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);//右往左推出效果
 * overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);//左往→推
 * overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);//下往上推出效果
 * overridePendingTransition(R.anim.slide_left,R.anim.slide_right);//左右交叉效果
 * overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);//缩小效果
 * overridePendingTransition(R.anim.slide_up_in,R.anim.slide_down_out);//上下文交错
 */