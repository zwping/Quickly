package zwp.quickly.utils.toolsUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import zwp.quickly.R;

/**
 * <p>describe：glide重写，加入常规使用方法
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 */

public class GlideUtils {
    public GlideUtils() {
        throw new UnsupportedOperationException("u can't instantiate me..."); //不支持操作的异常
    }

    /**
     * Glide 加载网络图片标配
     * fitCenter 裁剪技术，即缩放图像让图像都测量出来等于或小于 ImageView 的边界范围。该图像将会完全显示，但可能不会填满整个 ImageView。
     * glide默认使用fitCenter加载方式
     *
     * @param object
     * @param intoImageView
     * @param ifCache
     */
    public static <T> void glide(Context context,T object, ImageView intoImageView, Boolean ifCache) {
        if (object != null && intoImageView != null && ifCache != null)
            Glide.with(context).load(object).fitCenter().skipMemoryCache(!ifCache).diskCacheStrategy(ifCache ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                    .animate(R.anim.glide_loading_in_alpha).placeholder(R.mipmap.loading).error(R.mipmap.loading_err).crossFade().into(intoImageView);
    }

    /**
     * centerCrop 裁剪技术，即缩放图像让它填充到 ImageView 界限内并且侧键额外的部分。ImageView 可能会完全填充，但图像可能不会完整显示。
     *
     * @param object
     * @param intoImageView
     * @param ifCache
     */
    public static <T> void glideCenterCrop(Context context,T object, ImageView intoImageView, Boolean ifCache) {
        if (object != null && intoImageView != null && ifCache != null)
            Glide.with(context).load(object).centerCrop().skipMemoryCache(!ifCache).diskCacheStrategy(ifCache ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                    .animate(R.anim.glide_loading_in_alpha).placeholder(R.mipmap.loading).error(R.mipmap.loading_err).crossFade().into(intoImageView);
    }


    /**
     * Glide 加载网络图片为圆形
     *
     * @param object
     * @param intoImageView
     * @param ifCache
     */
    public static <T> void GlideCircular(final Context context, T object, final ImageView intoImageView, Boolean ifCache) {
        if (object != null && intoImageView != null && ifCache != null)
            Glide.with(context).load(object).asBitmap().centerCrop().skipMemoryCache(!ifCache).diskCacheStrategy(ifCache ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                    .animate(R.anim.glide_loading_in_alpha).placeholder(R.mipmap.loading).error(R.mipmap.loading_err).into(new BitmapImageViewTarget(intoImageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    intoImageView.setImageDrawable(circularBitmapDrawable);
                }
            });
    }

    /**
     * 加载没有过渡图片
     */
    public static <T> void glideNoErrorPic(Context context,T object, ImageView intoImageView, Boolean ifCache) {
        if (object != null && intoImageView != null && ifCache != null)
            Glide.with(context).load(object).fitCenter().skipMemoryCache(!ifCache).diskCacheStrategy(ifCache ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                    .animate(R.anim.glide_loading_in_alpha).into(intoImageView);
    }

    /**
     * 加载没有过渡图片
     */
    public static <T> void glideCenterCropNoErrorPic(Context context,T object, ImageView intoImageView, Boolean ifCache) {
        if (object != null && intoImageView != null && ifCache != null)
            Glide.with(context).load(object).centerCrop().skipMemoryCache(!ifCache).diskCacheStrategy(ifCache ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                    .animate(R.anim.glide_loading_in_alpha).into(intoImageView);
    }

    /**
     * 加载没有过渡图片
     */
    public static <T> void GlideCircularNoErrorPic(final Context context, T object, final ImageView intoImageView, Boolean ifCache) {
        if (object != null && intoImageView != null && ifCache != null)
            Glide.with(context).load(object).asBitmap().centerCrop().skipMemoryCache(!ifCache).diskCacheStrategy(ifCache ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                    .animate(R.anim.glide_loading_in_alpha).into(new BitmapImageViewTarget(intoImageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    intoImageView.setImageDrawable(circularBitmapDrawable);
                }
            });
    }

    /**
     * 中止当前所有请求
     * @deprecated You cannot start a load for a destroyed activity
     */
    @Deprecated
    public static void pauseRequest(Activity activity) {
        Glide.with(activity).pauseRequests();
    }

}
