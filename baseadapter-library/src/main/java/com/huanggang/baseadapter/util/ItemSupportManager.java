package com.huanggang.baseadapter.util;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.huanggang.baseadapter.base.BaseViewHolder;
import com.huanggang.baseadapter.base.ItemSupport;

/**
 * Item类型筛选及业务处理管理类
 * Created by huangg on 2016/8/23.
 */
public class ItemSupportManager<T> {
    private static final String TAG = ItemSupportManager.class.getSimpleName();
    /**
     * itemSupport集合。Key：itemType，Value：ItemSupport<T>
     */
    private SparseArrayCompat<ItemSupport<T>> itemSupports = new SparseArrayCompat<ItemSupport<T>>();

    public int getItemSupportCount() {
        return itemSupports.size();
    }

    /**
     * 筛选处理业务的Item
     *
     * @param viewHolder  通用ViewHolder
     * @param itemData    Item数据
     * @param position    位置
     * @param scrollState recyclerView的当前滑动状态。具体数值参考RecyclerView的mScrollState。
     *                    the current scrolling state of the RecyclerView.
     *                    @see RecyclerView#getScrollState()
     */
    public void onBind(BaseViewHolder viewHolder, T itemData, int position, int scrollState) {
        int itemSupportsCount = itemSupports.size();
        for (int i = 0; i < itemSupportsCount; i++) {
            ItemSupport<T> itemSupport = itemSupports.valueAt(i);
            if (itemSupport.isMatched(itemData, position)) {
                itemSupport.onBind(viewHolder, itemData, position, scrollState);
                return;
            }
        }
        Log.e(TAG, "No itemSupport matches position = " + position + " and itemData = " + itemData
                + " in data source");
    }

    /**
     * 添加新的Item类型
     *
     * @param itemSupport Item业务处理类，推荐使用{@link com.huanggang.baseadapter.base.BaseItemSupport}或其子类
     * @return ItemSupportManager
     */
    public ItemSupportManager<T> addItemSupport(ItemSupport<T> itemSupport) {
        if (null == itemSupport) {
            Log.e(TAG, "Adding itemSupport is null");
            return this;
        }
        int itemType = itemSupport.getItemType();
        if (null != itemSupports.get(itemType)) {
            throw new IllegalArgumentException(
                    "This " + itemSupport + " is already registered by " + itemSupports.get(itemType));
        }
        itemSupports.put(itemType, itemSupport);
        return this;
    }

    /**
     * 移除新的Item类型
     *
     * @param itemSupport Item业务处理实现类。
     * @return ItemSupportManager
     */
    public ItemSupportManager<T> removeItemSupport(ItemSupport<T> itemSupport) {
        if (null == itemSupport) {
            Log.e(TAG, "Removing itemSupport is null");
            return this;
        }
        int indexToRemove = itemSupports.indexOfValue(itemSupport);
        if (indexToRemove >= 0) {
            itemSupports.removeAt(indexToRemove);
        }
        return this;
    }

    public ItemSupportManager<T> removeItemSupport(int itemType) {
        int indexToRemove = itemSupports.indexOfKey(itemType);
        if (indexToRemove >= 0) {
            itemSupports.removeAt(indexToRemove);
        }
        return this;
    }

    public int getItemType(T itemData, int position) {
        int itemSupportsCount = itemSupports.size();
        for (int i = itemSupportsCount - 1; i >= 0; i--) {
            ItemSupport<T> itemSupport = itemSupports.valueAt(i);
            if (itemSupport.isMatched(itemData, position)) {
                return itemSupports.keyAt(i);
            }
        }
        Log.e(TAG, "No itemSupport matches position = " + position + " and itemData = " + itemData
                + " in data source");
        return 0;
    }

    public ItemSupport getItemSupport(int itemType) {
        return itemSupports.get(itemType);
    }

    public int getItemLayoutId(int itemType) {
        ItemSupport itemSupport = getItemSupport(itemType);
        if (itemSupport == null) {
            Log.e(TAG, "No itemSupport matches itemType = " + itemType);
            return 0;
        }
        return itemSupport.getItemLayoutId();
    }

}
