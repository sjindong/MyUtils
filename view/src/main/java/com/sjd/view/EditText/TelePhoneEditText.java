package com.sjd.view.EditText;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sjd on 2017/8/11.
 */

public class TelePhoneEditText extends EditText {
    public TelePhoneEditText(Context context) {
        super(context);
    }

    public TelePhoneEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TelePhoneEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 当文本改变时被调用(在当前文本s中，从start位置开始之后的before个字符（已经）被count个字符替换掉了)
     *
     * @param text         文本改变之后的内容
     * @param start        文本开始改变时的起点位置，从0开始计算
     * @param lengthBefore 要被改变的文本字数，即已经被替代的选中文本字数
     * @param lengthAfter  改变后添加的文本字数，即替代选中文本后的文本字数
     */
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (text == null || text.length() == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (i == 3 || i == 8 || text.charAt(i) != ' ') {
                sb.append(text.charAt(i));
                if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                    sb.insert(sb.length() - 1, ' ');
                }
            }
        }
        if (!TextUtils.isEmpty(sb.toString().trim()) && !sb.toString().equals(text.toString())) {
            setText(sb.toString());
            setSelection(sb.length());
        }
        Log.e("SJD", sb.length() + "  #" + sb + "#  ");
    }

    TextWatcher textWatcher = new TextWatcher() {
        /**
         * 文本改变之前调用(在当前文本s中，从start位置开始之后的count个字符（即将）要被after个字符替换掉)
         * @param s     文本改变之前的内容
         * @param start 文本开始改变时的起点位置，从0开始计数
         * @param count 要被改变的文本字数，即将要被替代的选中文本字数
         * @param after 改变后添加的文本字数，即即替代选中文本后的文本字数
         */
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        /**
         *当文本改变时被调用(在当前文本s中，从start位置开始之后的before个字符（已经）被count个字符替换掉了)
         * @param s 文本改变之后的内容
         * @param start 文本开始改变时的起点位置，从0开始计算
         * @param before 要被改变的文本字数，即已经被替代的选中文本字数
         * @param count 改变后添加的文本字数，即替代选中文本后的文本字数
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        /**
         * 在文本改变结束后调用()
         * @param s 改变后的最终文本
         */
        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /**
     * 获取电话号码
     *
     * @return
     */
    public String getPhoneText() {
        String str = getText().toString();
        return replaceBlank(str);
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     *
     * @param str
     * @return
     */
    private String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

}
