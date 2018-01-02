package com.huanggang.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.huanggang.baseadapter.ItemDecoration.DividerListItemDecoration;
import com.huanggang.baseadapter.base.BaseRecyclerAdapter;
import com.huanggang.sample.adapter.ProjectRecyclerAdapter;

import java.util.List;

/**
 * 单一列表类型使用示例
 */
public class SingleItemActivity extends AppCompatActivity {
    public static final String TAG = "hg" + SingleItemActivity.class.getSimpleName();
    private List<Project> mDatas;
    private RecyclerView mRecyclerView;
    private ProjectRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);
        setDatas(15);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        //设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setSingleItemAdapter(mRecyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerListItemDecoration(this, DividerListItemDecoration.VERTICAL_LIST));
    }

    private void setDatas(int i) {
        mDatas = Project.projectDatas(i);
    }

    private void setSingleItemAdapter(RecyclerView recyclerView) {
        mAdapter = new ProjectRecyclerAdapter(recyclerView, mDatas);
        recyclerView.setAdapter(mAdapter);
        // 列表项点击监听
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, RecyclerView.ViewHolder holder, int position) {
                Log.d(TAG, "onItemClick: 点击第" + position + "项" + "，插入：" + Project.projectData(position));
                mAdapter.insertData(position, Project.projectData(position));
            }
        });
        // 列表项长按监听
        mAdapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View itemView, RecyclerView.ViewHolder holder, int position) {
                Log.d(TAG, "onItemLongClick: 删除点击第" + position + "项");
                mAdapter.removeData(position);
                return true;
            }
        });
    }
}
