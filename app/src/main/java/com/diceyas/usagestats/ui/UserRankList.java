package com.diceyas.usagestats.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.diceyas.usagestats.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRankList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_paihang);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title2);
        ListView listview = (ListView)findViewById(R.id.pai_LV);
        SimpleAdapter adapter = new SimpleAdapter(this,getData(), R.layout.layout,
                new String[]{"num","huang","name", "img" ,"time"},
                new int[]{R.id.list_tv1,R.id.list_iv2,R.id.list_tv2, R.id.list_iv,R.id.list_tv3});
        listview.setAdapter(adapter);
        findViewById(R.id.button_title_l).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                Intent i = new Intent(UserRankList.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        }
        );
    }

    private List<Map<String, Object>> getData() {
        exchange ee=new exchange();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map ;

        SharedPreferences sharedPreferences= getSharedPreferences("test",
                Activity.MODE_PRIVATE);
// 使用getString方法获得value，注意第2个参数是value的默认值
        String name =sharedPreferences.getString("userName", "");
        String password =sharedPreferences.getString("password", "");
//使用toast信息提示框显示信息
        String url = "http://139.129.47.180:8004/php/queryRank.php";
        String data = "username=" + name + "&password=" + password;
        SendAndReceive sar = new SendAndReceive();
        String receive = sar.postAndGetString(url, data);
        try {
            JSONObject jsonObject2 =new JSONObject(receive);
            for(int i=0;i<=10;i++){
                String str=jsonObject2.getString(String.valueOf(i));
                JSONObject jsonObject3 =new JSONObject(str);
                if(i==0){
                    TextView textView=(TextView)findViewById(R.id.pai_tv4);
                   // textView.setText(jsonObject3.getString(""));
                    textView.setText(jsonObject3.getString("rank"));
                    ImageView imageView=(ImageView)findViewById(R.id.pai_iv1);
                    imageView.setImageResource(ee.getimage(jsonObject3.getInt("imgid")));
                    textView=(TextView)findViewById(R.id.pai_tv6);
                    textView.setText(jsonObject3.getString("phonetime"));
                    textView=(TextView)findViewById(R.id.pai_tv5);
                    textView.setText(jsonObject3.getString("username"));
                    continue;
                }
                map = new HashMap<String, Object>();
                if(i==1)
                    map.put("huang",R.drawable.huangzuan);
                else
                    map.put("huang",null);
                map.put("num", jsonObject3.getString("rank"));
                map.put("name", jsonObject3.getString("username"));
                map.put("img", ee.getimage(jsonObject3.getInt("imgid")));
                map.put("time",jsonObject3.getString("phonetime"));
                list.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("num","1");
        map.put("huang",R.drawable.huangzuan);
        map.put("name", "一脸懵比");
        map.put("img", R.drawable.user);
        map.put("time","24h 0min 0s");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("num","2");
        map.put("name", "两脸懵比");
        map.put("img", R.drawable.user);
        map.put("time","23h 59min 59s");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("num","3");
        map.put("name", "三脸懵比");
        map.put("img", R.drawable.user);
        map.put("time","23h 58min 58s");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("num","4");
        map.put("name", "四脸懵比");
        map.put("img", R.drawable.user);
        map.put("time","23h 57min 57s");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("num","5");
        map.put("name", "五脸懵比");
        map.put("img", R.drawable.user);
        map.put("time","23h 56min 56s");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("num","6");
        map.put("name", "六脸懵比");
        map.put("img", R.drawable.user);
        map.put("time","22h 0min 0s");
        list.add(map);
        */
        return list;
    }
}
