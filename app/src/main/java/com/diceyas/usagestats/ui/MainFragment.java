package com.diceyas.usagestats.ui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.diceyas.usagestats.ContextUtil;
import com.diceyas.usagestats.R;
import com.diceyas.usagestats.db.AppInfo;
import com.diceyas.usagestats.db.AppUseTime;
import com.diceyas.usagestats.db.LocalDataBase;
import com.diceyas.usagestats.db.MyDataBaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_today, container, false);
        ListView listview = (ListView)rootView.findViewById(R.id.t_lv);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        MyDataBaseHelper dbHelper = new MyDataBaseHelper(ContextUtil.getInstance(), "lianji.db",null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<AppUseTime> arrayList = LocalDataBase.getAppList(db);
        AppInfo appInfo = new AppInfo(ContextUtil.getInstance());
        int i = 0;
        for(AppUseTime app : arrayList)
        {
            if(appInfo.getAppName(app.pkgName) == null) continue;
            int appTime = LocalDataBase.select(db,app.pkgName);
            int totalTime = LocalDataBase.getSum(db);
            Map<String, Object> keyValuePair = new HashMap<String, Object>();
            keyValuePair.put("num", ++i );
            keyValuePair.put("user",appInfo.getAppIcon(app.pkgName));
            keyValuePair.put("name",appInfo.getAppName(app.pkgName));

            keyValuePair.put("time",appTime * 1.0/(totalTime * 1.0) * 100);
            keyValuePair.put("color",i + 1);
            list.add(keyValuePair);
        }
        /*for (int i = 0; i < 3; i++) {
            Map<String, Object> keyValuePair = new HashMap<String, Object>();
            keyValuePair.put("num", i + 1 );
            keyValuePair.put("user",R.drawable.user);
            keyValuePair.put("name","应用");
            keyValuePair.put("time",30 + 10 * (3 - i));
            keyValuePair.put("color",i + 1);
            list.add(keyValuePair);
        }*/

        MyAdapter adapter = new MyAdapter(getActivity(), list,R.layout.todaylist);
        listview.setAdapter(adapter);


        int totalTime = LocalDataBase.getSum(db);
        int loc1 = (totalTime / 3600) / 10;
        int loc2 = (totalTime / 3600) % 10;
        int loc3 = ((totalTime % 3600) / 60) / 10;
        int loc4 = ((totalTime % 3600) / 60) % 10;
        int loc5 = (totalTime % 60) / 10;
        int loc6 = (totalTime % 60) % 10;

        ((ImageView)rootView.findViewById(R.id.time1)).setImageResource(setImage(loc1));
        ((ImageView)rootView.findViewById(R.id.time2)).setImageResource(setImage(loc2));
        ((ImageView)rootView.findViewById(R.id.time4)).setImageResource(setImage(loc3));
        ((ImageView)rootView.findViewById(R.id.time5)).setImageResource(setImage(loc4));
        ((ImageView)rootView.findViewById(R.id.time7)).setImageResource(setImage(loc5));
        ((ImageView)rootView.findViewById(R.id.time8)).setImageResource(setImage(loc6));
        return rootView;
    }

    private int setImage(int loc)
    {
        // System.out.println("loc is " +loc);
        switch (loc)
        {
            case 0:
                return R.drawable.num0;
            case 1:
                return R.drawable.num1;
            case 2:
                return R.drawable.num2;
            case 3:
                return R.drawable.num3;
            case 4:
                return R.drawable.num4;
            case 5:
                return R.drawable.num5;
            case 6:
                return R.drawable.num6;
            case 7:
                return R.drawable.num7;
            case 8:
                return R.drawable.num8;
            case 9:
                return R.drawable.num9;
            default:
                return R.drawable.num0;

        }
    }
}
