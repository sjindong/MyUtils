package com.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 环形的ViewGroup，初始目的是一个图片选择器（不能滑动）
 *
 */
public class CircleViewGroup extends ViewGroup {
    // Child sizes
    private int childWidth = 0;
    private int childHeight = 0;

    // Sizes of the ViewGroup
    private int radius = 200;

    private int childCount[];

    private float angle = 180f;

    //圆心偏移
    private int offCenterX = 0;
    private int offCenterY = 0;

    public CircleViewGroup(Context context) {
        this(context, null);
    }

    public CircleViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    //初始化
    public void init() {
        removeAllViews();
        invalidate();

        angle = 180;
    }

    /**
     * 负责设置子控件的测量模式和大小 根据所有子控件设置自己的宽和高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // 如果是warp_content情况下，记录宽和高
        int width = 0;
        int height = 0;
        /**
         * 记录每一行的宽度，width不断取最大宽度
         */
        int lineWidth = 0;
        /**
         * 每一行的高度，累加至height
         */
        int lineHeight = 0;

        final int count = getChildCount();
        int length = count;
        for (int i = 0; i < count; i++) {
            if (getChildAt(i).getVisibility() == GONE) {
                length--;
            }
        }
        childCount = new int[length];

        for (int i = 0, a = 0; i < count; i++) {
            if (getChildAt(i).getVisibility() != GONE) {
                childCount[a] = i;
                a++;
            }
        }

        // 遍历每个子元素
        for (int i = 0; i < length; i++) {
            View child = getChildAt(childCount[i]);
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.AT_MOST);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.AT_MOST);
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);

            // 测量每一个child的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            // 当前子空间实际占据的宽度
            int childWidth = child.getMeasuredWidth();
            // 当前子空间实际占据的高度
            int childHeight = child.getMeasuredHeight();

            /**
             * 如果加入当前child，则超出最大宽度，则的到目前最大宽度给width，类加height 然后开启新行
             */
            if (lineWidth + childWidth > sizeWidth) {
                width = Math.max(lineWidth, childWidth);// 取最大的
                lineWidth = childWidth; // 重新开启新行，开始记录
                // 叠加当前高度，
                height += lineHeight;
                // 开启记录下一行的高度
                lineHeight = childHeight;
            } else
            // 否则累加值lineWidth,lineHeight取最大高度
            {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            // 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
            if (i == length - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }

        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : width,
                (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int layoutWidth = r - l;
        int layoutHeight = b - t;
        int left, top;

        offCenterX = 0;
        offCenterY = 0;

        if (childCount.length == 0) {
            return;
        }

        float angleDelay = 360 / childCount.length;

        for (int i = 0; i < childCount.length; i++) {
            final View child = getChildAt(childCount[i]);
            angle = (angle + 360) % 360;

            childWidth = child.getMeasuredWidth()/2;
            childHeight = child.getMeasuredHeight()/2;

            left = Math.round((float) (((layoutWidth / 2) - childWidth / 2) + radius * Math.cos(Math.toRadians(angle))) + offCenterX);
            top = Math.round((float) (((layoutHeight / 2) - childHeight / 2) + radius * Math.sin(Math.toRadians(angle))) + offCenterY);
            child.layout(left, top, left + childWidth, top + childHeight);
            angle += angleDelay;
        }
    }
}