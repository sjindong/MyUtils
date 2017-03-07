package com.sjd_utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sjd on 2017/2/10.
 */

public class Util {
    /**
     * 设置屏幕亮度
     *
     * @param activity
     * @param light
     */
    void setScreenLight(Activity activity, int light) {
        WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
        localLayoutParams.screenBrightness = (light / 255.0F);
        activity.getWindow().setAttributes(localLayoutParams);
    }

    /**
     * 获取屏幕亮度
     *
     * @param activity
     * @return
     */
    float GetScreenLight(Activity activity) {
        WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
        return localLayoutParams.screenBrightness;
    }



    /**
     * 判断所给路径文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean fileIsExists(String filePath) {
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    /**
     * 检测网络是否可用
     *
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 请求获取焦点
     *
     * @param view
     * @return
     */
    public static boolean getFocus(View view) {
        view.setFocusableInTouchMode(true);
        view.setFocusable(true);
        boolean b = view.requestFocus();
        return b;
    }

    /**
     * 获取下载链接的名称
     *
     * @param url
     * @return
     */
    public static String getPicNameInUrl(String url) {
        String regEx = ".+/(.+)$";//符合正则规则
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(url);
        String filename;
        if (!m.find()) {
            System.out.println("文件路径格式错误!");
            filename = "download.jpeg";
        } else {
            filename = m.group(1);//默认正则只匹配到一个
        }
        return filename;
    }

    //获取本地语言
    private String chooseLanguage(Context context) {
        if (isChineseLanguage(context)) {
            return "cn";
        } else {
            return "en";
        }
    }

    //判断语言是否为中文
    private boolean isChineseLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language.endsWith("zh");
    }


}
