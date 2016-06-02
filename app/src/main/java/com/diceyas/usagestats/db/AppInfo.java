package com.diceyas.usagestats.db;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by youansheng on 2016/5/28.
 */
public class AppInfo {
    static Context context;
    static PackageManager pm;

    public AppInfo(Context context) {
        this.context = context;
        pm = context.getPackageManager();
    }


    public Drawable getAppIcon(String packname) {
        try {
            //Log.d("test","get");
            ApplicationInfo info = pm.getApplicationInfo(packname, 0);
            Log.d("test","get");
            return info.loadIcon(pm);

        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return null;
    }

    public String getAppName(String packname) {
        try {
            ApplicationInfo info = pm.getApplicationInfo(packname, 0);
            return info.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return null;
    }
}