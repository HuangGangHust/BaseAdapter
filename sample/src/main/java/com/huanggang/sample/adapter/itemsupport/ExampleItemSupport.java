package com.huanggang.sample.adapter.itemsupport;

import com.huanggang.baseadapter.base.BaseItemSupport;
import com.huanggang.baseadapter.base.BaseViewHolder;
import com.huanggang.sample.Project;
import com.huanggang.sample.R;

/**
 * 使用R.layout.item_example的多列表样式示例
 * Created by huangg on 2017/3/13.
 */
public class ExampleItemSupport extends BaseItemSupport<Project> {
    public ExampleItemSupport() {
        super(R.layout.item_example);
    }

    @Override
    public boolean isMatched(Project itemData, int position) {
        return position % 2 == 0;
    }

    @Override
    public void onBind(BaseViewHolder viewHolder, Project itemData, int position, int scrollState) {
        viewHolder.setText(R.id.tv_title, itemData.getProjectName());
        viewHolder.setText(R.id.tv_content, itemData.getUpdateTime());
    }
}
