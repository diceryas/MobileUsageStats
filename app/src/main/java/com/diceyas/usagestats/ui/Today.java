package com.diceyas.usagestats.ui;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.diceyas.usagestats.ContextUtil;
import com.diceyas.usagestats.R;
import com.diceyas.usagestats.db.LocalDataBase;
import com.diceyas.usagestats.db.MyDataBaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Today extends Activity {
    private boolean iswrite = false;
    private int a1,a2,a3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_today);
        ListView listview = (ListView)findViewById(R.id.t_lv);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 3; i++) {
            Map<String, Object> keyValuePair = new HashMap<String, Object>();
            keyValuePair.put("num", i + 1 );
            keyValuePair.put("user",R.drawable.user);
            keyValuePair.put("name","应用");
            keyValuePair.put("time",30 + 10 * (3 - i));
            keyValuePair.put("color",i + 1);
            list.add(keyValuePair);
        }

        MyAdapter adapter = new MyAdapter(this, list,
                R.layout.todaylist);
        listview.setAdapter(adapter);
    }

}
