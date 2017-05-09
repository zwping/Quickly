package zwp.quickly.utils.toolsUtils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import zwp.quickly.Quickly;
import zwp.quickly.R;
import zwp.quickly.base.BaseApplication;

/**
 * <p>describe：glide重写，加入常规使用方法
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class PicassoUtils {

    public PicassoUtils() {
        throw new UnsupportedOperationException("u can't instantiate me..."); //不支持操作的异常
    }

    public static void picasso(Context context, String imgUrl, ImageView intoImageView) {
        if (imgUrl != null && intoImageView != null)
            Picasso.with(context).load(imgUrl).placeholder(R.mipmap.loading).error(R.mipmap.loading_err).into(intoImageView);
    }
    public static void picasso(Context context,Uri imgUri, ImageView intoImageView) {
        if (imgUri != null && intoImageView != null)
            Picasso.with(context).load(imgUri).placeholder(R.mipmap.loading).error(R.mipmap.loading_err).into(intoImageView);
    }
    public static void picasso(Context context,int resourceId, ImageView intoImageView) {
        if (intoImageView != null)
            Picasso.with(context).load(resourceId).placeholder(R.mipmap.loading).error(R.mipmap.loading_err).into(intoImageView);
    }
    public static void picasso(Context context,File file, ImageView intoImageView) {
        if (file != null && intoImageView != null)
            Picasso.with(context).load(file).placeholder(R.mipmap.loading).error(R.mipmap.loading_err).into(intoImageView);
    }

    public static void picassoNoErrorPic(Context context,String imgUrl, ImageView intoImageView) {
        if (imgUrl != null && intoImageView != null)
            Picasso.with(context).load(imgUrl).into(intoImageView);
    }
    public static void picassoNoErrorPic(Context context,Uri imgUrl, ImageView intoImageView) {
        if (imgUrl != null && intoImageView != null)
            Picasso.with(context).load(imgUrl).into(intoImageView);
    }
    public static void picassoNoErrorPic(Context context,int imgUrl, ImageView intoImageView) {
        if (intoImageView != null)
            Picasso.with(context).load(imgUrl).into(intoImageView);
    }
    public static void picassoNoErrorPic(Context context,File imgUrl, ImageView intoImageView) {
        if (imgUrl != null && intoImageView != null)
            Picasso.with(context).load(imgUrl).into(intoImageView);
    }
}
