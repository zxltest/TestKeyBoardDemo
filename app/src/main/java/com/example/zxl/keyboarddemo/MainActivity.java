package com.example.zxl.keyboarddemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.zxl.keyboarddemo.test1.Test1Activity;
import com.example.zxl.keyboarddemo.test2.Test2Activity;
import com.example.zxl.keyboarddemo.test3.Test3Activity;
import com.example.zxl.keyboarddemo.test4.LoadWebViewActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvTest1, mTvTest2, mTvTest3, mTvTest4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        mTvTest1 = (TextView) findViewById(R.id.tv_test1);
        mTvTest2 = (TextView) findViewById(R.id.tv_test2);
        mTvTest3 = (TextView) findViewById(R.id.tv_test3);
        mTvTest4 = (TextView) findViewById(R.id.tv_test4);
        mTvTest1.setOnClickListener(this);
        mTvTest2.setOnClickListener(this);
        mTvTest3.setOnClickListener(this);
        mTvTest4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_test1:
                startActivity(new Intent(this, Test1Activity.class));
                break;
            case R.id.tv_test2:
                startActivity(new Intent(this, Test2Activity.class));
                break;
            case R.id.tv_test3:
                startActivity(new Intent(this, Test3Activity.class));
                break;
            case R.id.tv_test4:
                startActivity(new Intent(this, LoadWebViewActivity.class));
                break;
        }
    }
}
