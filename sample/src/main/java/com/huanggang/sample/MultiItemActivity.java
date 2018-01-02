package com.huanggang.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.huanggang.baseadapter.ItemDecoration.DividerListItemDecoration;
import com.huanggang.baseadapter.base.BaseRecyclerAdapter;
import com.huanggang.sample.adapter.itemsupport.ExampleItemSupport;
import com.huanggang.sample.adapter.itemsupport.ProjectItemSupport;

import java.util.List;

public class MultiItemActivity extends AppCompatActivity {
    public static final String TAG = "hg" + MultiItemActivity.class.getSimpleName();
    public static final int MAX_PAGE = 2;// 分页列表最大页数。注意：此示例中起始页的page为0
    public static final int EVERY_PAGE_COUNT = 8;// 分页列表每页数据条数
    private List<Project> projects;
    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter multiItemAdapter;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);
        projects = Project.projectDatas(EVERY_PAGE_COUNT);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        //设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        setMultiItemAdapter1(mRecyclerView);
        setMultiItemAdapter2(mRecyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerListItemDecoration(this,
                DividerListItemDecoration.VERTICAL_LIST));
    }

    /**
     * 多类型Item基础用法
     */
    private void setMultiItemAdapter1(final RecyclerView recyclerView) {
        BaseRecyclerAdapter multiAdapter = new BaseRecyclerAdapter<>(recyclerView, projects)
                .addItemSupport(new ExampleItemSupport())
                .addItemSupport(new ProjectItemSupport());
        recyclerView.setAdapter(multiAdapter);
    }

    /**
     * 多类型Item扩展用法
     */
    private void setMultiItemAdapter2(final RecyclerView recyclerView) {
        View header = LayoutInflater.from(this).inflate(R.layout.list_header, recyclerView, false);
        View footer = LayoutInflater.from(this).inflate(R.layout.list_footer, recyclerView, false);
        multiItemAdapter = new BaseRecyclerAdapter<>(recyclerView, projects)
                .addItemSupport(new ExampleItemSupport())// 设置类型1列表项
                .addItemSupport(new ProjectItemSupport())// 设置类型2列表项
                .setHeaderView(header)// 设置列表的头部
                .setFooterView(footer);// 设置列表的尾部
        // 列表项点击监听
        multiItemAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, RecyclerView.ViewHolder holder, int position) {
                Log.d(TAG, "onItemClick: 点击第" + position + "项" + "，插入：" + Project.projectData(position));
                multiItemAdapter.insertData(position, Project.projectData(position));
            }
        });
        // 列表项长按监听
        multiItemAdapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View itemView, RecyclerView.ViewHolder holder, int position) {
                Log.d(TAG, "onItemLongClick: 删除点击第" + position + "项");
                multiItemAdapter.removeData(position);
                return true;
            }
        });
        // 加载更多
        multiItemAdapter.setFooterViewOnBindListener(new BaseRecyclerAdapter.FooterViewOnBindListener() {
            @Override
            public void onBind() {
                onLoadMore();
            }
        });
        recyclerView.setAdapter(multiItemAdapter);
    }

    /**
     * 加载更多处理
     */
    private void onLoadMore() {
        if (page < MAX_PAGE) {
            page++;
            Log.d(TAG, "onLoadMore: 请求加载第" + page + "页");
            // 以每页EVERY_PAGE_COUNT条数据进行模拟
            projects = Project.projectDatas((page + 1) * EVERY_PAGE_COUNT);
            multiItemAdapter.updateData(projects);
        } else {
            Log.d(TAG, "onLoadMore: 当前已到最后一页，无需再加载。page = " + page);
        }
    }
}
