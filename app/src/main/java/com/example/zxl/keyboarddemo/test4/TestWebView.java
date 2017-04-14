package com.example.zxl.keyboarddemo.test4;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;

/**
 * @Description:
 * @Author: zxl
 * @Date: 2017/4/13 15:44
 */

public class TestWebView extends WebView {
    public TestWebView(Context context) {
        this(context,null);
    }

    public TestWebView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestWebView(Context context, AttributeSet attrs, int defStyleAttr) {
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
