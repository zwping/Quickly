package zwp.quickly.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import zwp.quickly.R;
import zwp.quickly.utils.toolsUtils.LogUtils;

/**
 * <p>describe：通用EditText，包含清空功能，常用于账号密码输入
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 * 目的实现以下功能：
 * - 进入界面不聚焦
 * - 最右侧具有清空功能
 * - 设置hint颜色/只允许输入一行
 * <p>
 * 注意：
 * - 如果需要调用英文键盘使用inputType - TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or emailAddress (-小米)
 * - EditText不被软键盘遮盖，如果是纯Activity页面，只需在AndroidManifest文件中对对应的activity设置android:windowSoftInputMode="adjustPan"
 * - 输入限制{@see strings文件}，调用方法setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.no_input_chinese)));
 * <p>
 * 为什么封装EditText？除了熟悉EditText和添加清空功能，还有就是简化代码，针对项目中相同的EditText二次封装，二次封装的同时给予限制条件，Activity直接调用二次封装的结果。
 * 这样就实现了初始目的：快速开发
 * 二次封装代码末尾有案例
 * 如果为密码输入框，需要在当前activity中设置：
 * editText1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());  //显示密码
 * editText1.setTransformationMethod(PasswordTransformationMethod.getInstance());   //隐藏密码
 * -->
 */

public class CommEditText extends EditText {

    private Context mContext;
    private Drawable mEmptyDrawable;

    private Drawable mLeftDrawable;

    public CommEditText(Context context) {
        super(context);
        init(context);
    }

    public CommEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CommEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        /* 设置当前EditText失去键盘焦点andTouch焦点，在触摸时给其恢复。或者使其父控件获取焦点 */
        setFocusable(false);
        setFocusableInTouchMode(false);
        initView();
        // 是否自定义empty控件
        if (getCompoundDrawables()[2] != null) {
            mEmptyDrawable = getCompoundDrawables()[2];
            setEmptyIsShow(false);
        } else mEmptyDrawable = ContextCompat.getDrawable(mContext, R.drawable.clear_icon);
        mLeftDrawable = getCompoundDrawables()[0];
    }

    private void initView() {
        setHintTextColor(Color.LTGRAY);
        setMaxLines(1);
        setSingleLine();
    }

    /**
     * 设置最右侧清空按钮
     *
     * @param hasFocus 获取到焦点才判断显示清空按钮
     */
    private void setEmptyIsShow(Boolean hasFocus) {
        if (hasFocus && getText().toString().length() > 0) {
            setCompoundDrawablesWithIntrinsicBounds(mLeftDrawable, null, mEmptyDrawable, null);
        } else setCompoundDrawablesWithIntrinsicBounds(mLeftDrawable, null, null, null);
    }

    /**
     * 触摸监听
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        setObserver();
        if (mEmptyDrawable != null && getCompoundDrawables()[2] != null && event.getAction() == MotionEvent.ACTION_UP) { //清空按钮存在并显示
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 80;
            if (rect.contains(eventX, eventY)) {
                setText("");
            }
        } else {  //初始触摸恢复其焦点
            setFocusable(true);
            setFocusableInTouchMode(true);
        }
        return super.onTouchEvent(event);
    }

    /**
     * 触摸才给监听 防止setText触法清空按钮
     */
    private void setObserver() {

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { //内容变化前
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { //内容变化中
            }

            @Override
            public void afterTextChanged(Editable editable) {  //内容变化后
                setEmptyIsShow(true);
            }
        });

        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (hasFocus()) {
                    setEmptyIsShow(true);
                } else {
                    setEmptyIsShow(false);
                }
            }
        });
    }
}

//    /** 登陆账号初始化 */
//    public static void accountNumberInit(EditTextCustom editTextCustom) {
//        editTextCustom.setBackground(null);
//        editTextCustom.setTextSize(14);
//        editTextCustom.setHint(editTextCustom.getResources().getString(R.string.iphone_land_hint));
//        editTextCustom.setInputType(InputType.TYPE_CLASS_NUMBER);
//        editTextCustom.setKeyListener(DigitsKeyListener.getInstance(editTextCustom.getResources().getString(com.zwp.tools.R.string.input_number)));
//        editTextCustom.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
//    }

// xml封装，include引用

// 不建议对EditTextCustom二次封装，setInputType失效（暂未解决，目前网上资料显示是setInputType和setSingleLine有冲突，不影响使用，后续有时间再来解决）


//文本类型，多为大写、小写和数字符号。
//        android:inputType="none"
//        android:inputType="text"
//        android:inputType="textCapCharacters" 字母大写
//        android:inputType="textCapWords" 首字母大写
//        android:inputType="textCapSentences" 仅第一个字母大写
//        android:inputType="textAutoCorrect" 自动完成
//        android:inputType="textAutoComplete" 自动完成
//        android:inputType="textMultiLine" 多行输入
//        android:inputType="textImeMultiLine" 输入法多行（如果支持）
//        android:inputType="textNoSuggestions" 不提示
//        android:inputType="textUri" 网址
//        android:inputType="textEmailAddress" 电子邮件地址
//        android:inputType="textEmailSubject" 邮件主题
//        android:inputType="textShortMessage" 短讯
//        android:inputType="textLongMessage" 长信息
//        android:inputType="textPersonName" 人名
//        android:inputType="textPostalAddress" 地址
//        android:inputType="textPassword" 密码
//        android:inputType="textVisiblePassword" 可见密码
//        android:inputType="textWebEditText" 作为网页表单的文本
//        android:inputType="textFilter" 文本筛选过滤
//        android:inputType="textPhonetic" 拼音输入
//
//        android:textColorHint设置提示hint信息的颜色。
//
//
//        //数值类型
//        android:inputType="number" 数字
//        android:inputType="numberSigned" 带符号数字格式
//        android:inputType="numberDecimal" 带小数点的浮点格式
//        android:inputType="phone" 拨号键盘
//        android:inputType="datetime" 时间日期
//        android:inputType="date" 日期键盘
//        android:inputType="time" 时间键盘