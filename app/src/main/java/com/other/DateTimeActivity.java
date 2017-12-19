package com.other;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.amap.androidobackgroundlocation.R;
import com.amap.androidobackgroundlocation.StartActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tv;
    Button btnOK;
    DatePicker date;
    TimePicker time;

    String strDate;
    String strTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_date_time);

        initView();

    }

    private void initView() {
        long ctm=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d1=new Date(ctm);
        String t1=format.format(d1);

        Calendar calendar = Calendar.getInstance();

        tv = (TextView)findViewById(R.id.textView2);
        btnOK = (Button)findViewById(R.id.btnOK);
        btnOK.setOnClickListener(this);

        date = (DatePicker)findViewById(R.id.date);
        date.init(calendar.get(calendar.YEAR), (calendar.get(Calendar.MONTH)), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int y, int m, int d) {
                m+=1;
                strDate = String.valueOf(y)+"-"+String.valueOf(m)+"-"+String.valueOf(d);
                setDateTime();
            }
        });

        time = (TimePicker)findViewById(R.id.time);
        time.setIs24HourView(true);
        time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int h, int m) {
                strTime = String.valueOf(h)+":"+String.valueOf(m);
                setDateTime();
            }
        });

        strDate = t1.split(" ")[0];
        strTime = t1.split(" ")[1];
        setDateTime();
    }

    public void setDateTime()
    {
        btnOK.setText(strDate+" "+strTime);
        tv.setText("您选择的时间是:"+strDate+" "+strTime);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnOK:
                Intent intent = new Intent(this, StartActivity.class);
                // 设置启动Activity时携带的参数信息 的Intent
                Bundle bundle = new Bundle();
                bundle.putString("a", strDate+" "+strTime);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
                default:
                    break;
        }
    }
}
