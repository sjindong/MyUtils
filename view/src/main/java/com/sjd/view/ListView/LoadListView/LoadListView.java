package com.sjd.view.ListView.LoadListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.view.R;

/**
 * Created by sjd on 2017/2/19.
 */

public class LoadListView extends ListView{

    View footer;
    public LoadListView(Context context) {
        super(context);
    }

    public LoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LoadListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    /**
     * 添加底部加载提示listview
     * @param context
     */
    private void initView(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.loadlistview_footer,null);
        this.addFooterView(footer);
    }
}
