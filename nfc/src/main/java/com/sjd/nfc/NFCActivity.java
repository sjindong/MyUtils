package com.sjd.nfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.accountmanager.R;


public class NFCActivity extends AppCompatActivity {

    TextView tagid;
    TextView tagtype;
    TextView tagcontext;
    CheckBox mWriteData;

    NfcAdapter mNfcAdapter;
    PendingIntent mPendingIntent;
    private IntentFilter[] intentFilters;
    private String[][] techList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        tagid = (TextView) findViewById(R.id.tagid);
        tagtype = (TextView) findViewById(R.id.tagtype);
        mWriteData = (CheckBox) findViewById(R.id.checkbox);
        tagcontext = (TextView) findViewById(R.id.tagcontext);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) { //设备不支持NFC功能
//            Toast.makeText(this, "设备不支持NFC！", Toast.LENGTH_LONG).show();
            Log.e("SJD", "onCreate: 设备不支持NFC！");
            finish();
            return;
        }
        if (!mNfcAdapter.isEnabled()) {//设备没有启用NFC功能
//            Toast.makeText(this, "请在系统设置中先启用NFC功能！", Toast.LENGTH_LONG).show();
            Log.e("SJD", "onCreate: 请在系统设置中先启用NFC功能！");
//            mNfcAdapter.enable(); 系统函数
            finish();
            return;
        }
        //定义程序可以兼容的nfc协议，例子为nfca和nfcv
        //在Intent filters里声明你想要处理的Intent，一个tag被检测到时先检查前台发布系统，
        //如果前台Activity符合Intent filter的要求，那么前台的Activity的将处理此Intent。
        //如果不符合，前台发布系统将Intent转到Intent发布系统。如果指定了null的Intent filters，
        //当任意tag被检测到时，你将收到TAG_DISCOVERED intent。因此请注意你应该只处理你想要的Intent。
        techList = new String[][]{
                new String[]{android.nfc.tech.NfcV.class.getName()},
                new String[]{android.nfc.tech.NfcA.class.getName()}};
        intentFilters = new IntentFilter[]{new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED), new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)};
        //创建一个 PendingIntent 对象, 这样Android系统就能在一个tag被检测到时定位到这个对象
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, intentFilters, techList);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String id = Utils.bytesToHexString(tag.getId());//获取标签的唯一ID；
        String[] techList = tag.getTechList();//获取标签所支持的数据格式
        String tagcontext1 = null;

        Log.e("SJD", "onNewIntent: id=" + id + " \n techlist = " + techList.toString() + " \n tagcontext1 = " + tagcontext1);
        boolean haveMifareUltralight = false;
        for (String tech : techList) {
            if (tech.contains("MifareClassic")) {
                haveMifareUltralight = true;
                break;
            }
        }
        if (!haveMifareUltralight) {
            Toast.makeText(this, "不支持MifareClassic", Toast.LENGTH_LONG).show();
            return;
        }
        if (mWriteData.isChecked()) {
            Utils.writeTagMifareClassic(tag);
        } else {
            String data = Utils.readTagMifareClassic(tag);
            tagcontext1 = data;
            if (data != null) {
                Log.e(data, "output");
                Toast.makeText(this, data, Toast.LENGTH_LONG).show();
            }
        }
        show(id, techList, tagcontext1);
    }

    private void show(String id, String[] techList, String tagcontext1) {
        String type = "";
        for (String a : techList) {
            type += a + "\n";
        }
        tagtype.setText(type);
        tagid.setText(id);
        tagcontext.setText(tagcontext1);
        tagcontext.setMovementMethod(ScrollingMovementMethod.getInstance());
        Log.e("SJD", "show: id=" + id + " \n techlist = " + techList.toString() + " \n tagcontext1 = " + tagcontext1);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(this);
    }
}
