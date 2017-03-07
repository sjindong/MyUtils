package com.view.ListView.OptimizationListView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.view.R;

import java.util.List;

/**
 * Created by sjd on 2017/2/19.
 */

public class OptimizationListtViewActivity extends Activity {
    private ListView mDatasView;
    private List<Bean> mDatas;
    private OptimizationAdapter optimizationAdapter;
    private  ViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normallistview_activity);

        initData();
        initView();
    }


    private void initData() {
        for (int i =0;i<6;i++){
            Bean bean = new Bean(i+"Android "+i,"Android万能的ＬｉｓｔＶｉｅｗ和ＧｉｒｄＶｉｅｗ适配器","2017-2-1"+i,"10086 "+i);
            mDatas.add(bean);
        }

        optimizationAdapter = new OptimizationAdapter(this,mDatas);
    }

    private void initView() {
        mDatasView  = (ListView) findViewById(R.id.normallistview);
        mDatasView.setAdapter(optimizationAdapter);
    }
}
