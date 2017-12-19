package com.other;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.Utils.Utils;
import com.amap.androidobackgroundlocation.R;

public class PhoneInfoActivity extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_info);

        tv = (TextView)findViewById(R.id.textView3);
        init(this);
    }

    private void init(Context context) {
        boolean bNetWork = Utils.isNetworkAvailable(context);
        boolean bWifi = Utils.isWifi(context);
        boolean bMobil = Utils.isMobile(context);

        long tsize =  Utils.getSDcardTotalSize();
        long absize =  Utils.getSDcardAvailableSize();

        long phtsize =  Utils. getPhoneTotalSize();
        long phabsize =  Utils. getPhoneAvailableSize();

        String imsi = Utils.getImsi(context);
        String info = Utils.getHandSetInfo(context);
        String ver = Utils.getAppVersionName(context);

        String str="";
        str += String.valueOf(bNetWork) + " 网络是否可用\n";
        str += String.valueOf(bWifi) + " 是否wifi\n";
        str += String.valueOf(bMobil) + " 是否移动网络\n";
        str += String.valueOf(tsize) + " sd总大小\n";
        str += String.valueOf(absize) + " sd可用空间\n";
        str += String.valueOf(phtsize) + " 手机总空间\n";
        str += String.valueOf(imsi) + "\n\n";
        str += String.valueOf(info) + "\n\n";
        str += String.valueOf(ver) + "\n\n";
        tv.setText(str);
    }


}
