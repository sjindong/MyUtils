package com.sjd.view.ListView.OptimizationListView;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.view.R;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by sjd on 2017/2/19.
 */

public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    public ViewHolder(Context context, ViewGroup parent,int layoutId,int position) {
        mPosition = position;
        mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId,parent,false);

        mConvertView.setTag(this);
    }

    public  static ViewHolder get(Context context,View convertView,ViewGroup parent,int layoutId,int position){
        if (convertView == null) {
            return new ViewHolder(context,parent,layoutId,position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;//已经存在viewhold还是需要更新position
            return holder;
        }
    }

    /**
     * 通过viewID获取控件
     * @param viewId
     * @param <T>
     * @return
     */
    public  <T extends View>T getView(int viewId){
        View view = mViews.get(viewId);
        if (view ==null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T)view;
    }

    public View getmConvertView(){
        return mConvertView;
    }
}
