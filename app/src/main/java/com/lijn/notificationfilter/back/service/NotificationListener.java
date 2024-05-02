package com.lijn.notificationfilter.back.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import com.lijn.notificationfilter.back.entity.Program;
import com.lijn.notificationfilter.back.entity.programsetting.NotificationType;
import com.lijn.notificationfilter.back.manager.filterservice.IDoFilter;
import com.lijn.notificationfilter.back.manager.filterservice.InServiceManager;
import com.lijn.notificationfilter.back.manager.filterservice.LogProxyFactory;
import android.os.Process;

import java.util.List;

public class NotificationListener extends NotificationListenerService
{
    private static final String TAG = "NotificationListener";


    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onListenerConnected()
    {
        super.onListenerConnected();
        Log.d(TAG, "onListenerConnected: ");
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn)
    {
        Log.d(TAG, "onNotificationPosted: ");

        String packageName = sbn.getPackageName();
        String notificationKey = sbn.getKey();

        Notification notification = sbn.getNotification();
        Program program = new Program(packageName);
        IDoFilter doFilterManager = LogProxyFactory
                .getLogProxy(InServiceManager.getInstance());
        NotificationType type = doFilterManager.doFilter(program, notification);
        if (type == NotificationType.INTERCEPTED)
        {
            this.cancelNotification(notificationKey);
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
