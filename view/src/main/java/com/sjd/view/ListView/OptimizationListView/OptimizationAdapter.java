package com.sjd.view.ListView.OptimizationListView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.view.R;

import java.util.List;

/**
 * Created by sjd on 2017/2/19.
 */

public class OptimizationAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Bean> mDatas;
    private Context context;

    public OptimizationAdapter(Context context, List<Bean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(context,convertView,parent,R.layout.normallistview_item,position);

        Bean bean = mDatas.get(position);//如果数据时类型int类型的话，需要转化为String不然会作为资源ID来处理
        TextView title = viewHolder.getView(R.id.title);
        title.setText(bean.getTitile());
        TextView desc = viewHolder.getView(R.id.desc);
        desc.setText(bean.getDesc());
        TextView time = viewHolder.getView(R.id.time);
        time.setText(bean.getTime());
        TextView phone = viewHolder.getView(R.id.phone);
        phone.setText(bean.getPhone());

        return viewHolder.getmConvertView();
    }

}
