package com.diceyas.usagestats.service;

/**
 * Created by Administrator on 2016/5/22 0022.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;


import com.diceyas.usagestats.db.AppUseTime;
import com.diceyas.usagestats.db.LocalDataBase;
import com.diceyas.usagestats.db.MyDataBaseHelper;
import com.diceyas.usagestats.other.AndroidAppProcess;
import com.diceyas.usagestats.other.AndroidProcesses;
import com.diceyas.usagestats.receiver.LockScreenReceiver;
import com.diceyas.usagestats.ui.SendAndReceive;

import java.util.ArrayList;
import java.util.List;


public class NameRecordingService extends Service {
    private boolean Running=false;

    private static Context context;
    public static Context getContext() {
        return context;
    }
    private static MyDataBaseHelper dbHelper;


    public static List<ActivityManager.RunningAppProcessInfo> getRunningAppProcessInfo(Context ctx) {
        Log.d("version", ""+Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT > 22) {
            List<AndroidAppProcess> runningAppProcesses = AndroidProcesses.getRunningAppProcesses();
            List<ActivityManager.RunningAppProcessInfo> appProcessInfos = new ArrayList<>();
            for (AndroidAppProcess process : runningAppProcesses) {
                ActivityManager.RunningAppProcessInfo info = new ActivityManager.RunningAppProcessInfo(
                        process.name, process.pid, null
                );
                info.uid = process.uid;

                info.importance = process.foreground ? ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND : ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
                // TODO: Get more information about the process. pkgList, importance, lru, etc.
                appProcessInfos.add(info);
            }
            return appProcessInfos;
        }
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        return am.getRunningAppProcesses();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static boolean isApplicationBroughtToBackground(final Context context){


        PackageManager packageManager = context.getPackageManager();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE) ;
        List<ApplicationInfo> infos = packageManager.getInstalledApplications(0);
       // List<ActivityManager.RunningAppProcessInfo> runnings = activityManager.getRunningAppProcesses();
        List<ActivityManager.RunningAppProcessInfo> runnings = getRunningAppProcessInfo(context);
        System.out.println(runnings.size());
        for (ApplicationInfo info : infos) {
            if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0
                    && (info.flags & ApplicationInfo.FLAG_SYSTEM) != 0) continue;
            String appName = packageManager.getApplicationLabel(info).toString();
            String packageName=info.packageName;
           // System.out.println("packagename = "+packageName);
            for (ActivityManager.RunningAppProcessInfo running: runnings) {
                //getRunningAppProcessInfo(running.processName,context);
                //System.out.println("running = "+running.importance);

                if(running.processName.equals(packageName) && running.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.d("abc", appName + "      " + packageName + "      " + running.importance);//正在运行的应用程序名
                    dbHelper = new MyDataBaseHelper(context, "lianji.db", null, 1);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    //更新对应软件的数据
                    LocalDataBase.update(db, packageName, 5);
                    //Log.d(packageName, "" + LocalDataBase.select(db, packageName));
                    //Log.d("db",""+LocalDataBase.getSum(db));
                    int lastTime = LocalDataBase.getLastUpdateTime(db);
                    Log.d("db", " last updated in " + lastTime);
                    if ((int) System.currentTimeMillis() - lastTime > 15 * 1000 || (int) System.currentTimeMillis() - lastTime < 0) {
                        LocalDataBase.setLastUpdateTime(db);
                        Log.d("db", "update");
                        SendAndReceive sar = new SendAndReceive();
                        String url = "http://139.129.47.180:8004/php/setPastData.php";
                        SharedPreferences sharedPreferences= context.getSharedPreferences("test",
                                Activity.MODE_PRIVATE);
// 使用getString方法获得value，注意第2个参数是value的默认值
                        String name =sharedPreferences.getString("userName", "");
                        String password =sharedPreferences.getString("password", "");
                        String data = "username=" + name + "&password=" + password + "&pastdata=" + LocalDataBase.getSum(db);
                        sar.postAndGetString(url,data);
                    }
                }
            }
        }
        return false;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        System.out.println("onCreate");
        super.onCreate();
        Running=false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStart");
        Running=true;

        new Thread(){
            @Override
            public void run() {
                super.run();
                context = getApplicationContext();
                System.out.println("inThread");
                if (Running) System.out.println(isApplicationBroughtToBackground(getContext()));
            }
        }.start();

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int loopTime = 5 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + loopTime;
        Intent i = new Intent(this, LockScreenReceiver.class);
        i.setAction("com.broadcast.autorestart");
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    //@Override
    public void onStop() {
        System.out.println("onStop");
        Running=false;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
        Running=false;
    }
}
