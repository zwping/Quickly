package zwp.quickly.utils.toolsUtils;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * <p>describe：luban压缩 工具
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class LubanUtils {
    public LubanUtils() {
        throw new UnsupportedOperationException("u can't instantiate me..."); //不支持操作的异常
    }

    /**
     * 参考微信图片压缩效果进行压缩
     */
    public static void luban(Context context, File file, OnCompressListener onCompressListener) {
        Luban.get(context)
                .load(file)                     //传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                .setCompressListener(onCompressListener)
                .launch();    //启动压缩
    }

}
