package zwp.quickly.utils.toolsUtils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.model.IPickerViewData;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import zwp.quickly.R;
import zwp.quickly.base.BaseApplication;
import zwp.quickly.utils.EmptyUtils;
import zwp.quickly.utils.GetJsonDataUtils;
import zwp.quickly.utils.StringUtils;
import zwp.quickly.utils.ToastUtils;

/**
 * <p>describe：基于pickerView框架改装的城市选择
 * <p>    note：onCreate()中需要初始化{@link #init(Context)}
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class CityPickerUtils {

    private static CityPickerUtils cityPickerUtils = null; //单例

    public static CityPickerUtils getInstance() {
        if (cityPickerUtils == null) {
            cityPickerUtils = new CityPickerUtils();
        }
        return cityPickerUtils;
    }

    private Context mContext = null;
    private Thread thread = null;
    private boolean isLoaded = false;
    private ArrayList<JsonBean> options1Items = new ArrayList<>(); //省份信息
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>(); //市 信息
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>(); //区 信息

    public void init(Context context) {
        this.mContext = context;
        if (thread == null) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    initJsonData();
                }
            });
            thread.start();
        }
    }

    public void showPickerView(String title, String province, String city, String district, final CityPickerSelectListener listener) {// 弹出选择器

        if (!isLoaded) ToastUtils.showShortSafe("省市区数据加载失败，请重试");//加载json数据失败

        String defaultProvince = "广东省";
        String defaultCity = "深圳市";
        String defaultDistrict = "福田区";

        if (EmptyUtils.isNotEmpty(province)) defaultProvince = province;
        if (EmptyUtils.isNotEmpty(city)) defaultCity = city;
        if (EmptyUtils.isNotEmpty(district)) defaultDistrict = district;

        int defaultProvincePosition = -1;
        int defaultCityPosition = -1;
        int defaultDistrictPosition = -1;

        for (int i = 0; i < options1Items.size(); i++) { //获取当前省份的位置
            if (options1Items.get(i).getPickerViewText().equals(defaultProvince)) {
                defaultProvincePosition = i;
                break;
            }
        }
        if (defaultProvincePosition != -1) {
            for (int i = 0; i < options2Items.size(); i++) { //获取当前市 的位置
                if (options2Items.get(defaultProvincePosition).get(i).equals(defaultCity)) {
                    defaultCityPosition = i;
                    break;
                }
            }
            if (defaultCityPosition != -1) {
                for (int i = 0; i < options3Items.size(); i++) { //获取当前县区的位置
                    if (options3Items.get(defaultProvincePosition).get(defaultCityPosition).get(i).equals(defaultDistrict)) {
                        defaultDistrictPosition = i;
                        break;
                    }
                }
            }
        }

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                listener.selectResult(options1Items.get(options1).getPickerViewText(),
                        options2Items.get(options1).get(options2),
                        options3Items.get(options1).get(options2).get(options3));

            }
        })
                .setTitleText(title)
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器

        if (defaultProvincePosition != -1 && defaultCityPosition != -1 && defaultDistrictPosition != -1)
            pvOptions.setSelectOptions(defaultProvincePosition, defaultCityPosition, defaultDistrictPosition); //默认显示位置
        pvOptions.show();
    }

    public interface CityPickerSelectListener {
        void selectResult(String province, String city, String district);
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = GetJsonDataUtils.getJson(mContext, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        isLoaded = true;

    }

    /**
     * 解析json
     */
    private ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    /**
     * json数据源
     */
    public class JsonBean implements IPickerViewData {

        /**
         * name : 省份
         * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区"]}]
         */

        private String name;
        private List<CityBean> city;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<CityBean> getCityList() {
            return city;
        }

        public void setCityList(List<CityBean> city) {
            this.city = city;
        }

        // 实现 IPickerViewData 接口，
        // 这个用来显示在PickerView上面的字符串，
        // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
        @Override
        public String getPickerViewText() {
            return this.name;
        }


        public class CityBean {
            /**
             * name : 城市
             * area : ["东城区","西城区","崇文区","昌平区"]
             */

            private String name;
            private List<String> area;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<String> getArea() {
                return area;
            }

            public void setArea(List<String> area) {
                this.area = area;
            }
        }
    }


}
