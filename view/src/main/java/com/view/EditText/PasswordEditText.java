package com.view.EditText;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.view.R;

/**
 * Created by sjd on 2016/11/14.
 *
 * 带图片的Edittext，用于密码输入，可控制显示还是隐藏密码
 */

public class PasswordEditText extends EditText {


    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setTransformationMethod(PasswordTransformationMethod.getInstance());//设置为密码输入框

        Drawable down = getResources().getDrawable(R.drawable.neo_edittext_line);
        Drawable right = getResources().getDrawable(R.drawable.account_password_invisible);
        setBackground(null);
        right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
        down.setBounds(0, 0, down.getMinimumWidth(), down.getMinimumHeight());
        setCompoundDrawables(null, null, right, down);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // et.getCompoundDrawables()得到一个长度为4的数组，分别表示 左上右下 四张图片
        Drawable drawable = getCompoundDrawables()[2];

        //如果不是按下事件，不再处理
        if (event.getAction() == MotionEvent.ACTION_UP && drawable != null) {
            if (event.getX() > getWidth() - getPaddingRight() - drawable.getIntrinsicWidth() && event.getX() <= getWidth() - getPaddingRight()) {//判断触摸点击范围
                Drawable drawable1;
                if (getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                    setTransformationMethod(HideReturnsTransformationMethod.getInstance());//设置EditText文本为可见的
                    drawable1 = getResources().getDrawable(R.drawable.account_password_invisible);
                } else {
                    setTransformationMethod(PasswordTransformationMethod.getInstance());//设置EditText文本为隐藏的
                    drawable1 = getResources().getDrawable(R.drawable.account_password_visible);
                }
                drawable1.setBounds(0, 0, drawable.getMinimumWidth(), drawable1.getMinimumHeight());
                setCompoundDrawables(null, null, drawable1, null);
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        Drawable drawable;
        if (!focused) {
            drawable = getResources().getDrawable(R.drawable.neo_edittext_line);
        } else {
            drawable = getResources().getDrawable(R.drawable.neo_edittext_line2);
        }
        setCompoundDrawables(this, null, null, null, drawable);
    }


    //保证原来  不为空的图片 不被 null替换掉
    private void setCompoundDrawables(EditText v, Drawable left, Drawable top, Drawable right, Drawable bottom) {
        Drawable[] drawable = v.getCompoundDrawables();
        if (left != null) {
            left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());
            drawable[0] = left;
        }
        if (top != null) {
            top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
            drawable[1] = top;
        }
        if (right != null) {
            right.setBounds(0, 0, 60, 40);
            drawable[2] = right;
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, bottom.getMinimumWidth(), bottom.getMinimumHeight());
            drawable[3] = bottom;
        }
        v.setCompoundDrawables(drawable[0], drawable[1], drawable[2], drawable[3]);
    }
}
