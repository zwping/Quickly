package zwp.quickly.custom;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import zwp.quickly.R;
import zwp.quickly.base.BasePopupWindow;
import zwp.quickly.utils.CollectionUtils;
import zwp.quickly.utils.EmptyUtils;
import zwp.quickly.utils.toolsUtils.RecyclerViewUtils;

/**
 * <p>describe：
 * <p>    note：
 * <p>  author：zwp on 2017/4/28 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */

public class SpinnerPopup extends BasePopupWindow {

    private Context mContext = null;

    private RecyclerView recycler = null;

    private List<String> data = null;
    private Adapter adapter = null;

    public SpinnerPopup(Activity context, List<String> list, final SpinnerListener listener) {
        super(context);
        this.mContext = context;
        if (mPopupView != null) {
            initView();

            if (EmptyUtils.isEmpty(list)) return;
            data = new ArrayList<>();
            data = list;

            adapter = new Adapter(data);
            RecyclerViewUtils.setRecyclerLinearAdapter(adapter, recycler, true, null, false);
            recycler.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    dismiss();
                    listener.spinnerSelected(CollectionUtils.findList(data,position));
                }
            });
        }
    }

    public interface SpinnerListener{
        void spinnerSelected(String str);
    }

    private void initView() {
        if (mPopupView != null) {
            recycler = (RecyclerView) mPopupView.findViewById(R.id.recycler);
        }
    }

    @Override
    public void showPopupWindow(View v) {
//        setOffsetX(-(getPopupViewWidth() - v.getWidth() / 2));
        setOffsetY(v.getHeight());
        super.showPopupWindow(v);
    }

    @Override
    protected int bindLayout() {
        return R.layout.popup_spinner;
    }

    @Override
    protected View clickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    protected Animation showAnimation() {
        return getInterpolatorFromTopAnimation(getContext());
    }

    @Override
    protected Animation exitAnimation() {
        return getInterpolatorFromBottomAnimation(getContext());
    }

    class Adapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public Adapter(List<String> data) {
            super(R.layout.item_spinner_popup_list, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.content, item);
//            if (helper.getLayoutPosition() % 2 == 0) {
//                helper.getConvertView().setBackgroundColor(Color.parseColor("#d7d7d7"));
//            } else {
//                helper.getConvertView().setBackgroundColor(Color.parseColor("#ffffff"));
//            }
        }
    }


}
