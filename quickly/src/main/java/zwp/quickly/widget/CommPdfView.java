package zwp.quickly.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.ldoublem.loadingviewlib.LVLineWithText;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Response;
import zwp.quickly.R;
import zwp.quickly.utils.AnimatorUtils;
import zwp.quickly.utils.StringUtils;
import zwp.quickly.utils.ToastUtils;

/**
 * * 使用pdfview关闭时必须调用destroyTimer
 * <p>
 * <p>
 * author：ping on 2016/12/8 15:04 mail：1101558280@qq.com web: www.zcyke.com
 */

public class CommPdfView extends RelativeLayout {

    private PDFView pdfView;
    private LVLineWithText pdfNumberProgressBar;
    private TextView pdf_num;

    public CommPdfView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(getContext()).inflate(R.layout.pdfview_child_layout, this);
        pdfView = (PDFView) findViewById(R.id.comm_pdf_view);
        pdfNumberProgressBar = (LVLineWithText) findViewById(R.id.pdf_number_progressbar);
        pdfNumberProgressBar.setTextColor(Color.parseColor("#ff0099"));
        pdfNumberProgressBar.setViewColor(Color.parseColor("#ff0099"));
        pdf_num = (TextView) findViewById(R.id.pdf_num);

    }

    public void initView(String path) {
        if (!StringUtils.isEmpty(path))
            OkGo.get(path)
//                .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)  // 有缓存就不使用网络
                    .execute(new FileCallback() {

                        // TODO: 2016/12/24 目前不做缓存，网络请求链接相同(后台更新前台无法更新)
//                    @Override
//                    public void onCacheSuccess(File file, Call call) {
//                        super.onCacheSuccess(file, call);
//                        setPdfInfo(file, true);
//                    }

                        @Override
                        public void onSuccess(File file, Call call, Response response) {
                            setPdfInfo(file);
                        }

                        @Override
                        public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                            super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
//                            pdfNumberProgressBar.setMax((int) totalSize);
                            pdfNumberProgressBar.setValue((int) ((currentSize * 100) / totalSize));
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            ToastUtils.showShortSafe(getContext().getString(R.string.pdf_get_fail));
                        }
                    });
    }

    private void setPdfInfo(File file) {
        pdfView.fromFile(file)
                .defaultPage(0)//默认展示第一页
                .onPageChange(pdfviewPageChangeObserver)//监听页面切换
                .onRender(new OnRenderListener() {
                    @Override
                    public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                        pdfView.fitToWidth(); //自适应屏幕
                    }
                })
                .load();

        AnimatorUtils.alpha(pdfNumberProgressBar,1.0f,0.0f,500);
        pdfView.setVisibility(VISIBLE);
        AnimatorUtils.alpha(pdfView,0.0f,1.0f,500);
        pdf_num.setVisibility(VISIBLE);
        AnimatorUtils.alpha(pdf_num,0.0f,1.0f,500);
    }

    OnPageChangeListener pdfviewPageChangeObserver = new OnPageChangeListener() {
        @Override
        public void onPageChanged(int page, int pageCount) {
            pdf_num.setText((page + 1) + "/" + pageCount);
        }
    };


}
