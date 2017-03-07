package com.sjd_utils.Utils;

import android.util.Log;

/**
 * Created by sjd on 2017/2/17.
 *
 * 自定义工具类
 */

public class Util_Log {
    private boolean OpenLog = true;
    private final String defineTag = "SJD";

    public boolean isOpenLog() {
        return OpenLog;
    }

    public void setOpenLog(boolean OpenLog) {
        this.OpenLog = OpenLog;
    }

    public void Loge(String s) {
        if (OpenLog)
            Log.e(defineTag, s);
    }

    public void Loge(String tag, String s) {
        if (OpenLog)
            Log.e(tag, s);
    }

    public void Loge(String s, Throwable tr) {
        if (OpenLog)
            Log.e(defineTag, s, tr);
    }

    public void Loge(String tag, String s, Throwable tr) {
        if (OpenLog)
            Log.e(tag, s, tr);
    }

    public void Logi(String s) {
        if (OpenLog)
        Log.i(defineTag, s);
    }

    public void Logi(String tag, String s) {
        if (OpenLog)
            Log.i(tag, s);
    }

    public void Logi(String s, Throwable tr) {
        if (OpenLog)
        Log.i(defineTag, s, tr);
    }

    public void Logi(String tag, String s, Throwable tr) {
        if (OpenLog)
            Log.i(tag, s, tr);
    }

    public void Logw(String s) {
        if (OpenLog)
            Log.w(defineTag, s);
    }

    public void Logw(String tag, String s) {
        if (OpenLog)
            Log.w(tag, s);
    }

    public void Logw(String s, Throwable tr) {
        if (OpenLog)
        Log.w(defineTag, s, tr);
    }

    public void Logw(String tag, String s, Throwable tr) {
        if (OpenLog)
            Log.w(tag, s, tr);
    }

    public void Logd(String s) {
        if (OpenLog)
            Log.d(defineTag, s);
    }

    public void Logd(String tag, String s) {
        if (OpenLog)
            Log.d(tag, s);
    }

    public void Logd(String s, Throwable tr) {
        if (OpenLog)
            Log.d(defineTag, s, tr);
    }

    public void Logd(String tag, String s, Throwable tr) {
        if (OpenLog)
            Log.d(tag, s, tr);
    }

    public void Logv(String s) {
        if (OpenLog)
            Log.v(defineTag, s);
    }

    public void Logv(String tag, String s) {
        if (OpenLog)
            Log.v(tag, s);
    }

    public void Logv(String s, Throwable tr) {
        if (OpenLog)
            Log.v(defineTag, s, tr);
    }

    public void Logv(String tag, String s, Throwable tr) {
        if (OpenLog)
            Log.v(tag, s, tr);
    }

    public void Logwtf(String s) {
        if (OpenLog)
            Log.wtf(defineTag, s);
    }

    public void Logwtf(String tag, String s) {
        if (OpenLog)
            Log.wtf(tag, s);
    }

    public void Logwtf(String s, Throwable tr) {
        if (OpenLog)
            Log.wtf(defineTag, s, tr);
    }

    public void Logwtf(String tag, String s, Throwable tr) {
        if (OpenLog)
            Log.wtf(tag, s, tr);
    }
}
