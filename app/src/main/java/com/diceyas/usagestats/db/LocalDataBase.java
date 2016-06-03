package com.diceyas.usagestats.db;

/**
 * Created by youansheng on 2016/5/28.
 */
import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class LocalDataBase {

    public static boolean exist(SQLiteDatabase db, String packageName)
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String command;
        if (packageName == "LastUpdateTime") command = "select count(*) as cnt " + "from useTime "
                + " WHERE packageName = \"LastUpdateTime\"";
            else command = "select count(*) as cnt " + "from useTime "
                + " WHERE year = " + year + " and month = " + month
                + " and day = " + day + " and packageName = \"" + packageName + "\" ";
        Cursor cursor = db.rawQuery(command,null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("cnt")) >= 1;
        }
        return false;
    }

    public static void insert(SQLiteDatabase db, String packageName, int time)
	{
		ContentValues values = new ContentValues();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        values.put("year", year);
        values.put("month", month);
        values.put("day", day);
        values.put("packageName", packageName);
        values.put("time", time);
        db.insert("useTime", null, values);
	}

    public static void update(SQLiteDatabase db, String packageName, int interval)
    {
        if (!exist(db, packageName)) {
            insert(db, packageName, interval);
            return;
        }
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String command = "UPDATE useTime  " +
                            "SET time = time +" + interval +
                            " WHERE year = " + year + " and month = " + month
                            + " and day = " + day + " and packageName = \"" + packageName + "\" ";
        db.execSQL(command);
    }

    public static int getSum(SQLiteDatabase db)
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String command = "select sum(time) as sum " +
                "from useTime " +
                "WHERE year = " + year + " and month = " + month
                + " and day = " + day;
        Cursor cursor = db.rawQuery(command,null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("sum"));
        }
        return 0;
    }

    public static int getLastUpdateTime(SQLiteDatabase db)
    {
        if (!exist(db, "LastUpdateTime")) return -1;
        String command = "select time from useTime WHERE packageName = \"LastUpdateTime\"";
        Cursor cursor = db.rawQuery(command,null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("time"));
        }
        return 0;
    }

    public static void setLastUpdateTime(SQLiteDatabase db)
    {
        int time = (int)System.currentTimeMillis();
        if (!exist(db, "LastUpdateTime")) {
            ContentValues values = new ContentValues();
            values.put("packageName", "LastUpdateTime");
            values.put("time", time);
            db.insert("useTime", null, values);
            return;
        }
        String command = "UPDATE useTime  " +
                "SET time = " + time +
                " WHERE packageName = \"LastUpdateTime\"";
        db.execSQL(command);
        return;
    }

    public static double[] getUsageRatio(SQLiteDatabase db)
    {
        int totalTime = getSum(db);
        double ratio[] = new double[8];
        double percentage[] = new double[]{0.2, 0.1, 0.5, 0.95, 0.95, 0.95, 0.95, 0.75};
        Random r1 = new Random(System.currentTimeMillis() / (1000 * 60 * 60 * 24));
        double sum = 0;
        for (int i = 0; i < 8; i++) {
            if (r1.nextDouble() < percentage[i]) ratio[i] = 0; else ratio[i] = r1.nextDouble() * percentage[i];
            sum += ratio[i];
        }
        int useTime[] = new int[8];
        int flowTime = 0;
        for (int i = 0; i < 8; i++) {
            useTime[i] = (int)(ratio[i] / sum * totalTime);
            if (useTime[i] >= 10800) {
                flowTime += useTime[i] - 10800;
                useTime[i] = 10800;
            }
        }
        for (int i = 0; i < 8; i++) {
            if (useTime[i] < 10800 && flowTime > 0) {
                int delta = Math.min(10800 - useTime[i], flowTime);
                flowTime -= delta;
                useTime[i] += delta;
            }
        }
        for (int i = 0; i < 8; i++) ratio[i] = (double)useTime[i] / totalTime;
        return ratio;
    }



    public static int getSum(SQLiteDatabase db, int year, int month, int day)
    {
        Calendar calendar = Calendar.getInstance();
        String command = "select sum(time) as sum " +
                "from useTime " +
                "WHERE year = " + year + " and month = " + month
                + " and day = " + day;
        Cursor cursor = db.rawQuery(command,null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("sum"));
        }
        return 0;
    }

    public static ArrayList<AppUseTime> getAppList(SQLiteDatabase db)
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String command = "select packageName, time " +
                "from useTime" +
                " WHERE year = " + year + " and month = " + month
                + " and day = " + day + " order by time DESC";
        Cursor cursor = db.rawQuery(command,null);
        ArrayList<AppUseTime> arrayList = new ArrayList<AppUseTime>();
        if(cursor.moveToFirst())
        {
            do{
                AppUseTime appUseTime = new AppUseTime();
                String pkgName = cursor.getString(cursor.getColumnIndex("packageName"));
                int time = cursor.getInt(cursor.getColumnIndex("time"));
                appUseTime.pkgName = pkgName;
                appUseTime.time = time;
                arrayList.add(appUseTime);
            }while(cursor.moveToNext());
        }
        return arrayList;
    }

    public static void init(SQLiteDatabase db, Context context)
    {
        /*
        PackageManager packageManager = context.getPackageManager();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE) ;
        List<ApplicationInfo> infos = packageManager.getInstalledApplications(0);

        for (ApplicationInfo info : infos) {
           // if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0
           //         && (info.flags & ApplicationInfo.FLAG_SYSTEM) != 0) continue;

            String packageName=info.packageName;
            //Log.d("test", packageName);
            insert(db, packageName, 0);
        }*/
    }
    public static int select(SQLiteDatabase db, String packageName)
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String command = "select time as time " +
                "from useTime " +
                "WHERE year = " + year + " and month = " + month
                + " and day = " + day + " and packageName = \"" + packageName + "\"";
        Cursor cursor = db.rawQuery(command,null);
        //if (null == cursor) {
        //    return 0;
        //}
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("time"));
        }
        //int sum = cursor.getInt(cursor.getColumnIndex("time"));
        return 0;

    }
}

