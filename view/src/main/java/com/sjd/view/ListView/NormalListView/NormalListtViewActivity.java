package com.sjd.view.ListView.NormalListView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjd on 2017/2/19.
 */

public class NormalListtViewActivity extends Activity {
    private ListView mDatasView;
    private List<Bean> mDatas;
    private NormalAdapter normalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normallistview_activity);

        initView();
        initDatas();
    }

    private void initDatas() {
        mDatas = new ArrayList<Bean>();
        for (int i =0;i<6;i++){
            Bean  bean = new Bean(i+"Android "+i,"Android万能的ＬｉｓｔＶｉｅｗ和ＧｉｒｄＶｉｅｗ适配器","2017-2-1"+i,"10086 "+i);
            mDatas.add(bean);
        }
        
        normalAdapter = new NormalAdapter(this,mDatas);
        mDatasView.setAdapter(normalAdapter);
    }

    private void initView() {
        mDatasView  = (ListView) findViewById(R.id.normallistview);
    }
}
