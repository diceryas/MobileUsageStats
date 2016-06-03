package com.diceyas.usagestats.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.diceyas.usagestats.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/5/19.
 */
public class MyAdapter extends SimpleAdapter {
    private int mResource;
    private List<? extends Map<String, ?>> mData;
    Context con;

    public MyAdapter(Context context,
                     List<? extends Map<String, ?>> data, int resource) {
        super(context, data, resource, null, null);
        this.mResource = resource;
        this.mData = data;
        con = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup group) {
        System.out.println(position);
        LayoutInflater layoutInflater = LayoutInflater.from(con);
        View view = layoutInflater.inflate(mResource, null);
        TextView text = (TextView) view.findViewById(R.id.tl_tv1);
        text.setText(mData.get(position).get("num").toString());
        ImageView image = (ImageView)view.findViewById(R.id.tl_iv);
        image.setImageDrawable((Drawable) mData.get(position).get("user"));
        //image.setImageResource(Integer.parseInt(mData.get(position).get("user").toString()));
        TextView text1 = (TextView) view.findViewById(R.id.tl_tv2);
        text1.setText(mData.get(position).get("name").toString());
        MyRect mr = (MyRect)view.findViewById(R.id.tl_mr);
        float time = Float.parseFloat(mData.get(position).get("time").toString());
        int color = Integer.parseInt(mData.get(position).get("color").toString());
        mr.change(time,color);
        TextView text2 = (TextView)view.findViewById(R.id.tl_tv3);
        text2.setTextColor(mr.getcolor(color));
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        text2.setText(nf.format(time) + "%");
        return view;
    }
}