package com.diceyas.usagestats.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.json.JSONArray;

/**
 * Created by lenovo on 2016/5/21.
 */
public class MyRect extends SurfaceView implements SurfaceHolder.Callback {

    private Paint paint = null;
    private SurfaceHolder holder;
    private Canvas canvas;
    private int c;
    private float time;
    JSONArray array;
    public MyRect(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.RED);
        holder = getHolder();
        holder.addCallback(this);
        this.setZOrderOnTop(true);
        holder.setFormat(PixelFormat.TRANSLUCENT);
    }

    public MyRect(Context context, AttributeSet attrs) {
        super(context , attrs);
        paint = new Paint();
        paint.setColor(Color.RED);
        holder = getHolder();
        holder.addCallback(this);
        this.setZOrderOnTop(true);
        holder.setFormat(PixelFormat.TRANSLUCENT);
    }

    public void change(float num,int color)
    {
        c = getcolor(color);
        paint.setColor(c);
        time = 5.5f * (float)num;
    }
    private void draw()
    {
        canvas = holder.lockCanvas();
        canvas.drawColor(0x00FFFFFF);
        canvas.drawRect(1,1,time,50,paint);
        holder.unlockCanvasAndPost(canvas);
    }
    public int getcolor(int color)
    {
        switch (color)
        {
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.YELLOW;
        }
        return Color.BLACK;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        System.out.println("start1");
        draw();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

}
