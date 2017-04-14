package com.example.zxl.keyboarddemo.test4;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.example.zxl.keyboarddemo.R;

import java.io.File;

/**
 * @Description:
 * @Author: zxl
 * @Date: 2017/4/5 16:32
 */

public class LoadWebViewActivity extends AppCompatActivity {

    private TestWebView webView;
    private KeyboardView mKeyboardView;
    private String path = "";
    private Test4KeyBoardUtils keyboardUtil;// 软键盘类

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        initData();
    }

    private void initView() {
        webView = (TestWebView) findViewById(R.id.web);
        mKeyboardView = (KeyboardView) findViewById(R.id.keyboard_view);
        keyboardUtil = new Test4KeyBoardUtils(this, mKeyboardView);
        keyboardUtil.showKeyboard();
        keyboardUtil.setBindWebView(webView);
        initWebView();
    }

    public void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 支持js
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
    }

    private void initData() {
        path = Environment.getExternalStorageDirectory().getPath() + File.separator + "test" + File.separator + "test.html";
        File file = new File(path);
        webView.loadUrl("file://" + file.getAbsolutePath());
    }

    public void hideSoft() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(webView.getWindowToken(), 0);
    }
}
