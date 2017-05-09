package zwp.quickly.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

import zwp.quickly.R;
import zwp.quickly.utils.ActivityResultUtils;
import zwp.quickly.utils.CollectionUtils;
import zwp.quickly.utils.ScreenUtils;
import zwp.quickly.utils.ToastUtils;
import zwp.quickly.utils.ViewUtils;
import zwp.quickly.utils.toolsUtils.GlideUtils;

/**
 * <p>describe：comm add pic layout
 * <p>    note：{@link #init(Activity, int, int, AddPicListListener)} {@link #addData(int, int, Intent)}
 * <p>  author：zwp on 2017/4/28 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */

public class CommAddPic extends RelativeLayout {

    private RecyclerView recycler;
    private List<String> mData = null;
    private Adapter adapter = null;

    private Activity mActivity = null;
    private int rowNumber = 0;
    private int max = 0;

    private AddPicListListener listener = null;

    public CommAddPic(Context context) {
        super(context);
        initView();
    }

    public CommAddPic(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CommAddPic(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.child_add_pic_comm, this);
        recycler = (RecyclerView) findViewById(R.id.recycler);
    }

    public void init(final Activity activity, int rowNumber, int multiSelect, AddPicListListener listener) {
        this.mActivity = activity;
        this.rowNumber = rowNumber;
        this.max = multiSelect;
        this.listener = listener;

        mData = new ArrayList<>();
        mData.add("默认填充数据");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, rowNumber, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recycler.setLayoutManager(gridLayoutManager);
        recycler.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        recycler.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.HORIZONTAL));
        recycler.setItemAnimator(new DefaultItemAnimator());
        adapter = new Adapter(mData);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recycler.setAdapter(adapter);

        recycler.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == mData.size() - 1)
                    new PhotoPopup(mActivity, "请选择相片", true, max, true);
            }
        });
        recycler.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mData.remove(position);
                changeAdapter();
            }
        });
    }

    /**
     * 怼上onActivityResult数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void addData(int requestCode, int resultCode, Intent data) {
        ActivityResultUtils.imagePickerResult(requestCode, resultCode, data, new ActivityResultUtils.ImagePickerActivityResultInterface() {
            @Override
            public void resultImagePickerPhoto(ArrayList<ImageItem> images) {
                mData = new ArrayList<String>();
                for (int i = 0; i < images.size(); i++) {
                    mData.add(images.get(i).path);
                }
                changeAdapter();
            }

            @Override
            public void resultError() {
                ToastUtils.showShortSafe("照片选择失败");
            }
        });
    }

    public interface AddPicListListener {
        void returnPic(List<String> data);
    }

    private void changeAdapter() {
        if (mData.size() < max) { //不满当前最大数量
            if (!CollectionUtils.listIfContainElement(mData, "默认填充数据")) //判断是否已经添加了“占位符”
                mData.add("默认填充数据");
            adapter.falseDataPosition = mData.size();
        } else { //已经满足
            adapter.falseDataPosition = mData.size() + 1; //大于当前总数
        }
        if (CollectionUtils.listIfContainElement(mData, "默认填充数据")) //给当前activity返回数据
            listener.returnPic(CollectionUtils.removePositionList(mData, mData.size() - 1));
        else listener.returnPic(mData);
        adapter.setNewData(mData);
    }

    private class Adapter extends BaseQuickAdapter<String, BaseViewHolder> {

        /**
         * 填充数据的位置(显示add icon)(可能没有)
         */
        public int falseDataPosition = 0;

        private Adapter(List<String> data) {
            super(R.layout.item_add_pic_list, data);
            this.falseDataPosition = data.size();
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView img = (ImageView) helper.getView(R.id.img);
            ImageView del = (ImageView) helper.getView(R.id.del);
            int screenWidth = ScreenUtils.getScreenWidth(mActivity);
            ViewUtils.setWidgetSize(img, screenWidth / rowNumber, screenWidth / rowNumber);
            if (falseDataPosition == (helper.getLayoutPosition() + 1)) {
                GlideUtils.glideCenterCrop(mContext, R.mipmap.add_pic, img, true);
                del.setVisibility(View.GONE);
            } else {
                GlideUtils.glideCenterCrop(mContext, item, img, true);
                del.setVisibility(View.VISIBLE);
                helper.addOnClickListener(R.id.del);
            }
        }
    }


}