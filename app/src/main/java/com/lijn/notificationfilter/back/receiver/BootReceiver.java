package com.lijn.notificationfilter.back.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver
{
    private static final String ACTION_BOOT_COMPLETED =
            "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        /*
        if (intent.getAction().equals(ACTION_BOOT_COMPLETED) &&
                ProgramSettingManager.getInstance().
                        getProgramSetting().getAutoStartWhenBoot())
        {
            Intent mainIntent = new Intent(context, MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainIntent);
        }

         */
    }
}
