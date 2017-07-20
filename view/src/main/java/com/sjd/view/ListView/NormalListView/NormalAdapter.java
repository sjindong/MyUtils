package com.sjd.view.ListView.NormalListView;

import android.content.Context;
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

public class NormalAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Bean> mDatas;

    public NormalAdapter(Context context, List<Bean> mDatas) {
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.normallistview_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.desc = (TextView) convertView.findViewById(R.id.desc);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.phone = (TextView) convertView.findViewById(R.id.phone);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Bean bean = mDatas.get(position);//如果数据时类型int　类型的话，需要转化为String不然会作为资源ID来处理
        viewHolder.title.setText(bean.getTitile());
        viewHolder.desc.setText(bean.getDesc());
        viewHolder.time.setText(bean.getTime());
        viewHolder.phone.setText(bean.getPhone());
        return convertView;
    }

    private class ViewHolder {
        TextView title;
        TextView desc;
        TextView time;
        TextView phone;
    }
}
