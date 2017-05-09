package zwp.quickly.utils;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zwp.quickly.Quickly;
import zwp.quickly.R;
import zwp.quickly.base.BaseApplication;
import zwp.quickly.custom.SmoothCheckBox;

/**
 * <p>describe：验证数据合法性
 * <p>    note：
 * <p>  author：zwp on 2017/5/2 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */

public class VerifyUtils {

    /**
     * editText为空判断
     */
    public static boolean isEmpty(EditText editText, String toastStr) {
        if (StringUtils.isEmpty(editText.getText().toString().trim())) {  //是否为空
            ToastUtils.showShortSafe(toastStr);
            SoftKeyBoardUtils.showKeyBoard(editText);
            return false;
        } else return true;
    }

    /**
     * textView为空判断
     */
    public static boolean isEmpty(TextView textView, String toastStr) {
        if (StringUtils.isEmpty(textView.getText().toString().trim())) {  //是否为空
            ToastUtils.showShortSafe(toastStr);
            return false;
        } else return true;
    }


    /**
     * 协议查看验证(checkBox验证)
     */
    public static boolean agreement(CheckBox checkBox) {
        if (!checkBox.isChecked()) {
            ToastUtils.showShortSafe(Quickly.getContext().getString(R.string.read_and_confirm_agreement));
            return false;
        } else return true;
    }

    /**
     * 账号验证(未知其为手机/邮箱)
     */
    public static boolean account(EditText account) {
        String ac = ViewUtils.getStr(account);
        if (EmptyUtils.isEmpty(ac)) {
            ToastUtils.showShortSafe("");
            return false;
        }
        if (ac.contains("@")) {
            emailAccount(account);
        } else {
            photoAccount(account);
        }
        return true;
    }

    /**
     * 手机账号验证
     */
    public static boolean photoAccount(EditText account) {
        String photoNum = account.getText().toString().trim();
        if (StringUtils.isEmpty(photoNum)) {  //是否为空
            ToastUtils.showShortSafe(Quickly.getContext().getString(R.string.phone_ac_hint));
            SoftKeyBoardUtils.showKeyBoard(account);
            return false;
        }
        if (photoNum.length() != 11) {  //验证格式
            ToastUtils.showShortSafe(Quickly.getContext().getString(R.string.phone_warning));
            SoftKeyBoardUtils.showKeyBoard(account);
            return false;
        }
        return true;
    }

    /**
     * 邮箱账号验证
     */
    public static boolean emailAccount(EditText account) {
        String emailAccount = account.getText().toString().trim();
        if (StringUtils.isEmpty(emailAccount)) {  //是否为空
            ToastUtils.showShortSafe(Quickly.getContext().getString(R.string.email_ac_hint));
            SoftKeyBoardUtils.showKeyBoard(account);
            return false;
        }
        if (!RegexUtils.isEmail(emailAccount)) {  //验证格式
            ToastUtils.showShortSafe(Quickly.getContext().getString(R.string.email_warning));
            SoftKeyBoardUtils.showKeyBoard(account);
            return false;
        }
        return true;
    }

    /**
     * 验证码验证
     */
    public static boolean verifyCode(EditText code) {
        String verifyCode = code.getText().toString().trim();
        if (StringUtils.isEmpty(verifyCode)) {  //是否为空
            ToastUtils.showShortSafe(Quickly.getContext().getString(R.string.verify_hint));
            SoftKeyBoardUtils.showKeyBoard(code);
            return false;
        }
        return true;
    }

    /**
     * 密码验证
     */
    public static boolean passWord(EditText password) {
        String passWord = password.getText().toString().trim();
        if (StringUtils.isEmpty(passWord)) {  //是否为空
            ToastUtils.showShortSafe(Quickly.getContext().getString(R.string.password_hint));
            SoftKeyBoardUtils.showKeyBoard(password);
            return false;
        }
        if (passWord.length() < 6) {  //验证格式
            ToastUtils.showShortSafe(Quickly.getContext().getString(R.string.ps_length_max_6));
            password.setText("");
            SoftKeyBoardUtils.showKeyBoard(password);
            return false;
        }
        if (!RegexUtils.isContainNumber(passWord)) {  //验证格式
            ToastUtils.showShortSafe(Quickly.getContext().getString(R.string.ps_verify));
            password.setText("");
            SoftKeyBoardUtils.showKeyBoard(password);
            return false;
        }
        if (!RegexUtils.isContainLetter(passWord)) {  //验证格式
            ToastUtils.showShortSafe(Quickly.getContext().getString(R.string.ps_verify));
            password.setText("");
            SoftKeyBoardUtils.showKeyBoard(password);
            return false;
        }
        return true;
    }

    /**
     * 确认密码验证
     */
    public static boolean confirmPassWord(EditText password, EditText confirmPassword) {
        String passWord = password.getText().toString().trim();
        String confirmPassWord = confirmPassword.getText().toString().trim();
        if (StringUtils.isEmpty(confirmPassWord)) {  //是否为空
            ToastUtils.showShortSafe(Quickly.getContext().getString(R.string.please_re_input_ps));
            SoftKeyBoardUtils.showKeyBoard(confirmPassword);
            return false;
        }
        if (!passWord.equals(confirmPassWord)) {
            ToastUtils.showShortSafe(Quickly.getContext().getString(R.string.two_ps_warning));
            password.setText("");
            confirmPassword.setText("");
            SoftKeyBoardUtils.showKeyBoard(password);
            return false;
        }
        return true;
    }

    /**
     * 真实姓名验证
     * @param realName
     * @return
     */
    public static boolean realName(EditText realName) {
        String trueName = realName.getText().toString().trim();
        if (StringUtils.isEmpty(trueName)) {  //是否为空
            ToastUtils.showShortSafe(Quickly.getContext().getString(R.string.real_name_hint));
            SoftKeyBoardUtils.showKeyBoard(realName);
            return false;
        }
        if (trueName.length() < 2) {  //验证格式
            ToastUtils.showShortSafe(Quickly.getContext().getString(R.string.real_name_waring));
            SoftKeyBoardUtils.showKeyBoard(realName);
            return false;
        }
        return true;
    }

    /**
     * 身份证号验证
     * @param idNumber
     * @return
     */
    public static boolean idNumber(EditText idNumber) {
        String id = idNumber.getText().toString().trim();
        if (StringUtils.isEmpty(id)) {  //是否为空
            ToastUtils.showShortSafe(Quickly.getContext().getString(R.string.id_number_hint));
            SoftKeyBoardUtils.showKeyBoard(idNumber);
            return false;
        }
        if (id.length() != 18) {  //验证格式
            ToastUtils.showShortSafe(Quickly.getContext().getString(R.string.id_number_waring));
            SoftKeyBoardUtils.showKeyBoard(idNumber);
            return false;
        }
        return true;
    }
}
