package com.lijn.notificationfilter.back;

import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.lijn.notificationfilter.back.entity.Program;
import com.lijn.notificationfilter.back.manager.InServiceManager;

public class NotificationListener extends NotificationListenerService
{
    @Override
    public void onNotificationPosted(StatusBarNotification sbn)
    {
        String packageName = sbn.getPackageName();
        String notificationKey = sbn.getKey();

        Notification notification = sbn.getNotification();
        Program program = new Program(packageName);
        if (InServiceManager.getInstance().doFilter(program, notification))
        {
            cancelNotification(notificationKey);
        }
    }
}
