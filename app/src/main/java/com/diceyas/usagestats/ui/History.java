package com.diceyas.usagestats.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;


import com.diceyas.usagestats.ContextUtil;
import com.diceyas.usagestats.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.diceyas.usagestats.db.LocalDataBase;
import com.diceyas.usagestats.db.MyDataBaseHelper;
import com.diceyas.usagestats.lib.timessquare.*;

public class History extends Activity {

    private List<Calendar> calsList;
    private List<Calendar> calsList2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_history);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title2);
        CalendarView calendar = (CalendarView) findViewById( R.id.h_CV );
        calsList = new ArrayList<Calendar>();
        findViewById(R.id.button_title_l).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                SharedPreferences mySharedPreferences = getSharedPreferences("test",
                        Activity.MODE_PRIVATE);
                String str=mySharedPreferences.getString("tag", "");
                Intent i;
                if(str==""){
                    i = new Intent(History.this,MainActivity.class);
                }
                else {
                    i = new Intent(History.this,Setting.class);
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    editor.putString("tag","");
                    editor.commit();
                }
                startActivity(i);
                finish();
            }
        }
        );

        Calendar cal = Calendar.getInstance();
        cal.set(2016,5,14);
        calsList.add(cal);

        Calendar cal1 = Calendar.getInstance();
        cal1.set(2016,5,16);
        calsList.add(cal1);

        calsList2 = new ArrayList<Calendar>();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(2016,5,13);
        calsList2.add(cal2);

        Calendar cal3 = Calendar.getInstance();
        cal3.set(2016,5,15);
        calsList2.add(cal3);

//设置日期点击监听器
        calendar.setOnDateSelectedListener(new CalendarView.OnDateSelectedListener() {

            @Override
            public void onDateUnselected(Date date) {
            }

            @Override
            public void onDateSelected(Date date) {
            }
        });
        //设置月份改变监听器
        calendar.setOnDateSelectedListener(new CalendarView.OnDateSelectedListener() {

            @Override
            public void onDateUnselected(Date date) {
            }

            @Override
            public void onDateSelected(Date date) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                String str =simpleDateFormat.format(date);
                int year=Integer.parseInt(str.substring(0,4));
                int month = Integer.parseInt(str.substring(4,6));
                int day = Integer.parseInt(str.substring(6, 8));
                MyDataBaseHelper dbHelper = new MyDataBaseHelper(ContextUtil.getInstance(), "lianji.db",null, 1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                int totalTime = LocalDataBase.getSum(db, year,month,day);
                int hours = (totalTime / 3600);
                int minutes = ((totalTime % 3600) / 60);
                int seconds = (totalTime % 60);

                new AlertDialog.Builder(History.this).setTitle("" + year + "年" + month + "月" + day + "日")
                        .setMessage("当天手机使用时长为：" + hours + "时" + minutes + "分" + seconds + "秒")
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                // TODO Auto-generated method stub

                            }
                        }).show();
            }
        });
        //设置月份改变监听器
        calendar.setOnMonthChangedListener(new CalendarView.OnMonthChangedListener() {
            @Override
            public void onChangedToPreMonth(Date dateOfMonth) {
            }

            @Override
            public void onChangedToNextMonth(Date dateOfMonth) {
            }
        });
        //在某些天上标记红点，或者字体颜色的代码
        calendar.markDatesOfMonth(2016, 5, 1, true, calsList);
        calendar.markDatesOfMonth(2016, 5, 2, false, calsList2);
    }
}