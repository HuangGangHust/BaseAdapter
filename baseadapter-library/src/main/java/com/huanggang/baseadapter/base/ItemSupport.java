package com.huanggang.baseadapter.base;

import android.support.annotation.LayoutRes;

/**
 * ItemSupport实现类用于处理一个Item类型的匹配条件和业务处理
 * Created by huangg on 2016/8/23.
 */
public interface ItemSupport<T> {

    /**
     * 获取Item的布局Id
     *
     * @return itemLayoutId
     */
    @LayoutRes
    int getItemLayoutId();

    /**
     * 获取Item类型
     */
    int getItemType();

    /**
     * 此类型Item是否匹配当前位置
     *
     * @param itemData 此位置数据
     * @param position 位置
     */
     boolean isMatched(T itemData, int position);

    /**
     * 对外业务处理方法
     *
     * @param viewHolder  通用viewHolder
     * @param itemData    当前位置数据
     * @param position    当前Item位置
     * @param scrollState recyclerView的滑动状态，具体数值参考RecyclerView的mScrollState
     */
     void onBind(BaseViewHolder viewHolder, T itemData, int position, int scrollState);

}
