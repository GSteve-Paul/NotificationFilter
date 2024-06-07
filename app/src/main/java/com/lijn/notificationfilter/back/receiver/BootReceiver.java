package com.lijn.notificationfilter.back.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.lijn.notificationfilter.back.manager.programsettingservice.ProgramSettingManager;
import com.lijn.notificationfilter.front.MainActivity;

public class BootReceiver extends BroadcastReceiver
{
    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.i(TAG, "onReceive: " + intent.getAction());
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) &&
                ProgramSettingManager.getInstance().
                        getProgramSetting().getAutoStartWhenBoot())
        {
            Log.i(TAG, "onReceive: " + intent.getAction());
            Intent mainIntent = new Intent(context, MainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.i(TAG, "onReceive: startActivity");
            context.startActivity(mainIntent);
        }
    }
}
