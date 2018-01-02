package com.huanggang.baseadapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * 单一类型Item万能适配器基类
 * Created by huangg on 2016/8/23.
 */
public abstract class BaseSingleItemAdapter<T> extends BaseRecyclerAdapter<T> {

    private BaseSingleItemAdapter(RecyclerView recyclerView, List<T> datas) {
        super(recyclerView, datas);
    }

    public BaseSingleItemAdapter(RecyclerView recyclerView, List<T> datas, int layoutId) {
        super(recyclerView, datas);
        BaseItemSupport<T> itemSupport = new BaseItemSupport<T>(layoutId) {
            @Override
            public boolean isMatched(T data, int position) {
                return true;
            }

            @Override
            public void onBind(BaseViewHolder viewHolder, T itemData, int position, int scrollState) {
                BaseSingleItemAdapter.this.onBind(viewHolder, itemData, position, scrollState);
            }
        };
        addItemSupport(itemSupport);
    }

    /**
     * 对外业务处理方法
     *
     * @param viewHolder  通用viewHolder
     * @param itemData    当前位置数据
     * @param position    当前Item位置
     * @param scrollState recyclerView的滑动状态，具体数值参考RecyclerView的mScrollState
     */
    public abstract void onBind(BaseViewHolder viewHolder, T itemData, int position, int scrollState);
}
