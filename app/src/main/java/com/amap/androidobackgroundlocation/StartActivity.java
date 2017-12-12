package com.amap.androidobackgroundlocation;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.other.ReadSmsActivity;

public class StartActivity extends AppCompatActivity
implements View.OnClickListener {

    Button btnLocal;
    Button btnMap;
    Button btnSms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnLocal = (Button)findViewById(R.id.btnLocal);
        btnLocal.setOnClickListener(this);

        btnMap = (Button)findViewById(R.id.btnMap);
        btnMap.setOnClickListener(this);

        btnSms = (Button)findViewById(R.id.btnSms);
        btnSms.setOnClickListener(this);

        Intent intent = new Intent(this,MapActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
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
    }
}
