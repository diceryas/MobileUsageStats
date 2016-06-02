package com.diceyas.usagestats.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyView extends SurfaceView implements Callback {

	private Paint paint = null;
	private SurfaceHolder holder;
	private TimeCount clock;
	private Canvas canvas;
	private int num;
	private int y;
	private int b1[];
	JSONArray array;
	public MyView(Context context) {
		super(context);
		b1 = new int[9];
		paint = new Paint();
		paint.setColor(Color.RED);
		holder = getHolder();
		holder.addCallback(this);
		clock=new TimeCount(5000,20);
		this.setZOrderOnTop(true);
		y = 0;
		holder.setFormat(PixelFormat.TRANSLUCENT);
	}

	public MyView(Context context, AttributeSet attrs) {
		super(context , attrs);
		b1 = new int[9];
		paint = new Paint();
		paint.setColor(Color.RED);
		holder = getHolder();
		holder.addCallback(this);
		clock=new TimeCount(30000,10);
		this.setZOrderOnTop(true);
		y = 0;
		holder.setFormat(PixelFormat.TRANSLUCENT);
	}

	private void init()
	{
		this.setZOrderOnTop(true);
		holder.setFormat(PixelFormat.TRANSLUCENT);
		int a[] = new int[9];
		a[0] = 20;a[1] = 0;a[2] = 10;a[3] = 90;a[4] = 40;a[5] = 80;a[6] = 20;a[7] = 100;a[8] = 80;
		try {
			array = new JSONArray();
			for(int i = 0;i < 8;++i)
			{
				double d = 128 + 90 * i;
				double b = 490 - 4.5 * a[i];
				b1[i] = (int)b;
				double c = 490 - 4.5 * a[i + 1];
				b1[i+1] = (int)c;
				double e = (c - b)/20;
				for(int j = 0;j < 20;++j)
				{
					JSONObject object = new JSONObject();
					object.put("y",((int)b + j * e));
					object.put("x",((int)d + 4.2 * j));
					array.put(object);
				}
			}
			JSONObject object = new JSONObject();
			object.put("y",130);
			object.put("x",848);
			array.put(object);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		num = 0;
	}

	private void drawback(Canvas canvas)
	{
		paint.setTextSize(36);
		paint.setColor(Color.BLACK);
		canvas.drawCircle(128,535,8,paint);
		canvas.drawCircle(218,535,8,paint);
		canvas.drawCircle(308,535,8,paint);
		canvas.drawCircle(398,535,8,paint);
		canvas.drawCircle(488,535,8,paint);
		canvas.drawCircle(578,535,8,paint);
		canvas.drawCircle(668,535,8,paint);
		canvas.drawCircle(758,535,8,paint);
		canvas.drawCircle(848,535,8,paint);

		canvas.drawText("0",120,590,paint);
		canvas.drawText("3",210,590,paint);
		canvas.drawText("6",300,590,paint);
		canvas.drawText("9",390,590,paint);
		canvas.drawText("12",472,590,paint);
		canvas.drawText("15",562,590,paint);
		canvas.drawText("18",652,590,paint);
		canvas.drawText("21",742,590,paint);
		canvas.drawText("24",832,590,paint);

		canvas.drawText("100%",10,35,paint);
		canvas.drawText("80%",24,125,paint);
		canvas.drawText("60%",24,215,paint);
		canvas.drawText("40%",24,305,paint);
		canvas.drawText("20%",24,395,paint);
		canvas.drawText("0%",38,485,paint);
		for(int i = 40;i < 500;i += 90)
		{
			for(int j = 110;j < 880; j += 30)
			{
				canvas.drawLine(j,i,j + 18,i,paint);
			}
		}
		//canvas.drawLine(10,620,900,620,paint);
		paint.setColor(Color.RED);

	}
	private void drawline(int x,int h)
	{
		paint.setStrokeWidth(3.0f);
		for(int i = h;i < 535;i+=30)
		{
			canvas.drawLine(x,i,x,i+18,paint);
		}
		paint.setStrokeWidth(8.0f);
	}
	private void drawpoint(int n,int h){
		canvas.drawCircle(n-4,h-4,12,paint);
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
							   int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		init();

		clock.start();
		System.out.println("start");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		clock.cancel();

	}
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔

		}
		public void onFinish() {//计时完毕时触发

		}
		public void onTick(long millisUntilFinished){//计时过程显示

			canvas = holder.lockCanvas();
			canvas.drawColor(0x00FFFFFF);
			paint.setStrokeWidth(8.0f);
			try {
				if(num < 160)
				{
					drawback(canvas);
					//drawback(canvas);
				}
				JSONObject object0 = array.getJSONObject(num);
				JSONObject object1 = array.getJSONObject(num + 1);
				JSONObject object2 = array.getJSONObject(num + 2);
				JSONObject object3 = array.getJSONObject(num + 3);
				if(object0.getInt("x") >= 128){drawline(128,b1[0]);drawpoint(128,b1[0]);}
				if(object0.getInt("x") >= 218){drawline(218,b1[1]);drawpoint(218,b1[1]);}
				if(object0.getInt("x") >= 308){drawline(308,b1[2]);drawpoint(308,b1[2]);}
				if(object0.getInt("x") >= 398){drawline(398,b1[3]);drawpoint(398,b1[3]);}
				if(object0.getInt("x") >= 488){drawline(488,b1[4]);drawpoint(488,b1[4]);}
				if(object0.getInt("x") >= 578){drawline(578,b1[5]);drawpoint(578,b1[5]);}
				if(object0.getInt("x") >= 668){drawline(668,b1[6]);drawpoint(668,b1[6]);}
				if(object0.getInt("x") >= 758){drawline(758,b1[7]);drawpoint(758,b1[7]);}
				if(object0.getInt("x") >= 820){drawline(848,b1[8]);drawpoint(848,b1[8]);}

				canvas.drawLine(object0.getInt("x"),object0.getInt("y"),object1.getInt("x"),object1.getInt("y"), paint);
				canvas.drawLine(object1.getInt("x"),object1.getInt("y"),object2.getInt("x"),object2.getInt("y"), paint);
				canvas.drawLine(object2.getInt("x"),object2.getInt("y"),object3.getInt("x"),object3.getInt("y"), paint);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			num ++;
			holder.unlockCanvasAndPost(canvas);
			if(num > 160)this.cancel();
		}
	}
}
