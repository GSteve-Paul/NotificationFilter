package com.lijn.notificationfilter.back.service;

import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.lijn.notificationfilter.back.entity.Program;
import com.lijn.notificationfilter.back.entity.programsetting.NotificationType;
import com.lijn.notificationfilter.back.manager.filterservice.InServiceManager;

import java.io.IOException;

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
        NotificationType type = null;
        try
        {
            type = InServiceManager.getInstance().doFilterProxy(program, notification);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Log.i(TAG, "onNotificationPosted: " + type);
        if (type == NotificationType.INTERCEPTED)
        {
            this.cancelNotification(notificationKey);
        }
    }

    @Override
    public void onListenerDisconnected()
    {
        super.onListenerDisconnected();
        Log.d(TAG, "onListenerDisconnected: ");
    }

    @Override
    public void onDestroy()
    {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
