package com.example.zxl.keyboarddemo.test3;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.zxl.keyboarddemo.R;

import java.lang.reflect.Method;

/**
 * @Description:
 * @Author: zxl
 * @Date: 2017/4/11 12:09
 */

public class Test3Activity extends AppCompatActivity {
    private TestEditText mEt1;
    private EditText mEt2;
    private KeyboardView mKeyboardView;
    private Test3KeyBoardUtils keyboardUtil;// 软键盘类

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        initView();
    }

    private void initView() {
        mEt1 = (TestEditText) findViewById(R.id.et1);
        mEt2 = (EditText) findViewById(R.id.et2);
        mKeyboardView = (KeyboardView) findViewById(R.id.keyboard_view);
        disableShowSoftInput(mEt1);
        disableShowSoftInput(mEt2);
        hideSoft();
        keyboardUtil = new Test3KeyBoardUtils(this, mKeyboardView);
        mEt1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                keyboardUtil.showKeyboard();
                return false;
            }
        });
        mEt2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                keyboardUtil.showKeyboard();
                return false;
            }
        });
    }

    public void hideSoft() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 禁止Edittext弹出软件盘，光标依然正常显示。
     */
    public void disableShowSoftInput(EditText editText) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
            }
        }
    }

}
