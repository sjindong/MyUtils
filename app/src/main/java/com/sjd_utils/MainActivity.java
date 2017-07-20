package com.sjd_utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sjd.nfc.NFCActivity;
import com.sjd.view.ListView.NormalListView.NormalListtViewActivity;
import com.sjd.view.ListView.OptimizationListView.OptimizationListtViewActivity;

public class MainActivity extends AppCompatActivity {
    int test = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        int id = view.getId();
        Intent intent = null;
        switch (id) {
            case R.id.Button1:
                intent = new Intent(this, OkHttpActivity.class);
                break;
            case R.id.Button2:
                intent = new Intent(this, CameraAndAlbumActivity.class);
                break;
            case R.id.Button3:
                intent = new Intent(this, NFCActivity.class);
                break;
            case R.id.Button4:
                intent = new Intent(this, NormalListtViewActivity.class);
                break;
            case R.id.Button5:
                intent = new Intent(this, OptimizationListtViewActivity.class);
                break;
            case R.id.Button6:
//                intent = new Intent(this, NFCActivity.class);
                break;
            case R.id.Button7:
                break;
        }
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }



}
