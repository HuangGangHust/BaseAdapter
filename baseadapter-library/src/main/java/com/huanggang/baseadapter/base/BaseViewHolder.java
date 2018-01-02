package com.huanggang.baseadapter.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * RecyclerView通用ViewHolder
 * Created by huangg on 2016/8/23.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder implements ViewHolderSetter<BaseViewHolder> {
    private SparseArrayCompat<View> mViews;//convertView子View管理Map
    private View mConvertView;

    private BaseViewHolder(View itemView) {
        super(itemView);
        mConvertView = itemView;
        this.mViews = new SparseArrayCompat<>();
    }

    /**
     * 创建ViewHolder对象
     */
    public static BaseViewHolder onCreate(View itemView) {
        return new BaseViewHolder(itemView);
    }

    /**
     * 创建ViewHolder对象
     */
    public static BaseViewHolder onCreate(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        return new BaseViewHolder(itemView);
    }

    /**
     * 通过控件的Id获取对应的控件。如果控件管理Map中没有此View，则加进去。
     *
     * @param viewId 所需控件Id
     * @return 对应的控件
     */
    public <T extends View> T findViewById(int viewId) {
        View view = mViews.get(viewId);
        if (null == view) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    @Override
    public BaseViewHolder setAlpha(int viewId, @FloatRange(from = 0.0, to = 1.0) float value) {
        View view = findViewById(viewId);
        ViewCompat.setAlpha(view, value);
        return this;
    }

    @Override
    public BaseViewHolder setBackgroundColor(int viewId, int color) {
        View view = findViewById(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    @Override
    public BaseViewHolder setBackgroundRes(int viewId, int bgResId) {
        View view = findViewById(viewId);
        view.setBackgroundResource(bgResId);
        return this;
    }

    @Override
    public BaseViewHolder setCompoundDrawables(int viewId, @Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        TextView view = findViewById(viewId);
        view.setCompoundDrawables(left, top, right, bottom);
        return this;
    }

    @Override
    public BaseViewHolder setEnabled(int viewId, boolean enabled) {
        View view = findViewById(viewId);
        view.setEnabled(enabled);
        return this;
    }

    /**
     * 为ImageView设置图片
     */
    @Override
    public BaseViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = findViewById(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    @Override
    public BaseViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = findViewById(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    /**
     * 为ImageView设置图片
     */
    @Override
    public BaseViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = findViewById(viewId);
        view.setImageResource(drawableId);
        return this;
    }

//    /**
//     * 为ImageView设置图片
//     */
//    public BaseListViewHolder setImageByUrl(int viewId, String url) {
//        ImageView view = findViewById(viewId);
//        Glide.with(view.getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(view);
//        return this;
//    }

    @Override
    public BaseViewHolder setPressed(int viewId, boolean pressed) {
        View view = findViewById(viewId);
        view.setPressed(pressed);
        return this;
    }

    @Override
    public BaseViewHolder setSelected(int viewId, boolean selected) {
        View view = findViewById(viewId);
        view.setSelected(selected);
        return this;
    }

    /**
     * 为TextView设置字符串
     */
    @Override
    public BaseViewHolder setText(int viewId, String text) {
        TextView view = findViewById(viewId);
        view.setText(text);
        return this;
    }

    @Override
    public BaseViewHolder setText(int viewId, SpannableStringBuilder text) {
        TextView view = findViewById(viewId);
        view.setText(text);
        return this;
    }

    @Override
    public BaseViewHolder setTextRes(int viewId, @StringRes int textRes) {
        TextView view = findViewById(viewId);
        view.setText(view.getContext().getString(textRes));
        return this;
    }

    @Override
    public BaseViewHolder setTextColor(int viewId, int textColor) {
        TextView view = findViewById(viewId);
        view.setTextColor(textColor);
        return this;
    }

    @Override
    public BaseViewHolder setTextColorRes(int viewId, int textColorResId) {
        TextView view = findViewById(viewId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setTextColor(view.getContext().getColor(textColorResId));
        } else {
            view.setTextColor(view.getContext().getResources().getColor(textColorResId));
        }
        return this;
    }

    @Override
    public BaseViewHolder setTag(int viewId, Object tag) {
        View view = findViewById(viewId);
        view.setTag(tag);
        return this;
    }

    @Override
    public BaseViewHolder setTag(int viewId, int key, Object tag) {
        View view = findViewById(viewId);
        view.setTag(key, tag);
        return this;
    }

    @Override
    public BaseViewHolder setVisibility(int viewId, int visibility) {
        View view = findViewById(viewId);
        view.setVisibility(visibility);
        return this;
    }

    @Override
    public BaseViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = findViewById(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    @Override
    public BaseViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = findViewById(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    @Override
    public BaseViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = findViewById(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

}
