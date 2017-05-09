package zwp.quickly.utils.toolsUtils;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import zwp.quickly.utils.TimeUtils;

/**
 * <p>describe：pickerView方法封装
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */
//https://github.com/Bigkoo/Android-PickerView/wiki/中文说明文档（3.x版）

public class PickerViewUtils {

    private PickerViewUtils() {
        throw new UnsupportedOperationException("u can't instantiate me..."); //不支持操作的异常
    }

    /**
     * 时间选择器
     *
     * @param context
     * @param type     六种选择模式，年月日时分秒，年月日，时分，月日时分，年月，年月日时分{@link TimePickerView.Type}
     * @param listener
     */
    public static void timePickerInit(Context context, TimePickerView.Type type, TimePickerView.OnTimeSelectListener listener) {
        TimePickerView pvTime = new TimePickerView.Builder(context, listener)
                .setType(type)
                .build();
        //pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    /**
     * 高级时间选择器
     */
    public static void timePickerinit(Context context, TimePickerView.OnTimeSelectListener onTimeSelectListener) {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11

        /*获取当前年月日，设置当前选中时间为二天后的8点，开始时间为第二天，结束时间为后10年*/
        int currentYear = Integer.parseInt(TimeUtils.millis2String(System.currentTimeMillis(), "yyyy"));
        int currentMonth = Integer.parseInt(TimeUtils.millis2String(System.currentTimeMillis(), "MM"));
        int currentDay = Integer.parseInt(TimeUtils.millis2String(System.currentTimeMillis(), "dd"));
        int hourOfDay = Integer.parseInt(TimeUtils.millis2String(System.currentTimeMillis(), "hh"));
        int minute = Integer.parseInt(TimeUtils.millis2String(System.currentTimeMillis(), "mm"));
        int second = Integer.parseInt(TimeUtils.millis2String(System.currentTimeMillis(), "ss"));

        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(currentYear, currentMonth - 1, currentDay + 2, hourOfDay + 1, minute, second);  //当前显示时间为2天后的8点

        Calendar startDate = Calendar.getInstance();
        startDate.set(currentYear, currentMonth - 1, currentDay + 2);   //开始时间为2天后

        Calendar endDate = Calendar.getInstance();
        endDate.set(currentYear + 10, currentMonth - 1, currentDay);  //结束时间为10年后

        TimePickerView pvTime = new TimePickerView.Builder(context, onTimeSelectListener)
                .setRangDate(startDate, endDate)
                .setDate(selectedDate)
                .build();
        //pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }


    /**
     * 单条件选择器
     * @param context
     * @param list
     * @param defaultPosition 默认位置(-1)
     * @param listener
     */
    public static void singlePickerInit(Context context, String title, @NonNull final List<String> list, int defaultPosition, final SingleSelectListener listener) {
        OptionsPickerView optionsPickerView = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                listener.dataResult(list.get(options1));
            }
        })
                .setTitleText(title)
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        optionsPickerView.setPicker(list);
        if (defaultPosition >= 0) optionsPickerView.setSelectOptions((defaultPosition - 1));
        optionsPickerView.show();
    }

    public interface SingleSelectListener {
        void dataResult(String str);
    }

}
