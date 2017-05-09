package zwp.quickly.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <p>describe：读取Json文件的工具类(assets)
 * <p>    note：
 * <p>  author：zwp on 2017/4/25 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */

public class GetJsonDataUtils {


    public static String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
