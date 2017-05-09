package zwp.quickly.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * <p>describe：软键盘工具类
 * <p>    note：
 * <p>  author：zwp on 2017/5/2 mail：1101558280@qq.com web: http://www.zwping.win </p>
 * <table>
 * <tr>
 * <th>
 * 点击空白区域键盘隐藏，根布局为scrollView无效 {@link #clearAllEditTextFocusTouchListener(View)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 去除当前所有editText所有焦点，等待100ms {@link #clearAllEditTextFocus(View)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 显示键盘 {@link #showKeyBoard(EditText)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 隐藏键盘 {@link #hideKeyBoard(Activity)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 软键盘显示与隐藏的监听 //监听布局大小的改变 {@link #softKeyboardListener(Activity, SoftKeyBoardListener.OnSoftKeyBoardChangeInterface)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 切换键盘显示与否状态（开了就关，关了就开，慎用） {@link #toggleSoftInput(Context)}
 * </th>
 * </tr>
 * <tr>
 * <th>
 * 关闭dialog中打开的键盘 {@link #closeKeyboard(Dialog)}
 * </th>
 * </tr>
 * </table>
 */

public class SoftKeyBoardUtils {

    private SoftKeyBoardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 避免输入法面板遮挡
     * <p>在manifest.xml中activity中设置</p>
     * <p>android:windowSoftInputMode="adjustPan"</p>
     */

    /**
     * 点击空白区域键盘隐藏，根布局为scrollView无效
     * 让其获取焦点，使当前页面所有EditText失去焦点 （he点击监听冲突）
     *
     * @param view 一般为页面根布局/当前点击的控件
     */
    public static void clearAllEditTextFocusTouchListener(View view) {
        if (view != null)
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    hideKeyBoard(view);
                    return false;
                }
            });
    }

    /**
     * 去除当前所有editText所有焦点，等待100ms
     * <p>
     * - 把焦点转移给某个view，si当前页面所有EditText失去焦点
     *
     * @param view 一般为页面根布局/当前点击的控件
     */
    public static void clearAllEditTextFocus(View view) {
        if (view != null) {
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
            hideKeyBoard(view);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示键盘
     *
     * @param editText
     */
    public static void showKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);   //显示键盘
    }

    /**
     * 隐藏键盘
     *
     * @param activity
     */
    public static void hideKeyBoard(Activity activity) {
        View view = activity.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    /**
     * 隐藏键盘
     *
     * @param view
     */
    public static void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        view.clearFocus();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    /**
     * 软键盘显示与隐藏的监听 //监听布局大小的改变
     *
     * @param overallLayout  最大的布局
     * @param layoutListener 监听使用方法方法下面有eg
     */
    public static void softKeyBoardListener(View overallLayout, ViewTreeObserver.OnGlobalLayoutListener layoutListener) {
        overallLayout.getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
    }
// 使用方法案例
//    new ViewTreeObserver.OnGlobalLayoutListener() {
//        @Override
//        public void onGlobalLayout() {
//            Rect rect = new Rect();
//            overallLayout.getWindowVisibleDisplayFrame(rect);
//            int rootInvisibleHeight = overallLayout.getRootView().getHeight() - rect.bottom;
//            if (rootInvisibleHeight <= 100) { // 键盘隐藏
//            } else {  //键盘显示
//            }
//        }
//    }

    /**
     * 监听软键盘的显隐，并获取其键盘高度
     *
     * @param activity
     * @param onSoftKeyBoardChangeInterface
     */
    public static void softKeyboardListener(Activity activity, SoftKeyBoardListener.OnSoftKeyBoardChangeInterface onSoftKeyBoardChangeInterface) {
        SoftKeyBoardListener.setListener(activity, onSoftKeyBoardChangeInterface);
    }

    /**
     * 判断软键盘是否显示
     * 注意：现在android手机软键盘默认打开，无法直接的判断键盘是否显示，只能监听
     *
     * @param context
     * @return Boolean
     */
    @Deprecated
    public static Boolean ifSoftKeyBoardIsShow(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

    /**
     * 切换键盘显示与否状态（开了就关，关了就开，慎用）
     *
     * @param context 上下文
     */
    public static void toggleSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * 关闭dialog中打开的键盘
     *
     * @param dialog
     */
    public static void closeKeyboard(Dialog dialog) {
        View view = dialog.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) dialog.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    /**
     * 该类用于监听软键盘是否显示并获取其高度
     */
    public static class SoftKeyBoardListener {

        private View rootView;/*activity的根视图*/
        private int rootViewVisibleHeight;/*纪录根视图的显示高度*/
        private OnSoftKeyBoardChangeInterface mOnSoftKeyBoardChangeInterface;

        private SoftKeyBoardListener(Activity activity) {
        /*获取activity的根视图*/
            rootView = activity.getWindow().getDecorView();
        /*监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变*/
            rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                /*获取当前根视图在屏幕上显示的大小*/
                    Rect r = new Rect();
                    rootView.getWindowVisibleDisplayFrame(r);
                    int visibleHeight = r.height();
                    if (rootViewVisibleHeight == 0) {
                        rootViewVisibleHeight = visibleHeight;
                        return;
                    }
                /*根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变*/
                    if (rootViewVisibleHeight == visibleHeight) {
                        return;
                    }
                /*根视图显示高度变小超过200，可以看作软键盘显示了*/
                    if (rootViewVisibleHeight - visibleHeight > 200) {
                        if (mOnSoftKeyBoardChangeInterface != null) {
                            mOnSoftKeyBoardChangeInterface.keyBoardShow(rootViewVisibleHeight - visibleHeight);
                        }
                        rootViewVisibleHeight = visibleHeight;
                        return;
                    }
                /*根视图显示高度变大超过200，可以看作软键盘隐藏了*/
                    if (visibleHeight - rootViewVisibleHeight > 200) {
                        if (mOnSoftKeyBoardChangeInterface != null) {
                            mOnSoftKeyBoardChangeInterface.keyBoardHide(visibleHeight - rootViewVisibleHeight);
                        }
                        rootViewVisibleHeight = visibleHeight;
                        return;
                    }
                }
            });
        }

        private void setOnSoftKeyBoardChangeListener(OnSoftKeyBoardChangeInterface onSoftKeyBoardChangeInterface) {
            this.mOnSoftKeyBoardChangeInterface = onSoftKeyBoardChangeInterface;
        }

        public interface OnSoftKeyBoardChangeInterface {
            void keyBoardShow(int height);

            void keyBoardHide(int height);
        }

        private static void setListener(Activity activity, OnSoftKeyBoardChangeInterface onSoftKeyBoardChangeInterface) {
            SoftKeyBoardListener softKeyBoardListener = new SoftKeyBoardListener(activity);
            softKeyBoardListener.setOnSoftKeyBoardChangeListener(onSoftKeyBoardChangeInterface);
        }

    }

}
