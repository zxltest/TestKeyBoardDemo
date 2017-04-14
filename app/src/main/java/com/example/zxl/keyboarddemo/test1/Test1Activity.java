package com.example.zxl.keyboarddemo.test1;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.example.zxl.keyboarddemo.R;

import java.lang.reflect.Method;

/**
 * @Description:
 * @Author: zxl
 * @Date: 2017/4/11 12:01
 */

public class Test1Activity extends AppCompatActivity {

    private EditText mEt1, mEt2;
    private KeyboardView mKeyboardView;
    private Test1KeyBoardUtils keyboardUtil;// 软键盘类

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        initView();
    }

    private void initView() {
        mEt1 = (EditText) findViewById(R.id.et1);
        mEt2 = (EditText) findViewById(R.id.et2);
        mKeyboardView = (KeyboardView) findViewById(R.id.keyboard_view);
        disableShowSoftInput(mEt1);
        disableShowSoftInput(mEt2);
        keyboardUtil = new Test1KeyBoardUtils(this, mKeyboardView);
        mEt1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    keyboardUtil.setBindEditText(mEt1);
                    keyboardUtil.showKeyboard();
                }
            }
        });

        mEt2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    keyboardUtil.setBindEditText(mEt2);
                    keyboardUtil.showKeyboard();
                }
            }
        });
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
