package com.huanggang.sample.adapter.itemsupport;

import com.huanggang.baseadapter.base.BaseItemSupport;
import com.huanggang.baseadapter.base.BaseViewHolder;
import com.huanggang.sample.Project;
import com.huanggang.sample.R;

/**
 * layoutId：R.layout.item_example
 * data类型：Project
 * Created by huangg on 2017/3/13.
 */
public class ProjectItemSupport extends BaseItemSupport<Project> {
    public ProjectItemSupport() {
        super(R.layout.item_project);
    }

    @Override
    public boolean isMatched(Project itemData, int position) {
        return position % 2 == 1;
    }

    @Override
    public void onBind(BaseViewHolder viewHolder, Project data, int position, int scrollState) {
        viewHolder.setText(R.id.tv_project_name, data.getProjectName());
        viewHolder.setText(R.id.tv_leader, data.getLeader());
        viewHolder.setText(R.id.tv_update_time, data.getUpdateTime());
        // ……
    }
}
