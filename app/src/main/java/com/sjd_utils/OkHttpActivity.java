package com.sjd_utils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sjd.utils_okhttp.OkHttpContent;
import com.sjd.utils_okhttp.RSAUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.PublicKey;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.sjd.utils_okhttp.RSAUtils.loadPublicKey;

/**
 * Created by sjd on 2017/2/13.
 */

public class OkHttpActivity extends Activity {
    EditText edittext1;//明文信息
    TextView textView;//RSA算法 加密结果
    RadioGroup radioGroup;
    RadioButton radioButton;
    RadioButton radioButton1;
    TextView textView8;//返回信息  显示框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        textView = (TextView) findViewById(R.id.textView);
        edittext1 = (EditText) findViewById(R.id.edittext1);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
    }

    public void httpPost(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("lockId", OkHttpContent.TempTestLockId);
                    jsonObject.put("masterLock", 1);
                    jsonObject.put("lock", 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json;charset=utf-8");//
                Request request = new Request.Builder()
                        .url(OkHttpContent.DoorServiceUrl + OkHttpContent.lockStatus)
                        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, jsonObject.toString()))
                        .build();

                OkHttpClient client = new OkHttpClient();
                client.newBuilder().connectTimeout(20, TimeUnit.SECONDS)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        }
                        Headers responseHeaders = response.headers();
                        for (int i = 0; i < responseHeaders.size(); i++) {
                            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                        }

                        System.out.println(response.body().string());
                    }
                });
            }
        }).start();
    }

    public void rsaCalulate(View view) {
        if (view.getId() == R.id.button2) { //加密
            if (edittext1.getText() == null) {
                return;
            }
            String text = edittext1.getText().toString();
            String result = null;
            try {
                PublicKey publicKey = loadPublicKey(OkHttpContent.RSA_PUBLIC_KEY);
                result = RSAUtils.encryptData(text.getBytes(), publicKey).toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            textView.setText(result);
        } else if (view.getId() == R.id.button3) {//解密
            if (textView.getText() == null) {
                return;
            }
            String text = textView.getText().toString();
            Log.e("SJD", "text = " + text);
            String result = null;
            try {
                PublicKey publicKey = loadPublicKey(OkHttpContent.RSA_PUBLIC_KEY);
                result = RSAUtils.decryptWithRSAPubKey(publicKey, text);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (result != null || "".equals(result)) {
                textView.setText(text);
            }
        }
    }
}
