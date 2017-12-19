package com.other;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amap.androidobackgroundlocation.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class widgetActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnTime;
    DatePicker date;
    TimePicker time;
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        initView();
    }


    private void initView() {
        btnTime = (Button)findViewById(R.id.btnTime);
        btnTime.setOnClickListener(this);

        web = (WebView)findViewById(R.id.web);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("http://www.baidu.com");// 添加网页加载回调
        web.setWebViewClient(new WebViewClient() {

            // 网页内页面跳转还在webview中，不打开系统浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            // 页面开始打开
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                progressBar.setVisibility(android.view.View.VISIBLE);
                Toast.makeText(getApplicationContext(), "onPageStarted", 2000).show();
            }

            // 页面打开完成
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                progressBar.setVisibility(android.view.View.GONE);
                Toast.makeText(getApplicationContext(), "onPageFinished", 2000).show();
            }

            // 页面打开出错
            @Override
            public void onReceivedError(WebView view, int errorCode, String description,
                                        String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
//                progressBar.setVisibility(android.view.View.GONE);
                Toast.makeText(getApplicationContext(), "onReceivedError", 2000).show();
            }

        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnTime:
                Calendar currentTime = Calendar.getInstance();
                new TimePickerDialog(this, 0,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                //设置当前时间
                                Calendar c = Calendar.getInstance();
                                c.setTimeInMillis(System.currentTimeMillis());
                                // 根据用户选择的时间来设置Calendar对象
                                c.set(Calendar.HOUR, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                // ②设置AlarmManager在Calendar对应的时间启动Activity
                                //alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
                                Log.e("HEHE",c.getTimeInMillis()+"");   //这里的时间是一个unix时间戳
                                // 提示闹钟设置完毕:
                                Toast.makeText(getApplicationContext(), "[ "+String.valueOf(hourOfDay)+":"+String.valueOf(minute)+"]闹钟设置完毕~"+ c.getTimeInMillis(),Toast.LENGTH_SHORT).show();
                            }
                        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime
                        .get(Calendar.MINUTE), false).show();
                break;

                default:
                    break;
        }
    }

}
