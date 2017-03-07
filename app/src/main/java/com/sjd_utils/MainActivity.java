package com.sjd_utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sjd.nfc.NFCActivity;
import com.sjd.view.ListView.NormalListView.NormalListtViewActivity;
import com.sjd.view.ListView.OptimizationListView.OptimizationListtViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void okhttp(View view) {
        Intent intent = new Intent(this,OkHttpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void cameraAndAlbum(View view){
        Intent intent = new Intent(this,CameraAndAlbumActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void NFC(View view){
        Intent intent = new Intent(this, NFCActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void NormalListView(View view) {
        Intent intent = new Intent(this, NormalListtViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    public void OptimizationListView(View view) {
        Intent intent = new Intent(this, OptimizationListtViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void loadListView(View view) {
        Intent intent = new Intent(this, NFCActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}
