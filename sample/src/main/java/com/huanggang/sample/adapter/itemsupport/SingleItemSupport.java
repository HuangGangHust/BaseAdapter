package com.huanggang.sample.adapter.itemsupport;

import android.support.annotation.LayoutRes;

import com.huanggang.baseadapter.base.BaseItemSupport;
import com.huanggang.baseadapter.base.BaseViewHolder;
import com.huanggang.sample.Project;
import com.huanggang.sample.R;

/**
 * 使用R.layout.item_example的单一列表样式示例
 * Created by Huangg on 2017/12/26.
 */

public class SingleItemSupport extends BaseItemSupport<Project> {
    public SingleItemSupport() {
        super(R.layout.item_example);
    }

    @Override
    public boolean isMatched(Project itemData, int position) {
        return true;
    }

    @Override
    public void onBind(BaseViewHolder viewHolder, Project itemData, int position, int scrollState) {
        viewHolder.setText(R.id.tv_title, itemData.getProjectName());
        viewHolder.setText(R.id.tv_content, itemData.getUpdateTime());
    }
}
