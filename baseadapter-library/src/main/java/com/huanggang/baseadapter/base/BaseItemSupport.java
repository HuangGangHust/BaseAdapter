package com.huanggang.baseadapter.base;

import android.support.annotation.LayoutRes;

/**
 * ItemSupport实现基类，将Item的layoutId作为其标识viewType
 * Created by huangg on 2016/8/23.
 */
public abstract class BaseItemSupport<T> implements ItemSupport<T> {
    private int itemLayoutId;

    private BaseItemSupport() {
    }

    public BaseItemSupport(@LayoutRes int itemLayoutId) {
        this.itemLayoutId = itemLayoutId;
    }

    @Override
    public String toString() {
        return "BaseItemSupport{" +
                "itemLayoutId=" + itemLayoutId +
                ",itemType=" + getItemType() +
                '}';
    }

    /**
     * 获取Item的布局Id
     *
     * @return itemLayoutId
     */
    @Override
    @LayoutRes
    public int getItemLayoutId() {
        return itemLayoutId;
    }

    /**
     * 获取Item类型
     *
     * @return {@link #getItemLayoutId()}：Item的布局Id
     */
    @Override
    public int getItemType() {
        return getItemLayoutId();
    }

}
