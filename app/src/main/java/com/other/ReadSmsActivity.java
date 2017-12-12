package com.other;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amap.androidobackgroundlocation.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadSmsActivity extends AppCompatActivity implements View.OnClickListener {

    private DbAdapter DbHepler;
    Button btnSms;
    Button btnContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_sms);

        btnSms = (Button)findViewById(R.id.btnSms);
        btnSms.setOnClickListener(this);
        btnContact = (Button)findViewById(R.id.btnContact);
        btnContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnSms)
        {
            getSmsInPhone();
        }
        else if(view.getId()==R.id.btnContact)
        {
            queryContactPhoneNumber();
        }
    }

    public String getSmsInPhone()
    {
        final String SMS_URI_ALL   = "content://sms/";
        final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_SEND  = "content://sms/sent";
        final String SMS_URI_DRAFT = "content://sms/draft";

        StringBuilder smsBuilder = new StringBuilder();

        try{
            ContentResolver cr = getContentResolver();
            String[] projection = new String[]{"_id", "address", "person",
                    "body", "date", "type"};
            Uri uri = Uri.parse(SMS_URI_ALL);
            Cursor cur = cr.query(uri, projection, null, null, "date desc");

            DbHepler = new DbAdapter(this);
            DbHepler.open();
            if (cur.moveToFirst()) {
                String name;
                String phoneNumber;
                String smsbody;
                String date;
                String type;

                int nameColumn = cur.getColumnIndex("person");
                int phoneNumberColumn = cur.getColumnIndex("address");
                int smsbodyColumn = cur.getColumnIndex("body");
                int dateColumn = cur.getColumnIndex("date");
                int typeColumn = cur.getColumnIndex("type");

                do{
                    name = cur.getString(nameColumn);
                    phoneNumber = cur.getString(phoneNumberColumn);
                    smsbody = cur.getString(smsbodyColumn);

                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(Long.parseLong(cur.getString(dateColumn)));
                    date = dateFormat.format(d);

                    int typeId = cur.getInt(typeColumn);
                    if(typeId == 1){
                        type = "接收";
                        if (phoneNumber.equals("95533"))
                        {
                            if (smsbody.contains("工资"))
                            {
                                Pattern pt = Pattern.compile("[0-9]+[.][0-9]+");
                                Matcher match = pt.matcher(smsbody);
                                if (match.find()) {
                                    //System.out.println(match.group());
                                    name = match.group();
                                }
                            }
                        }
                    } else if(typeId == 2){
                        type = "发送";
                    } else {
                        type = "";
                    }

                    DbHepler.insertSms(name,phoneNumber,smsbody,type,Long.parseLong(cur.getString(dateColumn)));

//                    smsBuilder.append("[");
//                    smsBuilder.append(name+",");
//                    smsBuilder.append(phoneNumber+",");
//                    smsBuilder.append(smsbody+",");
//                    smsBuilder.append(date+",");
//                    smsBuilder.append(type);
//                    smsBuilder.append("] ");

                    if(smsbody == null) smsbody = "";
                }while(cur.moveToNext());
            } else {
                smsBuilder.append("no result!");
            }

            DbHepler.close();
            smsBuilder.append("getSmsInPhone has executed!");
        } catch(SQLiteException ex) {
            Log.d("SQLiteException in getSmsInPhone", ex.getMessage());
        }
        return smsBuilder.toString();
    }

    private void queryContactPhoneNumber() {
        String[] cols = {ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                cols, null, null, null);
        DbHepler = new DbAdapter(this);
        DbHepler.open();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            // 取得联系人名字
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            int numberFieldColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String name = cursor.getString(nameFieldColumnIndex);
            String number = cursor.getString(numberFieldColumnIndex);
            //Toast.makeText(this, name + " " + number, Toast.LENGTH_SHORT).show();

            DbHepler.insertSms(name,number,"","联系人",System.currentTimeMillis());
        }
        DbHepler.close();
    }
}
