package com.example.zxl.keyboarddemo.test2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zxl.keyboarddemo.R;

import java.util.List;

/**
 * @Description:  自定义输入法 需要用户设置才能生效
 * @Author: zxl
 * @Date: 2017/4/11 12:08
 */

public class Test2Activity extends AppCompatActivity {

    private EditText mEt1, mEt2;
    private TextView textView;

    public static final String DEFAULT_IME_ID = "com.example.zxl.keyboarddemo/.test2.SoftKeyboardIme";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        initView();
    }

    public void initView() {
        mEt1 = (EditText) findViewById(R.id.et1);
        mEt2 = (EditText) findViewById(R.id.et2);
        textView = (TextView) findViewById(R.id.change);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        getList();
    }

    public void getList() {
        InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> inputMethodList = input.getInputMethodList();
        if (inputMethodList != null) {
            Log.e("TAG", "size==" + inputMethodList.size());
            for (InputMethodInfo inputMethodInfo : inputMethodList) {
                Log.e("TAG", "inputMethodInfo==" + inputMethodInfo.getId());
            }
        }
    }


    public void show() {
        InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        input.showInputMethodPicker();
    }

    /***
     * com.example.zxl.keyboarddemo/.test2.SoftKeyboardIme
     */
    public void check() {
        int mCheck = checkCallingOrSelfPermission(android.Manifest.permission.WRITE_SECURE_SETTINGS);
        Log.e("TAG", "mCheck==" + mCheck);
        InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        input.setInputMethod(null, DEFAULT_IME_ID);

    }
}
