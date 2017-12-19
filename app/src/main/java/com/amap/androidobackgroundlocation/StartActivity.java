package com.amap.androidobackgroundlocation;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Utils.Utils;
import com.other.DateTimeActivity;
import com.other.PhoneInfoActivity;
import com.other.ReadSmsActivity;
import com.other.widgetActivity;

import java.io.File;

public class StartActivity extends AppCompatActivity
implements View.OnClickListener {

    Button btnLocal;
    Button btnMap;
    Button btnSms;
    Button btnWidget;
    Button btnDateTime;
    Button btnPost;
    Button btnShare;
    Button btnInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initView();


        Intent intent=getIntent();
        if(intent!=null)
        {
            String s = intent.getStringExtra("a");
            if (s!=null&&!s.isEmpty())
            {
                btnDateTime.setText(s);
            }
        }

//        Intent intent = new Intent(this,MapActivity.class);
//        startActivity(intent);
    }

    private void initView() {
        btnLocal = (Button)findViewById(R.id.btnLocal);
        btnLocal.setOnClickListener(this);

        btnMap = (Button)findViewById(R.id.btnMap);
        btnMap.setOnClickListener(this);

        btnSms = (Button)findViewById(R.id.btnSms);
        btnSms.setOnClickListener(this);

        btnWidget = (Button)findViewById(R.id.btnWidget);
        btnWidget.setOnClickListener(this);

        btnDateTime = (Button)findViewById(R.id.btnDateTime);
        btnDateTime.setOnClickListener(this);

        btnPost = (Button)findViewById(R.id.btnPost);
        btnPost.setOnClickListener(this);

        btnShare=(Button)findViewById(R.id.btnShare);
        btnShare.setOnClickListener(this);

        btnInfo = (Button)findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (view.getId()==R.id.btnLocal)
        {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.btnMap)
        {
            Intent intent = new Intent(this,MapActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.btnSms)
        {
            Intent intent = new Intent(this,ReadSmsActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.btnWidget)
        {
            Intent intent = new Intent(this,widgetActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.btnDateTime)
        {
            Intent intent = new Intent(this,DateTimeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
//            Dialog dialog = new Dialog(this, R.style.dialog);
//            dialog.setContentView(R.layout.activity_date_time);
//            dialog.show();
        }
        else if (view.getId()==R.id.btnPost)
        {
            Utils.post1();
        }
        else if (id == R.id.btnShare){
            shareMsg("A","B","C","");
        }
        else if (id == R.id.btnInfo){
            Intent intent = new Intent(this,PhoneInfoActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 分享功能
     * @param activityTitle
     *            Activity的名字
     * @param msgTitle
     *            消息标题
     * @param msgText
     *            消息内容
     * @param imgPath
     *            图片路径，不分享图片则传null
     */
    public void shareMsg(String activityTitle, String msgTitle, String msgText,
                         String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/*"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/*");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, activityTitle));
    }
}
