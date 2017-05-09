package zwp.quickly.utils.toolsUtils;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import zwp.quickly.Quickly;
import zwp.quickly.R;
import zwp.quickly.base.BaseApplication;
import zwp.quickly.utils.ViewUtils;

/**
 * <p>describe：recyclerView 工具类
 * <P>基于https://github.com/CymChad/BaseRecyclerViewAdapterHelper框架封装
 * <p>    note：
 * <p>  author：zwp on 2017/5/2 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */

public class RecyclerViewUtils {

    private RecyclerViewUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 设置recyclerView 线性布局（垂直排列）
     *
     * @param adapter
     * @param recyclerView
     * @param isAddItemDecoration 是否增加分割线
     * @param emptyViewText       空布局文字，不填写则添加空布局
     */
    public static void setRecyclerLinearAdapter(BaseQuickAdapter adapter, RecyclerView recyclerView, Boolean isAddItemDecoration, String emptyViewText,boolean ifAddLoadMore) {
        setRecyclerAdapter(adapter, recyclerView, true, true, -1, isAddItemDecoration, emptyViewText, -1, null, -1, ifAddLoadMore);
    }

    /**
     * 设置recyclerView 表格布局 （垂直排列）
     *
     * @param context
     * @param recyclerView
     * @param spanCount
     * @param isAddItemDecoration 是否增加分割线
     * @param emptyViewText       空布局文字，不填写则添加空布局
     * @param ifAddLoadMore       是否添加加载更多的功能
     */
    public static void setRecyclerGridAdapter(Context context, BaseQuickAdapter adapter, RecyclerView recyclerView, int spanCount, Boolean isAddItemDecoration, String emptyViewText, Boolean ifAddLoadMore) {
        setRecyclerAdapter(adapter, recyclerView, false, true, spanCount, isAddItemDecoration, emptyViewText, -1, null, -1, ifAddLoadMore);
    }

    /**
     * 设置recyclerView的布局
     *
     * @param adapter
     * @param recyclerView
     * @param linearOrGridLayout   线性布局true 或 网格布局false
     * @param verticalOrHorizontal 横向排列 或 纵向排列
     * @param spanCount            如果为网格布局，网格的列数 或 行数，线性布局时就填写-1
     * @param isAddItemDecoration  是否增加分割线  **有跟布局的时候不建议使用分割线
     * @param emptyViewText        空布局文字，不填写则添加空布局
     * @param emptyViewBgColor     不设置颜色填写-1
     * @param footerViewText       跟布局文字，不填写则添加跟布局   //不建议使用根布局，与分割线有冲突，使用loadView解决
     * @param footerViewBgColor    不设置颜色填写-1
     * @param ifAddLoadMore       是否添加加载更多的功能
     */
    public static <T> void setRecyclerAdapter(final BaseQuickAdapter adapter, final RecyclerView recyclerView, Boolean linearOrGridLayout, Boolean verticalOrHorizontal,
                                              int spanCount, Boolean isAddItemDecoration, String emptyViewText, int emptyViewBgColor, String footerViewText, int footerViewBgColor,
                                              Boolean ifAddLoadMore) {
        Context context = Quickly.getContext();
        /*必须集成CymChad框架*/
        //if (!(adapter instanceof BaseQuickAdapter)) return;
        /*设置布局显示类型 垂直/水平*/
        int orientation = LinearLayoutManager.HORIZONTAL;
        if (verticalOrHorizontal) orientation = LinearLayoutManager.VERTICAL;
        /*设置布局管理器*/
        if (linearOrGridLayout) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(orientation);
            // 写入
            recyclerView.setLayoutManager(linearLayoutManager);
        } else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanCount);
            gridLayoutManager.setOrientation(orientation);
            // 写入
            recyclerView.setLayoutManager(gridLayoutManager);
        }
        /*增加分隔线  <!-- All customizations that are NOT specific to a particular API-level can go here. --> <item name= "android:listDivider">@drawable/divider </item >*/
        if (isAddItemDecoration) {
            //如果是线性布局 垂直显示
            if (verticalOrHorizontal) {
                recyclerView.addItemDecoration(new DividerItemDecoration(context, orientation));
                //网格布局
                if (!linearOrGridLayout)
                    recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL));
            } else { //如果是线性布局 水平显示
                recyclerView.addItemDecoration(new DividerItemDecoration(context, orientation));
                //网格布局
                if (!linearOrGridLayout)
                    recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
            }
        }
        /*设置空布局*/
        if (emptyViewText != null) {
            View emptyView = getViewContainText(context, emptyViewText);
            //设置背景颜色
            if (emptyViewBgColor != -1) emptyView.setBackgroundColor(emptyViewBgColor);
            ((BaseQuickAdapter) adapter).setEmptyView(emptyView);
        }
        /*设置根布局*/
        if (footerViewText != null) { //不建议使用根布局，与分割线有冲突，使用loadView解决
            View footerView = getViewContainText(context, footerViewText);
            //设置背景颜色
            if (footerViewBgColor != -1) footerView.setBackgroundColor(footerViewBgColor);
            ((BaseQuickAdapter) adapter).setFooterView(footerView);
        }
        /*设置展现/删除动画*/
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        /*设置加载更多 现直接使用loadMoreEnd解决分割线 footerView冲突问题*/
        if (ifAddLoadMore) {
            adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            adapter.loadMoreEnd();
                        }
                    }, 0);
                }
            });
        }
        recyclerView.setAdapter(adapter);
    }

    private static View getViewContainText(Context context, String emptyViewText) {
        View view = null;
        view = ViewUtils.layoutInflater(context, R.layout.comm_textview);
        ((TextView) view).setText(emptyViewText);
        return view;
    }
}
