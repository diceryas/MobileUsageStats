package com.diceyas.usagestats.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.diceyas.usagestats.service.NameRecordingService;
import com.diceyas.usagestats.ui.MainActivity;


/**
 * Created by youansheng on 2016/5/29.
 */
public class BootCompleteReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent)
    {
        Intent mainActivityIntent = new Intent(context, MainActivity.class);  // 要启动的Activity
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mainActivityIntent);
        //Log.i("TAG", "广播被接收了");
        Intent serviceIntent=new Intent(context,NameRecordingService.class);
        serviceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(serviceIntent);

    }
}
