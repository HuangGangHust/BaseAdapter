package com.huanggang.baseadapter.base;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.SpannableStringBuilder;
import android.view.View;

/**
 * 通用ViewHolder常用Set方法
 * Created by huangg on 2016/8/23.
 */
public interface ViewHolderSetter<VH> {
    VH setAlpha(int viewId, @FloatRange(from = 0.0, to = 1.0) float value);

    VH setBackgroundColor(int viewId, int color);

    VH setBackgroundRes(int viewId, int bgResId);

    VH setCompoundDrawables(int viewId, @Nullable Drawable left, @Nullable Drawable top,
                            @Nullable Drawable right, @Nullable Drawable bottom);

    VH setEnabled(int viewId, boolean enabled);

    VH setImageBitmap(int viewId, Bitmap bm);

    VH setImageDrawable(int viewId, Drawable drawable);

    VH setImageResource(int viewId, int drawableId);

    VH setPressed(int viewId, boolean pressed);

    VH setSelected(int viewId, boolean selected);

    VH setText(int viewId, String text);
    
    VH setText(int viewId, SpannableStringBuilder text);

    /**
     * 为TextView设置字符串
     *
     * @param viewId  viewId
     * @param textRes string.xml中的id
     */
    VH setTextRes(int viewId, @StringRes int textRes);

    VH setTextColor(int viewId, int textColor);

    VH setTextColorRes(int viewId, int textColorResId);

    VH setTag(int viewId, Object tag);

    VH setTag(int viewId, int key, Object tag);

    VH setVisibility(int viewId, int visibility);

    VH setOnClickListener(int viewId, View.OnClickListener listener);

    VH setOnLongClickListener(int viewId, View.OnLongClickListener listener);

    VH setOnTouchListener(int viewId, View.OnTouchListener listener);

}
