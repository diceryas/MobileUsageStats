package com.diceyas.usagestats.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.app.Activity;

import com.diceyas.usagestats.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Setting extends Activity {

    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor editor;
    private int icon;
    private int choose;
    private ImageView im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_setting);
        mySharedPreferences= getSharedPreferences("test", Activity.MODE_PRIVATE);
        editor = mySharedPreferences.edit();
        icon = mySharedPreferences.getInt("icon",0);
        im = (ImageView)findViewById(R.id.setting_iv2);
        im.setImageResource(exchange.getimage(icon));
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title2);
        findViewById(R.id.button_title_l).setOnClickListener(new View.OnClickListener()
                                                             {
                                                                 public void onClick(View v) {
                                                                     Intent i = new Intent(Setting.this,MainActivity.class);
                                                                     startActivity(i);
                                                                     finish();
                                                                 }
                                                             }
        );

        final ImageView button_hu = (ImageView) this.findViewById(R.id.hourup);
        final ImageView button_hd = (ImageView) this.findViewById(R.id.hourdown);
        final ImageView button_mu = (ImageView) this.findViewById(R.id.minuteup);
        final ImageView button_md = (ImageView) this.findViewById(R.id.minutedown);
        final ImageView button_su = (ImageView) this.findViewById(R.id.secondup);
        final ImageView button_sd = (ImageView) this.findViewById(R.id.seconddown);
        final TextView textview_h = (TextView) this.findViewById(R.id.textViewhour);
        final TextView textview_m = (TextView) this.findViewById(R.id.textViewminute);
        final TextView textview_s = (TextView) this.findViewById(R.id.textViewsecond);
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.setting_rl);

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(Setting.this);
                View mychooseView = layoutInflater.inflate(R.layout.imagelist, null);
                ListView listview = (ListView) mychooseView.findViewById(R.id.il_lv);
                SimpleAdapter adapter = new SimpleAdapter(Setting.this,getData(), R.layout.chooseimage,
                        new String[]{"img","info"},
                        new int[]{R.id.ci_iv,R.id.ci_tv});
                listview.setAdapter(adapter);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        // TODO Auto-generated method stub
//                        ListView listView = (ListView)arg0;

//                        HashMap<String, Object> map = (HashMap<String, Object>) listView.getItemAtPosition(arg2);
//                        Data.lilist.add(map);
//                        String info = (String) map.get("info");
                        choose = arg2;
                        System.out.println(choose);

                    }
                });

                Dialog alertDialog = new AlertDialog.Builder(Setting.this).
                        setTitle("头像选择").
                        setView(mychooseView).
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                editor.putInt("password", choose);
                                System.out.println(choose);
                                editor.commit();
                                im.setImageResource(exchange.getimage(choose));
                            }
                        }).
                        setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                            }
                        }).
                        create();
                alertDialog.show();
            }
        });
        SharedPreferences sharedPreferences= getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        String h =sharedPreferences.getString("hour", "");
        String m =sharedPreferences.getString("minute", "");
        String s =sharedPreferences.getString("minute", "");
        textview_h.setText(h==""?" 00 H":" "+h + " H");
        textview_m.setText(h==""?" 00 M":" "+m + " M");
        textview_s.setText(h==""?" 00 S":" "+s + " S");
        button_hu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = (String) textview_h.getText();
                str = Up(str);
                textview_h.setText(str);
            }
        });
        button_hd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = (String) textview_h.getText();
                str = Down(str);
                textview_h.setText(str);
            }
        });
        button_mu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = (String) textview_m.getText();
                str = Up(str);
                textview_m.setText(str);
            }
        });
        button_md.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = (String) textview_m.getText();
                str = Down(str);
                textview_m.setText(str);
            }
        });
        button_su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = (String) textview_s.getText();
                str = Up(str);
                textview_s.setText(str);
            }
        });
        button_sd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = (String) textview_s.getText();
                str = Down(str);
                textview_s.setText(str);
            }
        });
        button_hu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    button_hu.setBackgroundResource(R.drawable.settingup3);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    button_hu.setBackgroundResource(R.drawable.settingup);
                }
                return false;
            }
        });
        button_hd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    button_hd.setBackgroundResource(R.drawable.settingdown3);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    button_hd.setBackgroundResource(R.drawable.settingdown);
                }
                return false;
            }
        });
        button_mu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    button_mu.setBackgroundResource(R.drawable.settingup3);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    button_mu.setBackgroundResource(R.drawable.settingup);
                }
                return false;
            }
        });
        button_md.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    button_md.setBackgroundResource(R.drawable.settingdown3);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    button_md.setBackgroundResource(R.drawable.settingdown);
                }
                return false;
            }
        });
        button_su.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    button_su.setBackgroundResource(R.drawable.settingup3);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    button_su.setBackgroundResource(R.drawable.settingup);
                }
                return false;
            }
        });
        button_sd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    button_sd.setBackgroundResource(R.drawable.settingdown3);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    button_sd.setBackgroundResource(R.drawable.settingdown);
                }
                return false;
            }
        });
        findViewById(R.id.imageButton_C).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mySharedPreferences = getSharedPreferences("test",
                        Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                editor.putString("tag", "1");
                editor.commit();
                Intent i=new Intent(Setting.this,History.class);
                startActivity(i);
                finish();
            }
        });
        findViewById(R.id.imageButton_U).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mySharedPreferences = getSharedPreferences("test",
                        Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                editor.putString("userName", "");
                editor.putString("password", "");
                editor.commit();

                // 注销操作
                finish();
            }
        });
    }
    @Override
    protected void onStop(){
        super.onStop();
        TextView textview_h = (TextView) this.findViewById(R.id.textViewhour);
        TextView textview_m = (TextView) this.findViewById(R.id.textViewminute);
        TextView textview_s = (TextView) this.findViewById(R.id.textViewsecond);
        SharedPreferences mySharedPreferences = getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("hour",textview_h.getText().toString().substring(1,3));
        editor.putString("minute",textview_m.getText().toString().substring(1,3));
        editor.putString("second",textview_s.getText().toString().substring(1,3));
        editor.putString("userName", "");
        editor.putString("password", "");
        editor.commit();
        // 注销操作
        finish();
    }
    public static String Up(String str) {
        switch (str.charAt(4)) {
            case 'H':
                int timeH = Integer.parseInt(str.substring(1, 3));
                if (timeH == 23) timeH = 0;
                else timeH++;
                if (timeH < 10)
                    str =" 0" + String.valueOf(timeH) + str.substring(3);
                else
                    str = ' ' + String.valueOf(timeH)  + str.substring(3);
                break;
            case 'M':
                int timeM = Integer.parseInt(str.substring(1, 3));
                if (timeM == 59) timeM = 0;
                else timeM++;
                if (timeM < 10)
                    str = " 0" +  String.valueOf(timeM) + str.substring(3);
                else
                    str = ' ' + String.valueOf(timeM)+ str.substring(3);
                break;
            case 'S':
                int timeS = Integer.parseInt(str.substring(1, 3));
                if (timeS == 59) timeS = 0;
                else timeS++;
                if (timeS < 10)
                    str =" 0" + String.valueOf(timeS)+ str.substring(3);
                else
                    str = ' ' +String.valueOf(timeS) + str.substring(3);
                break;
            default:
                str = null;
                break;
        }
        return str;
    }

    public static String Down(String str) {
        switch (str.charAt(4)) {
            case 'H':
                int timeH = Integer.parseInt(str.substring(1, 3));
                if (timeH == 0) timeH = 23;
                else timeH--;
                if (timeH < 10)
                    str = " 0"  + String.valueOf(timeH) + str.substring(3);
                else
                    str =' ' +String.valueOf(timeH) + str.substring(3);
                break;
            case 'M':
                int timeM = Integer.parseInt(str.substring(1, 3));
                if (timeM == 0) timeM = 59;
                else timeM--;
                if (timeM < 10)
                    str = " 0" +String.valueOf(timeM) + str.substring(3);
                else
                    str = ' ' + String.valueOf(timeM) + str.substring(3);
                break;
            case 'S':
                int timeS = Integer.parseInt(str.substring(1, 3));
                if (timeS == 0) timeS = 59;
                else timeS--;
                if (timeS < 10)
                    str = " 0" + String.valueOf(timeS) + str.substring(3);
                else
                    str = ' ' + String.valueOf(timeS) + str.substring(3);
                break;
            default:
                str = null;
                break;
        }
        return str;
    }
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("img", R.drawable.image00);
        map.put("info","格论者");
        list.add(map);

//        map = new HashMap<String, Object>();
//        map.put("img", R.drawable.image01);
//        map.put("info","格论者");
//        list.add(map);
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.image02);
        map.put("info","格论者");
        list.add(map);

//        map = new HashMap<String, Object>();
//        map.put("img", R.drawable.image04);
//        map.put("info","格论者");
//        list.add(map);
//        map = new HashMap<String, Object>();
//        map.put("img", R.drawable.image05);
//        map.put("info","格论者");
//        list.add(map);
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.image06);
        map.put("info","格论者");
        list.add(map);
//        map = new HashMap<String, Object>();
//        map.put("img", R.drawable.image07);
//        map.put("info","格论者");
//        list.add(map);
//        map = new HashMap<String, Object>();
//        map.put("img", R.drawable.image08);
//        map.put("info","格论者");
//        list.add(map);
//        map = new HashMap<String, Object>();
//        map.put("img", R.drawable.image09);
//        map.put("info","格论者");
//        list.add(map);
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.image10);
        map.put("info","格论者");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.image11);
        map.put("info","格论者");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.image12);
        map.put("info","格论者");
        list.add(map);
//        map = new HashMap<String, Object>();
//        map.put("img", R.drawable.image13);
//        map.put("info","格论者");
//        list.add(map);
//        map = new HashMap<String, Object>();
//        map.put("img", R.drawable.image14);
//        map.put("info","格论者");
//        list.add(map);
//        map = new HashMap<String, Object>();
//        map.put("img", R.drawable.image15);
//        map.put("info","格论者");
//        list.add(map);
//        map = new HashMap<String, Object>();
//        map.put("img", R.drawable.image16);
//        map.put("info","格论者");
//        list.add(map);
//        map = new HashMap<String, Object>();
//        map.put("img", R.drawable.image17);
//        map.put("info","格论者");
//        list.add(map);
//        map = new HashMap<String, Object>();
//        map.put("img", R.drawable.image18);
//        map.put("info","格论者");
//        list.add(map);
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.image19);
        map.put("info","格论者");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.image20);
        map.put("info","格论者");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.image03);
        map.put("info","格论者");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.image21);
        map.put("info","格论者");
        list.add(map);
        return list;
    }
}
