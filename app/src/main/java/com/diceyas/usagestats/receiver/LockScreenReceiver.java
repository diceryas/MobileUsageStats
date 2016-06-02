package com.diceyas.usagestats.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.diceyas.usagestats.service.NameRecordingService;


public class LockScreenReceiver extends BroadcastReceiver {
    private Intent serviceIntent;
    public LockScreenReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        if (intent.getAction().equals("com.broadcast.boot")
                || intent.getAction().equals("com.broadcast.autorestart")) {
            System.out.println("MyBoot.");
            serviceIntent=new Intent(context,NameRecordingService.class);
            context.startService(serviceIntent);
            System.out.println("Service start.");
        }
        //if (intent.getAction().equals("android.intent.Action_USER_PRESENT")) {
        //    System.out.println("Type 2.");
        //    context.startService(serviceIntent);
        //    System.out.println("Service start.");
        //}
    }
}
