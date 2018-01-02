package com.huanggang.sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.huanggang.sample.R;
import com.huanggang.baseadapter.base.BaseSingleItemAdapter;
import com.huanggang.baseadapter.base.BaseViewHolder;
import com.huanggang.sample.Project;

import java.util.List;

/**
 * 项目基本信息RecyclerAdapter
 */
public class ProjectRecyclerAdapter extends BaseSingleItemAdapter<Project> {
    public ProjectRecyclerAdapter(RecyclerView recyclerView, List datas) {
        // 直接传入该Item的layout即可
		super(recyclerView, datas, R.layout.item_project);
    }

    @Override
    public void onBind(BaseViewHolder viewHolder, Project itemData, int position, int scrollState) {
	    // 在此处直接写业务逻辑。无需编写实例化代码，一行代码搞定各种设置。
        viewHolder.setText(R.id.tv_project_name, itemData.getProjectName());
        int status = itemData.getStatus();
        String statusStr = "未勘点";
        // 项目状态：00 未勘点、01 勘点中、02可移交建设；21 建设中、22 建设完成；30 售后
        switch (status) {
            case 00:
                statusStr = "未勘点";
                viewHolder.setImageResource(R.id.iv_status, R.drawable.icon_status_exploring);
                viewHolder.setText(R.id.tv_finished_site_count, "已勘");
                viewHolder.setText(R.id.tv_explored, itemData.getSiteExplored() + "");
                break;
            case 01:
                statusStr = "勘点中";
                viewHolder.setImageResource(R.id.iv_status, R.drawable.icon_status_exploring);
                viewHolder.setText(R.id.tv_finished_site_count, "已勘");
                viewHolder.setText(R.id.tv_explored, itemData.getSiteExplored() + "");
                break;
            case 02:
                statusStr = "可移交建设";
                viewHolder.setImageResource(R.id.iv_status, R.drawable.icon_status_exploring);
                viewHolder.setText(R.id.tv_finished_site_count, "已勘");
                viewHolder.setText(R.id.tv_explored, itemData.getSiteExplored() + "");
                break;
            case 21:
                statusStr = "建设中";
                viewHolder.setImageResource(R.id.iv_status, R.drawable.icon_status_building);
                viewHolder.setText(R.id.tv_finished_site_count, "已建");
                viewHolder.setText(R.id.tv_explored, itemData.getSiteBuilded() + "");
                break;
            case 22:
                statusStr = "建设完成";
                viewHolder.setText(R.id.tv_finished_site_count, "已建");
                viewHolder.setText(R.id.tv_explored, itemData.getSiteBuilded() + "");
                break;
            case 30:
                statusStr = "售后";
                break;
            default:
                break;
        }
        viewHolder.setText(R.id.tv_site_count, itemData.getSiteCount() + "");
        viewHolder.setText(R.id.tv_leader, itemData.getLeader());
        if (itemData.getG4Count() > 0) {
            viewHolder.setText(R.id.tv_flag_01, "4G");
            viewHolder.setVisibility(R.id.tv_flag_01, View.VISIBLE);
        } else {
            viewHolder.setText(R.id.tv_flag_01, "");
            viewHolder.setVisibility(R.id.tv_flag_01, View.GONE);
        }
        if (itemData.getGsmCount() > 0) {
            viewHolder.setText(R.id.tv_flag_02, "GSM");
            viewHolder.setVisibility(R.id.tv_flag_02, View.VISIBLE);
        } else {
            viewHolder.setText(R.id.tv_flag_02, "");
            viewHolder.setVisibility(R.id.tv_flag_02, View.GONE);
        }
        String updateTime = itemData.getUpdateTime();
        viewHolder.setText(R.id.tv_update_time, updateTime);
        viewHolder.setText(R.id.tv_progress, itemData.getProgress() + "%");
    }
}
