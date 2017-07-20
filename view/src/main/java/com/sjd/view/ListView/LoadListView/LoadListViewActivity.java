package com.sjd.view.ListView.LoadListView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjd on 2017/2/19.
 */

public class LoadListViewActivity extends Activity  implements LoadListView.IReflashListener{
    LoadLVAdapter baseAdapter;
    List<String> data;
    LoadListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadlistview_activity);
        initData();
        initView();
    }

    private void initData() {
        data = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            data.add("" + i);
        }
        baseAdapter = new LoadLVAdapter(this, data);
    }

    private void initView() {
        listView = (LoadListView) findViewById(R.id.loadlistview);
        listView.setAdapter(baseAdapter);
        listView.setInterface(this);
    }

    @Override
    public void onReflash() {
        //添加延时，效果明显
        Handler handler  = new Handler() ;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //获取最新数据
                for (int i = 0; i < 2; i++) {
                    data.add(0,"新数据：" + i);
                }
                //通知界面显示数据
                baseAdapter.onDateChange(data);

                //tongzhi listView刷新数据
                listView.reflashComplete();
            }
        },2000);
    }
}

class LoadLVAdapter extends BaseAdapter {
    List<String> data;
    LayoutInflater layoutInflater;

    public LoadLVAdapter(Context context, List<String> data) {
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TextView textView = new TextView(viewGroup.getContext());
        textView.setText(data.get(i));

        return textView;
    }

    public void onDateChange(List<String> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }
}
