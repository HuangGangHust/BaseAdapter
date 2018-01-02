package com.huanggang.baseadapter.util;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

/**
 * RecyclerView特殊布局（Header、Footer等）展示工具类
 * Created by huangg on 2016/8/23.
 */
public class RecyclerViewSpanUtils {
    /**
     * The item will layout using all span area. That means, if orientation
     * is vertical, the view will have full width; if orientation is horizontal, the view will
     * have full height.
     *
     * @param holder RecyclerView.ViewHolder
     * @see {@link StaggeredGridLayoutManager.LayoutParams#isFullSpan()}
     */
    public static void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(true);
        }
    }

    /**
     * Sets the source to get the number of spans occupied by each item in the adapter.
     *
     * @param recyclerView Target recyclerView
     * @param callback     The callback when call {@link GridLayoutManager.SpanSizeLookup#getSpanSize(int)}
     */
    public static void setSpanSizeLookup(RecyclerView recyclerView, final SpanSizeLookupCallback callback) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) layoutManager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return callback.getSpanSize(gridManager.getSpanCount(),
                            gridManager.getSpanSizeLookup().getSpanSize(position),
                            position);
                }
            });
        }
    }

    /**
     * The callback when call {@link GridLayoutManager.SpanSizeLookup#getSpanSize(int)}
     *
     * @see GridLayoutManager#setSpanSizeLookup(GridLayoutManager.SpanSizeLookup)
     */
    public interface SpanSizeLookupCallback {
        /**
         * Returns the number of span occupied by the item at <code>position</code>.
         *
         * @param spanCount the number of spans laid out by this grid.
         * @param spanSize  the number of span occupied by the item at <code>position</code>.
         * @param position  The adapter position of the item
         * @return The number of spans occupied by the item at the provided position
         */
        int getSpanSize(int spanCount, int spanSize, int position);
    }
}
