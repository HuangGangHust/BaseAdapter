package com.huanggang.baseadapter.base;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.huanggang.baseadapter.util.ItemSupportManager;
import com.huanggang.baseadapter.util.RecyclerViewSpanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView万能Adapter基类
 * Created by huangg on 2016/8/23.
 */
public class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {
    private static final String TAG = BaseRecyclerAdapter.class.getSimpleName();
    public static final int ITEM_TYPE_HEADER = -0x100;
    public static final int ITEM_TYPE_FOOTER = -0x200;
    //    public static final int ITEM_TYPE_LOAD_MORE = -0x300;
    private List<T> mDatas;
    private ItemSupportManager<T> mItemSupportManager;
    /**
     * RecyclerView的当前滑动状态。具体数值参考RecyclerView的mScrollState。
     * the current scrolling state of the RecyclerView.
     *
     * @see RecyclerView#getScrollState()
     */
    private int scrollState = -1;
    /**
     * 显示隐藏与RecyclerView列表数据是否为空有关，但布局样式与RecyclerView无关，类似ListView的EmptyView用法。
     */
    private View mEmptyView;
    private View mHeaderView;
    private View mFooterView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private FooterViewOnBindListener mFooterViewOnBindListener;

    /**
     * 构造方法。
     *
     * @param recyclerView 绑定此Adapter的recyclerView
     * @param datas        数据源
     */
    public BaseRecyclerAdapter(RecyclerView recyclerView, @Nullable List<T> datas) {
        this.mDatas = datas == null ? new ArrayList<T>() : new ArrayList<T>(datas);
        mItemSupportManager = new ItemSupportManager<T>();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                scrollState = newState;
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && mFooterView != null
                        && mFooterViewOnBindListener != null) {// 当前处于停止状态
                    mFooterViewOnBindListener.onBind();
                }
            }
        });
    }

    /**
     * 获取ItemView类型
     */
    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            return ITEM_TYPE_HEADER;
        }
        if (isFooterPosition(position)) {
            return ITEM_TYPE_FOOTER;
        }
        if (isExistItemSupport()) {
            position = position - getHeaderCount();
            return mItemSupportManager.getItemType(mDatas.get(position), position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + getHeaderCount() + getFooterCount();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder;
        // 列表头部
        if (viewType == ITEM_TYPE_HEADER) {
            viewHolder = BaseViewHolder.onCreate(mHeaderView);
            onCreate(viewHolder, viewHolder.getConvertView());
            return viewHolder;
        }

        // 列表尾部
        if (viewType == ITEM_TYPE_FOOTER) {
            viewHolder = BaseViewHolder.onCreate(mFooterView);
            onCreate(viewHolder, viewHolder.getConvertView());
            return viewHolder;
        }

        // 列表数据项
        ItemSupport itemSupport = mItemSupportManager.getItemSupport(viewType);
        if (itemSupport == null) {
            Log.e(TAG, "No itemSupport matches viewType = " + viewType);
            return null;
        }
        int layoutId = itemSupport.getItemLayoutId();
        viewHolder = BaseViewHolder.onCreate(parent.getContext(), parent, layoutId);
        setItemClickListener(viewHolder);
        onCreate(viewHolder, viewHolder.getConvertView());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (isHeaderPosition(position) || isFooterPosition(position) || updateEmptyStatus()) {
            return;
        }
        position = position - getHeaderCount();
        mItemSupportManager.onBind((BaseViewHolder) viewHolder, mDatas.get(position), position, scrollState);
    }

    /**
     * 添加新的Item类型
     *
     * @param itemSupport Item业务处理实现类。推荐使用{@link BaseItemSupport}或其子类
     */
    public BaseRecyclerAdapter addItemSupport(ItemSupport<T> itemSupport) {
        mItemSupportManager.addItemSupport(itemSupport);
        return this;
    }

    /**
     * 针对GridLayoutManager的HeaderView和FooterView展示
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (isExistHeader() || isExistFooter()) {
            RecyclerViewSpanUtils.setSpanSizeLookup(recyclerView, new RecyclerViewSpanUtils.SpanSizeLookupCallback() {
                @Override
                public int getSpanSize(int spanCount, int spanSize, int position) {
                    return (isHeaderPosition(position) || isFooterPosition(position)) ? spanCount : spanSize;
                }
            });
        }
    }

    /**
     * 针对StaggeredGridLayoutManager的HeaderView和FooterView展示
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            RecyclerViewSpanUtils.setFullSpan(holder);
        }
    }

    /**
     * 更新数据
     *
     * @param datas 更新后数据源
     */
    public void updateData(@Nullable List<T> datas) {
        Log.d(TAG, "更新数据：" + datas);
        mDatas = datas == null ? new ArrayList<T>() : new ArrayList<T>(datas);
        notifyDataSetChanged();
        updateEmptyStatus();
    }

    /**
     * 向指定位置插入一项，并进行更新。若设置了插入动画，则更新时会显示动画
     *
     * @param position 插入位置
     * @param data     插入数据
     */
    public void insertData(int position, T data) {
        mDatas.add(position - getHeaderCount(), data);
        notifyItemInserted(position);
    }

    /**
     * 移除指定位置项，并进行更新。若设置了移除动画，则更新时会显示动画
     *
     * @param position 移除位置
     */
    public void removeData(int position) {
        mDatas.remove(position - getHeaderCount());
        notifyItemRemoved(position);
        updateEmptyStatus();
    }

    /**
     * mItemSupportManager中是否已存在ItemSupport
     */
    private boolean isExistItemSupport() {
        return mItemSupportManager.getItemSupportCount() > 0;
    }

    /**
     * 初始化方法。可复写实现初始化时所需其他特殊操作。
     *
     * @param viewHolder  通用适配器
     * @param convertView Item布局
     */
    public void onCreate(BaseViewHolder viewHolder, View convertView) {
    }

    /**
     * 数据源是否为空
     */
    private boolean dataIsEmpty() {
        return mDatas == null || mDatas.size() == 0;
    }

    /**
     * Update the status of the emptyView.
     *
     * @return true：emptyView可见，false：emptyView不可见
     */
    private boolean updateEmptyStatus() {
        if (mEmptyView == null) {
            return false;
        }

        if (dataIsEmpty()) {
            mEmptyView.setVisibility(View.VISIBLE);
            return true;
        }

        mEmptyView.setVisibility(View.GONE);
        return false;
    }

    /**
     * 设置emptyView
     *
     * @param emptyView 列表项为空时展示布局。若传入null，则会清除emptyView。
     */
    public BaseRecyclerAdapter setEmptyView(@Nullable View emptyView) {
        this.mEmptyView = emptyView;
        return this;
    }

    /**
     * 设置headerView
     *
     * @param header 列表项头部布局。若传入null，则会清除headerView。
     */
    public BaseRecyclerAdapter setHeaderView(@NonNull View header) {
        this.mHeaderView = header;
        return this;
    }

    /**
     * 设置footerView
     *
     * @param footer 列表项尾部布局。若传入null，则会清除footerView。
     */
    public BaseRecyclerAdapter setFooterView(@NonNull View footer) {
        this.mFooterView = footer;
        return this;
    }

    /**
     * @return 列表数据加载开始项位置，考虑HeaderView
     */
    private int listBeginPosition() {
        return getHeaderCount();
    }

    /**
     * @return 列表数据加载结束项位置，考虑HeaderView
     */
    private int listEndPosition() {
        return mDatas.size() - 1 + getHeaderCount();
    }

    private int getHeaderCount() {
        return isExistHeader() ? 1 : 0;
    }

    private int getFooterCount() {
        return isExistFooter() ? 1 : 0;
    }

    public boolean isExistHeader() {
        return mHeaderView != null;
    }

    public boolean isExistFooter() {
        return mFooterView != null;
    }

    private boolean isHeaderPosition(int position) {
        return isExistHeader() && position == 0;
    }

    private boolean isFooterPosition(int position) {
        return isExistFooter() && position > listEndPosition();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    /**
     * 设置列表项点击监听
     */
    private void setItemClickListener(final BaseViewHolder viewHolder) {
        // 单击监听器
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnItemClickListener) {
//                    int position = viewHolder.getAdapterPosition() - getHeaderCount();
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder, position);
                }
            }
        });
        // 长按监听器
        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemLongClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    public FooterViewOnBindListener getFooterViewOnBindListener() {
        return mFooterViewOnBindListener;
    }

    public void setFooterViewOnBindListener(FooterViewOnBindListener footerViewOnBindListener) {
        this.mFooterViewOnBindListener = footerViewOnBindListener;
    }

    /**
     * 列表项点击监听器
     */
    public interface OnItemClickListener {
        /**
         * @param itemView Item父布局
         * @param holder   RecyclerView.ViewHolder
         * @param position 列表项位置。
         *                 注意：若存在HeaderView时，HeaderView位置为列表项的第0项，
         *                 类似ListView存在HeaderView时的OnItemClickListener。
         */
        void onItemClick(View itemView, RecyclerView.ViewHolder holder, int position);
    }

    /**
     * 列表项长按监听器
     */
    public interface OnItemLongClickListener {
        /**
         * @param itemView Item父布局
         * @param holder   RecyclerView.ViewHolder
         * @param position 列表项位置。
         *                 注意：若存在HeaderView时，HeaderView位置为列表项的第0项，
         *                 类似ListView存在HeaderView时的OnItemLongClickListener。
         * @return true if the callback consumed the long click, false otherwise.
         */
        boolean onItemLongClick(View itemView, RecyclerView.ViewHolder holder, int position);
    }

    /**
     * RecyclerView加载FooterView时，执行onBindViewHolder的回调监听器
     */
    public interface FooterViewOnBindListener {
        /**
         * RecyclerView加载FooterView时，执行onBindViewHolder的回调。
         * 可用于加载更多等。
         */
        void onBind();
    }
}
