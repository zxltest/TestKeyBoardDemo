package com.example.zxl.keyboarddemo.test3;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * @Description:
 * @Author: zxl
 * @Date: 2017/4/13 15:17
 */

public class TestEditText extends EditText {
    public TestEditText(Context context) {
        super(context);
    }

    public TestEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        Log.e("TAG", "dispatchKeyEventPreIme==(" + event.getKeyCode()+","+event.getAction()+")");
        if (event.getKeyCode() != KeyEvent.KEYCODE_BACK) {
            dispatchKeyEvent(event);
            return true;
        }
        return super.dispatchKeyEventPreIme(event);
    }
}
