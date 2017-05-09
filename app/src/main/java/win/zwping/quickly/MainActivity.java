package win.zwping.quickly;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import zwp.quickly.Config;
import zwp.quickly.base.BaseActivity;
import zwp.quickly.utils.ArrayUtils;
import zwp.quickly.utils.CollectionUtils;
import zwp.quickly.utils.ConvertUtils;
import zwp.quickly.utils.FileUtils;
import zwp.quickly.utils.ImageUtils;
import zwp.quickly.utils.IntentUtils;
import zwp.quickly.utils.LocationUtils;
import zwp.quickly.utils.RandomUtils;
import zwp.quickly.utils.SDCardUtils;
import zwp.quickly.utils.TimeUtils;
import zwp.quickly.utils.UrlUtils;
import zwp.quickly.utils.ViewUtils;

public class MainActivity extends BaseActivity {


    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btn7)
    Button btn7;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.activity_player)
    LinearLayout activityPlayer;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.textview)
    TextView textview;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.relative_layout)
    RelativeLayout relativeLayout;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.layout2)
    LinearLayout layout2;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.layout3)
    LinearLayout layout3;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.activity_layout)
    LinearLayout activityLayout;

    private Integer[] ss = new Integer[]{1, 2, 3, 4, 5};
    private HashSet<Integer> hashSet = new HashSet<>();
    private List<Integer> list = new ArrayList<>();

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initIntentParameter(Bundle parameter) {

    }


    @Override
    public void doBusiness(Context mContext) {
        ButterKnife.bind(this);

        img = (ImageView) findViewById(R.id.img);

    }

    private String dirName = "zhan/zhan/zhan/zhan/";
    private String fileName = "zhan1.txt/";

    private String strFile = SDCardUtils.getSDCardPath() + "zhan/zhan/zhan/zhan";


    // 遍历接收一个文件路径，然后把文件子目录中的所有文件遍历并输出来
    private void getAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    getAllFiles(f);
                } else {
                    logger(f);
                }
            }
        }
    }

    String str1 = "1.0.01.1";
    String str2 = "0";

    UrlUtils urlUtils = new UrlUtils("https://www.github.com?time=123&date=222");

    List<Integer> ints;

    private void sort() {
        long time1 = System.currentTimeMillis();
        //获取随机值
        ints = new ArrayList<>();
        int randomFrequency = 0;
        while (true) {
            Integer temp = RandomUtils.randomInt(100) + 1;
            if (CollectionUtils.listIfNoContainElement(ints, temp)) ints.add(temp);
            ++randomFrequency;
            if (ints.size() == 100) break;
        }
        long time2 = System.currentTimeMillis();
        logger("获取100个随机值的次数：" + randomFrequency + "花费时间(毫秒)：" + (time2 - time1));

        int size1 = 0;
        int size = 0;
        int length = ints.size();
        for (int i = 0; i < length; i++) {
            ++size1;
            for (int i1 = 0; i1 < (length - 1 - i); i1++) {
                if (ints.get(i1) > ints.get(i1 + 1)) {
                    int temp = ints.get(i1);
                    ints.set(i1, ints.get(i1 + 1));
                    ints.set(i1 + 1, temp);
                    ++size;
                }
            }
        }
        long time3 = System.currentTimeMillis();
        logger("排序个数：" + length + "循环次数：" + size + "花费(毫秒)：" + (time3 - time2) + "结果：" + CollectionUtils.traverseCollection(ints));
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                OkGo.get("http://avatar.csdn.net/2/A/D/1_hantangsongming.jpg")
                        .execute(new BitmapCallback() {
                            @Override
                            public void onSuccess(Bitmap bitmap, Call call, Response response) {
                                File file = new File(Config.IMG + "1233.jpg");
                                ImageUtils.save(bitmap, file, Bitmap.CompressFormat.JPEG);
                                // 其次把文件插入到系统图库
                                try {
                                    MediaStore.Images.Media.insertImage(context().getContentResolver(),
                                            file.getAbsolutePath(), fileName, null);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                // 最后通知图库更新
                                context().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));
                                showToast("123");
                            }
                        });
                break;
            case R.id.btn2:
                break;
            case R.id.btn3:
                break;
            case R.id.btn4:
                break;
            case R.id.btn5:
                break;
            case R.id.btn6:
                break;
            case R.id.btn7:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
